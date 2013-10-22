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
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.CuentaplazofijoServiceRemote;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaplazofijoDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaplazofijo;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;
import org.ventura.entity.Titularcuenta;
import org.ventura.entity.Titularcuentahistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

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
	Log log;

	@Override
	public Cuentaplazofijo createCuentaPlazofijoWithPersonanatural(Cuentaplazofijo cuentaplazofijo) throws Exception {


		try {
			generarNumeroCuenta(cuentaplazofijo);
			generarDatosDeRegistro(cuentaplazofijo);

			//creando tablas relacionadas
			crearSocioPersonaNatural(cuentaplazofijo.getSocio());
			crearPersonanaturalForTitulares(cuentaplazofijo);
			generarDatosTitularHistorial(cuentaplazofijo);
			
			cuentaplazofijoDAO.create(cuentaplazofijo);
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}

		return cuentaplazofijo;
	}

	@Override
	public Cuentaplazofijo createCuentaPlazofijoWithPersonajuridica(Cuentaplazofijo cuentaplazofijo) throws Exception {
		try {
			generarNumeroCuenta(cuentaplazofijo);
			generarDatosDeRegistro(cuentaplazofijo);

			//creando tablas relacionadas
			crearSocioPersonaJuridica(cuentaplazofijo.getSocio());
			crearPersonanaturalForTitulares(cuentaplazofijo);
			generarDatosTitularHistorial(cuentaplazofijo);
			
			cuentaplazofijoDAO.create(cuentaplazofijo);
			
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaplazofijo;
	}
	
	protected void crearSocioPersonaNatural(Socio socio) throws Exception{
		if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("dni", socio.getDni());
			Object result = socioServiceLocal.findByNamedQuery(Socio.FindByDni, parameters);
			if (result == null) {
				socioServiceLocal.create(socio);
			}
		}
	}

	protected void crearSocioPersonaJuridica(Socio socio) throws Exception {
		if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ruc", socio.getDni());
			Object result = socioServiceLocal.findByNamedQuery(Socio.FindByRuc,
					parameters);
			if (result == null) {
				socioServiceLocal.create(socio);
			}
		}
	}
	
	protected void crearBeneficiarios(List<Beneficiariocuenta> beneficiarios) throws Exception {
		for (Iterator<Beneficiariocuenta> iterator = beneficiarios.iterator(); iterator.hasNext();) {
			Beneficiariocuenta beneficiariocuenta = (Beneficiariocuenta) iterator.next();
			beneficiariocuentaDAO.create(beneficiariocuenta);
		}
	}
	
	protected void crearPersonanaturalForTitulares(Cuentaplazofijo cuentaplazofijo) throws IllegalEntityException, NonexistentEntityException, Exception {
		List<Titularcuenta> titulares = cuentaplazofijo.getTitularcuentas();
		
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
	
	private void generarDatosTitularHistorial(Cuentaplazofijo cuentaplazofijo) {
        List<Titularcuenta> list = cuentaplazofijo.getTitularcuentas();     
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

	private void generarDatosDeRegistro(Cuentaplazofijo cuentaplazofijo) {
		cuentaplazofijo.setFechaapertura(Calendar.getInstance().getTime());
		cuentaplazofijo.getEstadocuenta().setIdestadocuenta(1);
	}

	private void generarNumeroCuenta(Cuentaplazofijo cuentaplazofijo) {

		Random random = new Random();
		int length = 14;

		char[] digits = new char[length];
		// Make sure the leading digit isn't 0.
		digits[0] = (char) ('1' + random.nextInt(9));

		for (int i = 1; i < length; i++) {
			digits[i] = (char) ('0' + random.nextInt(10));
		}

		cuentaplazofijo.setNumerocuentaplazofijo(digits.toString());

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
