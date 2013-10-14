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
import javax.persistence.TransactionRequiredException;

import org.ventura.boundary.local.AccionistaServiceLocal;
import org.ventura.boundary.remote.AccionistaServiceRemote;
import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.entity.Accionista;
import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;

@Stateless
@Local(AccionistaServiceLocal.class)
@Remote(AccionistaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccionistaServiceBean implements AccionistaServiceLocal {

	@EJB
	private AccionistaDAO oAccionistaDAO;

	@Override
	public Accionista create(Accionista oAccionista) {
		try {
			oAccionistaDAO.create(oAccionista);
		} catch (PreexistingEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oAccionista;
	}

	@Override
	public Accionista find(Integer id) {		
		try {
			return oAccionistaDAO.find(id);
		} catch (IllegalEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

	@Override
	public void delete(Accionista oAccionista) {
		try {
			oAccionistaDAO.delete(oAccionista);
		} catch (TransactionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Accionista update(Accionista oAccionista) {		
		try {
			return oAccionistaDAO.update(oAccionista);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

	@Override
	public Collection<Accionista> findByNamedQuery(String queryName) {		
		try {
			return oAccionistaDAO.findByNamedQuery(queryName);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

	@Override
	public Collection<Accionista> findByNamedQuery(String queryName, int resultLimit) {
		try {
			return oAccionistaDAO.findByNamedQuery(queryName, resultLimit);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

	@Override
	public List<Accionista> findByNamedQuery(String Accionista, Map<String, Object> parameters) {
		try {
			return oAccionistaDAO.findByNamedQuery(Accionista, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  null;
	}

	@Override
	public List<Accionista> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		try {
			return oAccionistaDAO.findByNamedQuery(namedQueryName, parameters);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

}
