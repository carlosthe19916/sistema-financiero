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

import org.ventura.boundary.local.TarjetadebitoasignadocuentacorrienteServiceLocal;
import org.ventura.boundary.remote.TarjetadebitoasignadocuentacorrienteServiceRemote;
import org.ventura.dao.impl.TarjetadebitoasignadocuentacorrienteDAO;
import org.ventura.entity.Tarjetadebitoasignadocuentacorriente;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(TarjetadebitoasignadocuentacorrienteServiceLocal.class)
@Remote(TarjetadebitoasignadocuentacorrienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TarjetadebitoasignadocuentacorrienteServiceBean implements TarjetadebitoasignadocuentacorrienteServiceLocal {

	@Inject
	private Log log;

	@EJB
	private TarjetadebitoasignadocuentacorrienteDAO oTarjetadebitoasignadocuentacorrienteDAO;

	@Override
	public Tarjetadebitoasignadocuentacorriente create(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente) {
		try {
			oTarjetadebitoasignadocuentacorrienteDAO.create(oTarjetadebitoasignadocuentacorriente);
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
		return oTarjetadebitoasignadocuentacorriente;
	}

	@Override
	public Tarjetadebitoasignadocuentacorriente find(Integer id) {
		Tarjetadebitoasignadocuentacorriente Tarjetadebitoasignadocuentacorriente = null;
		try {
			Tarjetadebitoasignadocuentacorriente = oTarjetadebitoasignadocuentacorrienteDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tarjetadebitoasignadocuentacorriente;
	}

	@Override
	public void delete(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente) {
		try {
			oTarjetadebitoasignadocuentacorrienteDAO.delete(oTarjetadebitoasignadocuentacorriente);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Tarjetadebitoasignadocuentacorriente update(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente) {
		Tarjetadebitoasignadocuentacorriente Tarjetadebitoasignadocuentacorriente = null;
		try {
			Tarjetadebitoasignadocuentacorriente = oTarjetadebitoasignadocuentacorrienteDAO.update(oTarjetadebitoasignadocuentacorriente);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Tarjetadebitoasignadocuentacorriente;
	}

	@Override
	public Collection<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String queryName) {
		Collection<Tarjetadebitoasignadocuentacorriente> collection = null;
		try {
			collection = oTarjetadebitoasignadocuentacorrienteDAO.findByNamedQuery(queryName);
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
	public Collection<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Tarjetadebitoasignadocuentacorriente> collection = null;
		try {
			collection = oTarjetadebitoasignadocuentacorrienteDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String Tarjetadebitoasignadocuentacorriente,
			Map<String, Object> parameters) {
		List<Tarjetadebitoasignadocuentacorriente> list = null;
		try {
			list = oTarjetadebitoasignadocuentacorrienteDAO.findByNamedQuery(Tarjetadebitoasignadocuentacorriente, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Tarjetadebitoasignadocuentacorriente> list = null;
		try {
			list = oTarjetadebitoasignadocuentacorrienteDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
