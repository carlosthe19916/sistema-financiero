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
import javax.inject.Inject;

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.boundary.remote.PersonanaturalServiceRemote;
import org.ventura.dao.impl.PersonanaturalDAO;
import org.ventura.entity.Personanatural;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(PersonanaturalServiceLocal.class)
@Remote(PersonanaturalServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonanaturalServiceBean implements PersonanaturalServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonanaturalDAO oPersonanaturalDAO;

	@Override
	public void create(Personanatural oPersonanatural) throws RollbackFailureException {
		try {
			oPersonanaturalDAO.create(oPersonanatural);
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new RollbackFailureException("Error al insertar los datos");
		}	
	}

	@Override
	public Personanatural find(Object id) {
		Personanatural Personanatural = null;
		try {
			Personanatural = oPersonanaturalDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Personanatural;
	}

	@Override
	public void delete(Personanatural oPersonanatural) {
		try {
			oPersonanaturalDAO.delete(oPersonanatural);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Personanatural oPersonanatural) {
		try {
			oPersonanaturalDAO.update(oPersonanatural);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Personanatural> findByNamedQuery(String queryName) {
		Collection<Personanatural> collection = null;
		try {
			collection = oPersonanaturalDAO.findByNamedQuery(queryName);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collection;
	}

	@Override
	public Collection<Personanatural> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Personanatural> collection = null;
		try {
			collection = oPersonanaturalDAO.findByNamedQuery(queryName, resultLimit);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collection;
	}

	@Override
	public List<Personanatural> findByNamedQuery(String Personanatural,
			Map<String, Object> parameters) {
		List<Personanatural> list = null;
		try {
			list = oPersonanaturalDAO.findByNamedQuery(Personanatural, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Personanatural> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Personanatural> list = null;
		try {
			list = oPersonanaturalDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
