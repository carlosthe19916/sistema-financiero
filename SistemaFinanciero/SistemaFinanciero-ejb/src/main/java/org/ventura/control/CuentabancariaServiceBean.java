package org.ventura.control;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.ventura.boundary.local.CuentabancariaServiceLocal;
import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.CuentabancariaViewDAO;
import org.ventura.dao.impl.SocioDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.cuentapersonal.Titular;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadocuentaType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipocuentabancariaType;

@Stateless
@Local(CuentabancariaServiceLocal.class)
@Remote(CuentabancariaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentabancariaServiceBean implements CuentabancariaServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CuentabancariaDAO cuentabancariaDAO;

	@EJB
	private CuentabancariaViewDAO cuentabancariaViewDAO;
	
	@EJB
	private SocioDAO socioDAO;
	@EJB
	private AccionistaDAO accionistaDAO;
	@EJB
	private TitularcuentaDAO titularcuentaDAO;
	
	@EJB
	private BeneficiariocuentaDAO beneficiariocuentaDAO;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB
	private PersonajuridicaServiceLocal personajuridicaServiceLocal;
	
	@Override
	public Cuentabancaria create(Cuentabancaria cuentabancaria)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cuentabancaria findById(Object id) throws Exception {
		Cuentabancaria cuentabancaria;
		try {
			cuentabancaria = cuentabancariaDAO.find(id);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancaria;
	}

	@Override
	public Cuentabancaria findByNumerocuenta(String numerocuenta) throws Exception {
		Cuentabancaria cuentabancaria;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("numerocuenta", numerocuenta);

			List<Cuentabancaria> historialbovedaList = cuentabancariaDAO.findByNamedQuery(Cuentabancaria.findByNumerocuenta,parameters);
			if (historialbovedaList.size() == 1) {
				cuentabancaria = historialbovedaList.get(0);
			} else {
				throw new Exception("No se encontro cuenta bancaria valida");
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancaria;
	}

	@Override
	public List<CuentabancariaView> findByDni(String dni) throws Exception {
		List<CuentabancariaView> cuentabancariaViews;
		try {
			 Map<String, Object> parameters = new HashMap<String, Object>();
			 parameters.put("dni", "%" + dni + "%");

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.findByLikeDni,parameters,100);

		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancariaViews;
	}

	@Override
	public CuentabancariaView findCuentabancariaViewByNumerocuenta(String numerocuenta) throws Exception {
		CuentabancariaView cuentabancaria;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("numerocuenta", numerocuenta);

			List<CuentabancariaView> cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.findByNumerocuenta,parameters);
			if (cuentabancariaViews.size() == 1) {
				cuentabancaria = cuentabancariaViews.get(0);
			} else {
				if (cuentabancariaViews.size() == 0){
					cuentabancaria = null;
				} else {
					throw new Exception("No se encontro cuenta bancaria valida");
				}		
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancaria;
	}

	@Override
	public List<CuentabancariaView> findCuentabancariaViewByDni(String dni) throws Exception {
		List<CuentabancariaView> cuentabancariaViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("dni","%" +dni+ "%" );

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.findByLikeDni,parameters,100);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancariaViews;
	}

	@Override
	public List<CuentabancariaView> findCuentabancariaViewByRuc(String ruc) throws Exception {
		List<CuentabancariaView> cuentabancariaViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ruc", "%" + ruc + "%");

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.findByLikeRuc,parameters,100);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancariaViews;
	}

	@Override
	public List<CuentabancariaView> findCuentabancariaViewByNombre(String nombre) throws Exception {
		List<CuentabancariaView> cuentabancariaViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nombres", "%" + nombre + "%");

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.findByLikeNombre,parameters,100);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancariaViews;
	}

	@Override
	public List<CuentabancariaView> findCuentabancariaViewByRazonsocial(String razonsocial) throws Exception {
		List<CuentabancariaView> cuentabancariaViews = null;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("razonsocial", "%" + razonsocial + "%");

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.findByLikeRazonsocial,parameters,100);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancariaViews;
	}

	@Override
	public List<Cuentabancaria> findAll()  throws Exception {
		try {
			return cuentabancariaDAO.findAll();
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}

	@Override
	public Cuentabancaria createCuentaahorroPersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural) throws Exception {
		try {				
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", personanatural.getTipodocumento());
			parameters.put("numerodocumento", personanatural.getNumerodocumento());			
			List<Socio> resultList = socioDAO.findByNamedQuery(Socio.FindSocioPNByTipodocumentoNumerodocumento,parameters,2);
			
			Socio socio;
			if(resultList.size() == 0){
				throw new PreexistingEntityException("El cliente necesita ser socio antes de obtener una cuenta de ahorros");
			} else {
				if(resultList.size() == 1){
					socio = resultList.get(0);
				} else {
					throw new PreexistingEntityException("Existen dos socios para la persona indicada");
				}	
			}
			
			List<Titular> titulares = cuentabancaria.getTitulares();
			List<Beneficiario> beneficiarios = cuentabancaria.getBeneficiarios();
			
			Tipodocumento tipodocumentoSocio = personanatural.getTipodocumento();
			String numerodocumentoSocio = personanatural.getNumerodocumento();
			Personanatural personanaturalBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoSocio, numerodocumentoSocio);
			if(personanaturalBD != null){
				personanaturalBD.setTipodocumento(personanatural.getTipodocumento());
				personanaturalBD.setNumerodocumento(personanatural.getNumerodocumento());
				personanaturalBD.setApellidopaterno(personanatural.getApellidopaterno());
				personanaturalBD.setApellidomaterno(personanatural.getApellidomaterno());
				personanaturalBD.setNombres(personanatural.getNombres());
				personanaturalBD.setFechanacimiento(personanatural.getFechanacimiento());
				personanaturalBD.setSexo(personanatural.getSexo());
				personanaturalBD.setEstadocivil(personanatural.getEstadocivil());
				personanaturalBD.setOcupacion(personanatural.getOcupacion());
				personanaturalBD.setDireccion(personanatural.getDireccion());
				personanaturalBD.setReferencia(personanatural.getReferencia());
				personanaturalBD.setTelefono(personanatural.getTelefono());
				personanaturalBD.setCelular(personanatural.getCelular());
				personanaturalServiceLocal.update(personanaturalBD);
				personanatural = personanaturalBD;
			} else {
				personanaturalServiceLocal.create(personanatural);
			}
			
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_AHORRO);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setSaldo(new Moneda());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			for (Titular titular : titulares) {
				Personanatural titularPersonanatural = titular.getPersonanatural();
				Tipodocumento tipodocumentoTitular = titularPersonanatural.getTipodocumento();
				String numeroDocumentoTitular = titularPersonanatural.getNumerodocumento();
				Personanatural titularPersonanaturalBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoTitular, numeroDocumentoTitular);
				
				if(titularPersonanaturalBD != null){
					titularPersonanaturalBD.setTipodocumento(titularPersonanatural.getTipodocumento());
					titularPersonanaturalBD.setNumerodocumento(titularPersonanatural.getNumerodocumento());
					titularPersonanaturalBD.setApellidopaterno(titularPersonanatural.getApellidopaterno());
					titularPersonanaturalBD.setApellidomaterno(titularPersonanatural.getApellidomaterno());
					titularPersonanaturalBD.setNombres(titularPersonanatural.getNombres());
					titularPersonanaturalBD.setFechanacimiento(titularPersonanatural.getFechanacimiento());
					titularPersonanaturalBD.setSexo(titularPersonanatural.getSexo());
					titularPersonanaturalBD.setEstadocivil(titularPersonanatural.getEstadocivil());
					titularPersonanaturalBD.setOcupacion(titularPersonanatural.getOcupacion());
					titularPersonanaturalBD.setDireccion(titularPersonanatural.getDireccion());
					titularPersonanaturalBD.setReferencia(titularPersonanatural.getReferencia());
					titularPersonanaturalBD.setTelefono(titularPersonanatural.getTelefono());
					titularPersonanaturalBD.setCelular(titularPersonanatural.getCelular());
					titularPersonanaturalBD.setEmail(titularPersonanatural.getEmail());
					personanaturalServiceLocal.update(titularPersonanaturalBD);
					titularPersonanatural = titularPersonanaturalBD;
				} else {
					personanaturalServiceLocal.create(titularPersonanaturalBD);
				}
				
				Titular titularNew = new Titular();	
				titularNew.setEstado(true);
				titularNew.setFechaactiva(Calendar.getInstance().getTime());
				titularNew.setPersonanatural(titularPersonanatural);
				titularNew.setCuentabancaria(cuentabancaria);
				
				titularcuentaDAO.create(titularNew);
			}
			
			for (Beneficiario beneficiario : beneficiarios) {
				beneficiario.setEstado(true);
				beneficiario.setCuentabancaria(cuentabancaria);
				beneficiariocuentaDAO.create(beneficiario);
			}
			
			return cuentabancaria;
		} catch (IllegalEntityException | PreexistingEntityException e) {		
			cuentabancaria.setIdcuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());	
		} catch (Exception e) {
			cuentabancaria.setIdcuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		} 	
	}

	@Override
	public Cuentabancaria createCuentaahorroPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica) throws Exception {
		try {				
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("tipodocumento", personajuridica.getTipodocumento());
			parameters.put("numerodocumento", personajuridica.getNumerodocumento());			
			List<Socio> resultList = socioDAO.findByNamedQuery(Socio.FindSocioPJByTipodocumentoNumerodocumento,parameters,2);
			
			Socio socio;
			if(resultList.size() == 0){
				throw new PreexistingEntityException("El cliente necesita ser socio antes de obtener una cuenta de ahorros");
			} else {
				if(resultList.size() == 1){
					socio = resultList.get(0);
				} else {
					throw new PreexistingEntityException("Existen dos socios para la persona indicada");
				}	
			}
			
			Personanatural representanteLegal = personajuridica.getRepresentanteLegal();
			List<Accionista> accionistas = personajuridica.getAccionistas();	
			List<Titular> titulares = cuentabancaria.getTitulares();
			List<Beneficiario> beneficiarios = cuentabancaria.getBeneficiarios();
			
			Tipodocumento tipodocumentoRepresentanteLegal = representanteLegal.getTipodocumento();
			String numeroDocumentoRepresentanteLegal = representanteLegal.getNumerodocumento();
			Personanatural representanteLegalBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoRepresentanteLegal, numeroDocumentoRepresentanteLegal);
			
			if(representanteLegalBD != null){
				representanteLegalBD.setTipodocumento(representanteLegal.getTipodocumento());
				representanteLegalBD.setNumerodocumento(representanteLegal.getNumerodocumento());
				representanteLegalBD.setApellidopaterno(representanteLegal.getApellidopaterno());
				representanteLegalBD.setApellidomaterno(representanteLegal.getApellidomaterno());
				representanteLegalBD.setNombres(representanteLegal.getNombres());
				representanteLegalBD.setFechanacimiento(representanteLegal.getFechanacimiento());
				representanteLegalBD.setSexo(representanteLegal.getSexo());		
				personanaturalServiceLocal.update(representanteLegalBD);
				
				representanteLegal = representanteLegalBD;
			} else {
				personanaturalServiceLocal.create(representanteLegal);
			}
			
			Tipodocumento tipodocumentoSocio = personajuridica.getTipodocumento();
			String numerodocumentoSocio = personajuridica.getNumerodocumento();
			Personajuridica personajuridicaBD =  personajuridicaServiceLocal.findByTipodocumento(tipodocumentoSocio, numerodocumentoSocio);
			
			if(personajuridicaBD != null){
				personajuridicaBD.setTipodocumento(personajuridica.getTipodocumento());
				personajuridicaBD.setNumerodocumento(personajuridica.getNumerodocumento());
				personajuridicaBD.setRazonsocial(personajuridica.getRazonsocial());
				personajuridicaBD.setNombrecomercial(personajuridica.getNombrecomercial());
				personajuridicaBD.setActividadprincipal(personajuridica.getActividadprincipal());
				personajuridicaBD.setFechaconstitucion(personajuridica.getFechaconstitucion());
				personajuridicaBD.setTipoempresa(personajuridica.getTipoempresa());
				personajuridicaBD.setFindelucro(personajuridicaBD.getFindelucro());
				personajuridicaBD.setDireccion(personajuridica.getDireccion());
				personajuridicaBD.setReferencia(personajuridica.getReferencia());
				personajuridicaBD.setTelefono(personajuridica.getTelefono());
				personajuridicaBD.setCelular(personajuridica.getCelular());
				personajuridicaBD.setEmail(personajuridica.getEmail());
				personajuridicaServiceLocal.update(personajuridicaBD);
				personajuridica = personajuridicaBD;
			} else {
				personajuridicaServiceLocal.create(personajuridica);
			}
			
			for (Accionista accionista : personajuridica.getAccionistas()) {
				accionistaDAO.delete(accionista);
			}
			
			for (Accionista accionista : accionistas) {
				Personanatural accionistaPersonanatural = accionista.getPersonanatural();
				Tipodocumento tipodocumentoAccionista = accionistaPersonanatural.getTipodocumento();
				String numeroDocumentoAccionista = accionistaPersonanatural.getNumerodocumento();
				Personanatural accionistaPersonanaturalBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoAccionista, numeroDocumentoAccionista);
				
				if(accionistaPersonanaturalBD != null){
					accionistaPersonanaturalBD.setTipodocumento(accionistaPersonanatural.getTipodocumento());
					accionistaPersonanaturalBD.setNumerodocumento(accionistaPersonanatural.getNumerodocumento());
					accionistaPersonanaturalBD.setApellidopaterno(accionistaPersonanatural.getApellidopaterno());
					accionistaPersonanaturalBD.setApellidomaterno(accionistaPersonanatural.getApellidomaterno());
					accionistaPersonanaturalBD.setNombres(accionistaPersonanatural.getNombres());
					accionistaPersonanaturalBD.setFechanacimiento(accionistaPersonanatural.getFechanacimiento());
					accionistaPersonanaturalBD.setSexo(accionistaPersonanatural.getSexo());		
					personanaturalServiceLocal.update(accionistaPersonanaturalBD);
					accionistaPersonanatural = accionistaPersonanaturalBD;
				} else {
					personanaturalServiceLocal.create(accionistaPersonanatural);
				}
				
				Accionista accionistaNew = new Accionista();		
				accionistaNew.setPersonanatural(accionistaPersonanatural);
				accionistaNew.setPersonajuridica(personajuridica);
				accionistaNew.setPorcentajeparticipacion(accionista.getPorcentajeparticipacion());
				accionistaDAO.create(accionistaNew);
			}
				
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_AHORRO);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setSaldo(new Moneda());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			for (Titular titular : titulares) {
				Personanatural titularPersonanatural = titular.getPersonanatural();
				Tipodocumento tipodocumentoTitular = titularPersonanatural.getTipodocumento();
				String numeroDocumentoTitular = titularPersonanatural.getNumerodocumento();
				Personanatural titularPersonanaturalBD = personanaturalServiceLocal.findByTipodocumento(tipodocumentoTitular, numeroDocumentoTitular);
				
				if(titularPersonanaturalBD != null){
					titularPersonanaturalBD.setTipodocumento(titularPersonanatural.getTipodocumento());
					titularPersonanaturalBD.setNumerodocumento(titularPersonanatural.getNumerodocumento());
					titularPersonanaturalBD.setApellidopaterno(titularPersonanatural.getApellidopaterno());
					titularPersonanaturalBD.setApellidomaterno(titularPersonanatural.getApellidomaterno());
					titularPersonanaturalBD.setNombres(titularPersonanatural.getNombres());
					titularPersonanaturalBD.setFechanacimiento(titularPersonanatural.getFechanacimiento());
					titularPersonanaturalBD.setSexo(titularPersonanatural.getSexo());
					titularPersonanaturalBD.setEstadocivil(titularPersonanatural.getEstadocivil());
					titularPersonanaturalBD.setOcupacion(titularPersonanatural.getOcupacion());
					titularPersonanaturalBD.setDireccion(titularPersonanatural.getDireccion());
					titularPersonanaturalBD.setReferencia(titularPersonanatural.getReferencia());
					titularPersonanaturalBD.setTelefono(titularPersonanatural.getTelefono());
					titularPersonanaturalBD.setCelular(titularPersonanatural.getCelular());
					titularPersonanaturalBD.setEmail(titularPersonanatural.getEmail());
					personanaturalServiceLocal.update(titularPersonanaturalBD);
					titularPersonanatural = titularPersonanaturalBD;
				} else {
					personanaturalServiceLocal.create(titularPersonanaturalBD);
				}
				
				Titular titularNew = new Titular();	
				titularNew.setEstado(true);
				titularNew.setFechaactiva(Calendar.getInstance().getTime());
				titularNew.setPersonanatural(titularPersonanatural);
				titularNew.setCuentabancaria(cuentabancaria);
				
				titularcuentaDAO.create(titularNew);
			}
			
			for (Beneficiario beneficiario : beneficiarios) {
				beneficiario.setEstado(true);
				beneficiario.setCuentabancaria(cuentabancaria);
				beneficiariocuentaDAO.create(beneficiario);
			}
			
			return cuentabancaria;
		} catch (IllegalEntityException | PreexistingEntityException e) {		
			cuentabancaria.setIdcuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new Exception(e.getMessage());	
		} catch (Exception e) {
			cuentabancaria.setIdcuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		} 	
	}

}
