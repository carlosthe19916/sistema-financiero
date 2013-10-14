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

import org.ventura.boundary.local.TitularcuentahistorialServiceLocal;
import org.ventura.boundary.remote.TitularcuentahistorialServiceRemote;
import org.ventura.dao.impl.TitularcuentahistorialDAO;
import org.ventura.entity.Titularcuentahistorial;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(TitularcuentahistorialServiceLocal.class)
@Remote(TitularcuentahistorialServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TitularcuentahistorialServiceBean implements TitularcuentahistorialServiceLocal {

	@Inject
	private Log log;

	@EJB
	private TitularcuentahistorialDAO oTitularcuentahistorialDAO;

	@Override
	public Titularcuentahistorial create(Titularcuentahistorial oTitularcuentahistorial) {
		try {
			oTitularcuentahistorialDAO.create(oTitularcuentahistorial);
		} catch (PreexistingEntityException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		} catch (IllegalEntityException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		} catch (RollbackFailureException e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		} catch (Exception e) {
			log.error("ERROR:" + e.getMessage());
			log.error("Caused by:" + e.getCause());
		}
		return oTitularcuentahistorial;
	}

	@Override
	public Titularcuentahistorial find(Integer id) {
		Titularcuentahistorial Titularcuentahistorial = null;
		try {
			Titularcuentahistorial = oTitularcuentahistorialDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Titularcuentahistorial;
	}

	@Override
	public void delete(Titularcuentahistorial oTitularcuentahistorial) {
		try {
			oTitularcuentahistorialDAO.delete(oTitularcuentahistorial);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Titularcuentahistorial update(Titularcuentahistorial oTitularcuentahistorial) {
		Titularcuentahistorial Titularcuentahistorial = null;
		try {
			Titularcuentahistorial = oTitularcuentahistorialDAO.update(oTitularcuentahistorial);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Titularcuentahistorial;
	}

	@Override
	public Collection<Titularcuentahistorial> findByNamedQuery(String queryName) {
		Collection<Titularcuentahistorial> collection = null;
		try {
			collection = oTitularcuentahistorialDAO.findByNamedQuery(queryName);
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
	public Collection<Titularcuentahistorial> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Titularcuentahistorial> collection = null;
		try {
			collection = oTitularcuentahistorialDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Titularcuentahistorial> findByNamedQuery(String Titularcuentahistorial,
			Map<String, Object> parameters) {
		List<Titularcuentahistorial> list = null;
		try {
			list = oTitularcuentahistorialDAO.findByNamedQuery(Titularcuentahistorial, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Titularcuentahistorial> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Titularcuentahistorial> list = null;
		try {
			list = oTitularcuentahistorialDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
