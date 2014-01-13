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
import org.ventura.util.maestro.TipoCambioCompraVentaType;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;

@Stateless
@Local(TasainteresServiceLocal.class)
@Remote(TasainteresServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
			parameters.put("moneda", tipomoneda);
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
	public BigDecimal getTasainteresCuentacorriente(Tipomoneda tipomoneda,
			BigDecimal monto) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		try {
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_CORRIENTE_TASA_INTERES);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipotasa", tipotasa);
			parameters.put("tipomoneda", tipomoneda);
			parameters.put("monto", monto);
			
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

	public TasaCambio getTipoCambioCompraVenta(TipoCambioCompraVentaType compraVentaType, BigDecimal monto) throws Exception {
		TasaCambio result = new TasaCambio();
		//BigDecimal result = BigDecimal.ZERO;
		try {
			Tipotasa tipotasa = ProduceObjectTasainteres.getTipoCambioCompraVenta(compraVentaType);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idtipotasa", tipotasa.getIdtipotasa());
			parameters.put("monto", monto);
			
			List<Tasainteres> resultList = tasainteresDAO.findByNamedQuery(Tasainteres.FindById, parameters);
			
			if(resultList.size() >= 0){
				if(resultList.size() == 1){
					 Tasainteres tasainteres = resultList.get(0);
					 result = tasainteres.getTasa();
				} else {
					throw new Exception("Se encontró muchas tasas de cambio para los parametros especificados");
				}
			} else {
				throw new Exception("No se encontró ninguna tasa de cambio para los parametros especificados");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:"+e.getCause());
			log.error("Class:"+e.getClass());
			throw e;
		}		
		return result;
	}
}
