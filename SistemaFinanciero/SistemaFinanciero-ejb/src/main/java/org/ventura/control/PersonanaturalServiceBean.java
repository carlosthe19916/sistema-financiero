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

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.PersonanaturalServiceRemote;
import org.ventura.dao.impl.PersonanaturalDAO;
import org.ventura.entity.Personanatural;

@Stateless
@Local(PersonanaturalServiceLocal.class)
@Remote(PersonanaturalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonanaturalServiceBean implements PersonanaturalServiceLocal {

	@EJB
	private PersonanaturalDAO oPersonanaturalDAO;

	@Override
	public Personanatural create(Personanatural oPersonanatural) {
		oPersonanaturalDAO.create(oPersonanatural);
		return oPersonanatural;
	}

	@Override
	public Personanatural find(Object id) {		
		return oPersonanaturalDAO.find(id);
	}

	@Override
	public void delete(Personanatural oPersonanatural) {
		oPersonanaturalDAO.delete(oPersonanatural);
	}

	@Override
	public Personanatural update(Personanatural oPersonanatural) {		
		return oPersonanaturalDAO.update(oPersonanatural);
	}

	@Override
	public Collection<Personanatural> findByNamedQuery(String queryName) {		
		return oPersonanaturalDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Personanatural> findByNamedQuery(String queryName, int resultLimit) {
		return oPersonanaturalDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Personanatural> findByNamedQuery(String Personanatural, Map<String, Object> parameters) {
		return oPersonanaturalDAO.findByNamedQuery(Personanatural, parameters);
	}

	@Override
	public List<Personanatural> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oPersonanaturalDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
