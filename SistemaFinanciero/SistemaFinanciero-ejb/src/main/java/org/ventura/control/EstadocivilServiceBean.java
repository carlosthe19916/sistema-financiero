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

import org.ventura.boundary.local.EstadocivilServiceLocal;
import org.ventura.boundary.remote.EstadocivilServiceRemote;
import org.ventura.dao.impl.EstadocivilDAO;
import org.ventura.entity.Estadocivil;

@Stateless
@Local(EstadocivilServiceLocal.class)
@Remote(EstadocivilServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EstadocivilServiceBean implements EstadocivilServiceLocal {

	@EJB
	private EstadocivilDAO oEstadocivilDAO;

	@Override
	public Estadocivil create(Estadocivil oEstadocivil) {
		oEstadocivilDAO.create(oEstadocivil);
		return oEstadocivil;
	}

	@Override
	public Estadocivil find(Integer id) {		
		return oEstadocivilDAO.find(id);
	}

	@Override
	public void delete(Estadocivil oEstadocivil) {
		oEstadocivilDAO.delete(oEstadocivil);
	}

	@Override
	public Estadocivil update(Estadocivil oEstadocivil) {		
		return oEstadocivilDAO.update(oEstadocivil);
	}

	@Override
	public Collection<Estadocivil> findByNamedQuery(String queryName) {		
		return oEstadocivilDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Estadocivil> findByNamedQuery(String queryName, int resultLimit) {
		return oEstadocivilDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Estadocivil> findByNamedQuery(String Estadocivil, Map<String, Object> parameters) {
		return oEstadocivilDAO.findByNamedQuery(Estadocivil, parameters);
	}

	@Override
	public List<Estadocivil> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oEstadocivilDAO.findByNamedQuery(namedQueryName, parameters);
	}

	@Override
	public Collection<Estadocivil> findAll() {
		// TODO Auto-generated method stub
		return oEstadocivilDAO.findAll();
	}

}
