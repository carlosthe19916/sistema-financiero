package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.BeneficiariocuentaDAO;
import org.ventura.facade.BeneficiariocuentaFacadeLocal;
import org.ventura.model.Beneficiariocuenta;

@Stateless
public class BeneficiariocuentaFacadeLocalImpl implements BeneficiariocuentaFacadeLocal {

	@EJB
	private BeneficiariocuentaDAO oBeneficiariocuentaDAO;

	@Override
	public Beneficiariocuenta create(Beneficiariocuenta oBeneficiariocuenta) {
		oBeneficiariocuentaDAO.create(oBeneficiariocuenta);
		return oBeneficiariocuenta;
	}

	@Override
	public Beneficiariocuenta find(Integer id) {		
		return oBeneficiariocuentaDAO.find(id);
	}

	@Override
	public void delete(Beneficiariocuenta oBeneficiariocuenta) {
		oBeneficiariocuentaDAO.delete(oBeneficiariocuenta);
	}

	@Override
	public Beneficiariocuenta update(Beneficiariocuenta oBeneficiariocuenta) {		
		return oBeneficiariocuentaDAO.update(oBeneficiariocuenta);
	}

	@Override
	public Collection<Beneficiariocuenta> findByNamedQuery(String queryName) {		
		return oBeneficiariocuentaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Beneficiariocuenta> findByNamedQuery(String queryName, int resultLimit) {
		return oBeneficiariocuentaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Beneficiariocuenta> findByNamedQuery(String Beneficiariocuenta, Map<String, Object> parameters) {
		return oBeneficiariocuentaDAO.findByNamedQuery(Beneficiariocuenta, parameters);
	}

	@Override
	public List<Beneficiariocuenta> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oBeneficiariocuentaDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
