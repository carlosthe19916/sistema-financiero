package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.TitularcuentahistorialDAO;
import org.ventura.facade.TitularcuentahistorialFacadeLocal;
import org.ventura.model.Titularcuentahistorial;

@Stateless
public class TitularcuentahistorialFacadeLocalImpl implements TitularcuentahistorialFacadeLocal {

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
