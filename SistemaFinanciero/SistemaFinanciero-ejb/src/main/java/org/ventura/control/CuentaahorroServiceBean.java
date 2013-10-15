package org.ventura.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import javax.inject.Named;
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.boundary.remote.CuentaahorroServiceRemote;
import org.ventura.dao.impl.CuentaahorroDAO;
import org.ventura.dao.impl.PersonanaturalDAO;
import org.ventura.dao.impl.PersonanaturalclienteDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentaahorrohistorial;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Personajuridica;
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
	/*
	@EJB
	private TitularcuentaServiceLocal titularCuentaServiceLocal;
	*/
	@EJB
	private CuentaahorroDAO cuentaahorroDAO;
	
	@EJB
	private PersonanaturalDAO personanaturalDAO;
	
	@EJB
	private TitularcuentaDAO titularcuentaDAO;
	
	@EJB
	private PersonanaturalclienteDAO personanaturalclienteDAO;
	
	@Inject
	Cuentaahorro cuentaahorro;

	@Inject
	Log log;

	@Override
	public void create(Cuentaahorro oCuentaahorro) {
		try {
			
			this.cuentaahorro = oCuentaahorro;
			generarNumeroCuenta();
			generarDatosDeRegistro();
			
			validarPersonaNatural(cuentaahorro.getPersonanaturalcliente().getPersonanatural(), cuentaahorro.getPersonanaturalcliente());
			
			validarTitularcuenta(cuentaahorro);
			generarDatosTitularHistorial();
			
			
			
			cuentaahorroDAO.create(cuentaahorro);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally{
			log.info("Service Close");
		}
	}
	
	protected void validarTitularcuenta(Cuentaahorro cuentaahorro) throws IllegalEntityException, NonexistentEntityException, Exception {
		for(int i=0;i<cuentaahorro.getTitularcuentas().size();i++){
			
			if(buscarTitularCuenta(cuentaahorro.getTitularcuentas().get(i))==false&& buscarPersonaNatural(cuentaahorro.getTitularcuentas().get(i).getPersonanatural())==true){
				System.out.println(cuentaahorro.getNumerocuentaahorro()+cuentaahorro.getTitularcuentas().get(i).getNumerocuentaahorro()+"a");
				createTitularcuenta(cuentaahorro,cuentaahorro.getTitularcuentas().get(i));
				System.out.println(cuentaahorro.getNumerocuentaahorro()+cuentaahorro.getTitularcuentas().get(i).getNumerocuentaahorro());
			}
			if(buscarTitularCuenta(cuentaahorro.getTitularcuentas().get(i))==false&& buscarPersonaNatural(cuentaahorro.getTitularcuentas().get(i).getPersonanatural())==false){
				System.out.println(cuentaahorro.getNumerocuentaahorro()+cuentaahorro.getTitularcuentas().get(i).getNumerocuentaahorro()+"b");
				createPersonanatural(cuentaahorro.getTitularcuentas().get(i).getPersonanatural());
				System.out.println(cuentaahorro.getNumerocuentaahorro()+cuentaahorro.getTitularcuentas().get(i).getNumerocuentaahorro()+"c");
				createTitularcuenta(cuentaahorro,cuentaahorro.getTitularcuentas().get(i));
				System.out.println(cuentaahorro.getNumerocuentaahorro()+cuentaahorro.getTitularcuentas().get(i).getNumerocuentaahorro());
			}
		}		
	}

	protected boolean buscarPersonaNatural(Personanatural personanatural) throws IllegalEntityException, NonexistentEntityException, Exception{
		if(personanaturalDAO.find(personanatural.getDni())!=null)
			return true;
		return false;
	}
	
	protected boolean buscarPersonaNaturalCliente(Personanaturalcliente personanaturalcliente) throws IllegalEntityException, NonexistentEntityException, Exception{
		if(personanaturalclienteDAO.find(personanaturalcliente.getDni())!=null)
			return true;
		return false;
	}
	
	protected void validarPersonaNatural(Personanatural personanatural,Personanaturalcliente personanaturalcliente) throws IllegalEntityException, NonexistentEntityException, Exception{
		if(buscarPersonaNatural(personanatural)==true && buscarPersonaNaturalCliente(personanaturalcliente)==false){
			createPersonanaturalcliente(personanaturalcliente);
		}
		if(buscarPersonaNatural(personanatural)==false && buscarPersonaNaturalCliente(personanaturalcliente)==false){
			createPersonanatural(personanatural);
			createPersonanaturalcliente(personanaturalcliente);
		}
	}
	
	protected boolean buscarTitularCuenta(Titularcuenta titularcuenta) throws IllegalEntityException, NonexistentEntityException, Exception{
		/*Map<String, Object> pa = new HashMap<String, Object>();
		pa.put("valor",titularcuenta.getDni());
		List<Titularcuenta> list = titularCuentaServiceLocal.findByNamedQuery(Titularcuenta.VA,pa);
		if(list.size()!=0)
			return true;*/
		return false;
	}
	
	private void generarDatosTitularHistorial() {
		List<Titularcuenta> list = cuentaahorro.getTitularcuentas();
		
		for (Iterator<Titularcuenta> iterator = list.iterator(); iterator.hasNext();) {
		Titularcuenta titularcuenta = (Titularcuenta) iterator.next();
		List<Titularcuentahistorial> lista = new ArrayList<Titularcuentahistorial>();
		Titularcuentahistorial historial =new Titularcuentahistorial();
		historial.setEstado(true);
		historial.setFechaactiva(Calendar.getInstance().getTime());
		lista.add(historial);
		titularcuenta.setTitularcuentahistorials(lista);
		historial.setTitularcuenta(titularcuenta);
		}
	}
	
	
	private void generarDatosDeRegistro() {
		cuentaahorro.setFechaapertura(Calendar.getInstance().getTime());
		cuentaahorro.setSaldo(0);
		cuentaahorro.setIdestadocuenta(1);
	}

	private void generarNumeroCuenta() {
		
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
	
	private void createPersonanatural(Personanatural personanatural){
		try {
			
			personanaturalDAO.create(personanatural);

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			log.info("Service Close");
		}

	}

	private void createPersonanaturalcliente(Personanaturalcliente personanaturalcliente){
		try {
			
			personanaturalclienteDAO.create(personanaturalcliente);

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			log.info("Service Close");
		}

	}
	private void createTitularcuenta(Cuentaahorro cuentaahorro, Titularcuenta titularcuenta){
		try {						
		
			
			titularcuenta.setCuentaahorro(cuentaahorro);
			
			//System.out.println(cuentaahorro.getNumerocuentaahorro());
			titularcuenta.setNumerocuentaahorro("11111111111111");
			titularcuentaDAO.create(titularcuenta);

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			log.info("Service Close");
		}
	}
	
	@Override
	public Cuentaahorro find(Object id) {
		try {
			return cuentaahorroDAO.find(id);
		} catch (IllegalEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cuentaahorro;
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
		/*try {
			return cuentaahorroDAO.update(oCuentaahorro);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
			Map<String, Object> parameters)  {
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
