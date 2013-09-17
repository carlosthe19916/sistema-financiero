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

import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.remote.PersonajuridicaServiceRemote;
import org.ventura.dao.impl.PersonajuridicaDAO;
import org.ventura.entity.Personajuridica;

@Stateless
@Local(PersonajuridicaServiceLocal.class)
@Remote(PersonajuridicaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonajuridicaServiceBean implements PersonajuridicaServiceLocal {

	@EJB
	private PersonajuridicaDAO oPersonajuridicaDAO;

	@Override
	public Personajuridica create(Personajuridica oPersonajuridica) {
		oPersonajuridicaDAO.create(oPersonajuridica);
		return oPersonajuridica;
	}

	@Override
	public Personajuridica find(Integer id) {		
		return oPersonajuridicaDAO.find(id);
	}

	@Override
	public void delete(Personajuridica oPersonajuridica) {
		oPersonajuridicaDAO.delete(oPersonajuridica);
	}

	@Override
	public Personajuridica update(Personajuridica oPersonajuridica) {		
		return oPersonajuridicaDAO.update(oPersonajuridica);
	}

	@Override
	public Collection<Personajuridica> findByNamedQuery(String queryName) {		
		return oPersonajuridicaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Personajuridica> findByNamedQuery(String queryName, int resultLimit) {
		return oPersonajuridicaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Personajuridica> findByNamedQuery(String Personajuridica, Map<String, Object> parameters) {
		return oPersonajuridicaDAO.findByNamedQuery(Personajuridica, parameters);
	}

	@Override
	public List<Personajuridica> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oPersonajuridicaDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
