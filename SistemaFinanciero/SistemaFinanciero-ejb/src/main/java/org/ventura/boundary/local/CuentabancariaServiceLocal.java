package org.ventura.boundary.local;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.util.maestro.TipocuentabancariaType;

@Local
public interface CuentabancariaServiceLocal extends CuentabancariaServiceRemote {

	public Cuentabancaria create(Cuentabancaria cuentabancaria) throws Exception;
	
	public Cuentabancaria createCuentaahorroPersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural) throws Exception;
	
	public Cuentabancaria createCuentaahorroPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica) throws Exception;
	
	public Cuentabancaria createCuentacorrientePersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural) throws Exception;
	
	public Cuentabancaria createCuentacorrientePersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica) throws Exception;
	
	public Cuentabancaria createCuentaplazofijoPersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural, BigDecimal tea, BigDecimal trea) throws Exception;
	
	public Cuentabancaria createCuentaplazofijoPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica, BigDecimal tea, BigDecimal trea) throws Exception;
	
	public Cuentabancaria renovarCuentaplazofijo(Cuentabancaria cuentabancaria, int periodo, BigDecimal tea, BigDecimal trea) throws Exception;
	
	public Cuentabancaria cancelacionAnticipadaCuentaplazofijo(Cuentabancaria cuentabancaria, Date fechaRecalculo, BigDecimal tea, BigDecimal trea) throws Exception;
	
	public List<Cuentabancaria> findAll() throws Exception;
	
	public Cuentabancaria find(Object id) throws Exception;
	
	public Cuentabancaria findByNumerocuenta(String numerocuenta) throws Exception;
	
	public CuentabancariaView findCuentabancariaViewByNumerocuenta(String numerocuenta) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaView(TipocuentabancariaType tipocuentabancariaType, Tipodocumento tipodocumento, String campoBusqueda) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaView(Tipodocumento tipodocumento, String campoBusqueda) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaViewByDni(String dni) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaViewByRuc(String ruc) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaViewByNombre(String nombre) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaViewByRazonsocial(String razonsocial) throws Exception;
	
	public List<CuentabancariaView> findByDni(String dni) throws Exception;
	
	public BigDecimal getInteresGeneradoPlazofijo(Integer idcuentaplazofijo) throws Exception;
	
}
