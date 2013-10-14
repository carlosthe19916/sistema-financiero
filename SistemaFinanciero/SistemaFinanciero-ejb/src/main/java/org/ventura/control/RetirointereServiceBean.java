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

import org.ventura.boundary.local.RetirointeresServiceLocal;
import org.ventura.boundary.remote.RetirointeresServiceRemote;
import org.ventura.dao.impl.RetirointereDAO;
import org.ventura.entity.Retirointere;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(RetirointeresServiceLocal.class)
@Remote(RetirointeresServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RetirointereServiceBean implements RetirointeresServiceLocal {

	@Inject
	private Log log;

	@EJB
	private RetirointereDAO oRetirointereDAO;

	@Override
	public Retirointere create(Retirointere oRetirointere) {
		try {
			oRetirointereDAO.create(oRetirointere);
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
		return oRetirointere;
	}

	@Override
	public Retirointere find(Integer id) {
		Retirointere Retirointere = null;
		try {
			Retirointere = oRetirointereDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Retirointere;
	}

	@Override
	public void delete(Retirointere oRetirointere) {
		try {
			oRetirointereDAO.delete(oRetirointere);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Retirointere update(Retirointere oRetirointere) {
		Retirointere Retirointere = null;
		try {
			Retirointere = oRetirointereDAO.update(oRetirointere);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Retirointere;
	}

	@Override
	public Collection<Retirointere> findByNamedQuery(String queryName) {
		Collection<Retirointere> collection = null;
		try {
			collection = oRetirointereDAO.findByNamedQuery(queryName);
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
	public Collection<Retirointere> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Retirointere> collection = null;
		try {
			collection = oRetirointereDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Retirointere> findByNamedQuery(String Retirointere,
			Map<String, Object> parameters) {
		List<Retirointere> list = null;
		try {
			list = oRetirointereDAO.findByNamedQuery(Retirointere, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Retirointere> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Retirointere> list = null;
		try {
			list = oRetirointereDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
