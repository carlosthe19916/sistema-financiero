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

import org.ventura.boundary.local.PersonanaturalclienteServiceLocal;
import org.ventura.boundary.remote.PersonanaturalclienteServiceRemote;
import org.ventura.dao.impl.PersonanaturalclienteDAO;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Personanaturalcliente;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(PersonanaturalclienteServiceLocal.class)
@Remote(PersonanaturalclienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonanaturalclienteServiceBean implements PersonanaturalclienteServiceLocal {

	@Inject
	private Log log;

	@EJB
	private PersonanaturalclienteDAO oPersonanaturalclienteDAO;
	
	@EJB
	private PersonanaturalServiceBean personanaturalServiceLocal;

	@Override
	public void create(Personanaturalcliente oPersonanaturalcliente) throws RollbackFailureException {
		try {
			Personanatural personanatural = oPersonanaturalcliente.getPersonanatural();
			if (personanatural != null) {
				Object key = personanatural.getDni();
				Object result = personanaturalServiceLocal.find(key);
				if (result == null) {
					personanaturalServiceLocal.create(personanatural);
				}
			}
			oPersonanaturalclienteDAO.create(oPersonanaturalcliente);		
		} catch (Exception e) {
			log.error("Error:" + e.getClass() + " " + e.getCause());
			throw new RollbackFailureException("Error al insertar los datos");
		}
	}

	@Override
	public Personanaturalcliente find(Object id) {
		Personanaturalcliente Personanaturalcliente = null;
		try {
			Personanaturalcliente = oPersonanaturalclienteDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Personanaturalcliente;
	}

	@Override
	public void delete(Personanaturalcliente oPersonanaturalcliente) {
		try {
			oPersonanaturalclienteDAO.delete(oPersonanaturalcliente);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Personanaturalcliente oPersonanaturalcliente) {
		try {
			 oPersonanaturalclienteDAO.update(oPersonanaturalcliente);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Personanaturalcliente> findByNamedQuery(String queryName) {
		Collection<Personanaturalcliente> collection = null;
		try {
			collection = oPersonanaturalclienteDAO.findByNamedQuery(queryName);
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
	public Collection<Personanaturalcliente> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Personanaturalcliente> collection = null;
		try {
			collection = oPersonanaturalclienteDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Personanaturalcliente> findByNamedQuery(String Personanaturalcliente,
			Map<String, Object> parameters) {
		List<Personanaturalcliente> list = null;
		try {
			list = oPersonanaturalclienteDAO.findByNamedQuery(Personanaturalcliente, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Personanaturalcliente> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Personanaturalcliente> list = null;
		try {
			list = oPersonanaturalclienteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
