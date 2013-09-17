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

import org.ventura.boundary.local.RetirointeresServiceLocal;
import org.ventura.boundary.remote.RetirointeresServiceRemote;
import org.ventura.dao.impl.RetirointereDAO;
import org.ventura.entity.Retirointere;

@Stateless
@Local(RetirointeresServiceLocal.class)
@Remote(RetirointeresServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RetirointereServiceBean implements RetirointeresServiceLocal {

	@EJB
	private RetirointereDAO oRetirointereDAO;

	@Override
	public Retirointere create(Retirointere oRetirointere) {
		oRetirointereDAO.create(oRetirointere);
		return oRetirointere;
	}

	@Override
	public Retirointere find(Integer id) {		
		return oRetirointereDAO.find(id);
	}

	@Override
	public void delete(Retirointere oRetirointere) {
		oRetirointereDAO.delete(oRetirointere);
	}

	@Override
	public Retirointere update(Retirointere oRetirointere) {		
		return oRetirointereDAO.update(oRetirointere);
	}

	@Override
	public Collection<Retirointere> findByNamedQuery(String queryName) {		
		return oRetirointereDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Retirointere> findByNamedQuery(String queryName, int resultLimit) {
		return oRetirointereDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Retirointere> findByNamedQuery(String Retirointere, Map<String, Object> parameters) {
		return oRetirointereDAO.findByNamedQuery(Retirointere, parameters);
	}

	@Override
	public List<Retirointere> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oRetirointereDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
