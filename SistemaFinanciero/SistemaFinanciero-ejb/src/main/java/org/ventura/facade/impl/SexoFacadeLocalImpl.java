package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.SexoDAO;
import org.ventura.facade.SexoFacadeLocal;
import org.ventura.model.Sexo;

@Stateless
public class SexoFacadeLocalImpl implements SexoFacadeLocal {

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

}
