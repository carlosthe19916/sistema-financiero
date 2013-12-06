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

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.remote.CajaServiceRemote;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.dao.impl.CajaDAO;
import org.ventura.dao.impl.DenominacionmonedaDAO;
import org.ventura.dao.impl.DetallehistorialbovedaDAO;
import org.ventura.dao.impl.DetallehistorialcajaDAO;
import org.ventura.dao.impl.HistorialbovedaDAO;
import org.ventura.dao.impl.HistorialcajaDAO;
import org.ventura.entity.GeneratedEstadocuenta.EstadocuentaType;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.Detallehistorialcaja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialboveda;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;

import com.beust.jcommander.Parameters;
import com.sun.tools.classfile.Annotation.element_value;

@Named
@Stateless
@Local(CajaServiceLocal.class)
@Remote(CajaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiveBean implements CajaServiceLocal{

	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	@EJB
	private CajaDAO cajaDAO;
	@EJB
	private HistorialcajaDAO historialcajaDAO;
	@EJB
	private DenominacionmonedaDAO denominacionmonedaDAO;
	@EJB
	private DetallehistorialcajaDAO detallehistorialcajaDAO;
	
	@Inject
	private Log log;
	
	public CajaServiveBean() {
		
	}

	@Override
	public Caja create(Caja oCaja) throws Exception {
		try {
			preCreateCaja(oCaja);
			cajaDAO.create(oCaja);
		} catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
			oCaja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			oCaja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Boveda");
		}
		return oCaja;
	}
	
	public void preCreateCaja(Caja oCaja) throws Exception{
		String denominacionCaja = oCaja.getDenominacion();
		String abreviaturaCaja = oCaja.getAbreviatura();
		
		if (denominacionCaja == null || denominacionCaja.isEmpty()
				|| denominacionCaja.trim().isEmpty()) {
			throw new Exception("Denominación de Caja Inválida");
		}
		
		if (abreviaturaCaja == null || abreviaturaCaja.isEmpty()
				|| abreviaturaCaja.trim().isEmpty()) {
			throw new Exception("Abreviatura de Caja Inválida");
		}
		
		if(oCaja.getBovedas()==null){
			throw new Exception("No se selecciono ninguna Boveda");
		}
		
		oCaja.setEstado(true);
		Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
		oCaja.setEstadoapertura(estadoapertura);
	}

	@Override
	public Caja find(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Caja oCaja) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Caja oCaja) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Caja> findByNamedQuery(String queryName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Caja> findByNamedQuery(String queryName, int resultLimit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caja> findByNamedQuery(String Boveda,
			Map<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caja> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openCaja(Caja caja) throws Exception {
		try {
			caja = cajaDAO.find(caja.getIdcaja());
			boolean resultCaja = verificarCaja(caja, EstadoAperturaType.CERRADO);
			if (resultCaja == false) {
				throw new Exception(
						"Caja Abierta, Imposible Abrirla nuevamente");
			}
			boolean resultBovedas = verificarBovedas(caja, EstadoAperturaType.ABIERTO);
			if (resultBovedas == false) {
				throw new Exception(
						"Bovedas Cerradas, Imposible Abrir la Caja");
			}
			
			Historialcaja historialCajaNew = new Historialcaja();
			Historialcaja historialCajaOld = this.getHistorialcajaLastActive(caja);
			List<Detallehistorialcaja> detallehistorialcajasNew = new ArrayList<Detallehistorialcaja>();
			historialCajaNew.setDetallehistorialcajas(detallehistorialcajasNew);
			
			
			
			
			
			if (historialCajaOld != null) {
				copyDetallehistorialOldtoNew(historialCajaOld, historialCajaNew);
			}
			
			
			
			//hacer for para cada tipo de moneda
			//esto solo es para un tipo de moneda
			List<Denominacionmoneda> denominacionmonedasAllActive = getDenominacionmonedasActive(caja.getBovedas().get(0).getTipomoneda());
			
			List<Denominacionmoneda> denominacionmonedasAllFromHistorialNew = new ArrayList<Denominacionmoneda>();
			for(Detallehistorialcaja e : detallehistorialcajasNew){
				Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
				denominacionmonedasAllFromHistorialNew.add(denominacionmoneda);
			}
			
			List<Denominacionmoneda> denominacionmoneda2 = getDiferenceWithoutDuplicates(denominacionmonedasAllActive, denominacionmonedasAllFromHistorialNew);
			for(Denominacionmoneda e : denominacionmoneda2){
				Detallehistorialcaja detallehistorialcaja = new Detallehistorialcaja();
				detallehistorialcaja.setDenominacionmoneda(e);
				detallehistorialcaja.setCantidad(0);
				
				detallehistorialcajasNew.add(detallehistorialcaja);
			}
			
			
			
			
			
			historialCajaNew.setCaja(caja);
			historialCajaNew.setFechaapertura(Calendar.getInstance().getTime());
			historialCajaNew.setHoraapertura(Calendar.getInstance().getTime());
			
			Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.CONGELADO);	
			historialCajaNew.setEstadomovimiento(estadomovimiento);
			
			historialcajaDAO.create(historialCajaNew);
			
			
		
			
			
			
			for (Detallehistorialcaja e : detallehistorialcajasNew) {
				e.setHistorialcaja(historialCajaNew);
				detallehistorialcajaDAO.create(e);
			}
			
			
			
			
			
			
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
			caja.setEstadoapertura(estadoapertura);
			cajaDAO.update(caja);

			log.info("FINISH SUCCESSFULLY: CAJA ABIERTA");

		} catch (IllegalArgumentException | NonexistentEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		} catch (Exception e) {
			caja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Abrir la Caja");
		}
	}
	
	public List<Denominacionmoneda> getDiferenceWithoutDuplicates(List<Denominacionmoneda> one, List<Denominacionmoneda> two) {
		List<Denominacionmoneda> resultList = new ArrayList<Denominacionmoneda>();
		resultList.addAll(one);
		for (Denominacionmoneda e : two) {
			if (resultList.contains(e))
				resultList.remove(e);
		}
		return resultList;
	}
	
	public List<Denominacionmoneda> getDenominacionmonedasActive(Tipomoneda tipomoneda) throws RollbackFailureException, Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtipomoneda", tipomoneda.getIdtipomoneda());
		List<Denominacionmoneda> denominacionmonedas = denominacionmonedaDAO.findByNamedQuery(Denominacionmoneda.findAllByTipoMoneda,parameters);
		return denominacionmonedas;
	}
	
	public void copyDetallehistorialOldtoNew(Historialcaja historialcajaOld, Historialcaja historialcajaNew) throws RollbackFailureException, Exception {
		if (historialcajaOld != null) {
			List<Detallehistorialcaja> detallehistorialcajasOld = historialcajaOld.getDetallehistorialcajas();
			List<Detallehistorialcaja> detallehistorialcajasNew = historialcajaNew.getDetallehistorialcajas();
			
			for (Detallehistorialcaja d : detallehistorialcajasOld){
				Denominacionmoneda denominacionmoneda = d.getDenominacionmoneda();
				Integer cantidad = d.getCantidad();
				
				Detallehistorialcaja dethistorialcajaNew = new Detallehistorialcaja();
				dethistorialcajaNew.setDenominacionmoneda(denominacionmoneda);
				dethistorialcajaNew.setCantidad(cantidad);
				
				detallehistorialcajasNew.add(dethistorialcajaNew);
			}
		}
	}
	
	@Override
	public Historialcaja getHistorialcajaLastActive(Caja caja) throws RollbackFailureException, Exception {
		Historialcaja historialcaja = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("caja", caja);
		
		List<Historialcaja> historialcajaList = historialcajaDAO.findByNamedQuery(Historialcaja.findHistorialActive, parameters);
		if (historialcajaList.size() == 0) {
			historialcaja = null;
		}
		if (historialcajaList.size() == 1) {
			historialcaja = historialcajaList.get(0);
		}
		if (historialcajaList.size() > 1) {
			throw new Exception("Existe más de un historial caja activo");
		}
		
		return historialcaja;
	}
	
	public boolean verificarBovedas(Caja caja, EstadoAperturaType estadoAperturaType) throws Exception{
		boolean result = true;
		//Estadoapertura estadoApertura2 = ProduceObject.getEstadoapertura(estadoAperturaType);
		List<Boveda> listBovedas = caja.getBovedas();
		for (Iterator iterator = listBovedas.iterator(); iterator.hasNext();) {
			Boveda boveda = (Boveda) iterator.next();
			Estadoapertura estadoapertura1 = boveda.getEstadoapertura();
			Estadoapertura estadoAperturaBoveda = ProduceObject.getEstadoapertura(estadoAperturaType);
			if (estadoapertura1.equals(estadoAperturaBoveda)) {
				log.info("Verificacion de boveda satisfactoria: Boveda " + boveda.getDenominacion() + " " + estadoapertura1);
			}else {
				result = false;
				log.info("Verificacion de boveda fallida: Boveda " + boveda.getDenominacion() + " " + estadoapertura1);
				break;
			}
		}
		if (result==true) {
			log.info("Verificacion de bovedas satisfactoria: BOVEDAS ABIERTAS");
		}else {
			log.info("Verificacion de bovedas fallida: EXISTEN BOVEDAS CERRADAS");
		}
		return result;
	}

	public boolean verificarCaja(Caja caja, EstadoAperturaType estadoAperturaType) throws Exception {
		boolean result = true;
		Estadoapertura estadoapertura1 = caja.getEstadoapertura();
		Estadoapertura estadoapertura2 = ProduceObject
				.getEstadoapertura(estadoAperturaType);
		if (estadoapertura1.equals(estadoapertura2)) {
			log.info("Verificacion de caja satisfactoria");
		} else {
			result = false;
			log.info("Verificacion de caja incorrecta");
		}
		return result;
	}
}
