package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;

@Local
public interface CuentabancariaServiceLocal extends CuentabancariaServiceRemote {

	public Cuentabancaria create(Cuentabancaria cuentabancaria) throws Exception;
	
	public Cuentabancaria createCuentaahorroPersonanatural(Cuentabancaria cuentabancaria, Personanatural personanatural) throws Exception;
	
	public Cuentabancaria createCuentaahorroPersonajuridica(Cuentabancaria cuentabancaria, Personajuridica personajuridica) throws Exception;
	
	public List<Cuentabancaria> findAll() throws Exception;
	
	public Cuentabancaria findById(Object id) throws Exception;
	
	public Cuentabancaria findByNumerocuenta(String numerocuenta) throws Exception;
	
	public CuentabancariaView findCuentabancariaViewByNumerocuenta(String numerocuenta) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaViewByDni(String dni) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaViewByRuc(String ruc) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaViewByNombre(String nombre) throws Exception;
	
	public List<CuentabancariaView> findCuentabancariaViewByRazonsocial(String razonsocial) throws Exception;
	
	public List<CuentabancariaView> findByDni(String dni) throws Exception;
	
}
