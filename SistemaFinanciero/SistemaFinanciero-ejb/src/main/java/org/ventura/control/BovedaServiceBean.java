package org.ventura.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
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
import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.remote.BovedaServiceRemote;
import org.ventura.dao.impl.BovedaCajaDAO;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.dao.impl.DenominacionmonedaDAO;
import org.ventura.dao.impl.DetallehistorialbovedaDAO;
import org.ventura.dao.impl.DetalletransaccionbovedaDAO;
import org.ventura.dao.impl.HistorialbovedaDAO;
import org.ventura.dao.impl.TransaccionbovedaDAO;
import org.ventura.dao.impl.VoucherbovedaViewDAO;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.BovedaCaja;
import org.ventura.entity.schema.caja.BovedaCajaPK;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.Detalletransaccionboveda;
import org.ventura.entity.schema.caja.Entidadfinanciera;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Estadomovimiento;
import org.ventura.entity.schema.caja.Historialboveda;
import org.ventura.entity.schema.caja.Transaccionboveda;
import org.ventura.entity.schema.caja.view.VoucherbovedaView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.InsufficientMoneyForTransactionException;
import org.ventura.util.exception.InvalidTransactionBovedaException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.EstadoMovimientoType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionType;

@Named
@Stateless
@Local(BovedaServiceLocal.class)
@Remote(BovedaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaServiceBean implements BovedaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CajaServiceLocal cajaServiceLocal;
	
	@EJB
	private BovedaDAO bovedaDAO;
	@EJB
	private BovedaCajaDAO bovedaCajaDAO;
	@EJB
	private VoucherbovedaViewDAO voucherbovedaViewDAO;
	
	@EJB
	private TransaccionbovedaDAO transaccionbovedaDAO;
	@EJB
	private DetalletransaccionbovedaDAO detalletransaccionbovedaDAO;

	@EJB
	private HistorialbovedaDAO historialbovedaDAO;
	@EJB
	private DetallehistorialbovedaDAO detallehistorialbovedaDAO;

	@EJB
	private DenominacionmonedaDAO denominacionmonedaDAO;

	/*
	 * Boveda operaciones
	 */

	@Override
	public Boveda create(Boveda boveda) throws Exception {
		try {
			preCreateBoveda(boveda);
			bovedaDAO.create(boveda);
		} catch (EntityExistsException | IllegalArgumentException
				| TransactionRequiredException e) {
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
		Tipomoneda tipomoneda = boveda.getTipomoneda();
		Agencia agencia = boveda.getAgencia();

		if (denominacionBoveda == null || denominacionBoveda.isEmpty()
				|| denominacionBoveda.trim().isEmpty()) {
			throw new Exception("Denominación de Bóveda Inválida");
		}
		if (tipomoneda == null) {
			throw new Exception("Tipo de Moneda Invalida");
		}
		if (agencia == null) {
			throw new Exception("Agencia Invalida");
		}

		boveda.setEstado(true);
		Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
		boveda.setEstadoapertura(estadoapertura);
	}

	@Override
	public Boveda find(Object id) throws Exception {
		Boveda boveda = null;
		try {
			boveda = bovedaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return boveda;
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

	public void inactive(Boveda boveda) throws Exception{
		try {
			Object id = boveda.getIdboveda();
			boveda = bovedaDAO.find(id);
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);		
			
			if(!boveda.getEstadoapertura().equals(estadoapertura)){
				throw new Exception("Boveda no esta Cerrada, intentelo nuevamente");
			}
			Historialboveda historialboveda = getHistorialbovedaLastActive(boveda);
			if(historialboveda != null){
				Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.CONGELADO);
				historialboveda.setEstadomovimiento(estadomovimiento);
				historialbovedaDAO.update(historialboveda);
			}
			
			boveda.setEstado(false);
			boveda.setEstadoapertura(estadoapertura);		
			bovedaDAO.update(boveda);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error al desactivar Boveda");
		}
	}
	
	@Override
	public void openBoveda(Boveda boveda) throws Exception {
		try {
			boveda = bovedaDAO.find(boveda.getIdboveda());

			boolean resultBoveda = verificarBoveda(boveda,EstadoAperturaType.CERRADO);
			if (resultBoveda == false) {
				throw new RollbackFailureException("Boveda Abierta, Imposible Abrirla nuevamente");
			}
			boolean resultCajas = verificarCajas(boveda,EstadoAperturaType.CERRADO);
			if (resultCajas == false) {
				throw new RollbackFailureException("Cajas de Boveda abiertas, Imposible Abrirlas nuevamente");
			}

			Historialboveda historialbovedaNew = new Historialboveda();
			Historialboveda historialbovedaOld = this.getHistorialbovedaLastActive(boveda);
			List<Detallehistorialboveda> detallehistorialbovedasNew = new ArrayList<Detallehistorialboveda>();
			historialbovedaNew.setDetallehistorialbovedas(detallehistorialbovedasNew);

			List<Denominacionmoneda> denominacionmonedasAllActive = getDenominacionmonedasActive(boveda.getTipomoneda());

			if (historialbovedaOld != null) {
				copyDetallehistorialOldtoNew(historialbovedaOld,historialbovedaNew);
			}

			List<Denominacionmoneda> denominacionmonedasAllFromHistorialNew = new ArrayList<Denominacionmoneda>();
			for (Detallehistorialboveda e : detallehistorialbovedasNew) {
				Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
				denominacionmonedasAllFromHistorialNew.add(denominacionmoneda);
			}
			List<Denominacionmoneda> denominacionmonedas2 = getDiferenceWithoutDuplicates(denominacionmonedasAllActive,denominacionmonedasAllFromHistorialNew);
			for (Denominacionmoneda e : denominacionmonedas2) {
				Detallehistorialboveda detallehistorialboveda = new Detallehistorialboveda();
				detallehistorialboveda.setDenominacionmoneda(e);
				detallehistorialboveda.setCantidad(0);

				detallehistorialbovedasNew.add(detallehistorialboveda);
			}

			historialbovedaNew.setBoveda(boveda);
			historialbovedaNew.setFechaapertura(Calendar.getInstance().getTime());
			historialbovedaNew.setHoraapertura(Calendar.getInstance().getTime());

			Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.CONGELADO);
			historialbovedaNew.setEstadomovimiento(estadomovimiento);

			historialbovedaDAO.create(historialbovedaNew);

			for (Detallehistorialboveda e : detallehistorialbovedasNew) {
				e.setHistorialboveda(historialbovedaNew);
				detallehistorialbovedaDAO.create(e);
			}

			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
			boveda.setEstadoapertura(estadoapertura);
			bovedaDAO.update(boveda);

			log.info("FINISH SUCCESSFULLY: BOVEDA ABIERTA");

		} catch (RollbackFailureException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		} catch (IllegalArgumentException | NonexistentEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Abrir la Boveda");
		}
	}

	@Override
	public void closeBoveda(Boveda boveda) throws Exception {
		try {
			boveda = bovedaDAO.find(boveda.getIdboveda());

			boolean resultBoveda = verificarBoveda(boveda,EstadoAperturaType.CERRADO);
			if (resultBoveda == true) {
				throw new RollbackFailureException("Boveda cerrada, Imposible cerrarla nuevamente");
			}

			boolean resultCajas = verificarCajas(boveda,EstadoAperturaType.CERRADO);
			if (resultCajas == false) {
				throw new RollbackFailureException("Cajas de Boveda abiertas, Imposible Cerrar Boveda");
			}

			Historialboveda historialboveda = this.getHistorialbovedaLastActive(boveda);
			if (historialboveda == null) {
				throw new Exception("No se puede cerrar boveda, no existe HistorialBovedaActivo");
			}

			// modificar el detalle historial boveda con transacciones
			// realizadas

			// modificar el historial boveda
			historialboveda.setFechacierre(Calendar.getInstance().getTime());
			historialboveda.setHoracierre(Calendar.getInstance().getTime());
			Estadomovimiento estadomovimiento = ProduceObject.getEstadomovimiento(EstadoMovimientoType.CONGELADO);
			historialboveda.setEstadomovimiento(estadomovimiento);
			historialbovedaDAO.update(historialboveda);

			// modificar boveda
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			boveda.setEstadoapertura(estadoapertura);
			bovedaDAO.update(boveda);

			log.info("FINISH SUCCESSFULLY: BOVEDA CERRADA");

		} catch (RollbackFailureException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		} catch (IllegalArgumentException | NonexistentEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Cerrar la Boveda");
		}
	}
	
	@Override
	public void freezeBoveda(Boveda oBoveda) throws Exception {
		try {
			Boveda boveda = find(oBoveda.getIdboveda());
			Historialboveda historialboveda = getHistorialbovedaLastActive(boveda);
			Estadomovimiento estadomovimientoNew = ProduceObject
					.getEstadomovimiento(EstadoMovimientoType.CONGELADO);
			if (historialboveda != null) {
				setEstadomovimientoHistorialboveda(historialboveda,
						estadomovimientoNew);
			} else {
				throw new Exception("No se puede congelar/decongelar boveda");
			}

		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public void defrostBoveda(Boveda oBoveda) throws Exception {
		try {
			Boveda boveda = find(oBoveda.getIdboveda());
			Historialboveda historialboveda = getHistorialbovedaLastActive(boveda);
			Estadomovimiento estadomovimientoNew = ProduceObject
					.getEstadomovimiento(EstadoMovimientoType.DESCONGELADO);
			if (historialboveda != null) {
				setEstadomovimientoHistorialboveda(historialboveda,
						estadomovimientoNew);
			} else {
				throw new Exception("No se puede congelar/decongelar boveda");
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public Historialboveda getHistorialbovedaLastActive(Boveda boveda) throws RollbackFailureException, Exception {
		Historialboveda historialboveda = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("boveda", boveda);

		List<Historialboveda> historialbovedaList = historialbovedaDAO.findByNamedQuery(Historialboveda.findHistorialActive,parameters);
		if (historialbovedaList.size() == 0) {
			historialboveda = null;
		}
		if (historialbovedaList.size() == 1) {
			historialboveda = historialbovedaList.get(0);
		}
		if (historialbovedaList.size() > 1) {
			throw new Exception("Existe mas de un historial activo");
		}
		return historialboveda;
	}

	@Override
	public Historialboveda getHistorialbovedaLastNoActive(Boveda boveda) throws Exception {
		Historialboveda historialboveda = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("boveda", boveda);

		List<Historialboveda> historialbovedaList = historialbovedaDAO
				.findByNamedQuery(Historialboveda.findLastHistorialNoActive,
						parameters);
		if (historialbovedaList.size() == 0) {
			historialboveda = null;
		}
		if (historialbovedaList.size() == 1) {
			historialboveda = historialbovedaList.get(0);
		}
		if (historialbovedaList.size() > 1) {
			throw new Exception("Existe mas de un historial activo");
		}
		return historialboveda;
	}
	
	@Override
	public List<Detallehistorialboveda> getDetallehistorialbovedaLastNoActive(
			Boveda boveda) throws Exception {
		try {
			Historialboveda historialboveda = getHistorialbovedaLastNoActive(boveda);
			List<Detallehistorialboveda> result;

			Tipomoneda tipomoneda = boveda.getTipomoneda();
			List<Denominacionmoneda> denominacionmonedasAllActive = getDenominacionmonedasActive(tipomoneda);
			List<Denominacionmoneda> denominacionmonedasAllFromHistorial = new ArrayList<Denominacionmoneda>();

			if (historialboveda == null) {
				result = new ArrayList<Detallehistorialboveda>();
			} else {
				result = historialboveda.getDetallehistorialbovedas();
				for (Detallehistorialboveda e : result) {
					Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
					denominacionmonedasAllFromHistorial.add(denominacionmoneda);
				}
			}

			List<Denominacionmoneda> denominacionmonedas2 = getDiferenceWithoutDuplicates(denominacionmonedasAllActive,denominacionmonedasAllFromHistorial);
			for (Denominacionmoneda e : denominacionmonedas2) {
				Detallehistorialboveda detallehistorialboveda = new Detallehistorialboveda();
				detallehistorialboveda.setDenominacionmoneda(e);
				detallehistorialboveda.setCantidad(0);

				result.add(detallehistorialboveda);
			}
			return result;
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
	public List<Detallehistorialboveda> getDetallehistorialbovedaLastActive(Boveda boveda) throws Exception {
		try {
			Historialboveda historialboveda = getHistorialbovedaLastActive(boveda);
			List<Detallehistorialboveda> result;

			Tipomoneda tipomoneda = boveda.getTipomoneda();
			List<Denominacionmoneda> denominacionmonedasAllActive = getDenominacionmonedasActive(tipomoneda);
			List<Denominacionmoneda> denominacionmonedasAllFromHistorial = new ArrayList<Denominacionmoneda>();

			if (historialboveda == null) {
				result = new ArrayList<Detallehistorialboveda>();
			} else {
				result = historialboveda.getDetallehistorialbovedas();
				for (Detallehistorialboveda e : result) {
					Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
					denominacionmonedasAllFromHistorial.add(denominacionmoneda);
				}
			}

			List<Denominacionmoneda> denominacionmonedas2 = getDiferenceWithoutDuplicates(denominacionmonedasAllActive,denominacionmonedasAllFromHistorial);
			for (Denominacionmoneda e : denominacionmonedas2) {
				Detallehistorialboveda detallehistorialboveda = new Detallehistorialboveda();
				detallehistorialboveda.setDenominacionmoneda(e);
				detallehistorialboveda.setCantidad(0);

				result.add(detallehistorialboveda);
			}
			return result;
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
	public List<Detallehistorialboveda> getDetalleforOpenBoveda(Boveda boveda)
			throws Exception {
		try {
			Historialboveda historialboveda = getHistorialbovedaLastActive(boveda);
			List<Detallehistorialboveda> result;

			Tipomoneda tipomoneda = boveda.getTipomoneda();
			List<Denominacionmoneda> denominacionmonedasAllActive = getDenominacionmonedasActive(tipomoneda);
			List<Denominacionmoneda> denominacionmonedasAllFromHistorial = new ArrayList<Denominacionmoneda>();

			if (historialboveda == null) {
				result = new ArrayList<Detallehistorialboveda>();
			} else {
				result = historialboveda.getDetallehistorialbovedas();
				for (Detallehistorialboveda e : result) {
					Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
					denominacionmonedasAllFromHistorial.add(denominacionmoneda);
				}
			}

			List<Denominacionmoneda> denominacionmonedas2 = getDiferenceWithoutDuplicates(denominacionmonedasAllActive,denominacionmonedasAllFromHistorial);
			for (Denominacionmoneda e : denominacionmonedas2) {
				Detallehistorialboveda detallehistorialboveda = new Detallehistorialboveda();
				detallehistorialboveda.setDenominacionmoneda(e);
				detallehistorialboveda.setCantidad(0);

				result.add(detallehistorialboveda);
			}
			return result;
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

	public List<Denominacionmoneda> getMergeWithoutDuplicates(List<Denominacionmoneda> one, List<Denominacionmoneda> two) {
		List<Denominacionmoneda> resultList = new ArrayList<Denominacionmoneda>();
		resultList.addAll(one);
		for (Denominacionmoneda e : two) {
			if (!resultList.contains(e))
				resultList.add(e);
		}
		return resultList;
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

	public void setEstadomovimientoHistorialboveda(Historialboveda historialboveda, Estadomovimiento estadomovimiento) throws Exception {
		Boveda boveda = historialboveda.getBoveda();
		Estadoapertura estadoapertura = boveda.getEstadoapertura();
		Estadoapertura estadoaperturaTocheck = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
		if (!estadoapertura.equals(estadoaperturaTocheck)) {
			historialboveda.setEstadomovimiento(estadomovimiento);
			historialbovedaDAO.update(historialboveda);
		} else {
			throw new Exception("Boveda cerrada, no se pueden activar/desactivar movimientos");
		}
	}

	public boolean verificarBoveda(Boveda boveda,
			EstadoAperturaType estadoAperturaType) throws Exception {
		boolean result = true;
		Estadoapertura estadoapertura1 = boveda.getEstadoapertura();
		Estadoapertura estadoapertura2 = ProduceObject
				.getEstadoapertura(estadoAperturaType);
		if (estadoapertura1.equals(estadoapertura2)) {
			log.info("Verificacion de Boveda satisfactoria");
		} else {
			result = false;
			log.info("Verificacion de Boveda incorrecta");
		}
		return result;
	}

	public boolean verificarCajas(Boveda boveda,EstadoAperturaType estadoAperturaType) throws Exception {
		boolean result = true;

		List<Caja> cajaList = boveda.getCajas();
		
		for (Caja caja : cajaList) {
			EstadoAperturaType estadoAperturaTypeCaja = ProduceObject.getEstadoaperturaType(caja.getEstadoapertura());
			if (estadoAperturaTypeCaja != estadoAperturaType) {
				result = false;
				break;
			}
			log.info("Caja " + caja.getDenominacion() + "en estado "+ estadoAperturaTypeCaja);
		}
		

		if (result == true) {
			log.info("Verificacion de Cajas satisfactoria");
		} else {
			log.info("Verificacion de Cajas incorrecta");
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

	public List<Denominacionmoneda> getDenominacionmonedasActive(Tipomoneda tipomoneda) throws RollbackFailureException, Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtipomoneda", tipomoneda.getIdtipomoneda());
		List<Denominacionmoneda> denominacionmonedas = denominacionmonedaDAO.findByNamedQuery(Denominacionmoneda.findAllByTipoMoneda,parameters);
		return denominacionmonedas;
	}

	public void copyDetallehistorialOldtoNew(Historialboveda historialbovedaOld, Historialboveda historialbovedaNew) throws RollbackFailureException, Exception {
		if (historialbovedaOld != null) {
			List<Detallehistorialboveda> detallehistorialbovedasOld = historialbovedaOld.getDetallehistorialbovedas();
			List<Detallehistorialboveda> detallehistorialbovedasNew = historialbovedaNew.getDetallehistorialbovedas();

			for (Detallehistorialboveda e : detallehistorialbovedasOld) {
				Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
				Integer cantidad = e.getCantidad();

				Detallehistorialboveda detallehistorialbovedaNew = new Detallehistorialboveda();
				detallehistorialbovedaNew.setDenominacionmoneda(denominacionmoneda);
				detallehistorialbovedaNew.setCantidad(cantidad);

				detallehistorialbovedasNew.add(detallehistorialbovedaNew);
			}
		}
	}

	public boolean containsTipoMoneda(
			List<Detallehistorialboveda> detallehistorialbovedas,
			Denominacionmoneda denominacionmoneda) {
		boolean result = false;
		for (Detallehistorialboveda e : detallehistorialbovedas) {
			Denominacionmoneda denominacionmoneda2 = e.getDenominacionmoneda();
			if (denominacionmoneda2.equals(denominacionmoneda)) {
				result = true;
				break;
			}
		}
		return result;
	}

	@Override
	public void openBovedaWithPendiente(Boveda boveda) throws Exception {
		/*
		 * boolean existsOldHistorialboveda; try { boveda =
		 * bovedaDAO.find(boveda.getIdboveda());
		 * 
		 * boolean resultBoveda = verificarBoveda(boveda,
		 * EstadoMovimientoType.CERRADO); if (resultBoveda == false) { throw new
		 * Exception( "Boveda Abierta, Imposible Abrirla nuevamente"); } boolean
		 * resultCajas = verificarCajas(boveda, EstadoMovimientoType.CERRADO);
		 * if (resultCajas == false) { throw new Exception(
		 * "Cajas de Boveda abiertas, Imposible Abrirlas nuevamente"); }
		 * 
		 * Historialboveda historialbovedaOld = this
		 * .getHistorialActive(boveda); Historialboveda historialbovedaNew = new
		 * Historialboveda();
		 * 
		 * List<Denominacionmoneda> denominacionmonedasTotal =
		 * getDenominacionmonedasActive(boveda .getTipomoneda());
		 * 
		 * existsOldHistorialboveda = (historialbovedaOld == null) ? false :
		 * true; copyDetallehistorialOldtoNew(historialbovedaOld,
		 * historialbovedaNew);
		 * union(historialbovedaNew.getDetallehistorialbovedainicial(),
		 * denominacionmonedasTotal);
		 * union(historialbovedaNew.getDetallehistorialbovedafinal(),
		 * denominacionmonedasTotal);
		 * 
		 * historialbovedaNew.setBoveda(boveda);
		 * historialbovedaNew.setFechaapertura(Calendar.getInstance()
		 * .getTime()); historialbovedaNew
		 * .setHoraapertura(Calendar.getInstance().getTime());
		 * historialbovedaNew.setSaldoinicial(boveda.getSaldo());
		 * historialbovedaNew.setSaldofinal(boveda.getSaldo());
		 * historialbovedaNew.setEstado(true);
		 * 
		 * if (existsOldHistorialboveda) { historialbovedaNew.setEstado(true);
		 * historialbovedaOld.setEstado(false); } else {
		 * historialbovedaNew.setEstado(true); } Integer estadoMovimientoAbierto
		 * = EstadoValue
		 * .getEstadoMovimientoValue(EstadoMovimientoType.ABIERTO_CONGELADO);
		 * boveda.setIdestadomovimiento(estadoMovimientoAbierto);
		 * 
		 * // LISTO PARA MODIFICAR LA BASE DE DATOS Detallehistorialboveda
		 * detallehistorialbovedainicial = historialbovedaNew
		 * .getDetallehistorialbovedainicial(); Detallehistorialboveda
		 * detallehistorialbovedafinal = historialbovedaNew
		 * .getDetallehistorialbovedafinal();
		 * 
		 * List<Detallehistorialboveda> detalleaperturacierrebovedasinicial =
		 * detallehistorialbovedainicial .getDetalleaperturacierrebovedaList();
		 * List<Detallehistorialboveda> detalleaperturacierrebovedasfinal =
		 * detallehistorialbovedafinal .getDetalleaperturacierrebovedaList();
		 * 
		 * detallehistorialbovedaDAO.create(detallehistorialbovedainicial);
		 * detallehistorialbovedaDAO.create(detallehistorialbovedafinal);
		 * 
		 * for (Iterator<Detallehistorialboveda> iterator =
		 * detalleaperturacierrebovedasinicial .iterator(); iterator.hasNext();)
		 * { Detallehistorialboveda detalleaperturacierreboveda =
		 * (Detallehistorialboveda) iterator .next();
		 * detalleaperturacierreboveda
		 * .setDetallehistorialboveda(detallehistorialbovedainicial);
		 * detalleaperturacierrebovedaDAO .create(detalleaperturacierreboveda);
		 * } for (Iterator<Detallehistorialboveda> iterator =
		 * detalleaperturacierrebovedasfinal .iterator(); iterator.hasNext();) {
		 * Detallehistorialboveda detalleaperturacierreboveda =
		 * (Detallehistorialboveda) iterator .next();
		 * detalleaperturacierreboveda
		 * .setDetallehistorialboveda(detallehistorialbovedafinal);
		 * detalleaperturacierrebovedaDAO .create(detalleaperturacierreboveda);
		 * }
		 * 
		 * historialbovedaNew
		 * .setDetallehistorialbovedainicial(detallehistorialbovedainicial);
		 * historialbovedaNew
		 * .setDetallehistorialbovedafinal(detallehistorialbovedafinal);
		 * 
		 * if (existsOldHistorialboveda) {
		 * historialbovedaDAO.create(historialbovedaNew);
		 * historialbovedaDAO.update(historialbovedaOld); } else {
		 * historialbovedaDAO.create(historialbovedaNew); }
		 * 
		 * bovedaDAO.update(boveda);
		 * log.info("FINISH SUCCESSFULLY: BOVEDA ABIERTA");
		 * 
		 * } catch (IllegalArgumentException | NonexistentEntityException e) {
		 * log.error("Exception:" + e.getClass()); log.error(e.getMessage());
		 * log.error("Caused by:" + e.getCause()); throw e; } catch (Exception
		 * e) { boveda.setIdboveda(null); log.error("Exception:" +
		 * e.getClass()); log.error(e.getMessage()); log.error("Caused by:" +
		 * e.getCause()); throw new
		 * Exception("Error Interno: No se pudo Abrir la Boveda"); }
		 */
	}

	/*
	 * private boolean verificarDetalleHistorialFinal(Historialboveda
	 * historialboveda) { if (historialboveda.getDetallehistorialbovedafinal()
	 * != null) { return true; } return false; }
	 */


	@Override
	public List<Detalletransaccionboveda> getDetalletransaccionboveda(Boveda boveda) {
		List<Detalletransaccionboveda> detalletransaccionbovedas = new ArrayList<Detalletransaccionboveda>();
		try {
			Tipomoneda tipomoneda = boveda.getTipomoneda();
			List<Denominacionmoneda> denominacionmonedaList;
			denominacionmonedaList = getDenominacionmonedasActive(tipomoneda);

			for (Denominacionmoneda e : denominacionmonedaList) {
				Detalletransaccionboveda detalletransaccionboveda = new Detalletransaccionboveda();
				detalletransaccionboveda.setCantidad(0);
				detalletransaccionboveda.setDenominacionmoneda(e);

				detalletransaccionbovedas.add(detalletransaccionboveda);
			}
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return detalletransaccionbovedas;
	}

	@Override
	public Transaccionboveda createTransaccionboveda(Boveda boveda, Caja caja,Transaccionboveda transaccionboveda) throws Exception {
		List<Detalletransaccionboveda> detalletransaccionbovedas = null;
		try {
			detalletransaccionbovedas = transaccionboveda.getDetalletransaccionbovedas();
			Historialboveda historialboveda = getHistorialbovedaLastActive(boveda);
			if (historialboveda != null) {

				TipoTransaccionType tipoTransaccion = ProduceObject.getTipotransaccion(transaccionboveda.getTipotransaccion());
				boolean isTransaccionvalida = false;

				switch (tipoTransaccion) {
				case DEPOSITO:
					isTransaccionvalida = isDepositoValido(boveda,transaccionboveda);
					break;
				case RETIRO:
					isTransaccionvalida = isRetiroValido(boveda,transaccionboveda);
					break;
				default:
					throw new Exception("Tipo de transaccion no valida");
				}

				//ACTUALIZAR SALDO DE CAJA PARA LA MONEDA ESPECIFICADA
				if (isTransaccionvalida == true) {
					
					BovedaCajaPK bovedaCajaPK = new BovedaCajaPK();
					bovedaCajaPK.setIdboveda(boveda.getIdboveda());
					bovedaCajaPK.setIdcaja(caja.getIdcaja());
					
					BovedaCaja bovedaCaja = bovedaCajaDAO.find(bovedaCajaPK);		
					Moneda montoTransaccion = transaccionboveda.getTotal();
					Moneda saldoFinal = bovedaCaja.getSaldototal();
					
					switch (tipoTransaccion) {
					case DEPOSITO:
						saldoFinal = saldoFinal.subtract(montoTransaccion);
						break;
					case RETIRO:
						saldoFinal = saldoFinal.add(montoTransaccion);
						break;
					default:
						throw new Exception("Tipo de transaccion no valida");
					}
					bovedaCaja.setSaldototal(saldoFinal);
					bovedaCajaDAO.update(bovedaCaja);
				}
						
				//actualizando el detalle de las transacciones
				if (isTransaccionvalida == true) {
				
					//obteniendo monto transaccion y saldo disponible
					List<Detallehistorialboveda> detallehistorialbovedasSaldo = historialboveda.getDetallehistorialbovedas();
					BigDecimal saldoTotal = BigDecimal.ZERO;
					BigDecimal transaccionTotal = BigDecimal.ZERO;
					BigDecimal saldoDisponible = BigDecimal.ZERO;
					for (Detallehistorialboveda d : detallehistorialbovedasSaldo) {
						int cantidad = d.getCantidad();
						BigDecimal valor = d.getDenominacionmoneda().getValor().getValue();
						saldoTotal = saldoTotal.add(valor.multiply(new BigDecimal(cantidad)));
					}
					for (Detalletransaccionboveda e : detalletransaccionbovedas) {
						int cantidad = e.getCantidad();
						BigDecimal valor = e.getDenominacionmoneda().getValor().getValue();
						transaccionTotal = transaccionTotal.add(valor.multiply(new BigDecimal(cantidad)));
					}			
					switch (tipoTransaccion) {
					case DEPOSITO:
						saldoDisponible = saldoTotal.add(transaccionTotal);
						break;
					case RETIRO:
						saldoDisponible = saldoTotal.subtract(transaccionTotal);
						break;
					default:
						throw new Exception("Tipo de transaccion no valida");
					}
					
					transaccionboveda.setHistorialboveda(historialboveda);
					preCreateTransaccionboveda(transaccionboveda);
					transaccionboveda.setHistorialcaja(cajaServiceLocal.getHistorialcajaLastActive(caja));
					transaccionboveda.setSaldodisponible(saldoDisponible);
					transaccionbovedaDAO.create(transaccionboveda);

					for (Detalletransaccionboveda e : detalletransaccionbovedas) {
						e.setTransaccionboveda(transaccionboveda);
						detalletransaccionbovedaDAO.create(e);
					}
					
					transaccionboveda.setDetalletransaccionbovedas(detalletransaccionbovedas);

					// ACTUALIZAR DETALLEHISTORIALBOVEDA
					List<Detallehistorialboveda> detallehistorialbovedas = historialboveda.getDetallehistorialbovedas();

					for (Detalletransaccionboveda e : detalletransaccionbovedas) {
						Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
						Integer cantidad = e.getCantidad();
						for (Detallehistorialboveda d : detallehistorialbovedas) {
							if (d.getDenominacionmoneda().equals(denominacionmoneda)) {
								switch (tipoTransaccion) {
								case DEPOSITO:
									d.setCantidad(d.getCantidad() + cantidad);
									break;
								case RETIRO:
									d.setCantidad(d.getCantidad() - cantidad);
									break;
								}
								detallehistorialbovedaDAO.update(d);
								break;
							}
						}
					}					
					
				} else {
					switch (tipoTransaccion) {
					case DEPOSITO:
						throw new InvalidTransactionBovedaException("Transaccion no permitida");
					case RETIRO:
						throw new InsufficientMoneyForTransactionException("Fondos Insuficientes para Retiro");
					}
				}
			} else {
				throw new RollbackFailureException("Error: La boveda no tiene un historial activo");
			}
		} catch (Exception e) {
			transaccionboveda.setIdtransaccionboveda(null);
			for (Detalletransaccionboveda d : detalletransaccionbovedas) {
				d.setIddetalletransaccionboveda(null);
			}
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return transaccionboveda;
	}
	
	@Override
	public Transaccionboveda createTransaccionboveda(Boveda boveda, Entidadfinanciera entidadfinanciera,Transaccionboveda transaccionboveda) throws Exception {
		List<Detalletransaccionboveda> detalletransaccionbovedas = null;
		try {
			detalletransaccionbovedas = transaccionboveda.getDetalletransaccionbovedas();
			Historialboveda historialboveda = getHistorialbovedaLastActive(boveda);
			if (historialboveda != null) {

				TipoTransaccionType tipoTransaccion = ProduceObject.getTipotransaccion(transaccionboveda.getTipotransaccion());
				boolean isTransaccionvalida = false;

				switch (tipoTransaccion) {
				case DEPOSITO:
					isTransaccionvalida = isDepositoValido(boveda,transaccionboveda);
					break;
				case RETIRO:
					isTransaccionvalida = isRetiroValido(boveda,transaccionboveda);
					break;
				default:
					throw new Exception("Tipo de transaccion no valida");
				}

				if (isTransaccionvalida == true) {
					
					//obteniendo monto transaccion y saldo disponible
					List<Detallehistorialboveda> detallehistorialbovedasSaldo = historialboveda.getDetallehistorialbovedas();
					BigDecimal saldoTotal = BigDecimal.ZERO;
					BigDecimal transaccionTotal = BigDecimal.ZERO;
					BigDecimal saldoDisponible = BigDecimal.ZERO;
					for (Detallehistorialboveda d : detallehistorialbovedasSaldo) {
						int cantidad = d.getCantidad();
						BigDecimal valor = d.getDenominacionmoneda().getValor().getValue();
						saldoTotal = saldoTotal.add(valor.multiply(new BigDecimal(cantidad)));
					}
					for (Detalletransaccionboveda e : detalletransaccionbovedas) {
						int cantidad = e.getCantidad();
						BigDecimal valor = e.getDenominacionmoneda().getValor().getValue();
						transaccionTotal = transaccionTotal.add(valor.multiply(new BigDecimal(cantidad)));
					}			
					switch (tipoTransaccion) {
					case DEPOSITO:
						saldoDisponible = saldoTotal.add(transaccionTotal);
						break;
					case RETIRO:
						saldoDisponible = saldoTotal.subtract(transaccionTotal);
						break;
					default:
						throw new Exception("Tipo de transaccion no valida");
					}
					
					transaccionboveda.setHistorialboveda(historialboveda);
					preCreateTransaccionboveda(transaccionboveda);
					transaccionboveda.setEntidadfinanciera(entidadfinanciera);
					transaccionboveda.setSaldodisponible(saldoDisponible);
					transaccionbovedaDAO.create(transaccionboveda);

					for (Detalletransaccionboveda e : detalletransaccionbovedas) {
						e.setTransaccionboveda(transaccionboveda);
						detalletransaccionbovedaDAO.create(e);
					}
					
					transaccionboveda.setDetalletransaccionbovedas(detalletransaccionbovedas);

					// ACTUALIZAR DETALLEHISTORIALBOVEDA
					List<Detallehistorialboveda> detallehistorialbovedas = historialboveda.getDetallehistorialbovedas();

					for (Detalletransaccionboveda e : detalletransaccionbovedas) {
						Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
						Integer cantidad = e.getCantidad();
						for (Detallehistorialboveda d : detallehistorialbovedas) {
							if (d.getDenominacionmoneda().equals(denominacionmoneda)) {
								switch (tipoTransaccion) {
								case DEPOSITO:
									d.setCantidad(d.getCantidad() + cantidad);
									break;
								case RETIRO:
									d.setCantidad(d.getCantidad() - cantidad);
									break;
								}
								detallehistorialbovedaDAO.update(d);
								break;
							}
						}
					}

				} else {
					switch (tipoTransaccion) {
					case DEPOSITO:
						throw new InvalidTransactionBovedaException("Transaccion no permitida");
					case RETIRO:
						throw new InsufficientMoneyForTransactionException("Fondos Insuficientes para Retiro");
					}
				}
			} else {
				throw new RollbackFailureException("Error: La boveda no tiene un historial activo");
			}
		} catch (Exception e) {
			transaccionboveda.setIdtransaccionboveda(null);
			for (Detalletransaccionboveda d : detalletransaccionbovedas) {
				d.setIddetalletransaccionboveda(null);
			}
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return transaccionboveda;
	}

	public Transaccionboveda preCreateTransaccionboveda(
			Transaccionboveda transaccionboveda) {
		transaccionboveda.setFecha(Calendar.getInstance().getTime());
		transaccionboveda.setHora(Calendar.getInstance().getTime());
		return transaccionboveda;
	}

	public boolean isRetiroValido(Boveda boveda,Transaccionboveda transaccionboveda) throws Exception {

		Historialboveda historialActive = getHistorialbovedaLastActive(boveda);
		List<Detallehistorialboveda> detallehistorialbovedas = historialActive.getDetallehistorialbovedas();
		List<Detalletransaccionboveda> detalletransaccionbovedas = transaccionboveda.getDetalletransaccionbovedas();

		for (Detallehistorialboveda e : detallehistorialbovedas) {
			Denominacionmoneda denominacionmoneda = e.getDenominacionmoneda();
			Integer cantidadDisponible = e.getCantidad();
			Integer cantidadRequerida = null;
			for (Detalletransaccionboveda d : detalletransaccionbovedas) {
				if (d.getDenominacionmoneda().equals(denominacionmoneda)) {
					cantidadRequerida = d.getCantidad();
					break;
				}
			}
			if (cantidadRequerida > cantidadDisponible) {
				return false;
			}
		}
		return true;

	}

	public boolean isDepositoValido(Boveda boveda,
			Transaccionboveda transaccionboveda) {
		return true;
	}

	@Override
	public Transaccionboveda findTransaccionboveda(Object id) throws Exception {
		Transaccionboveda transaccionboveda = null;
		try {
			transaccionboveda = transaccionbovedaDAO.find(id);

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("transaccionboveda", transaccionboveda);
			List<Detalletransaccionboveda> detalletransaccion = detalletransaccionbovedaDAO.findByNamedQuery(Detalletransaccionboveda.ALL_ACTIVE_BY_TRANSACCION,parameters);

			transaccionboveda.setDetalletransaccionbovedas(detalletransaccion);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return transaccionboveda;
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
	public VoucherbovedaView getVoucherTransaccionBoveda(Transaccionboveda transaccionboveda) throws Exception {
		VoucherbovedaView voucherbovedaView = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtransaccionboveda", transaccionboveda.getIdtransaccionboveda());
		List<VoucherbovedaView> list;
		try {
			list = voucherbovedaViewDAO.findByNamedQuery(VoucherbovedaView.f_idtransaccionboveda, parameters);
			if (list.size()==1) {
				voucherbovedaView = list.get(0);
			}else {
				if (list.size()==0) {
					voucherbovedaView = null;
				}else {
					throw new Exception("Error: voucher >= 2");
				}
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return voucherbovedaView;		
	}
}
