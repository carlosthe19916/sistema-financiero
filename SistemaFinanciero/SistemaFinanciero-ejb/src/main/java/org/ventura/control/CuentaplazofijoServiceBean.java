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
import org.ventura.entity.Agencia;
import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Cuentaaporte;
import org.ventura.entity.Cuentacorriente;
import org.ventura.entity.Cuentaplazofijo;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Socio;
import org.ventura.entity.Tipomoneda;
import org.ventura.entity.Titularcuenta;
import org.ventura.entity.Titularcuentahistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

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
	private Agencia agencia;
	
	@Inject
	Log log;

	@Override
	public Cuentaplazofijo createCuentaPlazofijoWithPersonanatural(Cuentaplazofijo cuentaplazofijo) throws Exception {


		try {
			
			Socio socio = buscarSocioPersonaNatural(cuentaplazofijo.getSocio());
			cuentaplazofijo.setSocio(socio);
		
			generarDatosDeRegistro(cuentaplazofijo);	
			String numerocuentaplazofijo = generarNumeroCuenta(cuentaplazofijo,socio);
			cuentaplazofijo.setNumerocuentaplazofijo(numerocuentaplazofijo);
			
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
			Socio socio = buscarSocioPersonaJuridica(cuentaplazofijo.getSocio());
			cuentaplazofijo.setSocio(socio);
				
			generarDatosDeRegistro(cuentaplazofijo);
			String numerocuentaplazofijo = generarNumeroCuenta(cuentaplazofijo,socio);
			cuentaplazofijo.setNumerocuentaplazofijo(numerocuentaplazofijo);
			
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
			if (result.size() == 0) {
				socioServiceLocal.create(socio);
			} else {
				throw new PreexistingEntityException("La Persona Natural ya tiene una cuenta de aportes Activa");
			}
		}
		return socio;
	}

	protected Socio buscarSocioPersonaJuridica(Socio socio) throws NonexistentEntityException, Exception {
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
	
	private void generarDatosDeRegistro(Cuentaplazofijo cuentaplazofijo) {
		cuentaplazofijo.setIdestadocuenta(1);							
		cuentaplazofijo.setFechaapertura(Calendar.getInstance().getTime());
		cuentaplazofijo.setFechavencimiento(calcularFechavencimientoContrato(cuentaplazofijo));
		cuentaplazofijo.setTiceaf(0.01);
		cuentaplazofijo.setTrea(0.01);
		cuentaplazofijo.setMontointerespagado(MontoInteresPagado(cuentaplazofijo));
		cuentaplazofijo.setItf(0.25);
		cuentaplazofijo.setIdfrecuenciacapitalizacion(1);
		cuentaplazofijo.setIdretirointeres(1);
	}

	private String generarNumeroCuenta(Cuentaplazofijo cuentaplazofijo, Socio socio) {

		/*Random random = new Random();
		int length = 14;
		char[] digits = new char[length];
		// Make sure the leading digit isn't 0.
		digits[0] = (char) ('1' + random.nextInt(9));

		for (int i = 1; i < length; i++) {
			digits[i] = (char) ('0' + random.nextInt(10));
		}
		cuentaahorro.setNumerocuentaaporte(digits.toString());
		*/
		String numeroCuenta = "";
		numeroCuenta = numeroCuenta + agencia.getCodigoagencia();
		
		String codigoSocio = socio.getCodigosocio().toString();
		for (int i = codigoSocio.length(); i < 8; i++) {
			codigoSocio = "0" + codigoSocio;
		}
		numeroCuenta = numeroCuenta + codigoSocio;
		
		numeroCuenta = numeroCuenta + cuentaplazofijo.getIdtipomoneda();
		numeroCuenta = numeroCuenta + "10";
		
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
	
	public Date calcularFechavencimientoContrato(Cuentaplazofijo cuentaplazofijo){
		Date fecha=cuentaplazofijo.getFechaapertura();
		int dias =cuentaplazofijo.getPlazo();
		    Calendar cal = new GregorianCalendar();
	        cal.setTimeInMillis(fecha.getTime());
	        cal.add(Calendar.DATE, dias);
	        
	        return new Date(cal.getTimeInMillis());
	
	}
	
	public double MontoInteresPagado(Cuentaplazofijo cuentaplazofijo){
		return cuentaplazofijo.getMonto()*cuentaplazofijo.getTiceaf();
	}
	
	@Override
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

}
