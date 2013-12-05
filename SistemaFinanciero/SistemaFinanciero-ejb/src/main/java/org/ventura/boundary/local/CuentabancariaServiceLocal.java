package org.ventura.boundary.local;

import javax.ejb.Local;

import org.ventura.boundary.remote.CuentabancariaServiceRemote;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;

@Local
public interface CuentabancariaServiceLocal extends CuentabancariaServiceRemote {

	public Cuentabancaria create(Cuentabancaria cuentabancaria) throws Exception;
	
	public Cuentabancaria findById(Object id) throws Exception;
	
	public Cuentabancaria findByNumerocuenta(String numerocuenta) throws Exception;
	
}
