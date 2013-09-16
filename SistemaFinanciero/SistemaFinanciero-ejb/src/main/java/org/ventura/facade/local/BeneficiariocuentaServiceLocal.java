package org.ventura.facade.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.model.Beneficiariocuenta;

@Remote
public interface BeneficiariocuentaServiceLocal {
	
	public Beneficiariocuenta create(Beneficiariocuenta oBeneficiariocuenta);

	public Beneficiariocuenta find(Integer id);

	public void delete(Beneficiariocuenta oBeneficiariocuenta);

	public Beneficiariocuenta update(Beneficiariocuenta oBeneficiariocuenta);

	public Collection<Beneficiariocuenta> findByNamedQuery(String queryName);

	public Collection<Beneficiariocuenta> findByNamedQuery(String queryName, int resultLimit);

	public List<Beneficiariocuenta> findByNamedQuery(String Beneficiariocuenta, Map<String, Object> parameters);

	public List<Beneficiariocuenta> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
