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
import javax.inject.Named;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.PersonajuridicaclienteServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.boundary.remote.CuentaahorroServiceRemote;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaahorroDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Personajuridicacliente;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Personanaturalcliente;
import org.ventura.entity.Titularcuenta;
import org.ventura.entity.Titularcuentahistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Named
@Stateless
@Local(CuentaahorroServiceLocal.class)
@Remote(CuentaahorroServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentaahorroServiceBean implements CuentaahorroServiceLocal {

	@EJB
	private PersonanaturalclienteServiceLocal personaNaturalClienteServicesLocal;

	@EJB
	private PersonajuridicaclienteServiceLocal personajuridicaclienteServiceLocal;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	
	@EJB
	private BeneficiariocuentaDAO beneficiariocuentaDAO;
	
	@EJB
	private TitularcuentaDAO titularcuentaDAO;
	
	@EJB
	private CuentaahorroDAO cuentaahorroDAO;
	
	@Inject
	Log log;

	@Override
	public Cuentaahorro createCuentaAhorroWithPersonanatural(Cuentaahorro cuentaahorro) throws Exception {
		try {
			generarNumeroCuenta(cuentaahorro);
			generarDatosDeRegistro(cuentaahorro);

			//creando tablas relacionadas
			crearPersonanaturalcliente(cuentaahorro.getPersonanaturalcliente());		
			crearPersonanaturalForTitulares(cuentaahorro);
			generarDatosTitularHistorial(cuentaahorro);
			
			cuentaahorroDAO.create(cuentaahorro);
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		} 
		return cuentaahorro;
	}

	@Override
	public Cuentaahorro createCuentaAhorroWithPersonajuridica(Cuentaahorro cuentaahorro) throws Exception {
		try {
			generarNumeroCuenta(cuentaahorro);
			generarDatosDeRegistro(cuentaahorro);

			//creando tablas relacionadas
			crearPersonajuridicacliente(cuentaahorro.getPersonajuridicacliente());
			crearPersonanaturalForTitulares(cuentaahorro);
			generarDatosTitularHistorial(cuentaahorro);
			
			cuentaahorroDAO.create(cuentaahorro);			
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaahorro;
	}
	
	protected void crearPersonanaturalcliente(Personanaturalcliente personanaturalcliente) throws Exception{
		if(personanaturalcliente!=null&&personanaturalcliente.getPersonanatural() != null){
			Object key = personanaturalcliente.getPersonanatural().getDni();
			Object result = personanaturalServiceLocal.find(key);
			Object result1= personaNaturalClienteServicesLocal.find(personanaturalcliente.getDni());
			if(result == null&&result1==null){
				personanaturalServiceLocal.create(personanaturalcliente.getPersonanatural());
				personaNaturalClienteServicesLocal.create(personanaturalcliente);
			}
			if(result!=null&& result1==null){
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
	
	protected void crearPersonanaturalForTitulares(Cuentaahorro cuentaahorro) throws IllegalEntityException, NonexistentEntityException, Exception {
		List<Titularcuenta> titulares = cuentaahorro.getTitularcuentas();
		
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
	
	private void generarDatosTitularHistorial(Cuentaahorro cuentaahorro) {
        List<Titularcuenta> list = cuentaahorro.getTitularcuentas();     
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

	private void generarDatosDeRegistro(Cuentaahorro cuentaahorro) {
		cuentaahorro.setFechaapertura(Calendar.getInstance().getTime());
		cuentaahorro.setSaldo(0);
		cuentaahorro.setIdestadocuenta(1);
	}

	private void generarNumeroCuenta(Cuentaahorro cuentaahorro) {

		Random random = new Random();
		int length = 14;

		char[] digits = new char[length];
		// Make sure the leading digit isn't 0.
		digits[0] = (char) ('1' + random.nextInt(9));

		for (int i = 1; i < length; i++) {
			digits[i] = (char) ('0' + random.nextInt(10));
		}

		cuentaahorro.setNumerocuentaahorro(digits.toString());

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

}
