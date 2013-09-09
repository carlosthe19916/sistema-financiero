package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.EstadocivilDAO;
import org.ventura.facade.EstadocivilFacadeLocal;
import org.ventura.model.Estadocivil;

@Stateless
public class EstadocivilFacadeLocalImpl implements EstadocivilFacadeLocal {

	@EJB
	private EstadocivilDAO oEstadocivilDAO;

	@Override
	public Estadocivil create(Estadocivil oEstadocivil) {
		oEstadocivilDAO.create(oEstadocivil);
		return oEstadocivil;
	}

	@Override
	public Estadocivil find(Integer id) {		
		return oEstadocivilDAO.find(id);
	}

	@Override
	public void delete(Estadocivil oEstadocivil) {
		oEstadocivilDAO.delete(oEstadocivil);
	}

	@Override
	public Estadocivil update(Estadocivil oEstadocivil) {		
		return oEstadocivilDAO.update(oEstadocivil);
	}

	@Override
	public Collection<Estadocivil> findByNamedQuery(String queryName) {		
		return oEstadocivilDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Estadocivil> findByNamedQuery(String queryName, int resultLimit) {
		return oEstadocivilDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Estadocivil> findByNamedQuery(String Estadocivil, Map<String, Object> parameters) {
		return oEstadocivilDAO.findByNamedQuery(Estadocivil, parameters);
	}

	@Override
	public List<Estadocivil> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oEstadocivilDAO.findByNamedQuery(namedQueryName, parameters);
	}

	@Override
	public Collection<Estadocivil> findAll() {
		// TODO Auto-generated method stub
		return oEstadocivilDAO.findAll();
	}

}
