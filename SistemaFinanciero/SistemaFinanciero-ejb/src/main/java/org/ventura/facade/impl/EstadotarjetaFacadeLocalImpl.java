package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.EstadotarjetaDAO;
import org.ventura.facade.EstadotarjetaFacadeLocal;
import org.ventura.model.Estadotarjeta;

@Stateless
public class EstadotarjetaFacadeLocalImpl implements EstadotarjetaFacadeLocal {

	@EJB
	private EstadotarjetaDAO oEstadotarjetaDAO;

	@Override
	public Estadotarjeta create(Estadotarjeta oEstadotarjeta) {
		oEstadotarjetaDAO.create(oEstadotarjeta);
		return oEstadotarjeta;
	}

	@Override
	public Estadotarjeta find(Integer id) {		
		return oEstadotarjetaDAO.find(id);
	}

	@Override
	public void delete(Estadotarjeta oEstadotarjeta) {
		oEstadotarjetaDAO.delete(oEstadotarjeta);
	}

	@Override
	public Estadotarjeta update(Estadotarjeta oEstadotarjeta) {		
		return oEstadotarjetaDAO.update(oEstadotarjeta);
	}

	@Override
	public Collection<Estadotarjeta> findByNamedQuery(String queryName) {		
		return oEstadotarjetaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Estadotarjeta> findByNamedQuery(String queryName, int resultLimit) {
		return oEstadotarjetaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Estadotarjeta> findByNamedQuery(String Estadotarjeta, Map<String, Object> parameters) {
		return oEstadotarjetaDAO.findByNamedQuery(Estadotarjeta, parameters);
	}

	@Override
	public List<Estadotarjeta> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oEstadotarjetaDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
