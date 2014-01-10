package org.ventura.control;

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

import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.remote.SocioServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.dao.impl.CuentaaporteDAO;
import org.ventura.dao.impl.SocioDAO;
import org.ventura.dao.impl.ViewSocioPJDAO;
import org.ventura.dao.impl.ViewSocioPNDAO;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.socio.ViewSocioPJ;
import org.ventura.entity.schema.socio.ViewSocioPN;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadocuentaType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.validate.Validator;

@Named
@Stateless
@Local(SocioServiceLocal.class)
@Remote(SocioServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SocioServiceBean implements SocioServiceLocal {

	@Inject
	private Log log;

	@EJB
	private SocioDAO socioDAO;
	@EJB
	private AccionistaDAO accionistaDAO;
	@EJB 
	private CuentaaporteDAO cuentaaporteDAO;
	@EJB
	private ViewSocioPNDAO viewSocioPNDAO;
	@EJB
	private ViewSocioPJDAO viewSocioPJDAO;
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB
	private PersonajuridicaServiceLocal personajuridicaServiceLocal;
	

	@Override
	public Socio find(Object id) throws Exception {
		Socio socio = null;
		try {
			socio = socioDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return socio;
	}
	
	@Override
	public Socio findByDNI(Object dni) throws Exception {
		Socio oSocio = null;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("dni", dni);
		
		List<Socio> list = null;
		try {
			list = socioDAO.findByNamedQuery(Socio.FindByDni, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, intÃ©ntelo nuevamente");
		}
		
		for (Iterator<Socio> iterator = list.iterator(); iterator.hasNext();) {
			oSocio = (Socio) iterator.next();
		}
		return oSocio;
	}
	
	@Override
	public Socio findByRUC(Object ruc) throws Exception {
		Socio oSocio = null;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ruc", ruc);
		
		List<Socio> list = null;
		try {
			list = socioDAO.findByNamedQuery(Socio.FindByRuc, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		
		for (Iterator<Socio> iterator = list.iterator(); iterator.hasNext();) {
			oSocio = (Socio) iterator.next();
		}
		return oSocio;
	}

	@Override
	public void delete(Socio socio) throws Exception {
		try {
			socioDAO.delete(socio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public void update(Socio socio) throws Exception {
		try {
			socioDAO.update(socio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
	}

	@Override
	public Collection<Socio> findByNamedQuery(String queryName)
			throws Exception {
		Collection<Socio> collection = null;
		try {
			collection = socioDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public Collection<Socio> findByNamedQuery(String queryName, int resultLimit)
			throws Exception {
		Collection<Socio> collection = null;
		try {
			collection = socioDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return collection;
	}

	@Override
	public List<Socio> findByNamedQuery(String socio,
			Map<String, Object> parameters) throws Exception {
		List<Socio> list = null;
		try {
			list = socioDAO.findByNamedQuery(socio, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}
	
	@Override
	public List<ViewSocioPN> findByNamedQueryViewSocioPN(String viewSocioPN, Map<String, Object> parameters) throws Exception {
		List<ViewSocioPN> list = null;
		try {
			list = viewSocioPNDAO.findByNamedQuery(viewSocioPN, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}
	
	@Override
	public List<ViewSocioPJ> findByNamedQueryViewSocioPJ(String viewSocioPJ, Map<String, Object> parameters) throws Exception {
		List<ViewSocioPJ> list = null;
		try {
			list = viewSocioPJDAO.findByNamedQuery(viewSocioPJ, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}
		return list;
	}

	@Override
	public List<Socio> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Socio> list = null;
		try {
			list = socioDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error interno, inténtelo nuevamente");
		}

		return list;
	}

	@Override
	public void createSocioPersonanatural(Socio socio) throws Exception {
		try {				
			socio.setPersonajuridica(null);
			
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", socio.getPersonanatural().getTipodocumento());
			parameters.put("numerodocumento", socio.getPersonanatural().getNumerodocumento());			
			List<Socio> resultList = socioDAO.findByNamedQuery(Socio.FindSocioPNByTipodocumentoNumerodocumento,parameters,1);
				
			if(resultList.size() != 0){
				throw new PreexistingEntityException("El cliente ya tiene una cuenta de aportes Activa");
			}
			
			Personanatural personanatural = socio.getPersonanatural();
			Personanatural apoderado = socio.getApoderado();
			
			Tipodocumento tipodocumentoSocio = personanatural.getTipodocumento();
			String numerodocumentoSocio = personanatural.getNumerodocumento();
			Personanatural personanaturalBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoSocio, numerodocumentoSocio);
			if(personanaturalBD != null){
				personanaturalBD.setTipodocumento(personanatural.getTipodocumento());
				personanaturalBD.setNumerodocumento(personanatural.getNumerodocumento());
				personanaturalBD.setApellidopaterno(personanatural.getApellidopaterno());
				personanaturalBD.setApellidomaterno(personanatural.getApellidomaterno());
				personanaturalBD.setNombres(personanatural.getNombres());
				personanaturalBD.setFechanacimiento(personanatural.getFechanacimiento());
				personanaturalBD.setSexo(personanatural.getSexo());
				personanaturalBD.setEstadocivil(personanatural.getEstadocivil());
				personanaturalBD.setOcupacion(personanatural.getOcupacion());
				personanaturalBD.setDireccion(personanatural.getDireccion());
				personanaturalBD.setReferencia(personanatural.getReferencia());
				personanaturalBD.setTelefono(personanatural.getTelefono());
				personanaturalBD.setCelular(personanatural.getCelular());
				personanaturalServiceLocal.update(personanaturalBD);
				personanatural = personanaturalBD;
			} else {
				personanaturalServiceLocal.create(personanatural);
			}
			
			if(apoderado != null){
				Tipodocumento tipodocumentoApoderado = apoderado.getTipodocumento();
				String numerodocumentoApoderado = apoderado.getNumerodocumento();
				Personanatural apoderadoBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoApoderado, numerodocumentoApoderado);			
				if(apoderadoBD != null){
					apoderadoBD.setTipodocumento(apoderado.getTipodocumento());
					apoderadoBD.setNumerodocumento(apoderado.getNumerodocumento());
					apoderadoBD.setApellidopaterno(apoderado.getApellidopaterno());
					apoderadoBD.setApellidomaterno(apoderado.getApellidomaterno());
					apoderadoBD.setNombres(apoderado.getNombres());
					apoderadoBD.setFechanacimiento(apoderado.getFechanacimiento());
					apoderadoBD.setSexo(apoderado.getSexo());
					personanaturalServiceLocal.update(apoderadoBD);
					apoderado = apoderadoBD;
				} else {
					personanaturalServiceLocal.create(apoderado);
				}
			}
					
			Cuentaaporte cuentaaporte = new Cuentaaporte();		
			cuentaaporte.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO));
			cuentaaporte.setTipomoneda(ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL));
			cuentaaporte.setFechaapertura(Calendar.getInstance().getTime());
			cuentaaporte.setFechacierre(null);
			cuentaaporte.setSaldo(new Moneda());				
			cuentaaporteDAO.create(cuentaaporte);
			
			socio.setEstado(true);
			socio.setFechaasociado(Calendar.getInstance().getTime());
			socio.setCuentaaporte(cuentaaporte);
			socio.setPersonanatural(personanatural);
			socio.setApoderado(apoderado);
			
			socioDAO.create(socio);
			
		} catch (IllegalEntityException | PreexistingEntityException e) {			
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());	
		} catch (Exception e) {
			socio.getCuentaaporte().setIdcuentaaporte(null);			
			socio.setIdsocio(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		} 	
	}

	@Override
	public void createSocioPersonajuridica(Socio socio) throws Exception {
		try {				
			socio.setPersonanatural(null);
			
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", socio.getPersonajuridica().getTipodocumento());
			parameters.put("numerodocumento", socio.getPersonajuridica().getNumerodocumento());			
			List<Socio> resultList = socioDAO.findByNamedQuery(Socio.FindSocioPJByTipodocumentoNumerodocumento,parameters,1);
				
			if(resultList.size() != 0){
				throw new PreexistingEntityException("El cliente ya tiene una cuenta de aportes Activa");
			}
				
			Personajuridica personajuridica = socio.getPersonajuridica();
			Personanatural representanteLegal = personajuridica.getRepresentanteLegal();
			List<Accionista> accionistas = personajuridica.getAccionistas();	
			
			Tipodocumento tipodocumentoRepresentanteLegal = representanteLegal.getTipodocumento();
			String numeroDocumentoRepresentanteLegal = representanteLegal.getNumerodocumento();
			Personanatural representanteLegalBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoRepresentanteLegal, numeroDocumentoRepresentanteLegal);
			
			if(representanteLegalBD != null){
				representanteLegalBD.setTipodocumento(representanteLegal.getTipodocumento());
				representanteLegalBD.setNumerodocumento(representanteLegal.getNumerodocumento());
				representanteLegalBD.setApellidopaterno(representanteLegal.getApellidopaterno());
				representanteLegalBD.setApellidomaterno(representanteLegal.getApellidomaterno());
				representanteLegalBD.setNombres(representanteLegal.getNombres());
				representanteLegalBD.setFechanacimiento(representanteLegal.getFechanacimiento());
				representanteLegalBD.setSexo(representanteLegal.getSexo());		
				personanaturalServiceLocal.update(representanteLegalBD);
				
				representanteLegal = representanteLegalBD;
			} else {
				personanaturalServiceLocal.create(representanteLegal);
			}
			
			Tipodocumento tipodocumentoSocio = personajuridica.getTipodocumento();
			String numerodocumentoSocio = personajuridica.getNumerodocumento();
			Personajuridica personajuridicaBD =  personajuridicaServiceLocal.findByTipodocumento(tipodocumentoSocio, numerodocumentoSocio);
			
			if(personajuridicaBD != null){
				personajuridicaBD.setTipodocumento(personajuridica.getTipodocumento());
				personajuridicaBD.setNumerodocumento(personajuridica.getNumerodocumento());
				personajuridicaBD.setRazonsocial(personajuridica.getRazonsocial());
				personajuridicaBD.setNombrecomercial(personajuridica.getNombrecomercial());
				personajuridicaBD.setActividadprincipal(personajuridica.getActividadprincipal());
				personajuridicaBD.setFechaconstitucion(personajuridica.getFechaconstitucion());
				personajuridicaBD.setTipoempresa(personajuridica.getTipoempresa());
				personajuridicaBD.setFindelucro(personajuridicaBD.getFindelucro());
				personajuridicaBD.setDireccion(personajuridica.getDireccion());
				personajuridicaBD.setReferencia(personajuridica.getReferencia());
				personajuridicaBD.setTelefono(personajuridica.getTelefono());
				personajuridicaBD.setCelular(personajuridica.getCelular());
				personajuridicaBD.setEmail(personajuridica.getEmail());
				personajuridicaServiceLocal.update(personajuridicaBD);
				personajuridica = personajuridicaBD;
			} else {
				personajuridicaServiceLocal.create(personajuridica);
			}
			
			for (Accionista accionista : personajuridica.getAccionistas()) {
				accionistaDAO.delete(accionista);
			}
			
			for (Accionista accionista : accionistas) {
				Personanatural accionistaPersonanatural = accionista.getPersonanatural();
				Tipodocumento tipodocumentoAccionista = accionistaPersonanatural.getTipodocumento();
				String numeroDocumentoAccionista = accionistaPersonanatural.getNumerodocumento();
				Personanatural accionistaPersonanaturalBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoAccionista, numeroDocumentoAccionista);
				
				if(accionistaPersonanaturalBD != null){
					accionistaPersonanaturalBD.setTipodocumento(accionistaPersonanatural.getTipodocumento());
					accionistaPersonanaturalBD.setNumerodocumento(accionistaPersonanatural.getNumerodocumento());
					accionistaPersonanaturalBD.setApellidopaterno(accionistaPersonanatural.getApellidopaterno());
					accionistaPersonanaturalBD.setApellidomaterno(accionistaPersonanatural.getApellidomaterno());
					accionistaPersonanaturalBD.setNombres(accionistaPersonanatural.getNombres());
					accionistaPersonanaturalBD.setFechanacimiento(accionistaPersonanatural.getFechanacimiento());
					accionistaPersonanaturalBD.setSexo(accionistaPersonanatural.getSexo());		
					personanaturalServiceLocal.update(accionistaPersonanaturalBD);
					accionistaPersonanatural = accionistaPersonanaturalBD;
				} else {
					personanaturalServiceLocal.create(accionistaPersonanatural);
				}
				
				Accionista accionistaNew = new Accionista();		
				accionistaNew.setPersonanatural(accionistaPersonanatural);
				accionistaNew.setPersonajuridica(personajuridica);
				accionistaNew.setPorcentajeparticipacion(accionista.getPorcentajeparticipacion());
				accionistaDAO.create(accionistaNew);
			}
			
			Cuentaaporte cuentaaporte = new Cuentaaporte();		
			cuentaaporte.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO));
			cuentaaporte.setTipomoneda(ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL));
			cuentaaporte.setFechaapertura(Calendar.getInstance().getTime());
			cuentaaporte.setFechacierre(null);
			cuentaaporte.setSaldo(new Moneda());				
			cuentaaporteDAO.create(cuentaaporte);
			
			socio.setEstado(true);
			socio.setFechaasociado(Calendar.getInstance().getTime());
			socio.setCuentaaporte(cuentaaporte);
			socio.setPersonajuridica(personajuridica);
			
			socioDAO.create(socio);
			
		} catch (IllegalEntityException | PreexistingEntityException e) {					
			socio.setIdsocio(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());	
		} catch (Exception e) {			
			socio.setIdsocio(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		} 	
	}
}
