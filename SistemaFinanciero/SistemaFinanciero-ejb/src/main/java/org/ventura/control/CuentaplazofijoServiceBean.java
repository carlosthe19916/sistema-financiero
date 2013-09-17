package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.ventura.boundary.local.CuentaplazofijoServiceLocal;
import org.ventura.boundary.remote.CuentaplazofijoServiceRemote;
import org.ventura.dao.impl.CuentaplazofijoDAO;
import org.ventura.entity.Cuentaplazofijo;

@Stateless
@Local(CuentaplazofijoServiceLocal.class)
@Remote(CuentaplazofijoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentaplazofijoServiceBean implements CuentaplazofijoServiceLocal {

	@EJB
	private CuentaplazofijoDAO oCuentaplazofijoDAO;

	@Override
	public Cuentaplazofijo create(Cuentaplazofijo oCuentaplazofijo) {
		oCuentaplazofijoDAO.create(oCuentaplazofijo);
		return oCuentaplazofijo;
	}

	@Override
	public Cuentaplazofijo find(Integer id) {		
		return oCuentaplazofijoDAO.find(id);
	}

	@Override
	public void delete(Cuentaplazofijo oCuentaplazofijo) {
		oCuentaplazofijoDAO.delete(oCuentaplazofijo);
	}

	@Override
	public Cuentaplazofijo update(Cuentaplazofijo oCuentaplazofijo) {		
		return oCuentaplazofijoDAO.update(oCuentaplazofijo);
	}

	@Override
	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName) {		
		return oCuentaplazofijoDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentaplazofijo> findByNamedQuery(String queryName, int resultLimit) {
		return oCuentaplazofijoDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentaplazofijo> findByNamedQuery(String Cuentaplazofijo, Map<String, Object> parameters) {
		return oCuentaplazofijoDAO.findByNamedQuery(Cuentaplazofijo, parameters);
	}

	@Override
	public List<Cuentaplazofijo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oCuentaplazofijoDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
