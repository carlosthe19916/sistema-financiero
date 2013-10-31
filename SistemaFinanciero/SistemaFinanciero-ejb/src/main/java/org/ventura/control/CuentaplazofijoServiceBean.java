package org.ventura.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.CuentaplazofijoServiceRemote;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaplazofijoDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentaahorro;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentacorriente;
import org.ventura.entity.schema.cuentapersonal.Cuentaplazofijo;
import org.ventura.entity.schema.cuentapersonal.Titularcuenta;
import org.ventura.entity.schema.cuentapersonal.Titularcuentahistorial;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;
import org.ventura.util.validate.Validator;

import com.sun.tools.internal.xjc.generator.bean.ImplStructureStrategy.Result;

@Stateless
@Local(CuentaplazofijoServiceLocal.class)
@Remote(CuentaplazofijoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentaplazofijoServiceBean implements CuentaplazofijoServiceLocal {

	@EJB
	private SocioServiceLocal socioServiceLocal;

	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	
	@EJB
	private BeneficiariocuentaDAO beneficiariocuentaDAO;
	
	@EJB
	private TitularcuentaDAO titularcuentaDAO;
	
	@EJB
	private CuentaplazofijoDAO cuentaplazofijoDAO;
	
	@Inject
	private Log log;

	@Override
	public Cuentaplazofijo createCuentaPlazofijoWithPersonanatural(Cuentaplazofijo cuentaplazofijo) throws Exception {
		try {
			boolean result = Validator.validateCuentaplazofijo(cuentaplazofijo);
			if (result == true) {
				Socio socio = buscarSocioPersonaNatural(cuentaplazofijo.getSocio());
				cuentaplazofijo.setSocio(socio);

				crearPersonanaturalForTitulares(cuentaplazofijo);

				generarDatosDeRegistro(cuentaplazofijo);
				cuentaplazofijoDAO.create(cuentaplazofijo);
				
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("idcuentaplazofijo", cuentaplazofijo.getIdcuentaplazofijo());
				List<Titularcuenta> titulares = titularcuentaDAO.findByNamedQuery(Titularcuenta.FindAllForCuentaplazofijo, parameters);
				cuentaplazofijo.setTitularcuentas(titulares);
				
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
		return cuentaplazofijo;
	}

	@Override
	public Cuentaplazofijo createCuentaPlazofijoWithPersonajuridica(Cuentaplazofijo cuentaplazofijo) throws Exception {
		try {
			Socio socio = buscarSocioPersonaJuridica(cuentaplazofijo.getSocio());
			cuentaplazofijo.setSocio(socio);
				
			crearPersonanaturalForTitulares(cuentaplazofijo);
			generarDatosTitularHistorial(cuentaplazofijo);
			
			String numerocuentaplazofijo = generarNumeroCuenta(cuentaplazofijo,socio);
			//cuentaplazofijo.setNumerocuentaplazofijo(numerocuentaplazofijo);
			cuentaplazofijoDAO.create(cuentaplazofijo);
			
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaplazofijo;
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
			parameters.put("ruc", socio.getDni());
			Object result = socioServiceLocal.findByNamedQuery(Socio.FindByRuc, parameters);
			if (result != null) {
				List<Socio> socios = (List<Socio>) result;
				for (Iterator<Socio> iterator = socios.iterator(); iterator.hasNext();) {
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
	
	protected void crearPersonanaturalForTitulares(Cuentaplazofijo cuentaplazofijo) throws IllegalEntityException, NonexistentEntityException, Exception {
		List<Titularcuenta> titulares = cuentaplazofijo.getTitularcuentas();
		if (titulares != null) {
			for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
				Titularcuenta titularcuenta = iterator.next();
				Personanatural personanatural = titularcuenta.getPersonanatural();
				if (personanatural != null) {
					Object key = personanatural.getDni();
					Object result = personanaturalServiceLocal.find(key);
					if (result == null) {
						personanaturalServiceLocal.create(personanatural);
					}
				}
			}
		}
	}
	private void generarDatosTitularHistorial(Cuentaplazofijo cuentaplazofijo) {
        List<Titularcuenta> list = cuentaplazofijo.getTitularcuentas();   
        if(list != null){
        	for (Iterator<Titularcuenta> iterator = list.iterator(); iterator.hasNext();) {
    			Titularcuenta titularcuenta = (Titularcuenta) iterator.next();
    			List<Titularcuentahistorial> lista = new ArrayList<Titularcuentahistorial>();
    			Titularcuentahistorial historial = new Titularcuentahistorial();
    			historial.setEstado(true);
    			historial.setFechaactiva(Calendar.getInstance().getTime());
    			lista.add(historial);
    			//titularcuenta.setTitularcuentahistorials(lista);
    			historial.setTitularcuenta(titularcuenta);
    		}
        }
    }
	
	private void generarDatosDeRegistro(Cuentaplazofijo cuentaplazofijo) {
		
	}

	private String generarNumeroCuenta(Cuentaplazofijo cuentaplazofijo, Socio socio) {

		String numeroCuenta = "";
	/*	numeroCuenta = numeroCuenta + agencia.getCodigoagencia();
		
		String codigoSocio = socio.getCodigosocio().toString();
		for (int i = codigoSocio.length(); i < 8; i++) {
			codigoSocio = "0" + codigoSocio;
		}
		numeroCuenta = numeroCuenta + codigoSocio;
		
		numeroCuenta = numeroCuenta + cuentaplazofijo.getIdtipomoneda();
		numeroCuenta = numeroCuenta + "11";
		*/
		return numeroCuenta;
	}

	@Override
	public Cuentaplazofijo find(Object id) {
		try {
			return cuentaplazofijoDAO.find(id);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void delete(Cuentaplazofijo oCuentaplazofijo) {
		try {
			cuentaplazofijoDAO.delete(oCuentaplazofijo);
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
	public void update(Cuentaplazofijo oCuentaplazofijo) {
		/*
		 * try { return cuentaahorroDAO.update(oCuentaahorro); } catch
		 * (RollbackFailureException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
	}

	@Override
	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName) {
		try {
			return cuentaplazofijoDAO.findByNamedQuery(queryName);
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
	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName,
			int resultLimit) {
		try {
			return cuentaplazofijoDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Cuentaplazofijo> findByNamedQuery(String Cuentaahorro,
			Map<String, Object> parameters) {
		try {
			return cuentaplazofijoDAO.findByNamedQuery(Cuentaahorro, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cuentaplazofijo> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		try {
			return cuentaplazofijoDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}	
	
}
