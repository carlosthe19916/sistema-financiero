package org.ventura.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.boundary.remote.CajaServiceRemote;
import org.ventura.dao.impl.BovedaCajaDAO;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.dao.impl.CajaDAO;
import org.ventura.dao.impl.CajaTransaccionesBovedaViewDAO;
import org.ventura.dao.impl.DenominacionmonedaDAO;
import org.ventura.dao.impl.DetallehistorialcajaDAO;
import org.ventura.dao.impl.HistorialcajaDAO;
import org.ventura.dao.impl.PendienteCajaDAO;
import org.ventura.dao.impl.TransaccioncuentaaporteDAO;
import org.ventura.dao.impl.TransaccioncuentabancariaDAO;
import org.ventura.dao.impl.UsuarioDAO;
import org.ventura.dao.impl.ViewPendienteCajaDAO;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.BovedaCaja;
import org.ventura.entity.schema.caja.BovedaCajaPK;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detallehistorialcaja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.PendienteCaja;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.CajaTransaccionesBovedaView;
import org.ventura.entity.schema.caja.view.PendientesView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionType;


@Named
@Stateless
@Local(CajaServiceLocal.class)
@Remote(CajaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiceBean implements CajaServiceLocal{

	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	@EJB
	private CajaDAO cajaDAO;

	@EJB
	private BovedaDAO bovedaDAO;
	@EJB
	private BovedaCajaDAO bovedaCajaDAO;
	@EJB
	private HistorialcajaDAO historialcajaDAO;
	@EJB
	private DenominacionmonedaDAO denominacionmonedaDAO;
	@EJB
	private DetallehistorialcajaDAO detallehistorialcajaDAO;
	@EJB 
	private UsuarioDAO usuarioDAO;
	
	@EJB private TransaccioncuentabancariaDAO transaccioncuentabancariaDAO;
	@EJB private TransaccioncuentaaporteDAO transaccioncuentaaporteDAO;
	@EJB private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	@EJB private PendienteCajaDAO pendienteCajaDAO;
	@EJB private ViewPendienteCajaDAO viewPendienteCajaDAO;
	
	@EJB private CajaTransaccionesBovedaViewDAO cajaTransaccionesBovedaViewDAO;
	
	@Inject
	private Log log;
	
	private Moneda totalCajaSoles;
	private Moneda totalCajaDolares;
	private Moneda totalCajaEuros;
	
	public CajaServiceBean() {
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
		
		if (denominacionCaja == null || denominacionCaja.isEmpty() || denominacionCaja.trim().isEmpty()) {
			throw new Exception("Denominación de Caja Inválida");
		}
		
		if (abreviaturaCaja == null || abreviaturaCaja.isEmpty() || abreviaturaCaja.trim().isEmpty()) {
			throw new Exception("Abreviatura de Caja Inválida");
		}
		
		if(oCaja.getBovedas()==null){
			throw new Exception("No se selecciono ninguna Boveda");
		}
		
		if(oCaja.getUsuarios()==null){
			throw new Exception("No se selecciono ningun Usuario");
		}
		
		oCaja.setEstado(true);
		Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
		oCaja.setEstadoapertura(estadoapertura);
	}

	@Override
	public Caja find(Object id) throws Exception {
		Caja caja = null;
		try {
			caja = cajaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return caja;
	}
	
	@Override
	public void inactive(Caja caja) throws Exception{
		try {
			Object id = caja.getIdcaja();
			caja = cajaDAO.find(id);
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			if(!caja.getEstadoapertura().equals(estadoapertura)){
				throw new Exception("La caja no esta Cerrada, intentelo nuevamente");
			}
			Historialcaja historialcaja = getHistorialcajaLastActive(caja);
			if (historialcaja != null) {
				Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.CONGELADO);
				historialcaja.setEstadomovimiento(estadomovimiento);
				historialcajaDAO.update(historialcaja);
			}
			caja.setEstado(false);
			caja.setEstadoapertura(estadoapertura);
			cajaDAO.update(caja);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error al desactivar Caja");
		}
	}

	@Override
	public void update(Caja oCaja) throws Exception {
		try {
			cajaDAO.update(oCaja);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		
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
			// son varias cajas hacer for
			caja = cajaDAO.find(caja.getIdcaja());
			boolean resultCaja = verificarCaja(caja, EstadoAperturaType.CERRADO);
			if (resultCaja == false) {
				throw new Exception(
						"Caja Abierta, Imposible Abrirla nuevamente");
			}
			boolean resultBovedas = verificarBovedas(caja,
					EstadoAperturaType.ABIERTO);
			if (resultBovedas == false) {
				throw new Exception("Bovedas Cerradas, Imposible Abrir la Caja");
			}

			Historialcaja historialCajaNew = new Historialcaja();
			Historialcaja historialCajaOld = this
					.getHistorialcajaLastActive(caja);
			List<Detallehistorialcaja> detallehistorialcajasNew = new ArrayList<Detallehistorialcaja>();
			historialCajaNew.setDetallehistorialcajas(detallehistorialcajasNew);

			if (historialCajaOld != null) {
				copyDetallehistorialOldtoNew(historialCajaOld, historialCajaNew);
			}

			//for para cada tipo de moneda
			for (int i = 0; i < caja.getBovedas().size(); i++) {
				List<Denominacionmoneda> denominacionmonedasAllActive = getDenominacionmonedasActive(caja
						.getBovedas().get(i).getTipomoneda());

				List<Denominacionmoneda> denominacionmonedasAllFromHistorialNew = new ArrayList<Denominacionmoneda>();
				for (Detallehistorialcaja e : detallehistorialcajasNew) {
					Denominacionmoneda denominacionmoneda = e
							.getDenominacionmoneda();
					denominacionmonedasAllFromHistorialNew
							.add(denominacionmoneda);
				}

				List<Denominacionmoneda> denominacionmoneda2 = getDiferenceWithoutDuplicates(
						denominacionmonedasAllActive,
						denominacionmonedasAllFromHistorialNew);
				for (Denominacionmoneda e : denominacionmoneda2) {
					Detallehistorialcaja detallehistorialcaja = new Detallehistorialcaja();
					detallehistorialcaja.setDenominacionmoneda(e);
					detallehistorialcaja.setCantidad(0);
					detallehistorialcajasNew.add(detallehistorialcaja);
				}
			}

			historialCajaNew.setCaja(caja);
			historialCajaNew.setFechaapertura(Calendar.getInstance().getTime());
			historialCajaNew.setHoraapertura(Calendar.getInstance().getTime());

			Estadomovimiento estadomovimiento = ProduceObject
					.getEstadomovimiento(EstadoMovimientoType.CONGELADO);
			historialCajaNew.setEstadomovimiento(estadomovimiento);

			historialcajaDAO.create(historialCajaNew);

			for (Detallehistorialcaja e : detallehistorialcajasNew) {
				e.setHistorialcaja(historialCajaNew);
				detallehistorialcajaDAO.create(e);
			}

			Estadoapertura estadoapertura = ProduceObject
					.getEstadoapertura(EstadoAperturaType.ABIERTO);
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
	
	public Historialcaja getHistorialcajaForClose(Caja caja) throws RollbackFailureException, Exception {
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
	
	@Override
	public void closeCaja(Caja caja, List<Detallehistorialcaja> detalleSoles, List<Detallehistorialcaja> detalleDolares, List<Detallehistorialcaja> detalleEuros) throws Exception {
		try {
			totalCajaSoles = new Moneda();
			totalCajaDolares = new Moneda();
			totalCajaEuros = new Moneda();
			
			caja = cajaDAO.find(caja.getIdcaja());
			
			boolean resultCaja = verificarCaja(caja, EstadoAperturaType.ABIERTO);
			if (resultCaja == false) {
				throw new Exception("Caja cerrada, Imposible cerrarla nuevamente");
			}
			
			boolean resultBovedas = verificarBovedas(caja, EstadoAperturaType.ABIERTO);
			if (resultBovedas == false) {
				throw new Exception("Bovedas Cerradas, Imposible cerrar la Caja");
			}
			
			Historialcaja historialcaja = this.getHistorialcajaLastActive(caja);
			if (historialcaja == null) {
				throw new Exception("No se puede cerrar caja, no existe HistorialCajaActivo");
			}
			historialcaja.getDetallehistorialcajas().clear();
			
			for (Detallehistorialcaja d : detalleDolares) {
				totalCajaDolares = totalCajaDolares.add(d.getSubtotal());	
				historialcaja.addDetallehistorialcaja(d);
			}
			
			for (Detallehistorialcaja s : detalleSoles) {
				totalCajaSoles = totalCajaSoles.add(s.getSubtotal());
				historialcaja.addDetallehistorialcaja(s);
			}
			
			for (Detallehistorialcaja e : detalleEuros) {
				totalCajaEuros = totalCajaEuros.add(e.getSubtotal());
				historialcaja.addDetallehistorialcaja(e);
			}
			
			if (compareSaldoTotalCajaSoles(caja).containsKey(0) && compareSaldoTotalCajaDolares(caja).containsKey(0) && compareSaldoTotalCajaEuros(caja).containsKey(0)) {
				
				historialcaja.setFechacierre(Calendar.getInstance().getTime());
				historialcaja.setHoracierre(Calendar.getInstance().getTime());
				Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.CONGELADO);
				historialcaja.setEstadomovimiento(estadomovimiento);
				
				for (Detallehistorialcaja dhc : historialcaja.getDetallehistorialcajas()) {
					detallehistorialcajaDAO.update(dhc);
				}
				historialcajaDAO.update(historialcaja);
				
				Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
				caja.setEstadoapertura(estadoapertura);
				cajaDAO.update(caja);
				
				log.info("FINISH SUCCESSFULLY: CAJA CERRADA");
			}else{
				log.info("FINISH ERROR: CAJA NO CERRADA");
			}
			
		} catch (IllegalArgumentException | NonexistentEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Cerrar la Caja");
		}
	}
	
	//comparar saldo de cierre en caja con saldo en base de datos Soles
	public Map<Integer, Moneda> compareSaldoTotalCajaSoles(Caja caja) throws Exception {
		System.out.println("Solessssssssssss");
		Map<Integer, Moneda> hashMapSoles = new HashMap<Integer, Moneda>();
		
		Tipomoneda tipoMonedaSoles = ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL);
		List<Boveda> bovedas = caja.getBovedas();
		Boveda bovedaSoles = null;
		for (Boveda boveda : bovedas) {
			Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
			if (tipoMonedaSoles.equals(tipomonedaBoveda)) {
				bovedaSoles = boveda;
				break;
			}
		}
		if (bovedaSoles != null) {
			BovedaCaja bovedaCaja;
			BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
			bovedaCajaPK.setIdboveda(bovedaSoles.getIdboveda());
			bovedaCajaPK.setIdcaja(caja.getIdcaja());
			
			bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);
			
			if (totalCajaSoles.isGreaterThan(bovedaCaja.getSaldototal())) {
				Moneda sobranteSoles = totalCajaSoles.subtract(bovedaCaja.getSaldototal());
				hashMapSoles.put(1, sobranteSoles);
			}
			if (totalCajaSoles.isLessThan(bovedaCaja.getSaldototal())) {
				Moneda faltanteSoles = bovedaCaja.getSaldototal().subtract(totalCajaSoles);
				hashMapSoles.put(-1, faltanteSoles);
			}if(totalCajaSoles.isEqual(bovedaCaja.getSaldototal())){
				Moneda soles = totalCajaSoles.subtract(bovedaCaja.getSaldototal());
				hashMapSoles.put(0, soles);
			}
		}else{
			Moneda soles = new Moneda();
			hashMapSoles.put(0, soles);
		}
		return hashMapSoles;
	}
	
	// comparar saldo de cierre en caja con saldo en base de datos Dolares
	public Map<Integer, Moneda> compareSaldoTotalCajaDolares(Caja caja) throws Exception {
		System.out.println("Dolaresssssssssss");
		
		Map<Integer, Moneda> hashMapDolares = new HashMap<Integer, Moneda>();
		
		Tipomoneda tipoMonedaDolares = ProduceObject
				.getTipomoneda(TipomonedaType.DOLAR);
		List<Boveda> bovedas = caja.getBovedas();
		Boveda bovedaDolares = null;
		for (Boveda boveda : bovedas) {
			Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
			if (tipoMonedaDolares.equals(tipomonedaBoveda)) {
				bovedaDolares = boveda;
				break;
			}
		}
		if (bovedaDolares != null) {
			BovedaCaja bovedaCaja;
			BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
			bovedaCajaPK.setIdboveda(bovedaDolares.getIdboveda());
			bovedaCajaPK.setIdcaja(caja.getIdcaja());

			bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);

			if (totalCajaDolares.isGreaterThan(bovedaCaja.getSaldototal())) {
				Moneda sobranteDolares = totalCajaDolares.subtract(bovedaCaja.getSaldototal());
				hashMapDolares.put(1, sobranteDolares);
			}
			if (totalCajaDolares.isLessThan(bovedaCaja.getSaldototal())) {
				Moneda faltanteDolares = bovedaCaja.getSaldototal().subtract(totalCajaDolares);
				hashMapDolares.put(-1, faltanteDolares);
			}
			if (totalCajaDolares.isEqual(bovedaCaja.getSaldototal())) {
				Moneda dolares = totalCajaDolares.subtract(bovedaCaja.getSaldototal());
				hashMapDolares.put(0, dolares);
			}
		} else {
			Moneda dolares = new Moneda();
			hashMapDolares.put(0, dolares);
		}
		return hashMapDolares;
	}
	
	// comparar saldo de cierre en caja con saldo en base de datos Euros
	public Map<Integer, Moneda> compareSaldoTotalCajaEuros(Caja caja) throws Exception {
		System.out.println("Eurosssssssssssss");
		Map<Integer, Moneda> hashMapEuros = new HashMap<Integer, Moneda>();
		
		Tipomoneda tipoMonedaEuros = ProduceObject
				.getTipomoneda(TipomonedaType.EURO);
		List<Boveda> bovedas = caja.getBovedas();
		Boveda bovedaEuros = null;
		for (Boveda boveda : bovedas) {
			Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
			if (tipoMonedaEuros.equals(tipomonedaBoveda)) {
				bovedaEuros = boveda;
				break;
			}
		}
		if (bovedaEuros != null) {
			BovedaCaja bovedaCaja;
			BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
			bovedaCajaPK.setIdboveda(bovedaEuros.getIdboveda());
			bovedaCajaPK.setIdcaja(caja.getIdcaja());

			bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);

			if (totalCajaEuros.isGreaterThan(bovedaCaja.getSaldototal())) {
				Moneda sobranteEuros = totalCajaEuros.subtract(bovedaCaja.getSaldototal());
				hashMapEuros.put(1, sobranteEuros);
			}
			if (totalCajaEuros.isLessThan(bovedaCaja.getSaldototal())) {
				Moneda faltanteEuros = bovedaCaja.getSaldototal().subtract(totalCajaEuros);
				hashMapEuros.put(-1, faltanteEuros);
			}
			if (totalCajaEuros.isEqual(bovedaCaja.getSaldototal())) {
				Moneda euros = totalCajaEuros.subtract(bovedaCaja.getSaldototal());
				hashMapEuros.put(0, euros);
			}
		} else {
			Moneda euros = new Moneda();
			hashMapEuros.put(0, euros);
		}
		return hashMapEuros;
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
		List<Boveda> listBovedas = caja.getBovedas();
		for (Iterator<Boveda> iterator = listBovedas.iterator(); iterator.hasNext();) {
			Boveda boveda = (Boveda) iterator.next();
			Estadoapertura estadoapertura1 = boveda.getEstadoapertura();
			Estadoapertura estadoAperturaBoveda = ProduceObject.getEstadoapertura(estadoAperturaType);
			if (estadoapertura1.equals(estadoAperturaBoveda)) {
				log.info("Verificacion de boveda satisfactoria: " + boveda.getDenominacion() + " " + estadoapertura1.getDenominacion());
			}else {
				result = false;
				log.info("Verificacion de boveda fallida: Boveda " + boveda.getDenominacion() + " " + estadoapertura1.getDenominacion());
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
		Estadoapertura estadoapertura2 = ProduceObject.getEstadoapertura(estadoAperturaType);
		if (estadoapertura1.equals(estadoapertura2)) {
			log.info("Verificacion de caja satisfactoria");
		} else {
			result = false;
			log.info("Verificacion de caja incorrecta");
		}
		return result;
	}
	
	@Override
	public List<Boveda> getBovedas(Caja oCaja) throws Exception{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idcaja", oCaja.getIdcaja());
		
		List<Boveda> bovedas = null;
		try {
			bovedas = bovedaDAO.findByNamedQuery(Boveda.ALL_FOR_CAJA,parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bovedas;
	}
	
	@Override
	public List<Usuario> getUsuariosFromCaja(Caja oCaja) throws Exception{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idcaja", oCaja.getIdcaja());
		
		List<Usuario> usuarios = null;
		try {
			usuarios = usuarioDAO.findByNamedQuery(Usuario.ALL_USER_FOR_CAJA, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuarios;
	}
	
	public List<Detallehistorialcaja> getDetalleHistorialCajaByTipoMoneda(Tipomoneda tipomoneda, Historialcaja historialcaja) throws RollbackFailureException, Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtipomoneda", tipomoneda.getIdtipomoneda());
		parameters.put("idhistorialcaja", historialcaja.getIdhistorialcaja());
		List<Detallehistorialcaja> detalleHCByTipoMoneda = detallehistorialcajaDAO.findByNamedQuery(Detallehistorialcaja.detalleHistorialCajaByTipoMoneda, parameters);
		return detalleHCByTipoMoneda;
	}
	
	public List<Detallehistorialcaja> getDetalleHistorialCajaInZero(Tipomoneda tipomoneda, Historialcaja historialcaja) throws RollbackFailureException, Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtipomoneda", tipomoneda.getIdtipomoneda());
		parameters.put("idhistorialcaja", historialcaja.getIdhistorialcaja());
		List<Detallehistorialcaja> detalleHCByTipoMoneda = detallehistorialcajaDAO.findByNamedQuery(Detallehistorialcaja.detalleHistorialCajaByTipoMoneda, parameters);
		
		List<Detallehistorialcaja> result = new ArrayList<Detallehistorialcaja>();
		for (Detallehistorialcaja e : detalleHCByTipoMoneda) {
			Detallehistorialcaja detallehistorialcaja = new Detallehistorialcaja();
			detallehistorialcaja.setCantidad(0);
			detallehistorialcaja.setDenominacionmoneda(e.getDenominacionmoneda());
			detallehistorialcaja.setHistorialcaja(e.getHistorialcaja());			
			result.add(detallehistorialcaja);
		}
		return result;
	}
	
	
	@Override
	public HashMap<Tipomoneda,List<Detallehistorialcaja>> getDetallehistorialcajaLastActive(Caja caja) throws Exception {
		try {
			Historialcaja historialcaja = getHistorialcajaLastActive(caja);
			List<Detallehistorialcaja> result = null;
			HashMap<Tipomoneda, List<Detallehistorialcaja>> colecctionDetealleHistorialCaja = new HashMap<>();
			
			for (int i = 0; i < caja.getBovedas().size(); i++) {
				Tipomoneda tipomoneda = caja.getBovedas().get(i).getTipomoneda();	
				List<Denominacionmoneda> denominacionmoedaAllActive = getDenominacionmonedasActive(tipomoneda);
				List<Denominacionmoneda> denominacionmonedasAllFromHistorial = new ArrayList<Denominacionmoneda>();
				
				if (historialcaja == null) {
					result = new ArrayList<Detallehistorialcaja>();
				}else{
					result = getDetalleHistorialCajaByTipoMoneda(tipomoneda, historialcaja);
					for (Detallehistorialcaja e : result) {
						Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
						denominacionmonedasAllFromHistorial.add(denominacionmoneda);
					}
				}
				
				List<Denominacionmoneda> denominacionmoneda2 = getDiferenceWithoutDuplicates(denominacionmoedaAllActive, denominacionmonedasAllFromHistorial);
				for (Denominacionmoneda e : denominacionmoneda2) {
					Detallehistorialcaja detallehistorialcaja = new Detallehistorialcaja();
					detallehistorialcaja.setDenominacionmoneda(e);
					detallehistorialcaja.setCantidad(0);
					
					result.add(detallehistorialcaja);
				}
				colecctionDetealleHistorialCaja.put(tipomoneda, result);
			}
			return colecctionDetealleHistorialCaja;
		} catch (RollbackFailureException e) {
			caja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se obtener el detalle de la caja");
		} catch (Exception e) {
			caja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se puede obtener el detalle de la caja");
		}
	}	
	
	
	
	@Override
	public HashMap<Tipomoneda,List<Detallehistorialcaja>> getDetallehistorialcajaInZero(Caja caja) throws Exception {
		try {
			Historialcaja historialcaja = getHistorialcajaLastActive(caja);
			List<Detallehistorialcaja> result = null;
			HashMap<Tipomoneda, List<Detallehistorialcaja>> colecctionDetalleHistorialCaja = new HashMap<>();
			
			for (int i = 0; i < caja.getBovedas().size(); i++) {
				Tipomoneda tipomoneda = caja.getBovedas().get(i).getTipomoneda();	
				List<Denominacionmoneda> denominacionmoedaAllActive = getDenominacionmonedasActive(tipomoneda);
				List<Denominacionmoneda> denominacionmonedasAllFromHistorial = new ArrayList<Denominacionmoneda>();
				
				if (historialcaja == null) {
					result = new ArrayList<Detallehistorialcaja>();
				}else{
					result = getDetalleHistorialCajaInZero(tipomoneda, historialcaja);					
					for (Detallehistorialcaja e : result) {
						Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
						denominacionmonedasAllFromHistorial.add(denominacionmoneda);
					}
				}
				
				List<Denominacionmoneda> denominacionmoneda2 = getDiferenceWithoutDuplicates(denominacionmoedaAllActive, denominacionmonedasAllFromHistorial);
				for (Denominacionmoneda e : denominacionmoneda2) {
					Detallehistorialcaja detallehistorialcaja = new Detallehistorialcaja();
					detallehistorialcaja.setDenominacionmoneda(e);
					detallehistorialcaja.setCantidad(0);
					
					result.add(detallehistorialcaja);
				}
				colecctionDetalleHistorialCaja.put(tipomoneda, result);
			}
			return colecctionDetalleHistorialCaja;
		} catch (RollbackFailureException e) {
			caja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se obtener el detalle de la caja");
		} catch (Exception e) {
			caja.setIdcaja(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se puede obtener el detalle de la caja");
		}
	}
	
	@Override
	public void freezeCaja(Caja oCaja) throws Exception {
		try {
			Caja caja = find(oCaja.getIdcaja());
			Historialcaja historialcaja = getHistorialcajaLastActive(caja);
			Estadomovimiento estadomovimientoNew = ProduceObject.getEstadomovimiento(EstadoMovimientoType.CONGELADO);
			if (historialcaja != null) {
				setEstadomovimientoHistorialcaja(historialcaja, estadomovimientoNew);
			}else {
				throw new Exception("No se puede Congelar/Decongelar caja");
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public void defrostCaja(Caja oCaja) throws Exception {
		try {
			Caja caja = find(oCaja.getIdcaja());
			Historialcaja historialcaja = getHistorialcajaLastActive(caja);
			Estadomovimiento estadomovimientoNew = ProduceObject.getEstadomovimiento(EstadoMovimientoType.DESCONGELADO);
			if (historialcaja != null) {
				setEstadomovimientoHistorialcaja(historialcaja, estadomovimientoNew);
			}else{
				throw new Exception("No se puede Congelar/Decongelar caja");
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	public void setEstadomovimientoHistorialcaja(Historialcaja historialcaja, Estadomovimiento estadomovimiento) throws Exception {
		Caja caja = historialcaja.getCaja();
		Estadoapertura estadoapertura = caja.getEstadoapertura();
		Estadoapertura estadoaperturatocheck = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
		if (!estadoapertura.equals(estadoaperturatocheck)) {
			historialcaja.setEstadomovimiento(estadomovimiento);
			historialcajaDAO.update(historialcaja);
		}else{
			throw new Exception("Caja cerrada, no se pueden Congelar/Descongelar caja");
		}
	}

	public Moneda getTotalCajaSoles() {
		return totalCajaSoles;
	}

	public void setTotalCajaSoles(Moneda totalCajaSoles) {
		this.totalCajaSoles = totalCajaSoles;
	}

	public Moneda getTotalCajaDolares() {
		return totalCajaDolares;
	}

	public void setTotalCajaDolares(Moneda totalCajaDolares) {
		this.totalCajaDolares = totalCajaDolares;
	}

	public Moneda getTotalCajaEuros() {
		return totalCajaEuros;
	}

	public void setTotalCajaEuros(Moneda totalCajaEuros) {
		this.totalCajaEuros = totalCajaEuros;
	}

	/**
	 * Transaccional*/
	@Override
	public void updateSaldo(Caja oCaja, Transaccioncuentabancaria oTransaccioncuentabancaria) throws Exception {
		try {
			int cajaPK = oCaja.getIdcaja();	
			int transaccioncuentabancariaPK = oTransaccioncuentabancaria.getIdtransaccioncuentabancaria();
			Caja caja = find(cajaPK);
			Transaccioncuentabancaria transaccioncuentabancaria = transaccioncuentabancariaDAO.find(transaccioncuentabancariaPK);
			
			Tipomoneda tipomoneda = transaccioncuentabancaria.getTipomoneda();			
			List<Boveda> bovedas = caja.getBovedas();
			Boveda bovedaTransaccion = null;
			for (Boveda boveda : bovedas) {
				Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
				if(tipomoneda.equals(tipomonedaBoveda)){
					bovedaTransaccion = boveda;
					break;
				}
			}
			if(bovedaTransaccion != null){
				BovedaCaja bovedaCaja;
				BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();				
				bovedaCajaPK.setIdboveda(bovedaTransaccion.getIdboveda());
				bovedaCajaPK.setIdcaja(caja.getIdcaja());
				
				bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);	
				
				Moneda saldoFinal = null;
				Tipotransaccion tipotransaccion = transaccioncuentabancaria.getTipotransaccion();
				TipoTransaccionType tipoTransaccionType = ProduceObject.getTipotransaccion(tipotransaccion);
				switch (tipoTransaccionType) {
				case DEPOSITO:
					if(transaccioncuentabancaria.getEstado() == true){
						saldoFinal = bovedaCaja.getSaldototal().add(transaccioncuentabancaria.getMonto());
					} else {
						saldoFinal = bovedaCaja.getSaldototal().subtract(transaccioncuentabancaria.getMonto());
					}
					break;
				case RETIRO :
					if(transaccioncuentabancaria.getEstado() == true){
						saldoFinal = bovedaCaja.getSaldototal().subtract(transaccioncuentabancaria.getMonto());
					} else {
						saldoFinal = bovedaCaja.getSaldototal().add(transaccioncuentabancaria.getMonto());
					}
					break;
				default:
					break;
				}
				if(saldoFinal.isGreaterThanOrEqual(new Moneda(0))){
					bovedaCaja.setSaldototal(saldoFinal);
					bovedaCajaDAO.update(bovedaCaja);
				} else {
					throw new Exception("La caja no tiene suficiente saldo");
				}								
			} else {
				throw new Exception("No se puede modificar el saldo de la caja");
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public void updateSaldo(Caja oCaja, Transaccioncuentaaporte oTransaccioncuentabancaria) throws Exception {
		try {
			int cajaPK = oCaja.getIdcaja();	
			int transaccioncuentaaportePK = oTransaccioncuentabancaria.getIdtransaccioncuentaaporte();
			Caja caja = find(cajaPK);
			Transaccioncuentaaporte transaccioncuentaaporte = transaccioncuentaaporteDAO.find(transaccioncuentaaportePK);
			
			Tipomoneda tipomoneda = transaccioncuentaaporte.getTipomoneda();			
			List<Boveda> bovedas = caja.getBovedas();
			Boveda bovedaTransaccion = null;
			for (Boveda boveda : bovedas) {
				Tipomoneda tipomonedaBoveda = boveda.getTipomoneda();
				if(tipomoneda.equals(tipomonedaBoveda)){
					bovedaTransaccion = boveda;
					break;
				}
			}
			if(bovedaTransaccion != null){
				BovedaCaja bovedaCaja;
				BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();				
				bovedaCajaPK.setIdboveda(bovedaTransaccion.getIdboveda());
				bovedaCajaPK.setIdcaja(caja.getIdcaja());
				
				bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);	
				
				Moneda saldoFinal = null;
				Tipotransaccion tipotransaccion = transaccioncuentaaporte.getTipotransaccion();
				TipoTransaccionType tipoTransaccionType = ProduceObject.getTipotransaccion(tipotransaccion);
				switch (tipoTransaccionType) {
				case DEPOSITO:
					if(transaccioncuentaaporte.getEstado() == true){
						saldoFinal = bovedaCaja.getSaldototal().add(transaccioncuentaaporte.getMonto());
					} else {
						saldoFinal = bovedaCaja.getSaldototal().subtract(transaccioncuentaaporte.getMonto());
					}
					break;
				case RETIRO :
					if(transaccioncuentaaporte.getEstado() == true){
						saldoFinal = bovedaCaja.getSaldototal().subtract(transaccioncuentaaporte.getMonto());
					} else {
						saldoFinal = bovedaCaja.getSaldototal().add(transaccioncuentaaporte.getMonto());
					}
					break;
				default:
					break;
				}
				if(saldoFinal.isGreaterThanOrEqual(new Moneda(0))){
					bovedaCaja.setSaldototal(saldoFinal);
					bovedaCajaDAO.update(bovedaCaja);
				} else {
					throw new Exception("La caja no tiene suficiente saldo");
				}								
			} else {
				throw new Exception("No se puede modificar el saldo de la caja");
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public void closeCaja(Caja caja,Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaCierre) throws Exception {
		try {
			Caja cajaDB = find(caja.getIdcaja());
			
			Historialcaja historialcaja = getHistorialcajaLastActive(cajaDB);
					
			//verificando que los saldos coincidan
			Map<Tipomoneda, BigDecimal> saldoDB = new HashMap<Tipomoneda, BigDecimal>();
			Map<Tipomoneda, BigDecimal> saldoVista = new HashMap<Tipomoneda, BigDecimal>();
			
			for (Tipomoneda tipomoneda : mapDetalleHistorialcajaCierre.keySet()) {
				BigDecimal total = BigDecimal.ZERO;
				List<Detallehistorialcaja> list = mapDetalleHistorialcajaCierre.get(tipomoneda);
				for (Detallehistorialcaja d : list) {
					total = total.add(d.getSubtotal().getValue());
				}
				saldoVista.put(tipomoneda, total);
			}
			
			List<Boveda> bovedas = cajaDB.getBovedas();
			for (Boveda boveda : bovedas) {
				BovedaCajaPK pk = new BovedaCajaPK();
				pk.setIdboveda(boveda.getIdboveda());
				pk.setIdcaja(cajaDB.getIdcaja());			
				BovedaCaja bovedaCaja = bovedaCajaDAO.find(pk);
				saldoDB.put(boveda.getTipomoneda(), bovedaCaja.getSaldototal().getValue());
			}
			
			for (Tipomoneda tipomoneda : saldoDB.keySet()) {
				BigDecimal totalDB = saldoDB.get(tipomoneda);
				BigDecimal totalVista = saldoVista.get(tipomoneda);
				if(totalDB.compareTo(totalVista) != 0){
					throw new Exception("Los saldos de la moneda " + tipomoneda.getDenominacion().toUpperCase() + "no coinciden");
				}
			}
			
			//pasó la validacion
			cajaDB.setEstadoapertura(ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO));
			cajaDAO.update(cajaDB);
			
			Calendar calendar = Calendar.getInstance();
			historialcaja.setEstadomovimiento(ProduceObject.getEstadomovimiento(EstadoMovimientoType.CONGELADO));
			historialcaja.setFechacierre(calendar.getTime());
			historialcaja.setHoracierre(calendar.getTime());
			historialcajaDAO.update(historialcaja);
			
			//Actualizar detalle historial
			Map<Denominacionmoneda, Detallehistorialcaja> detalle = new HashMap<Denominacionmoneda, Detallehistorialcaja>();
			for (Tipomoneda tipomoneda : mapDetalleHistorialcajaCierre.keySet()) {				
				List<Detallehistorialcaja> list = mapDetalleHistorialcajaCierre.get(tipomoneda);
				for (Detallehistorialcaja d : list) {
					detalle.put(d.getDenominacionmoneda(), d);
				}				
			}
			
			List<Detallehistorialcaja> detallehistorialcajas = historialcaja.getDetallehistorialcajas();
			for (Detallehistorialcaja detallehistorialcaja : detallehistorialcajas) {
				Denominacionmoneda denominacionmoneda = detallehistorialcaja.getDenominacionmoneda();
				Detallehistorialcaja detalleVista = detalle.get(denominacionmoneda);
				int cantidadVista = detalleVista.getCantidad();
				detallehistorialcaja.setCantidad(cantidadVista);
				detallehistorialcajaDAO.update(detallehistorialcaja);
			}
					
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public Map<Tipomoneda, BigDecimal> verificarSaldosCaja(Caja caja, Map<Tipomoneda, List<Detallehistorialcaja>> detalle) throws Exception {
		Map<Tipomoneda, BigDecimal> result = null;
		try {
			Caja cajaDB = cajaDAO.find(caja.getIdcaja());
			List<Boveda> bovedas = cajaDB.getBovedas();
			
			result = new HashMap<Tipomoneda, BigDecimal>();
			
			for (Boveda boveda : bovedas) {
				Tipomoneda tipomoneda = boveda.getTipomoneda();
				BovedaCajaPK pk = new BovedaCajaPK();
				pk.setIdboveda(boveda.getIdboveda());
				pk.setIdcaja(cajaDB.getIdcaja());
				BovedaCaja bovedaCaja = bovedaCajaDAO.find(pk);
				
				BigDecimal saldoSistema = bovedaCaja.getSaldototal().getValue();
				BigDecimal saldoEnviado = BigDecimal.ZERO;
				
				List<Detallehistorialcaja> detalleHistorial = detalle.get(tipomoneda);
				for (Detallehistorialcaja detallehistorialcaja : detalleHistorial) {
					saldoEnviado = saldoEnviado.add(detallehistorialcaja.getSubtotal().getValue());
				}
				
				BigDecimal saldoFinal = saldoEnviado.subtract(saldoSistema);
				result.put(tipomoneda, saldoFinal);
			}
			
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return result;
	}

	@Override
	public PendienteCaja crearPendiente(Caja caja, PendienteCaja pendienteCaja) throws Exception {
		try {
			pendienteCaja.setIdhistorialcaja(getHistorialcajaLastActive(caja));
			if(pendienteCaja.getMonto().isGreaterThan(new Moneda())){
				pendienteCaja.setTipopendiente("SOBRANTE");
			} else {
				pendienteCaja.setTipopendiente("FALTANTE");
			}
			pendienteCajaDAO.create(pendienteCaja);
			
			Tipomoneda tipomoneda = pendienteCaja.getTipomoneda();
			List<Boveda> bovedas = getBovedas(caja);
			for (Boveda boveda : bovedas) {
				if(boveda.getTipomoneda().equals(tipomoneda)){
					BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
					bovedaCajaPK.setIdboveda(boveda.getIdboveda());
					bovedaCajaPK.setIdcaja(caja.getIdcaja());
					
					BovedaCaja bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);
					Moneda saldoCaja = bovedaCaja.getSaldototal();
					Moneda montoPendiente = pendienteCaja.getMonto();
					Moneda saldoFinal = saldoCaja.add(montoPendiente);
					bovedaCaja.setSaldototal(saldoFinal);
					bovedaCajaDAO.update(bovedaCaja);
					break;
				}
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return pendienteCaja;
	}

	@Override
	public List<CajaTransaccionesBovedaView> getTransaccionesDelDia(Caja caja) throws Exception {
		List<CajaTransaccionesBovedaView> list;
		try {
			Integer id = caja.getIdcaja();
			Caja cajaDB = cajaDAO.find(id);
			Historialcaja historialcajaDB = getHistorialcajaLastActive(cajaDB);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idcaja", cajaDB.getIdcaja());
			parameters.put("idhistorialcaja", historialcajaDB.getIdhistorialcaja());
			
			list = cajaTransaccionesBovedaViewDAO.findByNamedQuery(CajaTransaccionesBovedaView.f_idcaja_idhistorialcaja, parameters);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}
	
	@Override
	public List<PendientesView> getPendientesCaja(Agencia agencia) throws Exception {
		List<PendientesView> list;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idagencia", agencia.getIdagencia());
			
			list = viewPendienteCajaDAO.findByNamedQuery(PendientesView.Pendientes_by_Agencia, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}
	
	@Override
	public PendientesView finPendienteCaja(Object id) throws Exception {
		PendientesView pendiente = null;
		try {
			pendiente = viewPendienteCajaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return pendiente;
	}
}
