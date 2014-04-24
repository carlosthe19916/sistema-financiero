package org.ventura.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.boundary.remote.CuentaaporteServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.dao.impl.AportesCuentaaporteViewDAO;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaaporteDAO;
import org.ventura.dao.impl.CuentaaporteViewDAO;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteViewPK;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.tipodato.Moneda;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionType;

@Named
@Stateless
@Local(CuentaaporteServiceLocal.class)
@Remote(CuentaaporteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CuentaaporteServiceBean implements CuentaaporteServiceLocal{

	@EJB
	private SocioServiceLocal socioServiceLocal;
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB
	private BeneficiariocuentaDAO beneficiariocuentaDAO;
	@EJB
	private AccionistaDAO accionistaDAO;
	@EJB
	private CuentaaporteDAO cuentaaporteDAO;
	
	@EJB
	private CuentaaporteViewDAO cuentaaporteViewDAO;
	@EJB
	private AportesCuentaaporteViewDAO aportesCuentaaporteViewDAO;
	@EJB
	private TransaccionCajaServiceLocal transaccionCajaServiceLocal;

	
	@Inject
	private Log log;

	@Override
	public CuentaaporteView findCuentaaporteViewByNumerocuenta(String numerocuenta) throws Exception {
		CuentaaporteView cuentaaporteView;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("numerocuenta", numerocuenta);

			List<CuentaaporteView> cuentaaporteViews = cuentaaporteViewDAO.findByNamedQuery(CuentaaporteView.findByNumerocuenta,parameters,100);
			if (cuentaaporteViews.size() == 1) {
				cuentaaporteView = cuentaaporteViews.get(0);
			} else {
				if (cuentaaporteViews.size() == 0){
					cuentaaporteView = null;
				} else {
					throw new Exception("No se encontro cuenta aporte valida");
				}		
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporteView;
	}
	
	@Override
	public List<AportesCuentaaporteView> getTableAportesPorpagar(Integer idcuentaaporte,Date startDate, Date endDate) throws Exception{
		List<AportesCuentaaporteView> aportesCuentaaporteViews;
		try {				
			Cuentaaporte cuentaaporte  = cuentaaporteDAO.find(idcuentaaporte);
			Date fechaInicioSocio = cuentaaporte.getFechaapertura();		
			
			//completando las fechas
			Calendar beginCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			beginCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			
			beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
			endCalendar.set(Calendar.DAY_OF_MONTH, 1);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String stringBeginDate =  sdf.format(beginCalendar.getTime());
			String stringEndDate =  sdf.format(endCalendar.getTime());
			
			beginCalendar.setTime(sdf.parse(stringBeginDate));
			endCalendar.setTime(sdf.parse(stringEndDate));
			
			if(fechaInicioSocio.compareTo(beginCalendar.getTime()) > 0){
				beginCalendar.setTime(fechaInicioSocio);
				beginCalendar.set(Calendar.DAY_OF_MONTH, 1);
			}
			
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idcuentaaporte", idcuentaaporte);
			parameters.put("startDate", beginCalendar.getTime());
			parameters.put("endDate", endCalendar.getTime());
			
			aportesCuentaaporteViews = aportesCuentaaporteViewDAO.findByNamedQuery(AportesCuentaaporteView.findBetweenDates,parameters);
							
			//poniendo la data final
			LinkedHashMap<Date, AportesCuentaaporteView> finalData = new LinkedHashMap<Date,AportesCuentaaporteView>();
			
			while (beginCalendar.compareTo(endCalendar) <= 0) {
				AportesCuentaaporteView aportesCuentaaporteView = new AportesCuentaaporteView();
				AportesCuentaaporteViewPK cuentaaporteViewPK =  new AportesCuentaaporteViewPK();
						
				aportesCuentaaporteView.setNumerocuentaaporte(cuentaaporte.getNumerocuentaaporte());
				aportesCuentaaporteView.setTotal(new Moneda());
				cuentaaporteViewPK.setIdcuentaaporte(cuentaaporte.getIdcuentaaporte());
				cuentaaporteViewPK.setMes(beginCalendar.getTime());	
				
				aportesCuentaaporteView.setId(cuentaaporteViewPK);
				
				finalData.put(beginCalendar.getTime(), aportesCuentaaporteView);
				
				beginCalendar.add(Calendar.MONTH, 1);
			}
			
			for (AportesCuentaaporteView aportesCuentaaporteView : aportesCuentaaporteViews) {
				Date date = aportesCuentaaporteView.getId().getMes();
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				
				calendar.set(Calendar.DATE, 1);
				calendar.clear(Calendar.HOUR_OF_DAY);
				calendar.clear(Calendar.HOUR);
				calendar.clear(Calendar.MINUTE);
				calendar.clear(Calendar.SECOND);
				calendar.clear(Calendar.MILLISECOND);
				
				if(finalData.containsKey(calendar.getTime())){
					finalData.remove(calendar.getTime());
				}
			}
			
			aportesCuentaaporteViews =  new ArrayList<AportesCuentaaporteView>(finalData.values());
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		
		return aportesCuentaaporteViews;
	}

	@Override
	public List<CuentaaporteView> findCuentaaporteView(Tipodocumento tipodocumento, String campoBusqueda) throws Exception {
		List<CuentaaporteView> cuentaaporteViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idtipodocumento", tipodocumento.getIdtipodocumento());
			parameters.put("estado", true);
			parameters.put("searched", "%" + campoBusqueda + "%");

			cuentaaporteViews = cuentaaporteViewDAO.findByNamedQuery(CuentaaporteView.f_tipodocumento_estado_searched,parameters,10);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporteViews;
	}

	@Override
	public List<CuentaaporteView> findCuentaaporteView(String campoBusqueda) throws Exception {
		List<CuentaaporteView> cuentaaporteViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("estado", true);
			parameters.put("searched", "%" + campoBusqueda.toUpperCase() + "%");

			cuentaaporteViews = cuentaaporteViewDAO.findByNamedQuery(CuentaaporteView.f_estado_searched,parameters,10);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporteViews;
	}

	@Override
	public Transaccioncuentaaporte cancelarCuentaaporte(Caja caja, Cuentaaporte cuentaaporte, Date fechaCancelacion, Usuario usuario) throws Exception {
		Transaccioncuentaaporte transaccioncuentaaporte = null;
		try {
			cuentaaporte = find(cuentaaporte.getIdcuentaaporte());			
				
			//realizar la transaccion de retiro de todo el dinero			
			transaccioncuentaaporte = new Transaccioncuentaaporte();
			transaccioncuentaaporte.setCuentaaporte(cuentaaporte);
			transaccioncuentaaporte.setEstado(true);
			transaccioncuentaaporte.setMesafecta(null);
			transaccioncuentaaporte.setMonto(cuentaaporte.getSaldo());
			transaccioncuentaaporte.setReferencia(null);
			transaccioncuentaaporte.setSaldodisponible(cuentaaporte.getSaldo().subtract(transaccioncuentaaporte.getMonto()).getValue());
			transaccioncuentaaporte.setTipomoneda(cuentaaporte.getTipomoneda());
			transaccioncuentaaporte.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO));			
			transaccioncuentaaporte = transaccionCajaServiceLocal.retiro(caja, cuentaaporte, transaccioncuentaaporte, usuario);
			//desactivar al socio y la cuenta de aportes
			Socio socio = socioServiceLocal.find(cuentaaporte);
			socioServiceLocal.desactivarSocio(socio);
				
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncuentaaporte;
	}

	
	
	
	
	
	
	@Override
	public Cuentaaporte create(Cuentaaporte cuentaaporte) throws Exception {
		try {
			cuentaaporteDAO.create(cuentaaporte);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporte;
	}
	
	@Override
	public void update(Cuentaaporte oCuentaaporte) throws Exception {	
		try {
			cuentaaporteDAO.update(oCuentaaporte);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}	
	}
	
	@Override
	public Cuentaaporte find(Object id) throws Exception {
		Cuentaaporte cuentaaporte = null;
		try {
			cuentaaporte = cuentaaporteDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporte;
	}

	@Override
	public void delete(Cuentaaporte oCuentaaporte) throws Exception {
		try {
			cuentaaporteDAO.delete(oCuentaaporte);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public Collection<Cuentaaporte> findByNamedQuery(String queryName) throws Exception {
		try {
			return cuentaaporteDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public Collection<Cuentaaporte> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		try {
			return cuentaaporteDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public List<Cuentaaporte> findByNamedQuery(String cuentaaporte, Map<String, Object> parameters) throws Exception {
		try {
			return cuentaaporteDAO.findByNamedQuery(cuentaaporte, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public List<Cuentaaporte> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		try {
			return cuentaaporteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public Cuentaaporte findByNumerocuenta(String numerocuenta) throws Exception {
		Cuentaaporte cuentaaporte;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("numerocuentaaporte", numerocuenta);

			List<Cuentaaporte> cuentaaportes = cuentaaporteDAO.findByNamedQuery(Cuentaaporte.findByNumerocuenta,parameters);
			if (cuentaaportes.size() == 1) {
				cuentaaporte = cuentaaportes.get(0);
			} else {
				throw new Exception("No se encontro cuenta aportes valida");
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporte;
	}

}
