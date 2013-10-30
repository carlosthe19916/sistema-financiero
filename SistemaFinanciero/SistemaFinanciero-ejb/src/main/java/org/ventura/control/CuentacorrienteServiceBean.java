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
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.CuentacorrienteServiceRemote;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentacorrienteDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentacorriente;
import org.ventura.entity.schema.cuentapersonal.Titularcuenta;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;
import org.ventura.util.validate.Validator;

@Stateless
@Local(CuentacorrienteServiceLocal.class)
@Remote(CuentacorrienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentacorrienteServiceBean implements CuentacorrienteServiceLocal {

	@EJB
	private SocioServiceLocal socioServiceLocal;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	
	@EJB
	private BeneficiariocuentaDAO beneficiariocuentaDAO;
	
	@EJB
	private TitularcuentaDAO titularcuentaDAO;
	
	@EJB
	private CuentacorrienteDAO cuentacorrienteDAO;
	
	@Inject
	Log log;

	@Override
	public Cuentacorriente createCuentaCorrienteWithPersonanatural(Cuentacorriente cuentacorriente) throws Exception {	
		try {
			boolean result = Validator.validateCuentacorriente(cuentacorriente);
			if (result == true) {
				Socio socio = buscarSocioPersonaNatural(cuentacorriente.getSocio());
				cuentacorriente.setSocio(socio);

				crearPersonanaturalForTitulares(cuentacorriente);

				generarDatosDeRegistro(cuentacorriente);
				cuentacorrienteDAO.create(cuentacorriente);
				
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("idcuentacorriente", cuentacorriente.getIdcuentacorriente());
				List<Titularcuenta> titulares = titularcuentaDAO.findByNamedQuery(Titularcuenta.FindAllForCuentacorriente, parameters);
				cuentacorriente.setTitularcuentas(titulares);		
			} else {
				log.error("Exception: method Validator(Cuentacorriente) is false");
				throw new IllegalEntityException("Datos de Cuenta de Ahorro Invalidos");
			}
		} catch (IllegalEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());
		} catch (NonexistentEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear la cuenta de ahorros");
		}
		return cuentacorriente;
	}

	@Override
	public Cuentacorriente createCuentaCorrienteWithPersonajuridica(Cuentacorriente cuentacorriente) throws Exception {
		try {
			boolean result = Validator.validateCuentacorriente(cuentacorriente);
			if (result == true) {
				Socio socio = buscarSocioPersonaJuridica(cuentacorriente.getSocio());
				cuentacorriente.setSocio(socio);

				crearPersonanaturalForTitulares(cuentacorriente);

				generarDatosDeRegistro(cuentacorriente);
				cuentacorrienteDAO.create(cuentacorriente);
				
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("idcuentacorriente", cuentacorriente.getIdcuentacorriente());
				List<Titularcuenta> titulares = titularcuentaDAO.findByNamedQuery(Titularcuenta.FindAllForCuentacorriente, parameters);
				cuentacorriente.setTitularcuentas(titulares);		
			} else {
				log.error("Exception: method Validator(Cuentaahorro) is false");
				throw new IllegalEntityException("Datos de Cuenta de Ahorro Invalidos");
			}
		} catch (IllegalEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());
		} catch (NonexistentEntityException e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception("Error Interno: No se pudo Crear la cuenta de ahorros");
		}
		return cuentacorriente;
	}
	
	protected Socio buscarSocioPersonaNatural(Socio socio) throws NonexistentEntityException, Exception {
		if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("dni", socio.getDni());
			List<Socio> result = socioServiceLocal.findByNamedQuery(Socio.FindByDni, parameters);
			if (result.size() != 0) {
				for (Iterator<Socio> iterator = result.iterator(); iterator.hasNext();) {
					socio = iterator.next();
					break;
				}
			} else {
				throw new NonexistentEntityException("La Persona Natural no tiene una cuenta de aportes registrada");
			}
		}
		return socio;
	}
	
	protected Socio buscarSocioPersonaJuridica(Socio socio) throws NonexistentEntityException, Exception {
		if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ruc", socio.getRuc());
			List<Socio> result = socioServiceLocal.findByNamedQuery(Socio.FindByRuc, parameters);
			if (result.size() != 0) {
				for (Iterator<Socio> iterator = result.iterator(); iterator.hasNext();) {
					socio = iterator.next();
					break;
				}
			} else {
				throw new NonexistentEntityException("La Persona Juridica no tiene una cuenta de aportes registrada");
			}
		}
		return socio;
	}
	
	protected void crearBeneficiarios(List<Beneficiariocuenta> beneficiarios) throws Exception {
		for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
			Beneficiariocuenta beneficiariocuenta = (Beneficiariocuenta) iterator.next();
			beneficiariocuentaDAO.create(beneficiariocuenta);
		}
	}
	
	protected void crearPersonanaturalForTitulares(Cuentacorriente cuentacorriente) throws IllegalEntityException, NonexistentEntityException, Exception {
		List<Titularcuenta> titulares = cuentacorriente.getTitularcuentas();
		
		for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
			Titularcuenta titularcuenta = (Titularcuenta) iterator.next();
			Personanatural personanatural = titularcuenta.getPersonanatural();
			
			Object key = personanatural.getDni();
			Object result = personanaturalServiceLocal.find(key);
			if(result == null){
				personanaturalServiceLocal.create(personanatural);
			}
		}
	}

	private void generarDatosDeRegistro(Cuentacorriente cuentacorriente) {
		cuentacorriente.setFechaapertura(Calendar.getInstance().getTime());
		cuentacorriente.setSaldo(0);
		cuentacorriente.setIdestadocuenta(1);
	}

	@Override
	public Cuentacorriente find(Object id) {
		try {
			return cuentacorrienteDAO.find(id);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void delete(Cuentacorriente oCuentacorriente) {
		try {
			cuentacorrienteDAO.delete(oCuentacorriente);
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
	public void update(Cuentacorriente oCuentacorriente) {
		/*
		 * try { return cuentaahorroDAO.update(oCuentaahorro); } catch
		 * (RollbackFailureException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
	}

	@Override
	public Collection<Cuentacorriente> findByNamedQuery(String queryName) {
		try {
			return cuentacorrienteDAO.findByNamedQuery(queryName);
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
	public Collection<Cuentacorriente> findByNamedQuery(String queryName,
			int resultLimit) {
		try {
			return cuentacorrienteDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Cuentacorriente> findByNamedQuery(String cuentacorriente,
			Map<String, Object> parameters) {
		try {
			return cuentacorrienteDAO.findByNamedQuery(cuentacorriente, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cuentacorriente> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		try {
			return cuentacorrienteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
