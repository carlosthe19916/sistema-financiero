package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.TipomonedaDAO;
import org.ventura.facade.TipomonedaFacadeLocal;
import org.ventura.model.Tipomoneda;

@Stateless
public class TipomonedaFacadeLocalImpl implements TipomonedaFacadeLocal {

	@EJB
	private TipomonedaDAO oTipomonedaDAO;

	@Override
	public Tipomoneda create(Tipomoneda oTipomoneda) {
		oTipomonedaDAO.create(oTipomoneda);
		return oTipomoneda;
	}

	@Override
	public Tipomoneda find(Integer id) {		
		return oTipomonedaDAO.find(id);
	}

	@Override
	public void delete(Tipomoneda oTipomoneda) {
		oTipomonedaDAO.delete(oTipomoneda);
	}

	@Override
	public Tipomoneda update(Tipomoneda oTipomoneda) {		
		return oTipomonedaDAO.update(oTipomoneda);
	}

	@Override
	public Collection<Tipomoneda> findByNamedQuery(String queryName) {		
		return oTipomonedaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Tipomoneda> findByNamedQuery(String queryName, int resultLimit) {
		return oTipomonedaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Tipomoneda> findByNamedQuery(String Tipomoneda, Map<String, Object> parameters) {
		return oTipomonedaDAO.findByNamedQuery(Tipomoneda, parameters);
	}

	@Override
	public List<Tipomoneda> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oTipomonedaDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
