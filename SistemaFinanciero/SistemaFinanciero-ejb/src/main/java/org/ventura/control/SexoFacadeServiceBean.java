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

import org.ventura.boundary.local.SexoServiceLocal;
import org.ventura.boundary.remote.SexoServiceRemote;
import org.ventura.dao.impl.SexoDAO;
import org.ventura.entity.Sexo;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Stateless
@Local(SexoServiceLocal.class)
@Remote(SexoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SexoFacadeServiceBean implements SexoServiceLocal {

	

	@EJB
	private SexoDAO oSexoDAO;

	@Override
	public Sexo create(Sexo oSexo) {
		try {
			oSexoDAO.create(oSexo);
		} catch (PreexistingEntityException e) {
			//log.error("ERROR:" + e.getMessage());
			//log.error("Caused by:" + e.getCause());
		} catch (IllegalEntityException e) {
			//log.error("ERROR:" + e.getMessage());
			//log.error("Caused by:" + e.getCause());
		} catch (RollbackFailureException e) {
			//log.error("ERROR:" + e.getMessage());
			//log.error("Caused by:" + e.getCause());
		} catch (Exception e) {
			//log.error("ERROR:" + e.getMessage());
			//log.error("Caused by:" + e.getCause());
		}
		return oSexo;
	}

	@Override
	public Sexo find(Integer id) {
		Sexo sexo = null;
		try {
			sexo = oSexoDAO.find(id);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sexo;
	}

	@Override
	public void delete(Sexo oSexo) {
		try {
			oSexoDAO.delete(oSexo);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Sexo update(Sexo oSexo) {
		Sexo sexo = null;
		try {
			sexo = oSexoDAO.update(oSexo);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sexo;
	}

	@Override
	public Collection<Sexo> findByNamedQuery(String queryName) {
		Collection<Sexo> collection = null;
		try {
			collection = oSexoDAO.findByNamedQuery(queryName);
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
	public Collection<Sexo> findByNamedQuery(String queryName, int resultLimit) {
		Collection<Sexo> collection = null;
		try {
			collection = oSexoDAO.findByNamedQuery(queryName, resultLimit);
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
	public List<Sexo> findByNamedQuery(String Sexo,
			Map<String, Object> parameters) {
		List<Sexo> list = null;
		try {
			list = oSexoDAO.findByNamedQuery(Sexo, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Sexo> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		List<Sexo> list = null;
		try {
			list = oSexoDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Collection<Sexo> findAll() {
		Collection<Sexo> collection = null;
		try {
			collection = oSexoDAO.findAll();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collection;
	}

}
