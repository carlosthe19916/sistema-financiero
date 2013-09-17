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

import org.ventura.boundary.local.ChequeraServiceLocal;
import org.ventura.boundary.remote.ChequeraServiceRemote;
import org.ventura.dao.impl.ChequeraDAO;
import org.ventura.entity.Chequera;

@Stateless
@Local(ChequeraServiceLocal.class)
@Remote(ChequeraServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ChequeraServiceBean implements ChequeraServiceLocal {

	@EJB
	private ChequeraDAO oChequeraDAO;

	@Override
	public Chequera create(Chequera oChequera) {
		oChequeraDAO.create(oChequera);
		return oChequera;
	}

	@Override
	public Chequera find(Integer id) {		
		return oChequeraDAO.find(id);
	}

	@Override
	public void delete(Chequera oChequera) {
		oChequeraDAO.delete(oChequera);
	}

	@Override
	public Chequera update(Chequera oChequera) {		
		return oChequeraDAO.update(oChequera);
	}

	@Override
	public Collection<Chequera> findByNamedQuery(String queryName) {		
		return oChequeraDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Chequera> findByNamedQuery(String queryName, int resultLimit) {
		return oChequeraDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Chequera> findByNamedQuery(String Chequera, Map<String, Object> parameters) {
		return oChequeraDAO.findByNamedQuery(Chequera, parameters);
	}

	@Override
	public List<Chequera> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oChequeraDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
