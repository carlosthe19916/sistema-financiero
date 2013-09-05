package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.FrecuenciacapitalizacionDAO;
import org.ventura.facade.FrecuenciacapitalizacionFacadeLocal;
import org.ventura.model.Frecuenciacapitalizacion;

@Stateless
public class FrecuenciacapitalizacionFacadeLocalImpl implements FrecuenciacapitalizacionFacadeLocal {

	@EJB
	private FrecuenciacapitalizacionDAO oFrecuenciacapitalizacionDAO;

	@Override
	public Frecuenciacapitalizacion create(Frecuenciacapitalizacion oFrecuenciacapitalizacion) {
		oFrecuenciacapitalizacionDAO.create(oFrecuenciacapitalizacion);
		return oFrecuenciacapitalizacion;
	}

	@Override
	public Frecuenciacapitalizacion find(Integer id) {		
		return oFrecuenciacapitalizacionDAO.find(id);
	}

	@Override
	public void delete(Frecuenciacapitalizacion oFrecuenciacapitalizacion) {
		oFrecuenciacapitalizacionDAO.delete(oFrecuenciacapitalizacion);
	}

	@Override
	public Frecuenciacapitalizacion update(Frecuenciacapitalizacion oFrecuenciacapitalizacion) {		
		return oFrecuenciacapitalizacionDAO.update(oFrecuenciacapitalizacion);
	}

	@Override
	public Collection<Frecuenciacapitalizacion> findByNamedQuery(String queryName) {		
		return oFrecuenciacapitalizacionDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Frecuenciacapitalizacion> findByNamedQuery(String queryName, int resultLimit) {
		return oFrecuenciacapitalizacionDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Frecuenciacapitalizacion> findByNamedQuery(String Frecuenciacapitalizacion, Map<String, Object> parameters) {
		return oFrecuenciacapitalizacionDAO.findByNamedQuery(Frecuenciacapitalizacion, parameters);
	}

	@Override
	public List<Frecuenciacapitalizacion> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oFrecuenciacapitalizacionDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
