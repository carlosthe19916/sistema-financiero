package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.PersonanaturalclienteDAO;
import org.ventura.facade.PersonanaturalclienteFacadeLocal;
import org.ventura.model.Personanaturalcliente;

@Stateless
public class PersonanaturalclienteFacadeLocalImpl implements PersonanaturalclienteFacadeLocal {

	@EJB
	private PersonanaturalclienteDAO oPersonanaturalclienteDAO;

	@Override
	public Personanaturalcliente create(Personanaturalcliente oPersonanaturalcliente) {
		oPersonanaturalclienteDAO.create(oPersonanaturalcliente);
		return oPersonanaturalcliente;
	}

	@Override
	public Personanaturalcliente find(Integer id) {		
		return oPersonanaturalclienteDAO.find(id);
	}

	@Override
	public void delete(Personanaturalcliente oPersonanaturalcliente) {
		oPersonanaturalclienteDAO.delete(oPersonanaturalcliente);
	}

	@Override
	public Personanaturalcliente update(Personanaturalcliente oPersonanaturalcliente) {		
		return oPersonanaturalclienteDAO.update(oPersonanaturalcliente);
	}

	@Override
	public Collection<Personanaturalcliente> findByNamedQuery(String queryName) {		
		return oPersonanaturalclienteDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Personanaturalcliente> findByNamedQuery(String queryName, int resultLimit) {
		return oPersonanaturalclienteDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Personanaturalcliente> findByNamedQuery(String Personanaturalcliente, Map<String, Object> parameters) {
		return oPersonanaturalclienteDAO.findByNamedQuery(Personanaturalcliente, parameters);
	}

	@Override
	public List<Personanaturalcliente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oPersonanaturalclienteDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
