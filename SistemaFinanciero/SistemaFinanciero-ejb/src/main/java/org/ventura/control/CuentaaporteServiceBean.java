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
import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.CuentaahorroServiceRemote;
import org.ventura.boundary.remote.CuentaaporteServiceRemote;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentaahorroDAO;
import org.ventura.dao.impl.CuentaaporteDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaahorro;
import org.ventura.entity.Cuentaaporte;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;
import org.ventura.entity.Titularcuenta;
import org.ventura.entity.Titularcuentahistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
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
	private CuentaaporteDAO cuentaaporteDAO;
	
	@Inject
	Log log;

	@Override
	public Cuentaaporte createCuentaAporteWithPersonanatural(Cuentaaporte cuentaaporte) throws Exception {
		try {
			generarNumeroCuenta(cuentaaporte);
			generarDatosDeRegistro(cuentaaporte);

			//creando tablas relacionadas
			Socio socio = crearSocioPersonaNatural(cuentaaporte.getSocio());
			cuentaaporte.setSocio(socio);
			cuentaaporteDAO.create(cuentaaporte);
		} catch(PreexistingEntityException e){
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error:" + e.getCause());
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaaporte;
	}

	@Override
	public Cuentaaporte createCuentaAporteWithPersonajuridica(Cuentaaporte cuentaaporte) throws Exception {
		try {
			generarNumeroCuenta(cuentaaporte);
			generarDatosDeRegistro(cuentaaporte);

			//creando tablas relacionadas
			Socio socio = crearSocioPersonaJuridica(cuentaaporte.getSocio());
			cuentaaporte.setSocio(socio);			
			cuentaaporteDAO.create(cuentaaporte);			
		} catch(PreexistingEntityException e){
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error:" + e.getCause());
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new Exception("Error al insertar los datos");
		}
		return cuentaaporte;
	}
	
	protected Socio crearSocioPersonaNatural(Socio socio) throws PreexistingEntityException, Exception {
		if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("dni", socio.getDni());
			Object result = socioServiceLocal.findByNamedQuery(Socio.FindByDni, parameters);
			if (result == null) {
				socio = socioServiceLocal.create(socio);
			} else {
				throw new PreexistingEntityException("El Socio Persona Natural ya tiene una cuenta de aportes activa");
			}
		}
		return socio;
	}

	protected Socio crearSocioPersonaJuridica(Socio socio) throws PreexistingEntityException, Exception {
		if (socio != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ruc", socio.getDni());
			Object result = socioServiceLocal.findByNamedQuery(Socio.FindByRuc, parameters);
			if (result == null) {
				socio = socioServiceLocal.create(socio);
			} else {
				throw new PreexistingEntityException("El Socio Persona Juridica ya tiene una cuenta de aportes activa");
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
	
		
	private void generarDatosDeRegistro(Cuentaaporte cuentaaporte) {
		cuentaaporte.setFechaapertura(Calendar.getInstance().getTime());
		cuentaaporte.setSaldo(0);		
	}

	private void generarNumeroCuenta(Cuentaaporte cuentaahorro) {

		Random random = new Random();
		int length = 14;

		char[] digits = new char[length];
		// Make sure the leading digit isn't 0.
		digits[0] = (char) ('1' + random.nextInt(9));

		for (int i = 1; i < length; i++) {
			digits[i] = (char) ('0' + random.nextInt(10));
		}

		cuentaahorro.setNumerocuentaaporte(digits.toString());

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
	public List<Cuentaaporte> findByNamedQuery(String cuentaaporte,
			Map<String, Object> parameters) {
		try {
			return cuentaaporteDAO.findByNamedQuery(cuentaaporte, parameters);
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
