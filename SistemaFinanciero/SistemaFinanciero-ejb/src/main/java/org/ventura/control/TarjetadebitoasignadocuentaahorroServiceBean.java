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

import org.ventura.boundary.local.TarjetadebitoasignadocuentaahorroServiceLocal;
import org.ventura.boundary.remote.TarjetadebitoasignadocuentaahorroServiceRemote;
import org.ventura.dao.impl.TarjetadebitoasignadocuentaahorroDAO;
import org.ventura.entity.Tarjetadebitoasignadocuentaahorro;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(TarjetadebitoasignadocuentaahorroServiceLocal.class)
@Remote(TarjetadebitoasignadocuentaahorroServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TarjetadebitoasignadocuentaahorroServiceBean implements TarjetadebitoasignadocuentaahorroServiceLocal {

	@Inject
	private Log log;

	@EJB
	private TarjetadebitoasignadocuentaahorroDAO oTarjetadebitoasignadocuentaahorroDAO;

	@Override
	public Tarjetadebitoasignadocuentaahorro create(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro) {
		try {
			oTarjetadebitoasignadocuentaahorroDAO.create(oTarjetadebitoasignadocuentaahorro);
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
		return oTarjetadebitoasignadocuentaahorro;
	}

	@Override
	public Tarjetadebitoasignadocuentaahorro find(Integer id) {
		Tarjetadebitoasignadocuentaahorro Tarjetadebitoasignadocuentaahorro = null;
		try {
			Tarjetadebitoasignadocuentaahorro = oTarjetadebitoasignadocuentaahorroDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tarjetadebitoasignadocuentaahorro;
	}

	@Override
	public void delete(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro) {
		try {
			oTarjetadebitoasignadocuentaahorroDAO.delete(oTarjetadebitoasignadocuentaahorro);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Tarjetadebitoasignadocuentaahorro update(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro) {
		Tarjetadebitoasignadocuentaahorro Tarjetadebitoasignadocuentaahorro = null;
		try {
			Tarjetadebitoasignadocuentaahorro = oTarjetadebitoasignadocuentaahorroDAO.update(oTarjetadebitoasignadocuentaahorro);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tarjetadebitoasignadocuentaahorro;
	}

	@Override
	public Collection<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String queryName) {
		Collection<Tarjetadebitoasignadocuentaahorro> collection = null;
		try {
			collection = oTarjetadebitoasignadocuentaahorroDAO.findByNamedQuery(queryName);
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
	public Collection<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Tarjetadebitoasignadocuentaahorro> collection = null;
		try {
			collection = oTarjetadebitoasignadocuentaahorroDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String Tarjetadebitoasignadocuentaahorro,
			Map<String, Object> parameters) {
		List<Tarjetadebitoasignadocuentaahorro> list = null;
		try {
			list = oTarjetadebitoasignadocuentaahorroDAO.findByNamedQuery(Tarjetadebitoasignadocuentaahorro, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Tarjetadebitoasignadocuentaahorro> list = null;
		try {
			list = oTarjetadebitoasignadocuentaahorroDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
