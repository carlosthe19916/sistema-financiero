package org.ventura.control;

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

import org.ventura.boundary.local.CuentaaporteServiceLocal;
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
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.socio.ViewSocioPJ;
import org.ventura.entity.schema.socio.ViewSocioPN;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadocuentaType;
import org.ventura.util.maestro.ProduceObject;

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
	@EJB
	private CuentaaporteServiceLocal cuentaaporteServiceLocal;
	
	@Override
	public Socio createSocioPersonanatural(Socio socio) throws Exception {
		try {				
			Socio socioDB = find(socio.getPersonanatural());
			if(socioDB != null){
				if(socioDB.getCuentaaporte() != null)
					throw new PreexistingEntityException("El cliente ya tiene una cuenta de aportes activa");
			}
			
			Personanatural personanatural = socio.getPersonanatural();
			Personanatural apoderado = socio.getApoderado();	
			personanatural = personanaturalServiceLocal.createIfNotExistsUpdateIfExist(personanatural);
			if(apoderado != null){
				apoderado = personanaturalServiceLocal.createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(apoderado);
			}
							
			Cuentaaporte cuentaaporte = new Cuentaaporte();		
			cuentaaporte.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO));
			cuentaaporte.setTipomoneda(ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL));
			cuentaaporte.setFechaapertura(Calendar.getInstance().getTime());
			cuentaaporte.setFechacierre(null);
			cuentaaporte.setSaldo(new Moneda());				
			cuentaaporte = cuentaaporteServiceLocal.create(cuentaaporte);
			
			if(socioDB == null){
				if(apoderado != null) {
					socio.setApoderado(apoderado);
				}
				socio.setEstado(true);
				socio.setFechaasociado(Calendar.getInstance().getTime());
				socio.setCuentaaporte(cuentaaporte);
				socio.setPersonanatural(personanatural);
				socioDAO.create(socio);
			} else {
				if(apoderado != null) {
					socioDB.setApoderado(apoderado);
				}
				socioDB.setCuentaaporte(cuentaaporte);
				socioDAO.update(socioDB);
			}
				
			cuentaaporte = cuentaaporteServiceLocal.find(cuentaaporte.getIdcuentaaporte());
			
			return socio;
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		} 	
	}

	@Override
	public Socio createSocioPersonajuridica(Socio socio) throws Exception {
		try {				
			Socio socioDB = find(socio.getPersonajuridica());
			if(socioDB != null){
				throw new PreexistingEntityException("El cliente ya tiene una cuenta de aportes activa");
			}
				
			Personajuridica personajuridica = socio.getPersonajuridica();
			personajuridica = personajuridicaServiceLocal.createIfNotExistsUpdateIfExist(personajuridica);
					
			Cuentaaporte cuentaaporte = new Cuentaaporte();		
			cuentaaporte.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO));
			cuentaaporte.setTipomoneda(ProduceObject.getTipomoneda(TipomonedaType.NUEVO_SOL));
			cuentaaporte.setFechaapertura(Calendar.getInstance().getTime());
			cuentaaporte.setFechacierre(null);
			cuentaaporte.setSaldo(new Moneda());				
			cuentaaporte = cuentaaporteServiceLocal.create(cuentaaporte);
			
			socio.setEstado(true);
			socio.setFechaasociado(Calendar.getInstance().getTime());
			socio.setCuentaaporte(cuentaaporte);
			socio.setPersonajuridica(personajuridica);	
			socioDAO.create(socio);
			
			return socio;
		} catch (Exception e) {			
			socio.setIdsocio(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		} 	
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
			throw e;
		}
		return socio;
	}
	
	@Override
	public Socio find(Personanatural personanatural) throws Exception {
		Socio socio = null;
		try {
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", personanatural.getTipodocumento());
			parameters.put("numerodocumento", personanatural.getNumerodocumento());			
			List<Socio> resultList = findByNamedQuery(Socio.fPN_tipodocumento_numerodocumento,parameters,2);
			if(resultList.size() == 0){
				socio = null;
			} else {
				if(resultList.size() == 1){
					socio = resultList.get(0);
				} else {
					throw new PreexistingEntityException("Existen mas de un socio para la persona indicada");
				}	
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return socio;	
	}

	@Override
	public Socio find(Personajuridica personajuridica) throws Exception {
		Socio socio = null;
		try {
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", personajuridica.getTipodocumento());
			parameters.put("numerodocumento", personajuridica.getNumerodocumento());			
			List<Socio> resultList = findByNamedQuery(Socio.fPJ_tipodocumento_numerodocumento,parameters,2);
			if(resultList.size() == 0){
				socio = null;
			} else {
				if(resultList.size() == 1){
					socio = resultList.get(0);
				} else {
					throw new PreexistingEntityException("Existen mas de un socio para la persona indicada");
				}	
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return socio;	
	}

	@Override
	public Socio find(Cuentaaporte cuentaaporte) throws Exception {
		Socio socio = null;
		try {
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("idcuentaaporte", cuentaaporte.getIdcuentaaporte());			
			List<Socio> resultList = findByNamedQuery(Socio.f_idcuentaaporte,parameters,2);
			if(resultList.size() == 0){
				socio = null;
			} else {
				if(resultList.size() == 1){
					socio = resultList.get(0);
				} else {
					throw new PreexistingEntityException("Existen mas de un socio para la cuenta indicada");
				}	
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return socio;	
	}
	
	@Override
	public void update(Socio socio) throws Exception {
		try {
			socioDAO.update(socio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public void delete(Socio socio) throws Exception {
		try {
			socioDAO.delete(socio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public Collection<Socio> findByNamedQuery(String queryName) throws Exception {
		Collection<Socio> collection = null;
		try {
			collection = socioDAO.findByNamedQuery(queryName);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return collection;
	}

	@Override
	public Collection<Socio> findByNamedQuery(String queryName, int resultLimit) throws Exception {
		Collection<Socio> collection = null;
		try {
			collection = socioDAO.findByNamedQuery(queryName, resultLimit);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return collection;
	}

	@Override
	public List<Socio> findByNamedQuery(String socio, Map<String, Object> parameters) throws Exception {
		List<Socio> list = null;
		try {
			list = socioDAO.findByNamedQuery(socio, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return list;
	}

	@Override
	public List<Socio> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception {
		List<Socio> list = null;
		try {
			list = socioDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
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
	public void desactivarSocio(Socio socio) throws Exception {
		try {
			socio = find(socio.getIdsocio());
			Cuentaaporte cuentaaporte = socio.getCuentaaporte();
			
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO);
			List<Cuentabancaria> cuentabancarias = getCuentasBancarias(socio);
			String mensajeErrorCuentasbancariasActivas = "Error al dar de baja al Socio; Cuentas Activas:\n";
			boolean existeCuentasActivas = false;
			for (Cuentabancaria cuentabancaria : cuentabancarias) {
				if(!estadocuenta.equals(cuentabancaria.getEstadocuenta())){		
					existeCuentasActivas = true;
					mensajeErrorCuentasbancariasActivas = mensajeErrorCuentasbancariasActivas + 
							" " + cuentabancaria.getTipocuentabancaria().getDenominacion() 
							+ " Numero:" + cuentabancaria.getNumerocuenta() + "\n";
				}
			}
			if(existeCuentasActivas == true){
				mensajeErrorCuentasbancariasActivas = mensajeErrorCuentasbancariasActivas + "Debe cancelar esta(s) cuenta(s) primero.";
				throw new Exception(mensajeErrorCuentasbancariasActivas);
			}
					
			//desactivar la cuenta de aportes
			cuentaaporte.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO));
			cuentaaporte.setFechacierre(Calendar.getInstance().getTime());
			cuentaaporteDAO.update(cuentaaporte);
			
			//desactivar al socio
			socio.setEstado(false);
			socioDAO.update(socio);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		}
	}

	@Override
	public List<Cuentabancaria> getCuentasBancarias(Socio socio) throws Exception {
		List<Cuentabancaria> list = null;
		try {
			Socio obj = find(socio.getIdsocio());
			list = obj.getCuentasbancarias();
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		}
		return list;
	}

	@Override
	public Cuentaaporte getCuentaaporte(Socio socio) throws Exception {
		Cuentaaporte cuentaaporte = null;
		try {
			socio = find(socio.getIdsocio());
			cuentaaporte = socio.getCuentaaporte();			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentaaporte;
	}

	@Override
	public Socio createSocioPersonanaturalSinCuentaaporte(Socio socio) throws Exception {
		try {				
			Socio socioDB = find(socio.getPersonanatural());
			if(socioDB != null){
				throw new PreexistingEntityException("El cliente ya es socio no se puede crear nuevamente");
			}
			
			Personanatural personanatural = socio.getPersonanatural();
			Personanatural apoderado = socio.getApoderado();	
			personanatural = personanaturalServiceLocal.createIfNotExistsUpdateIfExist(personanatural);
			if(apoderado != null){
				apoderado = personanaturalServiceLocal.createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(apoderado);
			}
			
			socio.setEstado(true);
			socio.setFechaasociado(Calendar.getInstance().getTime());
			socio.setCuentaaporte(null);
			socio.setPersonanatural(personanatural);
			if(apoderado != null) {
				socio.setApoderado(apoderado);
			}					
			socioDAO.create(socio);
			
			return socio;
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		} 	
	}

	@Override
	public Socio createSocioPersonajuridicaSinCuentaaporte(Socio socio) throws Exception {
		try {				
			Socio socioDB = find(socio.getPersonajuridica());
			if(socioDB != null){
				throw new PreexistingEntityException("El cliente ya es socio no se puede crear nuevamente");
			}
				
			Personajuridica personajuridica = socio.getPersonajuridica();
			personajuridica = personajuridicaServiceLocal.createIfNotExistsUpdateIfExist(personajuridica);
			
			socio.setEstado(true);
			socio.setFechaasociado(Calendar.getInstance().getTime());
			socio.setCuentaaporte(null);
			socio.setPersonajuridica(personajuridica);	
			socioDAO.create(socio);
			
			return socio;
		} catch (Exception e) {			
			socio.setIdsocio(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		} 	
	}

	
}
