package org.ventura.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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

import org.ventura.boundary.local.CuentacorrienteServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.boundary.remote.CuentacorrienteServiceRemote;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaahorroDAO;
import org.ventura.dao.impl.CuentacorrienteDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentacorriente;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Personanaturalcliente;
import org.ventura.entity.Titularcuenta;
import org.ventura.entity.Titularcuentahistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(CuentacorrienteServiceLocal.class)
@Remote(CuentacorrienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentacorrienteServiceBean implements CuentacorrienteServiceLocal {

	@EJB
	private PersonanaturalclienteServiceLocal personaNaturalClienteServicesLocal;

	@EJB
	private SocioServiceLocal personajuridicaclienteServiceLocal;
	
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

			generarNumeroCuenta(cuentacorriente);
			generarDatosDeRegistro(cuentacorriente);

			//creando tablas relacionadas
			crearSocioPersonaNatural(cuentacorriente.getPersonanaturalcliente());
			crearPersonanaturalForTitulares(cuentacorriente);
			generarDatosTitularHistorial(cuentacorriente);
			
			cuentacorrienteDAO.create(cuentacorriente);

			cuentacorrienteDAO.create(cuentacorriente);
		} catch (PreexistingEntityException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		} catch (IllegalEntityException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		} catch (RollbackFailureException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());

		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentacorriente;

	}

	@Override
	public Cuentacorriente createCuentaCorrienteWithPersonajuridica(Cuentacorriente cuentacorriente) throws Exception {
		try {
			generarNumeroCuenta(cuentacorriente);
			generarDatosDeRegistro(cuentacorriente);

			//creando tablas relacionadas
			crearPersonajuridicacliente(cuentacorriente.getPersonajuridicacliente());
			crearPersonanaturalForTitulares(cuentacorriente);
			generarDatosTitularHistorial(cuentacorriente);
			
			cuentacorrienteDAO.create(cuentacorriente);
			
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentacorriente;
	}
	
	protected void crearPersonanaturalcliente(Personanaturalcliente personanaturalcliente) throws Exception{
		if(personanaturalcliente!=null){
			Object key = personanaturalcliente.getDni();
			Object result= personaNaturalClienteServicesLocal.find(key);
			if(result==null){
				personaNaturalClienteServicesLocal.create(personanaturalcliente);
			}			
		}
	}

	protected void crearPersonajuridicacliente(Personajuridicacliente personajuridicacliente) throws Exception {
		if (personajuridicacliente != null) {
			Object key = personajuridicacliente.getRuc();
			Object result = personajuridicaclienteServiceLocal.find(key);
			if (result == null) {
				personajuridicaclienteServiceLocal.create(personajuridicacliente);
			}
		}
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
	
	private void generarDatosTitularHistorial(Cuentacorriente cuentacorriente) {
        List<Titularcuenta> list = cuentacorriente.getTitularcuentas();     
		for (Iterator<Titularcuenta> iterator = list.iterator(); iterator.hasNext();) {
			Titularcuenta titularcuenta = (Titularcuenta) iterator.next();
			List<Titularcuentahistorial> lista = new ArrayList<Titularcuentahistorial>();
			Titularcuentahistorial historial = new Titularcuentahistorial();
			historial.setEstado(true);
			historial.setFechaactiva(Calendar.getInstance().getTime());
			lista.add(historial);
			titularcuenta.setTitularcuentahistorials(lista);
			historial.setTitularcuenta(titularcuenta);
		}
    }

	private void generarDatosDeRegistro(Cuentacorriente cuentacorriente) {
		cuentacorriente.setFechaapertura(Calendar.getInstance().getTime());
		cuentacorriente.setSaldo(0);
		cuentacorriente.getEstadocuenta().setIdestadocuenta(1);
	}

	private void generarNumeroCuenta(Cuentacorriente cuentacorriente) {

		Random random = new Random();
		int length = 14;

		char[] digits = new char[length];
		// Make sure the leading digit isn't 0.
		digits[0] = (char) ('1' + random.nextInt(9));

		for (int i = 1; i < length; i++) {
			digits[i] = (char) ('0' + random.nextInt(10));
		}

		cuentacorriente.setNumerocuentacorriente(digits.toString());


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
	public List<Cuentacorriente> findByNamedQuery(String Cuentaahorro,
			Map<String, Object> parameters) {
		try {
			return cuentacorrienteDAO.findByNamedQuery(Cuentaahorro, parameters);
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
