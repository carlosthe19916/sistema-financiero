package org.ventura.control;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
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
import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.CuentabancariaTipotasaDAO;
import org.ventura.dao.impl.CuentabancariaViewDAO;
import org.ventura.dao.impl.InteresdiarioDAO;
import org.ventura.dao.impl.SocioDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.CuentabancariaTipotasa;
import org.ventura.entity.schema.cuentapersonal.CuentabancariaTipotasaPK;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.cuentapersonal.Interesdiario;
import org.ventura.entity.schema.cuentapersonal.Titular;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadocuentaType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.ProduceObjectTasainteres;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;
import org.ventura.util.math.BigDecimalMath;

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
	private InteresdiarioDAO interesdiarioDAO;
	
	@EJB
	private CuentabancariaTipotasaDAO cuentabancariaTipotasaDAO;
	
	@EJB
	private PersonanaturalServiceLocal personanaturalServiceLocal;
	@EJB
	private PersonajuridicaServiceLocal personajuridicaServiceLocal;
	@EJB
	private TasainteresServiceLocal tasainteresServiceLocal;
	
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
			
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_AHORRO_TASA_INTERES);
			BigDecimal tasaValor = tasainteresServiceLocal.getTasainteresCuentaahorro(cuentabancaria.getTipomoneda());
			
			CuentabancariaTipotasa cuentabancariaTipotasa =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasa.setId(pk);
			cuentabancariaTipotasa.setTasainteres(tasaValor);
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasa);
			
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
			

			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_AHORRO_TASA_INTERES);
			BigDecimal tasaValor = tasainteresServiceLocal.getTasainteresCuentaahorro(cuentabancaria.getTipomoneda());
			
			CuentabancariaTipotasa cuentabancariaTipotasa =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasa.setId(pk);
			cuentabancariaTipotasa.setTasainteres(tasaValor);
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasa);
			
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
	public Cuentabancaria createCuentacorrientePersonanatural(
			Cuentabancaria cuentabancaria, Personanatural personanatural)
			throws Exception {
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
			
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_CORRIENTE);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setSaldo(new Moneda());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_CORRIENTE_TASA_INTERES);
			BigDecimal tasaValor = tasainteresServiceLocal.getTasainteresCuentacorriente(cuentabancaria.getTipomoneda());
			
			CuentabancariaTipotasa cuentabancariaTipotasa =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasa.setId(pk);
			cuentabancariaTipotasa.setTasainteres(tasaValor);
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasa);
			
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
	public Cuentabancaria createCuentacorrientePersonajuridica(
			Cuentabancaria cuentabancaria, Personajuridica personajuridica)
			throws Exception {
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
				
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_CORRIENTE);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setSaldo(new Moneda());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_CORRIENTE_TASA_INTERES);
			BigDecimal tasaValor = tasainteresServiceLocal.getTasainteresCuentacorriente(cuentabancaria.getTipomoneda());
			
			CuentabancariaTipotasa cuentabancariaTipotasa =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasa.setId(pk);
			cuentabancariaTipotasa.setTasainteres(tasaValor);
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasa);
			
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
	public Cuentabancaria createCuentaplazofijoPersonanatural(
			Cuentabancaria cuentabancaria, Personanatural personanatural, BigDecimal tea, BigDecimal trea)
			throws Exception {
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
			
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			Tipotasa tipotasaTEA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			Tipotasa tipotasaTREA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TREA);
						
			CuentabancariaTipotasa cuentabancariaTipotasaTEA =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pkTEA = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasaTEA.setId(pkTEA);
			cuentabancariaTipotasaTEA.setTasainteres(tea);
			pkTEA.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pkTEA.setIdtipotasa(tipotasaTEA.getIdtipotasa());
			
			CuentabancariaTipotasa cuentabancariaTipotasaTREA =  new CuentabancariaTipotasa();			
			CuentabancariaTipotasaPK pkTREA = new CuentabancariaTipotasaPK();	
			cuentabancariaTipotasaTREA.setId(pkTREA);
			cuentabancariaTipotasaTREA.setTasainteres(trea);	
			pkTREA.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pkTREA.setIdtipotasa(tipotasaTREA.getIdtipotasa());
			
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTEA);
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTREA);
			
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
	public Cuentabancaria createCuentaplazofijoPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica, BigDecimal tea, BigDecimal trea)
			throws Exception {
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
				
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			Calendar start = Calendar.getInstance();
			start.setTime(cuentabancaria.getFechaapertura());
			Calendar end = Calendar.getInstance();
			start.setTime(cuentabancaria.getFechacierre());
			
			Tipotasa tipotasaTEA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			Tipotasa tipotasaTREA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TREA);
			Tipomoneda tipomoneda = cuentabancaria.getTipomoneda();
			int periodo = start.get(Calendar.DAY_OF_YEAR) - end.get(Calendar.DAY_OF_YEAR);
			BigDecimal monto = cuentabancaria.getSaldo().getValue();
			
			BigDecimal teaValor = tasainteresServiceLocal.getTea(tipomoneda, periodo, monto);
			BigDecimal treaValor = tasainteresServiceLocal.getTrea(tipomoneda, periodo, monto);
						
			CuentabancariaTipotasa cuentabancariaTipotasaTEA =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pkTEA = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasaTEA.setId(pkTEA);
			cuentabancariaTipotasaTEA.setTasainteres(teaValor);
			pkTEA.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pkTEA.setIdtipotasa(tipotasaTEA.getIdtipotasa());
			
			CuentabancariaTipotasa cuentabancariaTipotasaTREA =  new CuentabancariaTipotasa();			
			CuentabancariaTipotasaPK pkTREA = new CuentabancariaTipotasaPK();	
			cuentabancariaTipotasaTREA.setId(pkTREA);
			cuentabancariaTipotasaTREA.setTasainteres(treaValor);	
			pkTREA.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pkTREA.setIdtipotasa(tipotasaTREA.getIdtipotasa());
			
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTEA);
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTREA);
			
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
	public List<CuentabancariaView> findCuentabancariaView(TipocuentabancariaType tipocuentabancariaType,Tipodocumento tipodocumento, String campoBusqueda) throws Exception {	
		List<CuentabancariaView> cuentabancariaViews;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(tipocuentabancariaType);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO);
			
			parameters.put("idtipocuentabancaria", tipocuentabancaria.getIdtipocuentabancaria());
			parameters.put("idestadocuenta", estadocuenta.getIdestadocuenta());
			parameters.put("idtipodocumento", tipodocumento.getIdtipodocumento());
			parameters.put("numerodocumento", "%" + campoBusqueda + "%");

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.f_tipocuentabancaria_tipodocumento_estado_searched, parameters, 10);

		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancariaViews;
	}

	@Override
	public BigDecimal getInteresGeneradoPlazofijo(Integer idcuentaplazofijo) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		try {
			Cuentabancaria cuentabancaria = cuentabancariaDAO.find(idcuentaplazofijo);
			BigDecimal montoApertura = cuentabancaria.getSaldo().getValue();
			BigDecimal tea;
			int cantidadDias;
			
			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			calStart.setTime(cuentabancaria.getFechaapertura());
			calEnd.setTime(cuentabancaria.getFechacierre());
			long milis1 = calStart.getTimeInMillis();
			long milis2 = calEnd.getTimeInMillis();	
			long diff = milis2 - milis1;
			
			cantidadDias = (int) (diff / (24 * 60 * 60 * 1000));
			
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			CuentabancariaTipotasa cuentabancariaTipotasa;
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasa = cuentabancariaTipotasaDAO.find(pk);
			tea = cuentabancariaTipotasa.getTasainteres();
			
			BigDecimal a;
			
			BigDecimal potencia = new BigDecimal(cantidadDias);
			BigDecimal potenciaDivisor = new BigDecimal(360);
			potenciaDivisor.setScale(50);
			potencia.setScale(50);

			a = tea.add(BigDecimal.ONE);
			potencia = potencia.divide(potenciaDivisor, 50, RoundingMode.HALF_UP);
			
			result = BigDecimalMath.pow(a, potencia);
			result = result.subtract(BigDecimal.ONE);
			result = result.multiply(montoApertura);
			
			result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}	
		return result;
	}

	@Override
	public Cuentabancaria renovarCuentaplazofijo(Cuentabancaria cuentabancaria,int periodo, BigDecimal tea, BigDecimal trea) throws Exception {
		Cuentabancaria cuentabancariaNew = null;
		try {							
			Cuentabancaria cuentabancariaOld = cuentabancariaDAO.find(cuentabancaria.getIdcuentabancaria());
			cuentabancariaNew = new Cuentabancaria();
				
			//generar interes de cuenta OLD
			BigDecimal interes = getInteresGeneradoPlazofijo(cuentabancariaOld.getIdcuentabancaria());
			BigDecimal capital = cuentabancariaOld.getSaldo().getValue();		
			Interesdiario interesGenerado = new Interesdiario();
			interesGenerado.setCapital(new Moneda(capital));
			interesGenerado.setInteres(new Moneda(interes));
			interesGenerado.setFecha(Calendar.getInstance().getTime());
			interesGenerado.setIdcuentabancaria(cuentabancariaOld.getIdcuentabancaria());		
			interesdiarioDAO.create(interesGenerado);
			
			//actualizar datos de cuenta OLD			
			cuentabancariaOld.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO));
			cuentabancariaOld.setSaldo(new Moneda());
			cuentabancariaDAO.update(cuentabancariaOld);
			
			//crear la nueva cuenta a Plazo fijo
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			Date fechaApertura = Calendar.getInstance().getTime();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, periodo);
			Date fechaCierre = calendar.getTime();
			
			BigDecimal saldo = interes.add(capital);
			
			cuentabancariaNew.setTipocuentabancaria(tipocuentabancaria);
			cuentabancariaNew.setSocio(cuentabancariaOld.getSocio());
			cuentabancariaNew.setSaldo(new Moneda(saldo));
			cuentabancariaNew.setCantidadretirantes(cuentabancariaOld.getCantidadretirantes());
			cuentabancariaNew.setEstadocuenta(estadocuenta);
			cuentabancariaNew.setFechaapertura(fechaApertura);
			cuentabancariaNew.setFechacierre(fechaCierre);
			cuentabancariaNew.setTipomoneda(cuentabancariaOld.getTipomoneda());			
			cuentabancariaDAO.create(cuentabancariaNew);
			
			for (Titular titular : cuentabancariaOld.getTitulares()) {
				Titular titularNew = new Titular();
				titularNew.setCuentabancaria(cuentabancariaNew);
				titularNew.setEstado(true);
				titularNew.setFechaactiva(Calendar.getInstance().getTime());
				titularNew.setPersonanatural(titular.getPersonanatural());
				titularcuentaDAO.create(titularNew);
			}
			
			for (Beneficiario beneficiario : cuentabancariaOld.getBeneficiarios()) {
				Beneficiario beneficiarioNew = new Beneficiario();
				beneficiarioNew.setApellidopaterno(beneficiario.getApellidopaterno());
				beneficiarioNew.setApellidomaterno(beneficiario.getApellidomaterno());
				beneficiarioNew.setNombres(beneficiario.getNombres());
				beneficiarioNew.setCuentabancaria(cuentabancariaNew);
				beneficiarioNew.setDni(beneficiario.getDni());
				beneficiarioNew.setEstado(true);
				beneficiarioNew.setPorcentajebeneficio(beneficiario.getPorcentajebeneficio());
				beneficiariocuentaDAO.create(beneficiarioNew);
			}
			
			//crear los intereses para la nueva cuenta a plazo fijo
			Tipotasa tipotasaTEA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			Tipotasa tipotasaTREA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TREA);
						
			CuentabancariaTipotasa cuentabancariaTipotasaTEA =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pkTEA = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasaTEA.setId(pkTEA);
			cuentabancariaTipotasaTEA.setTasainteres(tea);
			pkTEA.setIdcuentabancaria(cuentabancariaNew.getIdcuentabancaria());
			pkTEA.setIdtipotasa(tipotasaTEA.getIdtipotasa());
			
			CuentabancariaTipotasa cuentabancariaTipotasaTREA =  new CuentabancariaTipotasa();			
			CuentabancariaTipotasaPK pkTREA = new CuentabancariaTipotasaPK();	
			cuentabancariaTipotasaTREA.setId(pkTREA);
			cuentabancariaTipotasaTREA.setTasainteres(trea);	
			pkTREA.setIdcuentabancaria(cuentabancariaNew.getIdcuentabancaria());
			pkTREA.setIdtipotasa(tipotasaTREA.getIdtipotasa());
			
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTEA);
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTREA);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		}		
		return cuentabancariaNew;
	}

	@Override
	public Cuentabancaria cancelacionAnticipadaCuentaplazofijo(Cuentabancaria cuentabancaria, Date fechaRecalculo, BigDecimal tea,BigDecimal trea) throws Exception {
		Cuentabancaria cuentabancariaBD;
		try {							
			cuentabancariaBD = cuentabancariaDAO.find(cuentabancaria.getIdcuentabancaria());
						
			//actualizar datos de cuenta OLD			
			cuentabancariaBD.setFechacierre(fechaRecalculo);
			cuentabancariaDAO.update(cuentabancariaBD);
				
			//actualizar los intereses para la nueva cuenta a plazo fijo
			Tipotasa tipotasaTEA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			Tipotasa tipotasaTREA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TREA);
									
			CuentabancariaTipotasaPK pkTEA = new CuentabancariaTipotasaPK();		
			pkTEA.setIdcuentabancaria(cuentabancariaBD.getIdcuentabancaria());
			pkTEA.setIdtipotasa(tipotasaTEA.getIdtipotasa());
			CuentabancariaTipotasa cuentabancariaTipotasaTEA = cuentabancariaTipotasaDAO.find(pkTEA);
			cuentabancariaTipotasaTEA.setTasainteres(tea);
			cuentabancariaTipotasaDAO.update(cuentabancariaTipotasaTEA);
			
			CuentabancariaTipotasaPK pkTREA = new CuentabancariaTipotasaPK();		
			pkTREA.setIdcuentabancaria(cuentabancariaBD.getIdcuentabancaria());
			pkTREA.setIdtipotasa(tipotasaTREA.getIdtipotasa());
			CuentabancariaTipotasa cuentabancariaTipotasaTREA =  cuentabancariaTipotasaDAO.find(pkTREA);	
			cuentabancariaTipotasaTREA.setTasainteres(trea);
			cuentabancariaTipotasaDAO.update(cuentabancariaTipotasaTREA);		
			
			//generar interes de cuenta OLD
			BigDecimal interes = getInteresGeneradoPlazofijo(cuentabancariaBD.getIdcuentabancaria());
			BigDecimal capital = cuentabancariaBD.getSaldo().getValue();		
			Interesdiario interesGenerado = new Interesdiario();
			interesGenerado.setCapital(new Moneda(capital));
			interesGenerado.setInteres(new Moneda(interes));
			interesGenerado.setFecha(Calendar.getInstance().getTime());
			interesGenerado.setIdcuentabancaria(cuentabancariaBD.getIdcuentabancaria());		
			interesdiarioDAO.create(interesGenerado);
			
			//desactivar la cuenta de plazo fijo
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO);
			cuentabancariaBD.setEstadocuenta(estadocuenta);
			cuentabancariaBD.setSaldo(new Moneda());
			cuentabancariaDAO.update(cuentabancariaBD);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e);
		}		
		return cuentabancariaBD;
	}

}
