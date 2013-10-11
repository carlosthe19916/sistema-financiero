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

import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.boundary.remote.PersonanaturalclienteServiceRemote;
import org.ventura.dao.impl.PersonanaturalclienteDAO;
import org.ventura.entity.Personanaturalcliente;

@Stateless
@Local(PersonanaturalclienteServiceLocal.class)
@Remote(PersonanaturalclienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonanaturalclienteServiceBean implements PersonanaturalclienteServiceLocal {

	@EJB
	private PersonanaturalclienteDAO oPersonanaturalclienteDAO;

	@Override
	public Personanaturalcliente create(Personanaturalcliente oPersonanaturalcliente) {
		oPersonanaturalclienteDAO.create(oPersonanaturalcliente);
		return oPersonanaturalcliente;
	}

	@Override
	public Personanaturalcliente find(Object id) {		
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
