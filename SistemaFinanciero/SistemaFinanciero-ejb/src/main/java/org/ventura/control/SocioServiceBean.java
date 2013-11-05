package org.ventura.control;

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

import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.remote.SocioServiceRemote;
import org.ventura.dao.impl.SocioDAO;
import org.ventura.dao.impl.ViewSocioPJDAO;
import org.ventura.dao.impl.ViewSocioPNDAO;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.socio.ViewSocioPJ;
import org.ventura.entity.schema.socio.ViewSocioPN;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.logger.Log;
import org.ventura.util.validate.Validator;

@Stateless
@Local(SocioServiceLocal.class)
@Remote(SocioServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SocioServiceBean implements SocioServiceLocal {

	@Inject
	private Log log;

	@EJB
	private SocioDAO socioDAO;
	@EJB
	private ViewSocioPNDAO viewSocioPNDAO;
	@EJB
	private ViewSocioPJDAO viewSocioPJDAO;
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB
	private PersonajuridicaServiceLocal personajuridicaServiceLocal;

	@Override
	public Socio create(Socio socio) throws Exception {
		try {
			socio.setFechaasociado(Calendar.getInstance().getTime());
			socio.setEstado(true);

			Map<String, Object> parameters = new HashMap<String, Object>();
			List<Socio> resultList = null;	
			boolean isValidSocio = Validator.validateSocio(socio);
			if (isValidSocio == true) {
				Personanatural personanatural = socio.getPersonanatural();
				Personajuridica personajuridica = socio.getPersonajuridica();
				if (personanatural != null) {
					Object key = personanatural.getDni();
					Object result = personanaturalServiceLocal.find(key);
					if (result == null) {
						personanaturalServiceLocal.create(personanatural);
					}
					parameters.put("dni", socio.getDni());
					resultList = socioDAO.findByNamedQuery(Socio.FindByDni, parameters);
				}
				if (personajuridica != null) {
					Object key = personajuridica.getRuc();
					Object result = personajuridicaServiceLocal.find(key);
					if (result == null) {
						personajuridicaServiceLocal.create(personajuridica);
					}
					parameters.put("ruc", socio.getRuc());
					resultList = socioDAO.findByNamedQuery(Socio.FindByRuc, parameters);
				}
				if (resultList.size() == 0) {
					socioDAO.create(socio);
				} else {
					throw new PreexistingEntityException("El cliente ya tiene una cuenta de aportes Activa");
				}
			} else {
				log.error("Exception: method Validator(socio) is false");
				throw new IllegalEntityException("Datos de Socio Invalidos");
			}
		} catch (IllegalEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());
		} catch (PreexistingEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear el Socio");
		}
		return socio;
	}

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
}
