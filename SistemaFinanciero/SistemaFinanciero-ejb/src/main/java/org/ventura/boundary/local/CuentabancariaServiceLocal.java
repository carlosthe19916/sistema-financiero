package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.cuentapersonal.view.CuentabancariaView;

@Local
public interface CuentabancariaServiceLocal extends CuentabancariaServiceRemote {

	public Cuentabancaria create(Cuentabancaria cuentabancaria) throws Exception;
	
	public Cuentabancaria findById(Object id) throws Exception;
	
	public Cuentabancaria findByNumerocuenta(String numerocuenta) throws Exception;
	
	public CuentabancariaView findCuentabancariaViewByNumerocuenta(String numerocuenta) throws Exception;
	
	public List<CuentabancariaView> findByDni(String dni) throws Exception;
	
}
