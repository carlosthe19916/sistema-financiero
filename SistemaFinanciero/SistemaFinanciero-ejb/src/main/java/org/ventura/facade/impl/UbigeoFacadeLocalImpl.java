package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.UbigeoDAO;
import org.ventura.facade.UbigeoFacadeLocal;
import org.ventura.model.Ubigeo;

@Stateless
public class UbigeoFacadeLocalImpl implements UbigeoFacadeLocal {

	@EJB
	private UbigeoDAO oUbigeoDAO;

	@Override
	public Ubigeo create(Ubigeo oUbigeo) {
		oUbigeoDAO.create(oUbigeo);
		return oUbigeo;
	}

	@Override
	public Ubigeo find(Integer id) {		
		return oUbigeoDAO.find(id);
	}

	@Override
	public void delete(Ubigeo oUbigeo) {
		oUbigeoDAO.delete(oUbigeo);
	}

	@Override
	public Ubigeo update(Ubigeo oUbigeo) {		
		return oUbigeoDAO.update(oUbigeo);
	}

	@Override
	public Collection<Ubigeo> findByNamedQuery(String queryName) {		
		return oUbigeoDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Ubigeo> findByNamedQuery(String queryName, int resultLimit) {
		return oUbigeoDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Ubigeo> findByNamedQuery(String Ubigeo, Map<String, Object> parameters) {
		return oUbigeoDAO.findByNamedQuery(Ubigeo, parameters);
	}

	@Override
	public List<Ubigeo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oUbigeoDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
