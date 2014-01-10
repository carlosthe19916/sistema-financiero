package org.ventura.control;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.SistemaServiceLocal;
import org.ventura.boundary.remote.SistemaServiceRemote;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.CuentabancariaTipotasaDAO;
import org.ventura.dao.impl.InteresdiarioDAO;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.CuentabancariaTipotasa;
import org.ventura.entity.schema.cuentapersonal.CuentabancariaTipotasaPK;
import org.ventura.entity.schema.cuentapersonal.Interesdiario;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.Moneda;
import org.ventura.util.helper.TasaInteres;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipoCambioCompraVentaType;
import org.ventura.util.math.BigDecimalMath;

@Named
@Stateless
@Local(SistemaServiceLocal.class)
@Remote(SistemaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SistemaServiceBean implements SistemaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentabancariaServiceLocal cuentabancariaServiceLocal;
	
	@EJB
	private InteresdiarioDAO interesdiarioDAO;
	@EJB
	private CuentabancariaTipotasaDAO cuentabancariaTipotasaDAO;
	@EJB
	private CuentabancariaDAO cuentabancariaDAO;
	
	@Override
	public void closeSistema(Date fecha) throws Exception{
		// TODO Auto-generated method stub
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.clear(Calendar.DAY_OF_WEEK);
			calendar.clear(Calendar.HOUR);
			calendar.clear(Calendar.MINUTE);
			calendar.clear(Calendar.SECOND);
			calendar.clear(Calendar.MILLISECOND);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fecha", calendar.getTime());
			List<Interesdiario> interesdiarios = interesdiarioDAO.findByNamedQuery(Interesdiario.InteresesForDate,parameters, 1);
			
			if(!interesdiarios.isEmpty()){
				throw new Exception("ya se generaron los interes para el dia señalado");
			}
			
			generarInteresCuentaAhorroAndCorriente(fecha);
			
			int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			
			if(lastDayOfMonth == currentDayOfMonth){
				generarCapitalizacionCuentaAhorroAndCorriente(fecha);
			}	
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw e;
		}
	}
	
	public void generarInteresCuentaAhorroAndCorriente(Date fecha) throws Exception{
		try{
			List<Cuentabancaria> cuentabancarias = cuentabancariaServiceLocal.findAll();
			for (Cuentabancaria cuentabancaria : cuentabancarias) {
				
				Tipotasa tipotasa = null;
				
				if(cuentabancaria.getTipocuentabancaria().equals(ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_AHORRO))){
					tipotasa = ProduceObject.getTipotasa(TipoCambioCompraVentaType.CUENTA_AHORRO_TASA_INTERES);
				}
				if(cuentabancaria.getTipocuentabancaria().equals(ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_CORRIENTE))){
					tipotasa = ProduceObject.getTipotasa(TipoCambioCompraVentaType.CUENTA_CORRIENTE_TASA_INTERES);
				}
							
				CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
				pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
				pk.setIdtipotasa(tipotasa.getIdtipotasa());
				
				CuentabancariaTipotasa cuentabancariaTipotasa = cuentabancariaTipotasaDAO.find(pk);
				if(cuentabancariaTipotasa == null){
					throw new Exception("la cuenta nro " + cuentabancaria.getNumerocuenta() + " no tiene tasa de interes asignada");
				}
						
				//calculo de interes
				Moneda capital = cuentabancaria.getSaldo();	
				TasaInteres i = cuentabancariaTipotasa.getTasainteres();
				
				//operaciones
				BigDecimal x = i.getValue().divide(new BigDecimal(100));
				x = x.add(new BigDecimal(1));
				
				BigDecimal potencia = new BigDecimal(1);
				potencia = potencia.divide(new BigDecimal(360),200,RoundingMode.HALF_EVEN);			
				
				x = BigDecimalMath.pow(x, potencia);			
				x = x.subtract(new BigDecimal(1));
				
				BigDecimal interesGanado = capital.getValue().multiply(x);
				interesGanado = interesGanado.setScale(2, RoundingMode.HALF_UP);
				
				//insertando los cambios
				Interesdiario interesdiario = new Interesdiario();
				interesdiario.setInteres(new Moneda(interesGanado));
				interesdiario.setCapital(cuentabancaria.getSaldo());
				interesdiario.setFecha(fecha);
				interesdiario.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());			
						
				interesdiarioDAO.create(interesdiario);
			}
		} catch(Exception e){
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw new EJBException(e);
		}		
	}
	
	public void generarCapitalizacionCuentaAhorroAndCorriente(Date fecha) throws Exception{	
		try{
			
			List<Cuentabancaria> cuentabancarias = cuentabancariaServiceLocal.findAll();
			
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			
			startCalendar.setTime(fecha);
			endCalendar.setTime(fecha);
			
			int lastDayOfMonth = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int firstDayOfMonth = startCalendar.getActualMinimum(Calendar.DAY_OF_MONTH);
			
			startCalendar.set(Calendar.DAY_OF_MONTH, firstDayOfMonth);
			endCalendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			
			startCalendar.clear(Calendar.HOUR_OF_DAY);
			startCalendar.clear(Calendar.HOUR);
			startCalendar.clear(Calendar.MINUTE);
			startCalendar.clear(Calendar.SECOND);
			startCalendar.clear(Calendar.MILLISECOND);
			
			endCalendar.clear(Calendar.HOUR_OF_DAY);
			endCalendar.clear(Calendar.HOUR);
			endCalendar.clear(Calendar.MINUTE);
			endCalendar.clear(Calendar.SECOND);
			endCalendar.clear(Calendar.MILLISECOND);
			
			for (Cuentabancaria cuentabancaria : cuentabancarias) {
				Moneda interesTotal = new Moneda(); 
				Moneda saldoFinal = cuentabancaria.getSaldo();
				
				Map<String, Object> parameters =  new HashMap<String, Object>();
				parameters.put("idcuentabancaria", cuentabancaria.getIdcuentabancaria());
				parameters.put("startDate", startCalendar.getTime());
				parameters.put("endDate", endCalendar.getTime());
						
				List<Interesdiario> result = interesdiarioDAO.findByNamedQuery(Interesdiario.InteresesForDateAndCuenta, parameters);
				for (Interesdiario interesdiario : result) {
					interesTotal = interesTotal.add(interesdiario.getInteres());
				}
				
				saldoFinal = saldoFinal.add(interesTotal);
				cuentabancaria.setSaldo(saldoFinal);
				cuentabancariaDAO.update(cuentabancaria);
			}
		} catch(Exception e){
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw new EJBException(e);
		}		
	}
}
