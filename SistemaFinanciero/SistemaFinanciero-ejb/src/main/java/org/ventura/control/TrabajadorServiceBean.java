package org.ventura.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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

import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.TrabajadorServiceLocal;
import org.ventura.boundary.remote.BovedaServiceRemote;
import org.ventura.boundary.remote.TrabajadorServiceRemote;
import org.ventura.dao.impl.BovedaCajaDAO;
import org.ventura.dao.impl.BovedaDAO;
import org.ventura.dao.impl.BovedaTransaccionesHistorialactivoViewDAO;
import org.ventura.dao.impl.DenominacionmonedaDAO;
import org.ventura.dao.impl.DetallehistorialbovedaDAO;
import org.ventura.dao.impl.DetalletransaccionbovedaDAO;
import org.ventura.dao.impl.HistorialbovedaDAO;
import org.ventura.dao.impl.TrabajadorDAO;
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
import org.ventura.entity.schema.caja.view.BovedaTransaccionesHistorialactivoView;
import org.ventura.entity.schema.caja.view.VoucherbovedaView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.rrhh.Trabajador;
import org.ventura.entity.schema.seguridad.Grupo;
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
@Local(TrabajadorServiceLocal.class)
@Remote(TrabajadorServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TrabajadorServiceBean implements TrabajadorServiceLocal {

	@Inject
	private Log log;

	@EJB private TrabajadorDAO trabajadorDAO;
	
	@Override
	public List<Trabajador> getTrabajadores(Agencia agencia) throws Exception {
		List<Trabajador> list = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idagencia", agencia.getIdagencia());
			list = trabajadorDAO.findByNamedQuery(Trabajador.f_idagencia, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Trabajador> find(Agencia agencia,Tipodocumento tipodocumento, String valorBusqueda) throws Exception {
		List<Trabajador> list = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			if(tipodocumento == null){
				parameters.put("idagencia", agencia.getIdagencia());
				parameters.put("searched", "%" + valorBusqueda.toUpperCase() + "%");
				list = trabajadorDAO.findByNamedQuery(Trabajador.f_idagencia_searched, parameters);
			} else {
				parameters.put("idagencia", agencia.getIdagencia());
				parameters.put("idtipodocumento", tipodocumento.getIdtipodocumento());
				parameters.put("numerodocumento", valorBusqueda);
				list = trabajadorDAO.findByNamedQuery(Trabajador.f_idagencia_idtipodocumento_numerodocumento, parameters);
			}		
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	
}
