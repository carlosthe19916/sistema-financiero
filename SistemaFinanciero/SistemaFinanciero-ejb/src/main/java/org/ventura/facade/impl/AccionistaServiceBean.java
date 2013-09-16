package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.ventura.dao.impl.AccionistaDAO;
import org.ventura.facade.AccionistaServiceLocal;
import org.ventura.facade.remote.AccionistaServiceRemote;
import org.ventura.model.Accionista;

@Stateless
@Local(AccionistaServiceLocal.class)
@Remote(AccionistaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AccionistaServiceBean implements AccionistaServiceLocal {

	@EJB
	private AccionistaDAO oAccionistaDAO;

	@Override
	public Accionista create(Accionista oAccionista) {
		oAccionistaDAO.create(oAccionista);
		return oAccionista;
	}

	@Override
	public Accionista find(Integer id) {		
		return oAccionistaDAO.find(id);
	}

	@Override
	public void delete(Accionista oAccionista) {
		oAccionistaDAO.delete(oAccionista);
	}

	@Override
	public Accionista update(Accionista oAccionista) {		
		return oAccionistaDAO.update(oAccionista);
	}

	@Override
	public Collection<Accionista> findByNamedQuery(String queryName) {		
		return oAccionistaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Accionista> findByNamedQuery(String queryName, int resultLimit) {
		return oAccionistaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Accionista> findByNamedQuery(String Accionista, Map<String, Object> parameters) {
		return oAccionistaDAO.findByNamedQuery(Accionista, parameters);
	}

	@Override
	public List<Accionista> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oAccionistaDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
