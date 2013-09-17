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

import org.ventura.boundary.local.SexoServiceLocal;
import org.ventura.boundary.remote.SexoServiceRemote;
import org.ventura.dao.impl.SexoDAO;
import org.ventura.entity.Sexo;

@Stateless
@Local(SexoServiceLocal.class)
@Remote(SexoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SexoFacadeServiceBean implements SexoServiceLocal {

	@EJB
	private SexoDAO oSexoDAO;

	@Override
	public Sexo create(Sexo oSexo) {
		oSexoDAO.create(oSexo);
		return oSexo;
	}

	@Override
	public Sexo find(Integer id) {		
		return oSexoDAO.find(id);
	}

	@Override
	public void delete(Sexo oSexo) {
		oSexoDAO.delete(oSexo);
	}

	@Override
	public Sexo update(Sexo oSexo) {		
		return oSexoDAO.update(oSexo);
	}

	@Override
	public Collection<Sexo> findByNamedQuery(String queryName) {		
		return oSexoDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Sexo> findByNamedQuery(String queryName, int resultLimit) {
		return oSexoDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Sexo> findByNamedQuery(String Sexo, Map<String, Object> parameters) {
		return oSexoDAO.findByNamedQuery(Sexo, parameters);
	}

	@Override
	public List<Sexo> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oSexoDAO.findByNamedQuery(namedQueryName, parameters);
	}

	@Override
	public Collection<Sexo> findAll() {
		// TODO Auto-generated method stub
		return oSexoDAO.findAll();
	}

}
