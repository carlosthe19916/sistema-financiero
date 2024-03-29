package org.ventura.control;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.boundary.remote.TasainteresServiceRemote;
import org.ventura.dao.impl.TasainteresDAO;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.tasas.Tasainteres;
import org.ventura.entity.tasas.Tiposervicio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.TasaCambio;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.ProduceObjectTasainteres;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;

@Stateless
@Local(TasainteresServiceLocal.class)
@Remote(TasainteresServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TasainteresServiceBean implements TasainteresServiceLocal {

	@Inject
	private Log log;
	@Inject
	private TasainteresDAO tasainteresDAO;

	@Override
	public TasaCambio getTasainteres(Tiposervicio tiposervicio, Tipotasa tipotasa, Double monto) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();		
		parameters.put("idtiposervicio", tiposervicio.getIdtiposervicio());
		parameters.put("idtipotasa", tipotasa.getIdtipotasa());
		parameters.put("monto", monto);
		
		List<Tasainteres> resultList = tasainteresDAO.findByNamedQuery(Tasainteres.FindById, parameters);
		for (Iterator<Tasainteres> iterator = resultList.iterator(); iterator.hasNext();) {
			Tasainteres tasainteres = iterator.next();		
			return tasainteres.getTasa();
		}		
		log.error("Error: tasa de interes no encontrada para el  monto enviado");
		throw new Exception("Error: Error: tasa de interes no encontrada para el  monto enviado");
	}

	@Override
	public BigDecimal getTasainteresCuentapersonal(TipotasaCuentasPersonalesType cuentasPersonalesType, BigDecimal monto) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		try {
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(cuentasPersonalesType);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idtipotasa", tipotasa.getIdtipotasa());
			parameters.put("monto", monto);
			
			List<Tasainteres> resultList = tasainteresDAO.findByNamedQuery(Tasainteres.FindById, parameters);
			
			if(resultList.size() > 0){
				if(resultList.size() == 1){
					 Tasainteres tasainteres = resultList.get(0);
					 result = tasainteres.getTasa().getValue();
				} else {
					throw new Exception("No se encontró ninguna tasa para los parametros especificados");
				}
			} else {
				throw new Exception("No se encontró ninguna tasa para los parametros especificados");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw e;
		}		
		return result;
	}

	@Override
	public BigDecimal getTea(Tipomoneda tipomoneda, Integer periodo, BigDecimal monto) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		try {
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipotasa", tipotasa);
			parameters.put("tipomoneda", tipomoneda);
			parameters.put("periodo", periodo);
			parameters.put("monto", monto);
			
			List<Tasainteres> resultList = tasainteresDAO.findByNamedQuery(Tasainteres.f_tipotasa_moneda_periodo_monto, parameters);
			
			if(resultList.size() > 0){
				if(resultList.size() == 1){
					 Tasainteres tasainteres = resultList.get(0);
					 result = tasainteres.getTasa().getValue();
				} else {
					throw new Exception("No se encontró ninguna tasa para los parametros especificados");
				}
			} else {
				throw new Exception("No se encontró ninguna tasa para los parametros especificados");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw e;
		}		
		return result;
	}

	@Override
	public BigDecimal getTrea(Tipomoneda tipomoneda, Integer periodo, BigDecimal monto)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getTasainteresCuentaahorro(Tipomoneda tipomoneda) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		try {
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_AHORRO_TASA_INTERES);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipotasa", tipotasa);
			parameters.put("tipomoneda", tipomoneda);
			
			List<Tasainteres> resultList = tasainteresDAO.findByNamedQuery(Tasainteres.f_tipotasa_moneda, parameters);
			
			if(resultList.size() > 0){
				if(resultList.size() == 1){
					 Tasainteres tasainteres = resultList.get(0);
					 result = tasainteres.getTasa().getValue();
				} else {
					throw new Exception("No se encontró ninguna tasa para los parametros especificados");
				}
			} else {
				throw new Exception("No se encontró ninguna tasa para los parametros especificados");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw e;
		}		
		return result;
	}

	@Override
	public BigDecimal getTasainteresCuentacorriente(Tipomoneda tipomoneda) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		try {
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_CORRIENTE_TASA_INTERES);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipotasa", tipotasa);
			parameters.put("tipomoneda", tipomoneda);
			
			List<Tasainteres> resultList = tasainteresDAO.findByNamedQuery(Tasainteres.f_tipotasa_moneda, parameters);
			
			if(resultList.size() > 0){
				if(resultList.size() == 1){
					 Tasainteres tasainteres = resultList.get(0);
					 result = tasainteres.getTasa().getValue();
				} else {
					throw new Exception("No se encontró ninguna tasa para los parametros especificados");
				}
			} else {
				throw new Exception("No se encontró ninguna tasa para los parametros especificados");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw e;
		}		
		return result;
	}

	@Override
	public BigDecimal getInteresGeneradoPlazofijo(BigDecimal montoApertura, Integer cantidadDias, BigDecimal tea) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		try {
			/*BigDecimal a;	
			BigDecimal potencia = new BigDecimal(cantidadDias);
			BigDecimal potenciaDivisor = new BigDecimal(360);
			potenciaDivisor.setScale(50);
			potencia.setScale(50);

			a = tea.add(BigDecimal.ONE);
			potencia = potencia.divide(potenciaDivisor, 50, RoundingMode.HALF_UP);
			
			result = BigDecimalMath.pow(a, potencia);
			result = result.subtract(BigDecimal.ONE);
			result = result.multiply(montoApertura);
			
			result = result.setScale(2, BigDecimal.ROUND_HALF_UP);*/
			Double base = tea.add(BigDecimal.ONE).doubleValue();
			Double potencia = (new Double(cantidadDias)/360);
			Double a = Math.pow(base, potencia) - 1;
			result = montoApertura.multiply(new BigDecimal(a));
			result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw e;
		}
		return result;
	}
}
