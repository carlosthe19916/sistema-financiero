package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.model.Cuentaplazofijo;

@Remote
public interface CuentaplazofijoFacadeLocal {
	
	public Cuentaplazofijo create(Cuentaplazofijo oCuentaplazofijo);

	public Cuentaplazofijo find(Integer id);

	public void delete(Cuentaplazofijo oCuentaplazofijo);

	public Cuentaplazofijo update(Cuentaplazofijo oCuentaplazofijo);

	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName);

	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName, int resultLimit);

	public List<Cuentaplazofijo> findByNamedQuery(String Cuentaplazofijo, Map<String, Object> parameters);

	public List<Cuentaplazofijo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
