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

import org.ventura.boundary.local.UbigeoServiceLocal;
import org.ventura.boundary.remote.UbigeoServiceRemote;
import org.ventura.dao.impl.UbigeoDAO;
import org.ventura.entity.Ubigeo;

@Stateless
@Local(UbigeoServiceLocal.class)
@Remote(UbigeoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UbigeoServiceBean implements UbigeoServiceLocal {

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
