package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.TipoempresaDAO;
import org.ventura.facade.TipoempresaFacadeLocal;
import org.ventura.model.Tipoempresa;

@Stateless
public class TipoempresaFacadeLocalImpl implements TipoempresaFacadeLocal {

	@EJB
	private TipoempresaDAO oTipoempresaDAO;

	@Override
	public Tipoempresa create(Tipoempresa oTipoempresa) {
		oTipoempresaDAO.create(oTipoempresa);
		return oTipoempresa;
	}

	@Override
	public Tipoempresa find(Integer id) {		
		return oTipoempresaDAO.find(id);
	}

	@Override
	public void delete(Tipoempresa oTipoempresa) {
		oTipoempresaDAO.delete(oTipoempresa);
	}

	@Override
	public Tipoempresa update(Tipoempresa oTipoempresa) {		
		return oTipoempresaDAO.update(oTipoempresa);
	}

	@Override
	public Collection<Tipoempresa> findByNamedQuery(String queryName) {		
		return oTipoempresaDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Tipoempresa> findByNamedQuery(String queryName, int resultLimit) {
		return oTipoempresaDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Tipoempresa> findByNamedQuery(String Tipoempresa, Map<String, Object> parameters) {
		return oTipoempresaDAO.findByNamedQuery(Tipoempresa, parameters);
	}

	@Override
	public List<Tipoempresa> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oTipoempresaDAO.findByNamedQuery(namedQueryName, parameters);
	}

	@Override
	public Collection<Tipoempresa> findAll() {
			return oTipoempresaDAO.findAll();
	
	}

}
