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

import org.ventura.boundary.local.TitularcuentahistorialServiceLocal;
import org.ventura.boundary.remote.TitularcuentahistorialServiceRemote;
import org.ventura.dao.impl.TitularcuentahistorialDAO;
import org.ventura.entity.Titularcuentahistorial;

@Stateless
@Local(TitularcuentahistorialServiceLocal.class)
@Remote(TitularcuentahistorialServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TitularcuentahistorialServiceBean implements TitularcuentahistorialServiceLocal {

	@EJB
	private TitularcuentahistorialDAO oTitularcuentahistorialDAO;

	@Override
	public Titularcuentahistorial create(Titularcuentahistorial oTitularcuentahistorial) {
		oTitularcuentahistorialDAO.create(oTitularcuentahistorial);
		return oTitularcuentahistorial;
	}

	@Override
	public Titularcuentahistorial find(Integer id) {		
		return oTitularcuentahistorialDAO.find(id);
	}

	@Override
	public void delete(Titularcuentahistorial oTitularcuentahistorial) {
		oTitularcuentahistorialDAO.delete(oTitularcuentahistorial);
	}

	@Override
	public Titularcuentahistorial update(Titularcuentahistorial oTitularcuentahistorial) {		
		return oTitularcuentahistorialDAO.update(oTitularcuentahistorial);
	}

	@Override
	public Collection<Titularcuentahistorial> findByNamedQuery(String queryName) {		
		return oTitularcuentahistorialDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Titularcuentahistorial> findByNamedQuery(String queryName, int resultLimit) {
		return oTitularcuentahistorialDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Titularcuentahistorial> findByNamedQuery(String Titularcuentahistorial, Map<String, Object> parameters) {
		return oTitularcuentahistorialDAO.findByNamedQuery(Titularcuentahistorial, parameters);
	}

	@Override
	public List<Titularcuentahistorial> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oTitularcuentahistorialDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
