package org.ventura.control;

import java.util.ArrayList;
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
import javax.inject.Named;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.CuentaahorroServiceRemote;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaahorroDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.Agencia;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;
import org.ventura.entity.Titularcuenta;
import org.ventura.entity.Titularcuentahistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;
import org.ventura.util.validate.Validator;

@Named
@Stateless
@Local(CuentaahorroServiceLocal.class)
@Remote(CuentaahorroServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentaahorroServiceBean implements CuentaahorroServiceLocal {

	@EJB
	private SocioServiceLocal socioServiceLocal;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	
	@EJB
	private BeneficiariocuentaDAO beneficiariocuentaDAO;
	
	@EJB
	private TitularcuentaDAO titularcuentaDAO;
	
	@EJB
	private CuentaahorroDAO cuentaahorroDAO;
	
	@Inject
	private Agencia agencia;
	@Inject
	Log log;
	
	@Override
	public Cuentaahorro createCuentaAhorroWithPersonanatural(Cuentaahorro cuentaahorro) throws Exception {
		try {
			boolean result = Validator.validateCuentaahorro(cuentaahorro);
			if(result == true){
				Socio socio = buscarSocioPersonaNatural(cuentaahorro.getSocio());
				cuentaahorro.setSocio(socio);
				
				crearPersonanaturalForTitulares(cuentaahorro);
					
				generarDatosDeRegistro(cuentaahorro);	
				String numerocuentaahorro = generarNumeroCuenta(cuentaahorro,socio);
				//cuentaahorro.setNumerocuentaahorro(numerocuentaahorro);
							
				cuentaahorroDAO.create(cuentaahorro);
			} else {
				log.error("Error: Cuentaahorro validate failed");
				throw new Exception("Cuenta de ahorro tiene datos invalidos");
			}	
		} catch(NonexistentEntityException e){
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error:" + e.getMessage());
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaahorro;
	}

	@Override
	public Cuentaahorro createCuentaAhorroWithPersonajuridica(Cuentaahorro cuentaahorro) throws Exception {
		try {
			Socio socio = buscarSocioPersonaJuridica(cuentaahorro.getSocio());
			cuentaahorro.setSocio(socio);
			
			crearPersonanaturalForTitulares(cuentaahorro);
			generarDatosTitularHistorial(cuentaahorro);
			
			String numerocuentaaporte = generarNumeroCuenta(cuentaahorro,socio);
			//cuentaahorro.setNumerocuentaahorro(numerocuentaaporte);
			cuentaahorroDAO.create(cuentaahorro);	
			
		} catch(NonexistentEntityException e){
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error:" + e.getMessage());
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaahorro;
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
	
	protected void crearPersonanaturalForTitulares(Cuentaahorro cuentaahorro) throws IllegalEntityException, NonexistentEntityException, Exception {
		List<Titularcuenta> titulares = cuentaahorro.getTitularcuentas();
		
		if(titulares != null) {
			for (Iterator<Titularcuenta> iterator = titulares.iterator(); iterator.hasNext();) {
				Titularcuenta titularcuenta = iterator.next();
				Personanatural personanatural = titularcuenta.getPersonanatural();	
				if(personanatural != null){
					Object key = personanatural.getDni();
					Object result = personanaturalServiceLocal.find(key);
					if(result == null){
						personanaturalServiceLocal.create(personanatural);
					}
				}			
			}
		}
	}
	
	private void generarDatosTitularHistorial(Cuentaahorro cuentaahorro) {
        List<Titularcuenta> list = cuentaahorro.getTitularcuentas();   
        if(list != null){
        	for (Iterator<Titularcuenta> iterator = list.iterator(); iterator.hasNext();) {
    			Titularcuenta titularcuenta = (Titularcuenta) iterator.next();
    			List<Titularcuentahistorial> lista = new ArrayList<Titularcuentahistorial>();
    			Titularcuentahistorial historial = new Titularcuentahistorial();
    			historial.setEstado(true);
    			historial.setFechaactiva(Calendar.getInstance().getTime());
    			lista.add(historial);
    		//	titularcuenta.setTitularcuentahistorials(lista);
    			historial.setTitularcuenta(titularcuenta);
    		}
        }
    }

	private void generarDatosDeRegistro(Cuentaahorro cuentaahorro) {
		cuentaahorro.setFechaapertura(Calendar.getInstance().getTime());
		cuentaahorro.setSaldo(0);
		cuentaahorro.setIdestadocuenta(1);
		cuentaahorro.setIdtipomoneda(cuentaahorro.getIdtipomoneda());
	}

	private String generarNumeroCuenta(Cuentaahorro cuentaahorro, Socio socio) throws IllegalEntityException, NonexistentEntityException, Exception {
	/*	String numeroCuenta = "";
		numeroCuenta = numeroCuenta + agencia.getCodigoagencia();
		
		String codigoSocio = socio.getCodigosocio().toString();
		for (int i = codigoSocio.length(); i < 8; i++) {
			codigoSocio = "0" + codigoSocio;
		}
		numeroCuenta = numeroCuenta + codigoSocio;
		
		numeroCuenta = numeroCuenta + cuentaahorro.getIdtipomoneda();
		numeroCuenta = numeroCuenta + "11";
	
		Cuentaahorro aux = cuentaahorroDAO.find(numeroCuenta);
		if(aux != null){
			return numeroCuenta;
		} else {
			return generarNumeroCuenta(cuentaahorro, socio);
		}*/
		return null;
	}

	@Override
	public Cuentaahorro find(Object id) {
		try {
			return cuentaahorroDAO.find(id);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void delete(Cuentaahorro oCuentaahorro) {
		try {
			cuentaahorroDAO.delete(oCuentaahorro);
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
	public void update(Cuentaahorro oCuentaahorro) {
		/*
		 * try { return cuentaahorroDAO.update(oCuentaahorro); } catch
		 * (RollbackFailureException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
	}

	@Override
	public Collection<Cuentaahorro> findByNamedQuery(String queryName) {
		try {
			return cuentaahorroDAO.findByNamedQuery(queryName);
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
	public Collection<Cuentaahorro> findByNamedQuery(String queryName,
			int resultLimit) {
		try {
			return cuentaahorroDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Cuentaahorro> findByNamedQuery(String Cuentaahorro,
			Map<String, Object> parameters) {
		try {
			return cuentaahorroDAO.findByNamedQuery(Cuentaahorro, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cuentaahorro> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		try {
			return cuentaahorroDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

}
