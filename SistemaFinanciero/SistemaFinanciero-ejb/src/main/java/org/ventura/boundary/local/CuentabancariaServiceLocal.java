package org.ventura.boundary.local;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.Transaccionmayorcuantia;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;

@Local
public interface CuentabancariaServiceLocal extends CuentabancariaServiceRemote {

	public Cuentabancaria create(Cuentabancaria cuentabancaria) throws Exception;
	
	public Cuentabancaria createCuentaahorroPersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural, Agencia agencia, BigDecimal tea) throws Exception;
	
	public Cuentabancaria createCuentaahorroPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica, Agencia agencia, BigDecimal tea) throws Exception;
	
	public Cuentabancaria createCuentacorrientePersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural, Agencia agencia, BigDecimal tea) throws Exception;
	
	public Cuentabancaria createCuentacorrientePersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica, Agencia agencia, BigDecimal tea) throws Exception;
	
	public Cuentabancaria createCuentaplazofijoPersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural,BigDecimal monto, BigDecimal tea, Caja caja, Agencia agencia, Usuario usuario, Transaccionmayorcuantia transaccionmayorcuantia) throws Exception;
	
	public Cuentabancaria createCuentaplazofijoPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica, BigDecimal tea, Agencia agencia) throws Exception;
	
	public Cuentabancaria renovarCuentaplazofijo(Cuentabancaria cuentabancaria, int periodo, BigDecimal tea, Caja  caja) throws Exception;
	
	public Cuentabancaria cancelacionAnticipadaCuentaplazofijo(Cuentabancaria cuentabancaria, Date fechaRecalculo, BigDecimal tea, BigDecimal trea) throws Exception;
	
	public Cuentabancaria recalculoCuentaplazofijo(Cuentabancaria cuentabancaria, Date fechaRecalculo, BigDecimal tea) throws Exception;
	
	public Transaccioncuentabancaria cancelarCuentaplazofijo(Caja caja, Cuentabancaria cuentabancaria, Date fechaCancelacion, Usuario usuario) throws Exception;
	
	public Transaccioncuentabancaria cancelarCuentaahorro(Caja caja, Cuentabancaria cuentabancaria, Date fechaCancelacion, Usuario usuario) throws Exception;;
	
	public Transaccioncuentabancaria cancelarCuentacorriente(Caja caja, Cuentabancaria cuentabancaria, Date fechaCancelacion, Usuario usuario) throws Exception;;
	
	public List<Cuentabancaria> findAll() throws Exception;
	
	public Cuentabancaria find(Object id) throws Exception;
	
	public Cuentabancaria findByNumerocuenta(String numerocuenta) throws Exception;
	
	public CuentabancariaView findCuentabancariaViewByNumerocuenta(String numerocuenta) throws Exception;
	
	public BigDecimal getTasainteres(TipotasaCuentasPersonalesType tipotasaCuentasPersonalesType, Object idCuenta) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaView(TipocuentabancariaType tipocuentabancariaType, Tipodocumento tipodocumento, String campoBusqueda) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaView(TipocuentabancariaType tipocuentabancariaType, String campoBusqueda) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaView(Tipodocumento tipodocumento, String campoBusqueda) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaView(String campoBusqueda) throws Exception;		
	
	public List<CuentabancariaView> findCuentabancariaViewByNombre(String nombre) throws Exception;
		
	public BigDecimal getInteresGeneradoPlazofijo(Integer idcuentaplazofijo) throws Exception;

	public void capitalizarCuenta(Cuentabancaria cuentabancaria);
	
}
