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

import org.ventura.boundary.local.EstadotarjetaServiceLocal;
import org.ventura.boundary.remote.EstadotarjetaServiceRemote;
import org.ventura.dao.impl.EstadotarjetaDAO;
import org.ventura.entity.Estadotarjeta;

@Stateless
@Local(EstadotarjetaServiceLocal.class)
@Remote(EstadotarjetaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EstadotarjetaServiceBean implements EstadotarjetaServiceLocal {

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
