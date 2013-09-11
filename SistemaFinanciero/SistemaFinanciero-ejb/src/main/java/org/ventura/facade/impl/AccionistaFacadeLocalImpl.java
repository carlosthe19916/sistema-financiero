package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.facade.AccionistaFacadeLocal;
import org.ventura.model.Accionista;

@Stateless
public class AccionistaFacadeLocalImpl implements AccionistaFacadeLocal {

	@EJB
	private AccionistaDAO oAccionistaDAO;

	@Override
	public Accionista create(Accionista oAccionista) {
		oAccionistaDAO.create(oAccionista);
		return oAccionista;
	}

	@Override
	public Accionista find(Integer id) {		
		return oAccionistaDAO.find(id);
	}

	@Override
	public void delete(Accionista oAccionista) {
		oAccionistaDAO.delete(oAccionista);
	}

	@Override
	public Accionista update(Accionista oAccionista) {		
		return oAccionistaDAO.update(oAccionista);
	}

	@Override
	public Collection<Accionista> findByNamedQuery(String queryName) {		
		return oAccionistaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Accionista> findByNamedQuery(String queryName, int resultLimit) {
		return oAccionistaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Accionista> findByNamedQuery(String Accionista, Map<String, Object> parameters) {
		return oAccionistaDAO.findByNamedQuery(Accionista, parameters);
	}

	@Override
	public List<Accionista> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oAccionistaDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
