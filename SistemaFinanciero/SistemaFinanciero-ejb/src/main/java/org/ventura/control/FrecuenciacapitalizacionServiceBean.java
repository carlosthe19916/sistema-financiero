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

import org.ventura.boundary.local.FrecuenciacapitalizacionServiceLocal;
import org.ventura.boundary.remote.FrecuenciacapitalizacionServiceRemote;
import org.ventura.dao.impl.FrecuenciacapitalizacionDAO;
import org.ventura.entity.Frecuenciacapitalizacion;

@Stateless
@Local(FrecuenciacapitalizacionServiceLocal.class)
@Remote(FrecuenciacapitalizacionServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FrecuenciacapitalizacionServiceBean implements FrecuenciacapitalizacionServiceLocal {

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
