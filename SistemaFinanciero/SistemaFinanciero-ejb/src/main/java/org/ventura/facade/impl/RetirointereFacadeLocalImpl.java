package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.RetirointereDAO;
import org.ventura.facade.RetirointeresFacadeLocal;
import org.ventura.model.Retirointere;

@Stateless
public class RetirointereFacadeLocalImpl implements RetirointeresFacadeLocal {

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
