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

import org.ventura.boundary.local.TitularcuentaServiceLocal;
import org.ventura.boundary.remote.TitularcuentaServiceRemote;
import org.ventura.dao.impl.TitularcuentaDAO;
import org.ventura.entity.Titularcuenta;

@Stateless
@Local(TitularcuentaServiceLocal.class)
@Remote(TitularcuentaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TitularcuentaServiceBean implements TitularcuentaServiceLocal {

	@EJB
	private TitularcuentaDAO oTitularcuentaDAO;

	@Override
	public Titularcuenta create(Titularcuenta oTitularcuenta) {
		oTitularcuentaDAO.create(oTitularcuenta);
		return oTitularcuenta;
	}

	@Override
	public Titularcuenta find(Integer id) {		
		return oTitularcuentaDAO.find(id);
	}

	@Override
	public void delete(Titularcuenta oTitularcuenta) {
		oTitularcuentaDAO.delete(oTitularcuenta);
	}

	@Override
	public Titularcuenta update(Titularcuenta oTitularcuenta) {		
		return oTitularcuentaDAO.update(oTitularcuenta);
	}

	@Override
	public Collection<Titularcuenta> findByNamedQuery(String queryName) {		
		return oTitularcuentaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Titularcuenta> findByNamedQuery(String queryName, int resultLimit) {
		return oTitularcuentaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Titularcuenta> findByNamedQuery(String Titularcuenta, Map<String, Object> parameters) {
		return oTitularcuentaDAO.findByNamedQuery(Titularcuenta, parameters);
	}

	@Override
	public List<Titularcuenta> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oTitularcuentaDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
