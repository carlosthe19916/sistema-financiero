package org.ventura.control;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.ventura.boundary.local.SocioServiceLocal;
import org.ventura.boundary.local.TasainteresServiceLocal;
import org.ventura.boundary.local.TransaccionCajaServiceLocal;
import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.dao.impl.CuentabancariaDAO;
import org.ventura.dao.impl.CuentabancariaTipotasaDAO;
import org.ventura.dao.impl.CuentabancariaViewDAO;
import org.ventura.dao.impl.InteresdiarioDAO;
import org.ventura.dao.impl.SocioDAO;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.CuentabancariaTipotasa;
import org.ventura.entity.schema.cuentapersonal.CuentabancariaTipotasaPK;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.cuentapersonal.Interesdiario;
import org.ventura.entity.schema.cuentapersonal.Titular;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.EstadocuentaType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.ProduceObjectTasainteres;
import org.ventura.util.maestro.TipoTransaccionType;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;

@Stateless
@Local(CuentabancariaServiceLocal.class)
@Remote(CuentabancariaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
	@EJB
	private SocioServiceLocal socioServiceLocal;
	@EJB
	private TransaccionCajaServiceLocal transaccionCajaServiceLocal;
	
	@Override
	public Cuentabancaria create(Cuentabancaria cuentabancaria) throws Exception {
		try {
			cuentabancariaDAO.create(cuentabancaria);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancaria;
	}

	@Override
	public Cuentabancaria find(Object id) throws Exception {
		Cuentabancaria cuentabancaria;
		try {
			cuentabancaria = cuentabancariaDAO.find(id);
			List<Titular> listTitulares = cuentabancaria.getTitulares();
			List<Beneficiario> listBeneficiarios = cuentabancaria.getBeneficiarios();
			
			for (Titular titular : listTitulares) {
				Personanatural personanatural = titular.getPersonanatural();
				titular.setPersonanatural(personanatural);
				titular.setCuentabancaria(cuentabancaria);
			}
			cuentabancaria.setTitulares(listTitulares);
			cuentabancaria.setBeneficiarios(listBeneficiarios);
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

			List<Cuentabancaria> cuentabancarias = cuentabancariaDAO.findByNamedQuery(Cuentabancaria.findByNumerocuenta,parameters);
			if (cuentabancarias.size() == 1) {
				cuentabancaria = cuentabancarias.get(0);
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
	public Cuentabancaria createCuentaahorroPersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural, Agencia agencia, BigDecimal tea) throws Exception {
		try {				
			Socio socio = socioServiceLocal.find(personanatural);
			if(socio == null){
				socio = new Socio();
				socio.setPersonanatural(personanatural);
				socio.setAgencia(agencia);
				socio = socioServiceLocal.createSocioPersonanaturalSinCuentaaporte(socio);
			} 
			
			personanaturalServiceLocal.createIfNotExistsUpdateIfExist(personanatural);
			List<Titular> listTitulares = cuentabancaria.getTitulares();
			List<Beneficiario> listBeneficiarios = cuentabancaria.getBeneficiarios();
			
			//crear la cuenta bancaria
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_AHORRO);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setSaldo(new Moneda());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			this.create(cuentabancaria);
			
			//crear titulares y beneficiarios
			cuentabancaria.setTitulares(listTitulares);
			cuentabancaria.setBeneficiarios(listBeneficiarios);
			this.createTitulares(cuentabancaria);
			this.createBeneficiarios(cuentabancaria);
			
			//crear las tasas de interes para la cuenta
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_AHORRO_TASA_INTERES);
			BigDecimal tasaValor = tasainteresServiceLocal.getTasainteresCuentaahorro(cuentabancaria.getTipomoneda());
			
			CuentabancariaTipotasa cuentabancariaTipotasa =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasa.setId(pk);
			cuentabancariaTipotasa.setTasainteres(tasaValor);
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasa);
								
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
			throw new EJBException(e.getMessage());
		} 	
	}

	public void createTitulares(Cuentabancaria cuentabancaria) throws Exception{
		try {
			List<Titular> titulares = cuentabancaria.getTitulares();
			for (Titular titular : titulares) {
				Personanatural titularPersonanatural = titular.getPersonanatural();		
				titularPersonanatural = personanaturalServiceLocal.createIfNotExistsUpdateIfExistWithOnlyRequiredColumns(titularPersonanatural);			
				Titular titularNew = new Titular();	
				titularNew.setEstado(true);
				titularNew.setFechaactiva(Calendar.getInstance().getTime());
				titularNew.setPersonanatural(titularPersonanatural);
				titularNew.setCuentabancaria(cuentabancaria);			
				titularcuentaDAO.create(titularNew);
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	public void createBeneficiarios(Cuentabancaria cuentabancaria) throws Exception{
		try {
			List<Beneficiario> beneficiarios = cuentabancaria.getBeneficiarios();
			for (Beneficiario beneficiario : beneficiarios) {
				Beneficiario beneficiarioNew = new Beneficiario();					
				beneficiarioNew.setCuentabancaria(cuentabancaria);
				beneficiarioNew.setEstado(true);
				beneficiarioNew.setApellidopaterno(beneficiario.getApellidopaterno());
				beneficiarioNew.setApellidomaterno(beneficiario.getApellidomaterno());
				beneficiarioNew.setNombres(beneficiario.getNombres());
				beneficiarioNew.setDni(beneficiario.getDni());
				beneficiarioNew.setPorcentajebeneficio(beneficiario.getPorcentajebeneficio());
				beneficiariocuentaDAO.create(beneficiarioNew);
			}
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
	}
	
	@Override
	public Cuentabancaria createCuentaahorroPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica, Agencia agencia) throws Exception {
		try {				
			Socio socio = socioServiceLocal.find(personajuridica);
			if(socio == null){
				socio = new Socio();
				socio.setPersonajuridica(personajuridica);
				socio.setAgencia(agencia);
				socio = socioServiceLocal.createSocioPersonajuridicaSinCuentaaporte(socio);
			}
			
			personajuridicaServiceLocal.createIfNotExistsUpdateIfExist(personajuridica);
			List<Titular> listTitulares = cuentabancaria.getTitulares();
			List<Beneficiario> listBeneficiarios = cuentabancaria.getBeneficiarios();
			
			//crear la cuenta bancaria
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_AHORRO);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setSaldo(new Moneda());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			//crear titulares y beneficiarios
			cuentabancaria.setTitulares(listTitulares);
			cuentabancaria.setBeneficiarios(listBeneficiarios);
			this.createTitulares(cuentabancaria);
			this.createBeneficiarios(cuentabancaria);
			
			//crear las tasas de interes para la cuenta
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_AHORRO_TASA_INTERES);
			BigDecimal tasaValor = tasainteresServiceLocal.getTasainteresCuentaahorro(cuentabancaria.getTipomoneda());
			
			CuentabancariaTipotasa cuentabancariaTipotasa =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasa.setId(pk);
			cuentabancariaTipotasa.setTasainteres(tasaValor);
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasa);
			
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
			throw new EJBException(e.getMessage());
		} 	
	}

	@Override
	public Cuentabancaria createCuentacorrientePersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural, Agencia agencia) throws Exception {
		try {				
			Socio socio = socioServiceLocal.find(personanatural);
			if(socio == null){
				socio = new Socio();
				socio.setPersonanatural(personanatural);
				socio.setAgencia(agencia);
				socio = socioServiceLocal.createSocioPersonanaturalSinCuentaaporte(socio);
			}
				
			personanaturalServiceLocal.createIfNotExistsUpdateIfExist(personanatural);
			List<Titular> listTitulares = cuentabancaria.getTitulares();
			List<Beneficiario> listBeneficiarios = cuentabancaria.getBeneficiarios();
			
			//crear cuenta bancaria
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_CORRIENTE);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setSaldo(new Moneda());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			//crear titulares y beneficiarios
			cuentabancaria.setTitulares(listTitulares);
			cuentabancaria.setBeneficiarios(listBeneficiarios);
			this.createTitulares(cuentabancaria);
			this.createBeneficiarios(cuentabancaria);
			
			//crear tasas de interes para la cuenta bancaria
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_CORRIENTE_TASA_INTERES);
			BigDecimal tasaValor = tasainteresServiceLocal.getTasainteresCuentacorriente(cuentabancaria.getTipomoneda());
			
			CuentabancariaTipotasa cuentabancariaTipotasa =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasa.setId(pk);
			cuentabancariaTipotasa.setTasainteres(tasaValor);
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasa);
			
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
			throw new EJBException(e.getMessage());
		} 	
	}

	@Override
	public Cuentabancaria createCuentacorrientePersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica, Agencia agencia)throws Exception {
		try {				
			Socio socio = socioServiceLocal.find(personajuridica);
			if(socio == null){
				socio = new Socio();
				socio.setPersonajuridica(personajuridica);
				socio.setAgencia(agencia);
				socio = socioServiceLocal.createSocioPersonajuridicaSinCuentaaporte(socio);
			}
			
			personajuridicaServiceLocal.createIfNotExistsUpdateIfExist(personajuridica);
			List<Titular> listTitulares = cuentabancaria.getTitulares();
			List<Beneficiario> listBeneficiarios = cuentabancaria.getBeneficiarios();
			
			//crear cuenta bancaria
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_CORRIENTE);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(Calendar.getInstance().getTime());
			cuentabancaria.setSaldo(new Moneda());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			//crear titulares y beneficiarios
			cuentabancaria.setTitulares(listTitulares);
			cuentabancaria.setBeneficiarios(listBeneficiarios);
			this.createTitulares(cuentabancaria);
			this.createBeneficiarios(cuentabancaria);
			
			//crear tasas de interes para cuenta bancaria
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.CUENTA_CORRIENTE_TASA_INTERES);
			BigDecimal tasaValor = tasainteresServiceLocal.getTasainteresCuentacorriente(cuentabancaria.getTipomoneda());
			
			CuentabancariaTipotasa cuentabancariaTipotasa =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasa.setId(pk);
			cuentabancariaTipotasa.setTasainteres(tasaValor);
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasa);
			
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
			throw new EJBException(e.getMessage());
		} 
	}
	
	@Override
	public Cuentabancaria createCuentaplazofijoPersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural,BigDecimal monto, BigDecimal tea, Caja caja, Agencia agencia, Usuario usuario) throws Exception {
		try {				
			Socio socio = socioServiceLocal.find(personanatural);
			if(socio == null){
				socio = new Socio();
				socio.setPersonanatural(personanatural);
				socio.setAgencia(agencia);
				socio = socioServiceLocal.createSocioPersonanaturalSinCuentaaporte(socio);
			}
			
			Calendar fechaCreacion = Calendar.getInstance();
			
			personanaturalServiceLocal.createIfNotExistsUpdateIfExist(personanatural);
			List<Titular> listTitulares = cuentabancaria.getTitulares();
			List<Beneficiario> listBeneficiarios = cuentabancaria.getBeneficiarios();
			
			//crear cuenta bancaria
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setFechaapertura(fechaCreacion.getTime());
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancaria.setSaldo(new Moneda());
			cuentabancariaDAO.create(cuentabancaria);
			
			//crear transaccion del deposito 
			Transaccioncuentabancaria transaccioncuentabancaria = new Transaccioncuentabancaria();
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancaria.setEstado(true);
			transaccioncuentabancaria.setMonto(new Moneda(monto));
			transaccioncuentabancaria.setSaldodisponible(new Moneda(monto));
			transaccioncuentabancaria.setTipomoneda(cuentabancaria.getTipomoneda());
			transaccioncuentabancaria.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO));
			transaccionCajaServiceLocal.deposito(caja, cuentabancaria, transaccioncuentabancaria,null, usuario);
			
			//crear titulares y beneficiarios
			cuentabancaria.setTitulares(listTitulares);
			cuentabancaria.setBeneficiarios(listBeneficiarios);
			this.createTitulares(cuentabancaria);
			this.createBeneficiarios(cuentabancaria);
			
			//crear tasas de interes 
			Tipotasa tipotasaTEA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			//Tipotasa tipotasaTREA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TREA);
						
			CuentabancariaTipotasa cuentabancariaTipotasaTEA =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pkTEA = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasaTEA.setId(pkTEA);
			cuentabancariaTipotasaTEA.setTasainteres(tea);
			pkTEA.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pkTEA.setIdtipotasa(tipotasaTEA.getIdtipotasa());
			
			/*CuentabancariaTipotasa cuentabancariaTipotasaTREA =  new CuentabancariaTipotasa();			
			CuentabancariaTipotasaPK pkTREA = new CuentabancariaTipotasaPK();	
			cuentabancariaTipotasaTREA.setId(pkTREA);
			cuentabancariaTipotasaTREA.setTasainteres(trea);	
			pkTREA.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pkTREA.setIdtipotasa(tipotasaTREA.getIdtipotasa());*/
			
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTEA);
			//cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTREA);
			
			return cuentabancaria;
		} catch (Exception e) {
			cuentabancaria.setIdcuentabancaria(null);
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		} 	
	}

	@Override
	public Cuentabancaria createCuentaplazofijoPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica, BigDecimal tea, Agencia agencia) throws Exception {
		try {				
			Socio socio = socioServiceLocal.find(personajuridica);
			if(socio == null){
				socio = new Socio();
				socio.setPersonajuridica(personajuridica);
				socio.setAgencia(agencia);
				socio = socioServiceLocal.createSocioPersonajuridicaSinCuentaaporte(socio);
			}
			
			personajuridicaServiceLocal.createIfNotExistsUpdateIfExist(personajuridica);
			List<Titular> listTitulares = cuentabancaria.getTitulares();
			List<Beneficiario> listBeneficiarios = cuentabancaria.getBeneficiarios();
			
			//crear cuenta bancaria
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			cuentabancaria.setEstadocuenta(estadocuenta);
			cuentabancaria.setTipocuentabancaria(tipocuentabancaria);
			cuentabancaria.setSocio(socio);
			cuentabancariaDAO.create(cuentabancaria);
			
			//crear titulares y beneficiarios
			cuentabancaria.setTitulares(listTitulares);
			cuentabancaria.setBeneficiarios(listBeneficiarios);
			this.createTitulares(cuentabancaria);
			this.createBeneficiarios(cuentabancaria);
			
			//crear tasas de interes 
			Tipotasa tipotasaTEA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			//Tipotasa tipotasaTREA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TREA);
					
			CuentabancariaTipotasa cuentabancariaTipotasaTEA =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pkTEA = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasaTEA.setId(pkTEA);
			
			cuentabancariaTipotasaTEA.setTasainteres(tea);
			pkTEA.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pkTEA.setIdtipotasa(tipotasaTEA.getIdtipotasa());
			
			/*CuentabancariaTipotasa cuentabancariaTipotasaTREA =  new CuentabancariaTipotasa();			
			CuentabancariaTipotasaPK pkTREA = new CuentabancariaTipotasaPK();	
			cuentabancariaTipotasaTREA.setId(pkTREA);
			cuentabancariaTipotasaTREA.setTasainteres(trea);	
			pkTREA.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pkTREA.setIdtipotasa(tipotasaTREA.getIdtipotasa());*/
			
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTEA);
			//cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTREA);
				
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
			throw new EJBException(e.getMessage());
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
	public List<CuentabancariaView> findCuentabancariaView(TipocuentabancariaType tipocuentabancariaType, String campoBusqueda) throws Exception {	
		List<CuentabancariaView> cuentabancariaViews;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(tipocuentabancariaType);
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO);
			
			parameters.put("idtipocuentabancaria", tipocuentabancaria.getIdtipocuentabancaria());
			parameters.put("idestadocuenta", estadocuenta.getIdestadocuenta());
			parameters.put("searched", "%" + campoBusqueda.toUpperCase() + "%");

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.f_tipocuentabancaria_estado_searched, parameters, 10);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancariaViews;
	}
	
	@Override
	public List<CuentabancariaView> findCuentabancariaView(Tipodocumento tipodocumento, String campoBusqueda) throws Exception {	
		List<CuentabancariaView> cuentabancariaViews;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO);
			
			parameters.put("idestadocuenta", estadocuenta.getIdestadocuenta());
			parameters.put("idtipodocumento", tipodocumento.getIdtipodocumento());
			parameters.put("numerodocumento", "%" + campoBusqueda + "%");

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.f_tipodocumento_estado_searched, parameters, 10);

		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}
		return cuentabancariaViews;
	}

	@Override
	public List<CuentabancariaView> findCuentabancariaView(String campoBusqueda) throws Exception {
		List<CuentabancariaView> cuentabancariaViews;
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO);
			
			parameters.put("idestadocuenta", estadocuenta.getIdestadocuenta());
			parameters.put("searched", "%" + campoBusqueda.toUpperCase() + "%");

			cuentabancariaViews = cuentabancariaViewDAO.findByNamedQuery(CuentabancariaView.f_estadocuenta_searched, parameters, 10);

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
			
			result = tasainteresServiceLocal.getInteresGeneradoPlazofijo(montoApertura, cantidadDias, tea);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw e;
		}	
		return result;
	}

	@Override
	public Cuentabancaria renovarCuentaplazofijo(Cuentabancaria cuentabancaria,int periodo, BigDecimal tea, Caja caja) throws Exception {
		Cuentabancaria cuentabancariaNew = null;
		try {							
			Cuentabancaria cuentabancariaOld = cuentabancariaDAO.find(cuentabancaria.getIdcuentabancaria());
			Date fechaAperturaCuenta = cuentabancariaOld.getFechaapertura();
			Date fechaVencimientoCuenta = cuentabancariaOld.getFechacierre();
			
			Calendar calendarVencimiento = Calendar.getInstance();
			calendarVencimiento.setTime(fechaVencimientoCuenta);
			fechaVencimientoCuenta = calendarVencimiento.getTime();
			
			Calendar calendarRenovacion = Calendar.getInstance();
			calendarRenovacion.set(Calendar.HOUR_OF_DAY, 0);
			calendarRenovacion.set(Calendar.MINUTE, 0);
			calendarRenovacion.set(Calendar.SECOND, 0);
			calendarRenovacion.set(Calendar.MILLISECOND, 0);
			Date fechaRenovacion = calendarRenovacion.getTime();
			
			if(fechaAperturaCuenta.compareTo(fechaRenovacion) == -1){
				if(calendarRenovacion.compareTo(calendarVencimiento) == 1){
					
				} else {
					throw new Exception("La cuenta no puede ser renovada porque no vencio aun");
				}
			} else {
				throw new Exception("La cuenta no puede ser renovada porque no vencio aun");
			}
			
			List<Titular> listTitulares = cuentabancariaOld.getTitulares();
			List<Beneficiario> listBeneficiarios = cuentabancariaOld.getBeneficiarios();
			
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
						
			//actualizar el saldo de la cuenta			
			cuentabancariaOld.setSaldo(new Moneda(interes.add(capital)));
			cuentabancariaDAO.update(cuentabancariaOld);
			
			//generar la transaccion de retiro de todo el dinero
			Transaccioncuentabancaria transaccioncuentabancariaRetiro = new Transaccioncuentabancaria();
			transaccioncuentabancariaRetiro.setCuentabancaria(cuentabancariaOld);
			transaccioncuentabancariaRetiro.setEstado(true);
			transaccioncuentabancariaRetiro.setMonto(cuentabancariaOld.getSaldo());
			transaccioncuentabancariaRetiro.setSaldodisponible(cuentabancariaOld.getSaldo().subtract(transaccioncuentabancariaRetiro.getMonto()));
			transaccioncuentabancariaRetiro.setTipomoneda(cuentabancariaOld.getTipomoneda());
			transaccioncuentabancariaRetiro.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO));
			transaccionCajaServiceLocal.createTransaccionCuentabancaria(caja, transaccioncuentabancariaRetiro);
			
			//actualizar datos de cuenta OLD			
			cuentabancariaOld.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO));
			cuentabancariaDAO.update(cuentabancariaOld);
			
			//crear la nueva cuenta a Plazo fijo
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			Tipocuentabancaria tipocuentabancaria = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			Date fechaApertura = Calendar.getInstance().getTime();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, periodo);
			Date fechaCierre = calendar.getTime();
			
			cuentabancariaNew.setTipocuentabancaria(tipocuentabancaria);
			cuentabancariaNew.setSocio(cuentabancariaOld.getSocio());
			cuentabancariaNew.setSaldo(new Moneda());
			cuentabancariaNew.setCantidadretirantes(cuentabancariaOld.getCantidadretirantes());
			cuentabancariaNew.setEstadocuenta(estadocuenta);
			cuentabancariaNew.setFechaapertura(fechaApertura);
			cuentabancariaNew.setFechacierre(fechaCierre);
			cuentabancariaNew.setTipomoneda(cuentabancariaOld.getTipomoneda());			
			cuentabancariaDAO.create(cuentabancariaNew);
			
			//cargar los datos de la cuenta 
			Socio socio = cuentabancariaNew.getSocio();
			Personanatural personanatural = socio.getPersonanatural();
			Personajuridica personajuridica = socio.getPersonajuridica();
			socio.setPersonanatural(personanatural);
			socio.setPersonajuridica(personajuridica);
			if(personajuridica != null) {
				List<Accionista> listAccionista = personajuridica.getAccionistas();
				for (Accionista accionista : listAccionista) {
					Personanatural personanatural2 = accionista.getPersonanatural();
					accionista.setPersonanatural(personanatural2);
				}
				personajuridica.setAccionistas(listAccionista);
			}
			cuentabancariaNew.setSocio(socio);
			
			
			//crear la transaccion de deposito a la nueva cuenta
			Transaccioncuentabancaria transaccioncuentabancariaDeposito = new Transaccioncuentabancaria();
			transaccioncuentabancariaDeposito.setCuentabancaria(cuentabancariaNew);
			transaccioncuentabancariaDeposito.setEstado(true);
			transaccioncuentabancariaDeposito.setMonto(transaccioncuentabancariaRetiro.getMonto());
			transaccioncuentabancariaDeposito.setSaldodisponible(cuentabancariaNew.getSaldo().add(transaccioncuentabancariaRetiro.getMonto()));
			transaccioncuentabancariaDeposito.setTipomoneda(cuentabancariaNew.getTipomoneda());
			transaccioncuentabancariaDeposito.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO));
			transaccionCajaServiceLocal.createTransaccionCuentabancaria(caja, transaccioncuentabancariaDeposito);
			
			//crear titulares y beneficiarios
			cuentabancariaNew.setTitulares(new ArrayList<Titular>(listTitulares));
			cuentabancariaNew.setBeneficiarios(new ArrayList<Beneficiario>(listBeneficiarios));
			this.createTitulares(cuentabancariaNew);
			this.createBeneficiarios(cuentabancariaNew);
				
			//crear los intereses para la nueva cuenta a plazo fijo
			Tipotasa tipotasaTEA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			//Tipotasa tipotasaTREA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TREA);
						
			CuentabancariaTipotasa cuentabancariaTipotasaTEA =  new CuentabancariaTipotasa();
			CuentabancariaTipotasaPK pkTEA = new CuentabancariaTipotasaPK();
			cuentabancariaTipotasaTEA.setId(pkTEA);
			cuentabancariaTipotasaTEA.setTasainteres(tea);
			pkTEA.setIdcuentabancaria(cuentabancariaNew.getIdcuentabancaria());
			pkTEA.setIdtipotasa(tipotasaTEA.getIdtipotasa());
			
			/*CuentabancariaTipotasa cuentabancariaTipotasaTREA =  new CuentabancariaTipotasa();			
			CuentabancariaTipotasaPK pkTREA = new CuentabancariaTipotasaPK();	
			cuentabancariaTipotasaTREA.setId(pkTREA);
			cuentabancariaTipotasaTREA.setTasainteres(trea);	
			pkTREA.setIdcuentabancaria(cuentabancariaNew.getIdcuentabancaria());
			pkTREA.setIdtipotasa(tipotasaTREA.getIdtipotasa());*/
			
			cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTEA);
			//cuentabancariaTipotasaDAO.create(cuentabancariaTipotasaTREA);
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
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
			throw new EJBException(e.getMessage());
		}		
		return cuentabancariaBD;
	}
	
	@Override
	public Cuentabancaria recalculoCuentaplazofijo(Cuentabancaria cuentabancaria, Date fechaRecalculo, BigDecimal tea) throws Exception {
		Cuentabancaria cuentabancariaBD;
		try {							
			cuentabancariaBD = cuentabancariaDAO.find(cuentabancaria.getIdcuentabancaria());
						
			//actualizar datos de cuenta OLD			
			cuentabancariaBD.setFechacierre(fechaRecalculo);
			cuentabancariaDAO.update(cuentabancariaBD);
				
			//actualizar los intereses para la nueva cuenta a plazo fijo
			Tipotasa tipotasaTEA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TEA);
			//Tipotasa tipotasaTREA = ProduceObjectTasainteres.getTasaInteres(TipotasaCuentasPersonalesType.TREA);
									
			CuentabancariaTipotasaPK pkTEA = new CuentabancariaTipotasaPK();		
			pkTEA.setIdcuentabancaria(cuentabancariaBD.getIdcuentabancaria());
			pkTEA.setIdtipotasa(tipotasaTEA.getIdtipotasa());
			CuentabancariaTipotasa cuentabancariaTipotasaTEA = cuentabancariaTipotasaDAO.find(pkTEA);
			cuentabancariaTipotasaTEA.setTasainteres(tea);
			cuentabancariaTipotasaDAO.update(cuentabancariaTipotasaTEA);
			
			/*CuentabancariaTipotasaPK pkTREA = new CuentabancariaTipotasaPK();		
			pkTREA.setIdcuentabancaria(cuentabancariaBD.getIdcuentabancaria());
			pkTREA.setIdtipotasa(tipotasaTREA.getIdtipotasa());
			CuentabancariaTipotasa cuentabancariaTipotasaTREA =  cuentabancariaTipotasaDAO.find(pkTREA);	
			cuentabancariaTipotasaTREA.setTasainteres(trea);
			cuentabancariaTipotasaDAO.update(cuentabancariaTipotasaTREA);*/		
			
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}		
		return cuentabancariaBD;
	}

	@Override
	public BigDecimal getTasainteres(TipotasaCuentasPersonalesType tipotasaCuentasPersonalesType,Object idCuenta) throws Exception {
		BigDecimal result;
		try {
			Cuentabancaria cuentabancaria = cuentabancariaDAO.find(idCuenta);
			
			Tipotasa tipotasa = ProduceObjectTasainteres.getTasaInteres(tipotasaCuentasPersonalesType);
			
			CuentabancariaTipotasa cuentabancariaTipotasa;
			CuentabancariaTipotasaPK pk = new CuentabancariaTipotasaPK();
			pk.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());
			pk.setIdtipotasa(tipotasa.getIdtipotasa());	
			cuentabancariaTipotasa = cuentabancariaTipotasaDAO.find(pk);
			
			result = cuentabancariaTipotasa.getTasainteres();
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}		
		return result;
	}

	@Override
	public Transaccioncuentabancaria cancelarCuentaplazofijo(Caja caja,Cuentabancaria cuentabancaria, Date fechaCancelacion, Usuario usuario) throws Exception {
		Transaccioncuentabancaria transaccioncuentabancaria = null;
		try {
			cuentabancaria = cuentabancariaDAO.find(cuentabancaria.getIdcuentabancaria());
				
			Date fechaAperturaCuenta = cuentabancaria.getFechaapertura();
			Date fechaVencimientoCuenta = cuentabancaria.getFechacierre();
			
			Calendar calendarVencimiento = Calendar.getInstance();
			calendarVencimiento.setTime(fechaVencimientoCuenta);
			fechaVencimientoCuenta = calendarVencimiento.getTime();
			
			Calendar calendarRenovacion = Calendar.getInstance();
			calendarRenovacion.set(Calendar.HOUR_OF_DAY, 0);
			calendarRenovacion.set(Calendar.MINUTE, 0);
			calendarRenovacion.set(Calendar.SECOND, 0);
			calendarRenovacion.set(Calendar.MILLISECOND, 0);
			Date fechaRenovacion = calendarRenovacion.getTime();
			
			if(fechaAperturaCuenta.compareTo(fechaRenovacion) == -1){
				if(calendarRenovacion.compareTo(calendarVencimiento) == 1){
					
				} else {
					throw new Exception("Error al cancelar la cuenta, fantan días para su vencimiento.");
				}
			} else {
				throw new Exception("Error al cancelar la cuenta, fantan días para su vencimiento.");
			}
			
			//generar interes de cuenta OLD
			BigDecimal interes = getInteresGeneradoPlazofijo(cuentabancaria.getIdcuentabancaria());
			BigDecimal capital = cuentabancaria.getSaldo().getValue();		
			Interesdiario interesGenerado = new Interesdiario();
			interesGenerado.setCapital(new Moneda(capital));
			interesGenerado.setInteres(new Moneda(interes));
			interesGenerado.setFecha(Calendar.getInstance().getTime());
			interesGenerado.setIdcuentabancaria(cuentabancaria.getIdcuentabancaria());		
			interesdiarioDAO.create(interesGenerado);
						
			//actualizar el saldo de la cuenta			
			cuentabancaria.setSaldo(new Moneda(interes.add(capital)));
			cuentabancariaDAO.update(cuentabancaria);
			
			//realizar la transaccion
			transaccioncuentabancaria = new Transaccioncuentabancaria();
			transaccioncuentabancaria.setCuentabancaria(cuentabancaria);
			transaccioncuentabancaria.setEstado(true);
			transaccioncuentabancaria.setMonto(cuentabancaria.getSaldo());
			transaccioncuentabancaria.setSaldodisponible(cuentabancaria.getSaldo().subtract(transaccioncuentabancaria.getMonto()));
			transaccioncuentabancaria.setTipomoneda(cuentabancaria.getTipomoneda());
			transaccioncuentabancaria.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO));			
			transaccioncuentabancaria = transaccionCajaServiceLocal.retiro(caja, cuentabancaria, transaccioncuentabancaria,null,usuario);
			
			//cancelar la cuenta
			cuentabancaria.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO));
			cuentabancaria.setFechacierre(Calendar.getInstance().getTime());
			
			cuentabancariaDAO.update(cuentabancaria);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncuentabancaria;
	}
	
	@Override
	public Transaccioncuentabancaria cancelarCuentaahorro(Caja caja,Cuentabancaria cuentabancaria, Date fechaCancelacion, Usuario usuario) throws Exception {
		Transaccioncuentabancaria transaccioncuentabancaria = null;
		try {
			Cuentabancaria cuentabancariaDB = cuentabancariaDAO.find(cuentabancaria.getIdcuentabancaria());
				
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			Estadocuenta estadocuentaDB = cuentabancariaDB.getEstadocuenta();
			if(!estadocuenta.equals(estadocuentaDB)){
				throw new Exception("La cuenta no está Activa, no se puede cancelar");
			}
			
			//realizar la transaccion
			transaccioncuentabancaria = new Transaccioncuentabancaria();
			transaccioncuentabancaria.setCuentabancaria(cuentabancariaDB);
			transaccioncuentabancaria.setEstado(true);
			transaccioncuentabancaria.setMonto(cuentabancariaDB.getSaldo());
			transaccioncuentabancaria.setSaldodisponible(cuentabancariaDB.getSaldo().subtract(transaccioncuentabancaria.getMonto()));
			transaccioncuentabancaria.setTipomoneda(cuentabancariaDB.getTipomoneda());
			transaccioncuentabancaria.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO));			
			transaccioncuentabancaria = transaccionCajaServiceLocal.retiro(caja, cuentabancariaDB, transaccioncuentabancaria,null, usuario);
			
			//cancelar la cuenta
			cuentabancariaDB.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO));
			cuentabancariaDB.setFechacierre(fechaCancelacion);
			
			cuentabancariaDAO.update(cuentabancariaDB);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncuentabancaria;
	}

	@Override
	public Transaccioncuentabancaria cancelarCuentacorriente(Caja caja,Cuentabancaria cuentabancaria, Date fechaCancelacion, Usuario usuario) throws Exception {
		Transaccioncuentabancaria transaccioncuentabancaria = null;
		try {
			Cuentabancaria cuentabancariaDB = cuentabancariaDAO.find(cuentabancaria.getIdcuentabancaria());
				
			Estadocuenta estadocuenta = ProduceObject.getEstadocuenta(EstadocuentaType.ACTIVO);
			Estadocuenta estadocuentaDB = cuentabancariaDB.getEstadocuenta();
			if(!estadocuenta.equals(estadocuentaDB)){
				throw new Exception("La cuenta no está Activa, no se puede cancelar");
			}
			
			//realizar la transaccion
			transaccioncuentabancaria = new Transaccioncuentabancaria();
			transaccioncuentabancaria.setCuentabancaria(cuentabancariaDB);
			transaccioncuentabancaria.setEstado(true);
			transaccioncuentabancaria.setMonto(cuentabancariaDB.getSaldo());
			transaccioncuentabancaria.setSaldodisponible(cuentabancariaDB.getSaldo().subtract(transaccioncuentabancaria.getMonto()));
			transaccioncuentabancaria.setTipomoneda(cuentabancariaDB.getTipomoneda());
			transaccioncuentabancaria.setTipotransaccion(ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO));			
			transaccioncuentabancaria = transaccionCajaServiceLocal.retiro(caja, cuentabancariaDB, transaccioncuentabancaria,null, usuario);
			
			//cancelar la cuenta
			cuentabancariaDB.setEstadocuenta(ProduceObject.getEstadocuenta(EstadocuentaType.INACTIVO));
			cuentabancariaDB.setFechacierre(fechaCancelacion);
			
			cuentabancariaDAO.update(cuentabancariaDB);
		} catch (Exception e) {
			log.error("Exception:" + e.getClass());
			log.error(e.getMessage());
			log.error("Caused by:" + e.getCause());
			throw new EJBException(e.getMessage());
		}
		return transaccioncuentabancaria;
	}

	@Override
	public void capitalizarCuenta(Cuentabancaria cuentabancaria) {
		try {
			Cuentabancaria cuentabancariasDB = cuentabancariaDAO.find(cuentabancaria.getIdcuentabancaria());
			if(cuentabancariasDB == null)
				throw new Exception("Cuenta bancaria no existente");
			
			Tipocuentabancaria tipocuentabancaria = cuentabancariasDB.getTipocuentabancaria();
			if(ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO).equals(tipocuentabancaria)) {
				throw new Exception("Cuenta plazo fijo no se puede capitalizar");
			}
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idcuentabancaria",cuentabancaria.getIdcuentabancaria());

			Moneda saldoFinal = cuentabancariasDB.getSaldo();
			Moneda interesTotal = new Moneda();
			List<Interesdiario> result = interesdiarioDAO.findByNamedQuery(Interesdiario.InteresesCapitalizable, parameters);	
			for (Interesdiario interesdiario : result) {
				interesTotal = interesTotal.add(interesdiario.getInteres());
			}

			//capitalizar cuenta bancaria
			saldoFinal = saldoFinal.add(interesTotal);
			cuentabancariasDB.setSaldo(saldoFinal);
			cuentabancariaDAO.update(cuentabancariasDB);
		
			//actualizar ultimo interes generado
			List<Interesdiario> intereses = interesdiarioDAO.findByNamedQuery(Interesdiario.InteresesMaxDate, parameters);
			if(intereses.size() >= 2) {
				throw new Exception("Mas de un interes activo");
			}			
			else {
				if(intereses.size() == 1){
					Interesdiario interesUltimo = intereses.get(0);	
					interesUltimo.setEstadocapitalizacion(true);
					interesdiarioDAO.update(interesUltimo);
				}							
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("Cause:" + e.getCause());
			log.error("Class:" + e.getClass());
			throw new EJBException(e);
		}
	}

}
