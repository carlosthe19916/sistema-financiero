package org.ventura.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.remote.BovedaServiceRemote;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.dao.impl.DenominacionmonedaDAO;
import org.ventura.dao.impl.DetalleaperturacierrebovedaDAO;
import org.ventura.dao.impl.DetallehistorialbovedaDAO;
import org.ventura.dao.impl.HistorialbovedaDAO;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detalleaperturacierreboveda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialboveda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.EstadoValue;

@Named
@Stateless
@Local(BovedaServiceLocal.class)
@Remote(BovedaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaServiceBean implements BovedaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private BovedaDAO bovedaDAO;

	@EJB
	private HistorialbovedaDAO historialbovedaDAO;
	
	@EJB
	private DetallehistorialbovedaDAO detallehistorialbovedaDAO;
	
	@EJB
	private DetalleaperturacierrebovedaDAO detalleaperturacierrebovedaDAO;
	
	@EJB
	private DenominacionmonedaDAO denominacionmonedaDAO;
	

	//@EJB
	//private ViewBovedadetalleDAO viewBovedadetalleDAO;

	@Override
	public Boveda create(Boveda boveda) throws Exception {
		try {
			preCreateBoveda(boveda);
			bovedaDAO.create(boveda);
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return boveda;
	}

	public void preCreateBoveda(Boveda boveda) throws Exception {
		String denominacionBoveda = boveda.getDenominacion();
		Integer idTipomoneda = boveda.getIdtipomoneda();
		Integer idAgencia = boveda.getIdagencia();
			
		if (denominacionBoveda == null || denominacionBoveda.isEmpty() || denominacionBoveda.trim().isEmpty()) {
			throw new Exception("Denominación de Bóveda Inválida");
		}
		if (idTipomoneda == null) {
			throw new Exception("Tipo de Moneda Invalida");
		}
		if (idAgencia == null) {
			throw new Exception("Agencia Invalida");
		}

		boveda.setEstado(true);
		boveda.setSaldo(new Double(0));
		boveda.setIdestadomovimiento(EstadoValue.getEstadoMovimientoValue(EstadoMovimientoType.CERRADO));
	}

	@Override
	public Boveda find(Object id) throws Exception {
		Boveda Boveda = null;
		try {
			Boveda = bovedaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return Boveda;
	}

	@Override
	public void delete(Boveda Boveda) throws Exception {
		try {
			bovedaDAO.delete(Boveda);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public void update(Boveda Boveda) throws Exception {
		try {
			bovedaDAO.update(Boveda);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public Collection<Boveda> findByNamedQuery(String queryName)
			throws Exception {
		Collection<Boveda> collection = null;
		try {
			collection = bovedaDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public Collection<Boveda> findByNamedQuery(String queryName, int resultLimit)
			throws Exception {
		Collection<Boveda> collection = null;
		try {
			collection = bovedaDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public List<Boveda> findByNamedQuery(String Boveda,
			Map<String, Object> parameters) throws Exception {
		List<Boveda> list = null;
		try {
			list = bovedaDAO.findByNamedQuery(Boveda, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<Boveda> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Boveda> list = null;
		try {
			list = bovedaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}

	@Override
	public Historialboveda getLastHistorialboveda(Boveda boveda) throws Exception {
		Historialboveda historialboveda = this.getHistorialActive(boveda);
		return historialboveda;
	}
	
	@Override
	public List<Detalleaperturacierreboveda> getDetalleforOpenBoveda(Boveda boveda) throws Exception{	
		try {
			Historialboveda historialboveda = getHistorialActive(boveda);
			Detallehistorialboveda detallehistorialbovedafinal;
			
			Tipomoneda tipomoneda = boveda.getTipomoneda();
			List<Denominacionmoneda> denominacionmonedas = getDenominacionmonedasActive(tipomoneda);
			
			if (historialboveda == null) {
				detallehistorialbovedafinal =  new Detallehistorialboveda();
				detallehistorialbovedafinal.setDetalleaperturacierrebovedaList(new ArrayList<Detalleaperturacierreboveda>());
			} else {
				detallehistorialbovedafinal = historialboveda.getDetallehistorialbovedafinal();							
			}
			this.union(detallehistorialbovedafinal, denominacionmonedas);
			return detallehistorialbovedafinal.getDetalleaperturacierrebovedaList();			
		} catch (RollbackFailureException e) {
			boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se obtener el detalle de la boveda");
		} catch (Exception e) {
			boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se obtener el detalle de la boveda");
		}
	}

	@Override
	public void openBoveda(Boveda boveda) throws Exception {
		boolean existsOldHistorialboveda;
		try {		
			boveda = bovedaDAO.find(boveda.getIdboveda());
			
			boolean resultBoveda = verificarBoveda(boveda, EstadoMovimientoType.CERRADO);
			if(resultBoveda == false){
				throw new Exception("Boveda Abierta, Imposible Abrirla nuevamente");
			}		
			boolean resultCajas= verificarCajas(boveda,EstadoMovimientoType.CERRADO);		
			if(resultCajas == false){
				throw new Exception("Cajas de Boveda abiertas, Imposible Abrirlas nuevamente");
			}
			
			Historialboveda historialbovedaOld = this.getHistorialActive(boveda);
			Historialboveda historialbovedaNew = new Historialboveda();
					
			List<Denominacionmoneda> denominacionmonedasTotal = getDenominacionmonedasActive(boveda.getTipomoneda());
			
			existsOldHistorialboveda = (historialbovedaOld == null) ? false : true;		
			copyDetallehistorialOldtoNew(historialbovedaOld,historialbovedaNew);		
			union(historialbovedaNew.getDetallehistorialbovedainicial(), denominacionmonedasTotal);
								
			historialbovedaNew.setBoveda(boveda);
			historialbovedaNew.setFechaapertura(Calendar.getInstance().getTime());
			historialbovedaNew.setHoraapertura(Calendar.getInstance().getTime());
			historialbovedaNew.setSaldoinicial(boveda.getSaldo());
			historialbovedaNew.setEstado(true);
						
			if(existsOldHistorialboveda){
				historialbovedaNew.setEstado(true);	
				historialbovedaOld.setEstado(false);
			} else {			
				historialbovedaNew.setEstado(true);			
			}
			Integer estadoMovimientoAbierto = EstadoValue.getEstadoMovimientoValue(EstadoMovimientoType.ABIERTO_CONGELADO);
			boveda.setIdestadomovimiento(estadoMovimientoAbierto);
			
			//LISTO PARA MODIFICAR LA BASE DE DATOS
			Detallehistorialboveda detallehistorialboveda = historialbovedaNew.getDetallehistorialbovedainicial();		
			List<Detalleaperturacierreboveda> detalleaperturacierrebovedas = detallehistorialboveda.getDetalleaperturacierrebovedaList();
			
			detallehistorialbovedaDAO.create(detallehistorialboveda);
				
			for (Iterator<Detalleaperturacierreboveda> iterator = detalleaperturacierrebovedas.iterator(); iterator.hasNext();) {
				Detalleaperturacierreboveda detalleaperturacierreboveda = (Detalleaperturacierreboveda) iterator.next();
				detalleaperturacierreboveda.setDetallehistorialboveda(detallehistorialboveda);
				detalleaperturacierrebovedaDAO.create(detalleaperturacierreboveda);
			}
			
			historialbovedaNew.setDetallehistorialbovedainicial(detallehistorialboveda);
			
			if(existsOldHistorialboveda){
				historialbovedaDAO.create(historialbovedaNew);
				historialbovedaDAO.update(historialbovedaOld);
			} else {
				historialbovedaDAO.create(historialbovedaNew);				
			}
			
			bovedaDAO.update(boveda);			
			log.info("FINISH SUCCESSFULLY: BOVEDA ABIERTA");

		} catch (IllegalArgumentException | NonexistentEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		} catch (Exception e) {
			boveda.setIdboveda(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
	}
	
	public boolean verificarBoveda(Boveda boveda, EstadoMovimientoType estadoMovimientoType ) throws Exception{
		boolean result = true;		
		Estadomovimiento estadomovimiento = boveda.getEstadomovimiento();
		EstadoMovimientoType estadoMovimientoTypeBoveda = EstadoValue.getEstadoType(estadomovimiento.getIdestadomovimiento());
		if (estadoMovimientoTypeBoveda.equals(estadoMovimientoType)) {		
			log.info("Verificacion de Boveda satisfactoria");
		} else {
			result = false;
			log.info("Verificacion de Boveda incorrecta");
		}
		return result;
	}
	
	public boolean verificarCajas(Boveda boveda, EstadoMovimientoType estadoMovimientoType ) throws Exception{
		boolean result = true;		
		List<Caja> cajaList = boveda.getCajas();
		for (Iterator<Caja> iterator = cajaList.iterator(); iterator.hasNext();) {
			Caja caja = iterator.next();
			Estadomovimiento estadomovimiento = caja.getEstadomovimiento();
			EstadoMovimientoType estadoMovimientoTypeCaja = EstadoValue.getEstadoType(estadomovimiento.getIdestadomovimiento());
			if(estadoMovimientoTypeCaja != estadoMovimientoType){
				result = false;
				break;
			}		
			log.info("Caja " + caja.getDenominacion() + "en estado " + estadoMovimientoTypeCaja);
		}	
		if(result == true){
			log.info("Verificacion de Cajas satisfactoria");
		} else {
			log.info("Verificacion de Cajas incorrecta");
		}			
		return result;
	}
	
	public Historialboveda getHistorialActive(Boveda boveda) throws RollbackFailureException, Exception{
		Historialboveda historialboveda = null;	
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idboveda", boveda.getIdboveda());
		
		List<Historialboveda> historialbovedaList = historialbovedaDAO.findByNamedQuery(Historialboveda.findHistorialActive,parameters);	
		if(historialbovedaList.size() == 0){
			historialboveda = null;
		}	
		if(historialbovedaList.size() == 1){
			historialboveda = historialbovedaList.get(0);		
		} 
		if(historialbovedaList.size() > 1) {
			throw new Exception("Existe mas de un historial activo");
		}
		return historialboveda;
	}

	public List<Denominacionmoneda> getDenominacionmonedasActive(Tipomoneda tipomoneda) throws RollbackFailureException, Exception{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtipomoneda", tipomoneda.getIdtipomoneda());			
		List<Denominacionmoneda> denominacionmonedas = denominacionmonedaDAO.findByNamedQuery(Denominacionmoneda.findAllByTipoMoneda, parameters);
		return denominacionmonedas;
	}
	
	public void copyDetallehistorialOldtoNew(Historialboveda historialbovedaOld, Historialboveda historialbovedaNew) throws RollbackFailureException, Exception {		
		Detallehistorialboveda detallehistorialbovedaFinalOld;	
		Detallehistorialboveda detallehistorialbovedaInicialNew = new Detallehistorialboveda();
		Detallehistorialboveda detallehistorialbovedaInicialFinal = null;
		
		if(historialbovedaOld == null) {				
			detallehistorialbovedaInicialNew = new Detallehistorialboveda();	
			detallehistorialbovedaInicialNew.setDetalleaperturacierrebovedaList(new ArrayList<Detalleaperturacierreboveda>());
		} else {
			detallehistorialbovedaFinalOld = historialbovedaOld.getDetallehistorialbovedafinal();				
			List<Detalleaperturacierreboveda> detalleaperturacierrebovedasOld = detallehistorialbovedaFinalOld.getDetalleaperturacierrebovedaList();
			List<Detalleaperturacierreboveda> detalleaperturacierrebovedasNew = new ArrayList<Detalleaperturacierreboveda>();
			
			for (Iterator<Detalleaperturacierreboveda> iterator = detalleaperturacierrebovedasOld.iterator(); iterator.hasNext();) {
				Detalleaperturacierreboveda detalleaperturacierrebovedaOld =  iterator.next();
				Detalleaperturacierreboveda detalleaperturacierrebovedaNew = new Detalleaperturacierreboveda();
				
				Integer cantidad = detalleaperturacierrebovedaOld.getCantidad();
				Denominacionmoneda denominacionmoneda = detalleaperturacierrebovedaOld.getDenominacionmoneda();
				Double subtotal = detalleaperturacierrebovedaOld.getSubtotal();
				
				detalleaperturacierrebovedaNew.setCantidad(cantidad);
				detalleaperturacierrebovedaNew.setDenominacionmoneda(denominacionmoneda);
				detalleaperturacierrebovedaNew.setSubtotal(subtotal);
				
				detalleaperturacierrebovedasNew.add(detalleaperturacierrebovedaNew);
			}	
			detallehistorialbovedaInicialNew.setDetalleaperturacierrebovedaList(detalleaperturacierrebovedasNew);
		}
		
		historialbovedaNew.setDetallehistorialbovedainicial(detallehistorialbovedaInicialNew);
		historialbovedaNew.setDetallehistorialbovedafinal(detallehistorialbovedaInicialFinal);	
	}
	
	public void setAllDenominacionMoneda(Detallehistorialboveda detallehistorialboveda, Tipomoneda tipomoneda) throws RollbackFailureException, Exception{
		List<Denominacionmoneda> denominacionmonedaList = getDenominacionmonedasActive(tipomoneda);
		
		for (Iterator<Denominacionmoneda> iterator = denominacionmonedaList.iterator(); iterator.hasNext();) {
			Denominacionmoneda denominacionmoneda = iterator.next();
			
			Detalleaperturacierreboveda detalleaperturacierreboveda = new Detalleaperturacierreboveda();		
			detalleaperturacierreboveda.setDenominacionmoneda(denominacionmoneda);		
			detalleaperturacierreboveda.setCantidad(0);
			
			detallehistorialboveda.addDetalleaperturacierrebovedaList(detalleaperturacierreboveda);
		}
	}
	
	public void union(Detallehistorialboveda detallehistorialboveda,List<Denominacionmoneda> denominacionmonedas){
		List<Detalleaperturacierreboveda> detalleaperturacierrebovedas = detallehistorialboveda.getDetalleaperturacierrebovedaList();
		
		for (Iterator<Denominacionmoneda> iterator = denominacionmonedas.iterator(); iterator.hasNext();) {
			Denominacionmoneda denominacionmoneda = (Denominacionmoneda) iterator.next();
			if(!containsTipoMoneda(detalleaperturacierrebovedas,denominacionmoneda)){
				Detalleaperturacierreboveda detalleaperturacierreboveda2 = new Detalleaperturacierreboveda();
				detalleaperturacierreboveda2.setCantidad(0);
				detalleaperturacierreboveda2.setDenominacionmoneda(denominacionmoneda);
							
				detalleaperturacierrebovedas.add(detalleaperturacierreboveda2);
			}
		}
	}
	
	public boolean containsTipoMoneda(List<Detalleaperturacierreboveda> detalleaperturacierrebovedas, Denominacionmoneda denominacionmoneda){
		boolean result = false;
		for (Iterator<Detalleaperturacierreboveda> iterator = detalleaperturacierrebovedas.iterator(); iterator.hasNext();) {
			Detalleaperturacierreboveda detalleaperturacierreboveda = iterator.next();
			Denominacionmoneda denominacionmoneda2 = detalleaperturacierreboveda.getDenominacionmoneda();		
			if(denominacionmoneda2.equals(denominacionmoneda)){
				result = true;
				break;
			}		
		}
		return result;
	} 
	
	@Override
	public void openBovedaWithPendiente(Boveda oBoveda) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeBoveda(Boveda oBoveda) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEstadomovimientoBoveda(Estadomovimiento estadomovimiento,
			Boveda oBoveda) throws Exception {
		// TODO Auto-generated method stub

	}

}
