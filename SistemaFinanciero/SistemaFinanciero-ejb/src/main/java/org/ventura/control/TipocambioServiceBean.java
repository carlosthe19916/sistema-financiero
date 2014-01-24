package org.ventura.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.ventura.boundary.local.TipocambioServiceLocal;
import org.ventura.boundary.remote.TipocambioServiceRemote;
import org.ventura.dao.impl.TipocambioDAO;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.tasas.Tipocambio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.Moneda;
import org.ventura.tipodato.TasaCambio;
import org.ventura.util.logger.Log;

@Stateless
@Local(TipocambioServiceLocal.class)
@Remote(TipocambioServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TipocambioServiceBean implements TipocambioServiceLocal {

	@Inject
	private Log log;
	@Inject
	private TipocambioDAO tipocambioDAO;

	@Override
	public TasaCambio getTipoCambioCompraVenta(Tipotasa tipotasa, Moneda monto, Tipomoneda monedaRecibida, Tipomoneda monedaEntregado) throws Exception {
		TasaCambio result = new TasaCambio();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("idtipotasa", tipotasa.getIdtipotasa());
			parameters.put("monto", monto);
			parameters.put("idtipomonedarecibida", monedaRecibida.getIdtipomoneda());
			parameters.put("idtipomonedaentregado", monedaEntregado.getIdtipomoneda());
			
			List<Tipocambio> resultList = tipocambioDAO.findByNamedQuery(Tipocambio.FindById, parameters);
			
			if(resultList.size() > 0){
				if(resultList.size() == 1){
					 Tipocambio tipocambio = resultList.get(0);
					 result = tipocambio.getTipocambio();
				} else {
					throw new Exception("Se encontró muchas tasas de cambio para los parametros especificados");
				}
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
	public Tipocambio retornarObjetoTipoCambioCompraVenta(Tipotasa tipotasa, Moneda monto, Tipomoneda monedaRecibida, Tipomoneda monedaEntregado) throws Exception {
		Tipocambio result = new Tipocambio();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("idtipotasa", tipotasa.getIdtipotasa());
			parameters.put("monto", monto);
			parameters.put("idtipomonedarecibida", monedaRecibida.getIdtipomoneda());
			parameters.put("idtipomonedaentregado", monedaEntregado.getIdtipomoneda());
			
			List<Tipocambio> resultList = tipocambioDAO.findByNamedQuery(Tipocambio.FindById, parameters);
			
			if(resultList.size() > 0){
				if(resultList.size() == 1){
					 Tipocambio tipocambio = resultList.get(0);
					 result = tipocambio;
				} else {
					throw new Exception("Se encontró muchas tipos de cambio para los parametros especificados");
				}
			} else {
				throw new Exception("No se encontró ningun tipo de cambio para los parametros especificados");
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
	public void create(Tipocambio tipocambio) throws Exception {
		try {
			tipocambioDAO.create(tipocambio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}	
	}
	
	@Override
	public void update(Tipocambio tipocambio) throws Exception {
		try {
			tipocambioDAO.update(tipocambio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}
}
