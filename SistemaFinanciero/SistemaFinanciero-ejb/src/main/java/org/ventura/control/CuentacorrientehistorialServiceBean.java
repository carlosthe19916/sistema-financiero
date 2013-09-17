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

import org.ventura.boundary.local.CuentacorrientehistorialServiceLocal;
import org.ventura.boundary.remote.CuentacorrientehistorialServiceRemote;
import org.ventura.dao.impl.CuentacorrientehistorialDAO;
import org.ventura.entity.Cuentacorrientehistorial;

@Stateless
@Local(CuentacorrientehistorialServiceLocal.class)
@Remote(CuentacorrientehistorialServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class CuentacorrientehistorialServiceBean implements CuentacorrientehistorialServiceLocal {

	@EJB
	private CuentacorrientehistorialDAO oCuentacorrientehistorialDAO;

	@Override
	public Cuentacorrientehistorial create(Cuentacorrientehistorial oCuentacorrientehistorial) {
		oCuentacorrientehistorialDAO.create(oCuentacorrientehistorial);
		return oCuentacorrientehistorial;
	}

	@Override
	public Cuentacorrientehistorial find(Integer id) {		
		return oCuentacorrientehistorialDAO.find(id);
	}

	@Override
	public void delete(Cuentacorrientehistorial oCuentacorrientehistorial) {
		oCuentacorrientehistorialDAO.delete(oCuentacorrientehistorial);
	}

	@Override
	public Cuentacorrientehistorial update(Cuentacorrientehistorial oCuentacorrientehistorial) {		
		return oCuentacorrientehistorialDAO.update(oCuentacorrientehistorial);
	}

	@Override
	public Collection<Cuentacorrientehistorial> findByNamedQuery(String queryName) {		
		return oCuentacorrientehistorialDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Cuentacorrientehistorial> findByNamedQuery(String queryName, int resultLimit) {
		return oCuentacorrientehistorialDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Cuentacorrientehistorial> findByNamedQuery(String Cuentacorrientehistorial, Map<String, Object> parameters) {
		return oCuentacorrientehistorialDAO.findByNamedQuery(Cuentacorrientehistorial, parameters);
	}

	@Override
	public List<Cuentacorrientehistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oCuentacorrientehistorialDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
