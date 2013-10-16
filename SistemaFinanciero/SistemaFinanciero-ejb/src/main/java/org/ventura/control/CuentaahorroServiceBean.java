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
	public void createCuentaAhorroWithPersonanatural(Cuentaahorro cuentaahorro) throws Exception {
		try {

			generarNumeroCuenta(cuentaahorro);
			generarDatosDeRegistro(cuentaahorro);

			//creando tablas relacionadas
			crearPersonanaturalcliente(cuentaahorro.getPersonanaturalcliente());
			crearBeneficiarios(cuentaahorro.getBeneficiariocuentas());
			crearTitulares(cuentaahorro);
			generarDatosTitularHistorial(cuentaahorro);
			
			cuentaahorroDAO.create(cuentaahorro);
			
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		} 
	}

	@Override
	public void createCuentaAhorroWithPersonajuridica(Cuentaahorro cuentaahorro) throws Exception {
		try {

			generarNumeroCuenta(cuentaahorro);
			generarDatosDeRegistro(cuentaahorro);

			//creando tablas relacionadas
			crearPersonajuridicacliente(cuentaahorro.getPersonajuridicacliente());
			//crearBeneficiarios(cuentaahorro.getBeneficiariocuentas());
			//crearTitulares(cuentaahorro);
			
			cuentaahorroDAO.create(cuentaahorro);
			
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
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
	
	protected void crearTitulares(Cuentaahorro cuentaahorro) throws IllegalEntityException, NonexistentEntityException, Exception {
		for(int i=0;i<cuentaahorro.getTitularcuentas().size();i++){
			if(buscarTitularCuenta(cuentaahorro.getTitularcuentas().get(i))==false&& personanaturalServiceLocal.find(cuentaahorro.getTitularcuentas().get(i).getPersonanatural().getDni())==null){
				personanaturalServiceLocal.create(cuentaahorro.getTitularcuentas().get(i).getPersonanatural());
				titularcuentaDAO.create(cuentaahorro.getTitularcuentas().get(i));
			}
			if(buscarTitularCuenta(cuentaahorro.getTitularcuentas().get(i))==false&& personanaturalServiceLocal.find(cuentaahorro.getTitularcuentas().get(i).getPersonanatural().getDni())!=null){
				titularcuentaDAO.create(cuentaahorro.getTitularcuentas().get(i));
			}
		}
	}
	
	protected boolean buscarTitularCuenta(Titularcuenta titularcuenta) throws IllegalEntityException, NonexistentEntityException, Exception{
        Map<String, Object> pa = new HashMap<String, Object>();
        pa.put("valor",titularcuenta.getDni());
        List<Titularcuenta> list = titularcuentaDAO.findByNamedQuery(Titularcuenta.VA,pa);
        if(list.size()!=0)
                return true;
        return false;
	}
	
	private void generarDatosTitularHistorial(Cuentaahorro cuentaahorro) {
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
