package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.CuentaplazofijoServiceRemote;
import org.ventura.entity.schema.cuentapersonal.Cuentacorriente;
import org.ventura.entity.schema.cuentapersonal.Cuentaplazofijo;
import org.ventura.entity.schema.sucursal.Agencia;

@Local
public interface CuentaplazofijoServiceLocal extends CuentaplazofijoServiceRemote{
	
	public Cuentaplazofijo createCuentaPlazofijoWithPersonanatural(Cuentaplazofijo cuentaplazofijo) throws Exception;

	public Cuentaplazofijo createCuentaPlazofijoWithPersonajuridica(Cuentaplazofijo cuentaplazofijo) throws Exception;

	public void setAgencia(Agencia agencia);
	
	public Cuentaplazofijo find(Object id);

	public void delete(Cuentaplazofijo oCuentaplazofijo)throws Exception;

	public void update(Cuentaplazofijo oCuentaplazofijo)throws Exception;

	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName)throws Exception;

	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName, int resultLimit)throws Exception;

	public List<Cuentaplazofijo> findByNamedQuery(String Cuentaplazofijo, Map<String, Object> parameters)throws Exception;

	public List<Cuentaplazofijo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
