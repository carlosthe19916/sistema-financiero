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

import org.ventura.boundary.local.TarjetadebitoServiceLocal;
import org.ventura.boundary.remote.TarjetadebitoServiceRemote;
import org.ventura.dao.impl.TarjetadebitoDAO;
import org.ventura.entity.Tarjetadebito;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(TarjetadebitoServiceLocal.class)
@Remote(TarjetadebitoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TarjetadebitoServiceBean implements TarjetadebitoServiceLocal {

	@Inject
	private Log log;

	@EJB
	private TarjetadebitoDAO oTarjetadebitoDAO;

	@Override
	public Tarjetadebito create(Tarjetadebito oTarjetadebito) {
		try {
			oTarjetadebitoDAO.create(oTarjetadebito);
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
		return oTarjetadebito;
	}

	@Override
	public Tarjetadebito find(Integer id) {
		Tarjetadebito Tarjetadebito = null;
		try {
			Tarjetadebito = oTarjetadebitoDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tarjetadebito;
	}

	@Override
	public void delete(Tarjetadebito oTarjetadebito) {
		try {
			oTarjetadebitoDAO.delete(oTarjetadebito);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Tarjetadebito update(Tarjetadebito oTarjetadebito) {
		Tarjetadebito Tarjetadebito = null;
		try {
			Tarjetadebito = oTarjetadebitoDAO.update(oTarjetadebito);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tarjetadebito;
	}

	@Override
	public Collection<Tarjetadebito> findByNamedQuery(String queryName) {
		Collection<Tarjetadebito> collection = null;
		try {
			collection = oTarjetadebitoDAO.findByNamedQuery(queryName);
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
	public Collection<Tarjetadebito> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Tarjetadebito> collection = null;
		try {
			collection = oTarjetadebitoDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Tarjetadebito> findByNamedQuery(String Tarjetadebito,
			Map<String, Object> parameters) {
		List<Tarjetadebito> list = null;
		try {
			list = oTarjetadebitoDAO.findByNamedQuery(Tarjetadebito, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tarjetadebito> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Tarjetadebito> list = null;
		try {
			list = oTarjetadebitoDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
