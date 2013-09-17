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

import org.ventura.boundary.local.TipomonedaServiceLocal;
import org.ventura.boundary.remote.TipomonedaServiceRemote;
import org.ventura.dao.impl.TipomonedaDAO;
import org.ventura.entity.Tipomoneda;

@Stateless
@Local(TipomonedaServiceLocal.class)
@Remote(TipomonedaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipomonedaServiceBean implements TipomonedaServiceLocal {

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
