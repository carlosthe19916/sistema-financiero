package org.ventura.control;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import javax.persistence.TemporalType;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.CuentaaporteServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.dao.impl.AportesCuentaaporteViewDAO;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaaporteDAO;
import org.ventura.dao.impl.CuentaaporteViewDAO;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteViewPK;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Named
@Stateless
@Local(CuentaaporteServiceLocal.class)
@Remote(CuentaaporteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
	
	@Inject
	private Log log;

	@Override
	public Cuentaaporte createCuentaAporteWithPersonanatural(Cuentaaporte cuentaaporte) throws Exception {
		try {	
			cuentaaporteDAO.create(cuentaaporte);
			
			//Socio socio = cuentaaporte.getSocio();
			//socio.setCuentaaporte(cuentaaporte);
			//socio = buscarSocioPersonaNatural(socio);			
		} catch(PreexistingEntityException e){
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error:" + e.getMessage());
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaaporte;
	}

	@Override
	public Cuentaaporte createCuentaAporteWithPersonajuridica(Cuentaaporte cuentaaporte) throws Exception {
		try {	
			cuentaaporteDAO.create(cuentaaporte);
			
			/*Socio socio = new Socio();
			socio.setCuentaaporte(cuentaaporte);
			socio = buscarSocioPersonaNatural(socio);
						
			cuentaaporteDAO.create(cuentaaporte);		*/	
		} catch(PreexistingEntityException e){
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error:" + e.getMessage());
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaaporte;
	}
	
	protected Socio buscarSocioPersonaNatural(Socio socio) throws PreexistingEntityException, Exception {
		/*if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("dni", socio.getDni());
			List<Socio> result = socioServiceLocal.findByNamedQuery(Socio.FindByDni, parameters);
			if (result.size() == 0) {
				socioServiceLocal.create(socio);
			} else {
				throw new PreexistingEntityException("La Persona Natural ya tiene una cuenta de aportes Activa");
			}
		}*/
		return socio;
	}

	protected Socio buscarSocioPersonaJuridica(Socio socio) throws PreexistingEntityException, Exception {
		/*if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ruc", socio.getDni());
			List<Socio> result = socioServiceLocal.findByNamedQuery(Socio.FindByRuc, parameters);
			if (result.size() == 0) {
				socioServiceLocal.create(socio);
			} else {
				throw new PreexistingEntityException("La Persona Juridica ya tiene una cuenta de aportes Activa");
			}
		}*/
		return socio;
	}

	@Override
	public Cuentaaporte find(Object id) {
		try {
			return cuentaaporteDAO.find(id);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void delete(Cuentaaporte oCuentaaporte) {
		try {
			cuentaaporteDAO.delete(oCuentaaporte);
		} catch (TransactionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Cuentaaporte oCuentaaporte) {
		/*
		 * try { return cuentaahorroDAO.update(oCuentaahorro); } catch
		 * (RollbackFailureException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
	}

	@Override
	public Collection<Cuentaaporte> findByNamedQuery(String queryName) {
		try {
			return cuentaaporteDAO.findByNamedQuery(queryName);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Cuentaaporte> findByNamedQuery(String queryName,
			int resultLimit) {
		try {
			return cuentaaporteDAO.findByNamedQuery(queryName, resultLimit);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cuentaaporte> findByNamedQuery(String cuentaaporte, Map<String, Object> parameters) {
		try {
			return cuentaaporteDAO.findByNamedQuery(cuentaaporte, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Beneficiario> findByNamedQueryBeneficiario(String beneficiario,
			Map<String, Object> parameters) {
		try {
			return beneficiariocuentaDAO.findByNamedQuery(beneficiario, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Accionista> findByNamedQueryAccionista(String accionista,
			Map<String, Object> parameters) {
		try {
			return accionistaDAO.findByNamedQuery(accionista, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cuentaaporte> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		try {
			return cuentaaporteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void removeBeneficiario(String cuentaAporte, Object parameters) throws Exception {
		try {
			cuentaaporteDAO.executeQuerry(cuentaAporte, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}
	
	@Override
	public void updateBeneficiario(Socio oSocio) throws Exception{
		try{
			/*List<Beneficiario> beneficiarios = oSocio.getCuentaaporte().getBeneficiarios();
			if(beneficiarios != null){
				for (Iterator<Beneficiario> iterator = beneficiarios.iterator(); iterator.hasNext();) {
					Beneficiario beneficiariocuenta = (Beneficiario) iterator.next();
					beneficiariocuenta.setIdbeneficiariocuenta(null);
					beneficiariocuenta.setCuentaaporte(oSocio.getCuentaaporte());
					
					createBeneficiario(beneficiariocuenta);
				}
			}*/
		}catch(Exception e){
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	private void createBeneficiario(Beneficiario beneficiariocuenta) throws Exception{
		try {
			beneficiariocuentaDAO.create(beneficiariocuenta);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
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

	@Override
	public List<CuentaaporteView> findByDni(String dni) throws Exception {
		List<CuentaaporteView> cuentaaporteViews;
		try {
			 Map<String, Object> parameters = new HashMap<String, Object>();
			 parameters.put("dni", "%" + dni + "%");
			 cuentaaporteViews = cuentaaporteViewDAO.findByNamedQuery(CuentaaporteView.findByNumerocuenta,parameters,100);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporteViews;
	}

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
	public List<CuentaaporteView> findCuentaaporteViewByDni(String dni) throws Exception {
		List<CuentaaporteView> cuentaaporteViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("dni","%" +dni+ "%" );

			cuentaaporteViews = cuentaaporteViewDAO.findByNamedQuery(CuentaaporteView.findByLikeDni,parameters,100);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporteViews;
	}

	@Override
	public List<CuentaaporteView> findCuentaaporteViewByRuc(String ruc) throws Exception {
		List<CuentaaporteView> cuentaaporteViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ruc", "%"+ruc+"%");

			cuentaaporteViews = cuentaaporteViewDAO.findByNamedQuery(CuentaaporteView.findByLikeRuc,parameters,100);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporteViews;
	}

	@Override
	public List<CuentaaporteView> findCuentaaporteViewByNombre(String nombre) throws Exception {
		List<CuentaaporteView> cuentaaporteViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nombres", "%" + nombre + "%");

			cuentaaporteViews = cuentaaporteViewDAO.findByNamedQuery(CuentaaporteView.findByLikeNombre,parameters,100);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporteViews;
	}

	@Override
	public List<CuentaaporteView> findCuentaaporteViewByRazonsocial(String razonsocial) throws Exception {
		List<CuentaaporteView> cuentaaporteViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("razonsocial", "%" + razonsocial + "%");

			cuentaaporteViews = cuentaaporteViewDAO.findByNamedQuery(CuentaaporteView.findByLikeRazonsocial,parameters,100);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporteViews;
	}
	
	@Override
	public List<AportesCuentaaporteView> getTableAportes(Integer idcuentaaporte,Date startDate, Date endDate) throws Exception{
		List<AportesCuentaaporteView> aportesCuentaaporteViews = null;
		try {		
			Cuentaaporte cuentaaporte  = cuentaaporteDAO.find(idcuentaaporte);
					
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idcuentaaporte", idcuentaaporte);
			parameters.put("startDate", startDate);
			parameters.put("endDate", endDate);
			aportesCuentaaporteViews = aportesCuentaaporteViewDAO.findByNamedQuery(AportesCuentaaporteView.findBetweenDates,parameters);
						
			
			
			//completando las fechas
			Calendar beginCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			beginCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
					
			beginCalendar.set(Calendar.DATE, 1);
			endCalendar.set(Calendar.DATE, 1);
			
			beginCalendar.clear(Calendar.HOUR_OF_DAY);
			beginCalendar.clear(Calendar.HOUR);
			beginCalendar.clear(Calendar.MINUTE);
			beginCalendar.clear(Calendar.SECOND);
			beginCalendar.clear(Calendar.MILLISECOND);
			
			endCalendar.clear(Calendar.HOUR_OF_DAY);
			endCalendar.clear(Calendar.HOUR);
			endCalendar.clear(Calendar.MINUTE);
			endCalendar.clear(Calendar.SECOND);
			endCalendar.clear(Calendar.MILLISECOND);
			
			//poniendo la data final
			LinkedHashMap<Date, AportesCuentaaporteView> finalData = new LinkedHashMap<Date,AportesCuentaaporteView>();
			
			while (beginCalendar.compareTo(endCalendar) <= 0) {
				/*AportesCuentaaporteView aportesCuentaaporteView = new AportesCuentaaporteView();
				aportesCuentaaporteView.setIdcuentaaporte(cuentaaporte.getIdcuentaaporte());
				aportesCuentaaporteView.setNumerocuentaaporte(cuentaaporte.getNumerocuentaaporte());
				aportesCuentaaporteView.setTotal(new Moneda());
				aportesCuentaaporteView.setMes(beginCalendar.getTime());	
				
				finalData.put(beginCalendar.getTime(), aportesCuentaaporteView);
				
				beginCalendar.add(Calendar.MONTH, 1);*/
			}
			
			
			for (AportesCuentaaporteView aportesCuentaaporteView : aportesCuentaaporteViews) {
				//finalData.put(aportesCuentaaporteView.getMes(), aportesCuentaaporteView);
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
	public List<AportesCuentaaporteView> getTableAportesPorpagar(Integer idcuentaaporte,Date startDate, Date endDate) throws Exception{
		List<AportesCuentaaporteView> aportesCuentaaporteViews;
		try {				
			Cuentaaporte cuentaaporte  = cuentaaporteDAO.find(idcuentaaporte);
					
			//completando las fechas
			Calendar beginCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			beginCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
					
			beginCalendar.set(Calendar.DATE, 1);		
			beginCalendar.clear(Calendar.HOUR_OF_DAY);
			beginCalendar.clear(Calendar.HOUR);
			beginCalendar.clear(Calendar.MINUTE);
			beginCalendar.clear(Calendar.SECOND);
			beginCalendar.clear(Calendar.MILLISECOND);
			
			endCalendar.set(Calendar.DATE, 1);
			endCalendar.clear(Calendar.HOUR_OF_DAY);
			endCalendar.clear(Calendar.HOUR);
			endCalendar.clear(Calendar.MINUTE);
			endCalendar.clear(Calendar.SECOND);
			endCalendar.clear(Calendar.MILLISECOND);
			
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

}
