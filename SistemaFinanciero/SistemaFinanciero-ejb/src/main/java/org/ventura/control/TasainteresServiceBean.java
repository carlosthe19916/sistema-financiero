package org.ventura.control;

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
import org.ventura.entity.schema.caja.TasaInteresTipoCambio;
import org.ventura.entity.tasas.Tasainteres;
import org.ventura.entity.tasas.Tiposervicio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.util.logger.Log;

@Stateless
@Local(TasainteresServiceLocal.class)
@Remote(TasainteresServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TasainteresServiceBean implements TasainteresServiceLocal {

	@Inject
	private Log log;
	@Inject
	private TasainteresDAO tasainteresDAO;
/*
	@Override
	public Double getTasainteres(TiposervicioType tiposervicioType, TipotasaPasivaType tipotasa, Double monto) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		switch (tiposervicioType) {
		case CUENTA_APORTE:
			parameters.put("tiposervicio", TiposervicioType.CUENTA_APORTE.toString());
			break;
		case CUENTA_AHORRO:
			parameters.put("tiposervicio", TiposervicioType.CUENTA_AHORRO.toString());
			break;
		case CUENTA_CORRIENTE:
			parameters.put("tiposervicio", TiposervicioType.CUENTA_CORRIENTE.toString());
			break;
		case CUENTA_PLAZO_FIJO:
			parameters.put("tiposervicio", TiposervicioType.CUENTA_PLAZO_FIJO.toString());
			break;
		default:
			log.error("Error: tiposervicioType no valido");
			throw new Exception("Error: TipotasaPasivaType not found");
		}
				
		switch (tipotasa) {
		case TICAH:
			parameters.put("tipotasa", TipotasaPasivaType.TICAH.toString());
			break;
		case TICC:
			parameters.put("tipotasa", TipotasaPasivaType.TICC.toString());
			break;
		case TICEAF:
			parameters.put("tipotasa", TipotasaPasivaType.TICEAF.toString());
			break;
		case TREA:
			parameters.put("tipotasa", TipotasaPasivaType.TREA.toString());
			break;
		case ITF:
			parameters.put("tipotasa", TipotasaPasivaType.ITF.toString());
			break;				
			
		default:
			log.error("Error: TipotasaPasivaType no valido");
			throw new Exception("Error: TipotasaPasivaType not found");
		}
			
		List<Tasainteres> resultList = tasainteresDAO.findByNamedQuery(Tasainteres.FindByAbreviatura, parameters);
		for (Iterator<Tasainteres> iterator = resultList.iterator(); iterator.hasNext();) {
			Tasainteres tasainteres = iterator.next();
			if(monto>=tasainteres.getMontominimo() && monto<=tasainteres.getMontomaximo()){
				return tasainteres.getTasa();
			}
		}
		
		log.error("Error: tasa de interes no encontrada para el  monto enviado");
		throw new Exception("Error: Error: tasa de interes no encontrada para el  monto enviado");
	}
*/

	@Override
	public TasaInteresTipoCambio getTasainteres(Tiposervicio tiposervicio, Tipotasa tipotasa, Double monto) throws Exception {
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

}
