package org.ventura.control;

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
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.CuentaaporteServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaaporteDAO;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Accionista;
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
	
	@Inject
	private Log log;

	@Override
	public Cuentaaporte createCuentaAporteWithPersonanatural(Cuentaaporte cuentaaporte) throws Exception {
		try {	
			generarDatosDeRegistro(cuentaaporte);	
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
			generarDatosDeRegistro(cuentaaporte);	
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
		if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("dni", socio.getDni());
			List<Socio> result = socioServiceLocal.findByNamedQuery(Socio.FindByDni, parameters);
			if (result.size() == 0) {
				socioServiceLocal.create(socio);
			} else {
				throw new PreexistingEntityException("La Persona Natural ya tiene una cuenta de aportes Activa");
			}
		}
		return socio;
	}

	protected Socio buscarSocioPersonaJuridica(Socio socio) throws PreexistingEntityException, Exception {
		if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ruc", socio.getDni());
			List<Socio> result = socioServiceLocal.findByNamedQuery(Socio.FindByRuc, parameters);
			if (result.size() == 0) {
				socioServiceLocal.create(socio);
			} else {
				throw new PreexistingEntityException("La Persona Juridica ya tiene una cuenta de aportes Activa");
			}
		}
		return socio;
	}
		
	private void generarDatosDeRegistro(Cuentaaporte cuentaaporte) {		
		cuentaaporte.setIdestadocuenta(1);
		
		Tipomoneda tipomoneda = new Tipomoneda();
		tipomoneda.setIdtipomoneda(1);
		tipomoneda.setDenominacion("NUEVOS SOLES");
		tipomoneda.setEstado(true);
		cuentaaporte.setTipomoneda(tipomoneda);
		
		cuentaaporte.setFechaapertura(Calendar.getInstance().getTime());
		cuentaaporte.setSaldo(0);		
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
	public List<Beneficiariocuenta> findByNamedQueryBeneficiario(String beneficiario,
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

}
