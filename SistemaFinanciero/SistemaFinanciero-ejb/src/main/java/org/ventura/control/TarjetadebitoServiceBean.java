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

import org.ventura.boundary.local.TarjetadebitoServiceLocal;
import org.ventura.boundary.remote.TarjetadebitoServiceRemote;
import org.ventura.dao.impl.TarjetadebitoDAO;
import org.ventura.entity.Tarjetadebito;

@Stateless
@Local(TarjetadebitoServiceLocal.class)
@Remote(TarjetadebitoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TarjetadebitoServiceBean implements TarjetadebitoServiceLocal {

	@EJB
	private TarjetadebitoDAO oTarjetadebitoDAO;

	@Override
	public Tarjetadebito create(Tarjetadebito oTarjetadebito) {
		oTarjetadebitoDAO.create(oTarjetadebito);
		return oTarjetadebito;
	}

	@Override
	public Tarjetadebito find(Integer id) {		
		return oTarjetadebitoDAO.find(id);
	}

	@Override
	public void delete(Tarjetadebito oTarjetadebito) {
		oTarjetadebitoDAO.delete(oTarjetadebito);
	}

	@Override
	public Tarjetadebito update(Tarjetadebito oTarjetadebito) {		
		return oTarjetadebitoDAO.update(oTarjetadebito);
	}

	@Override
	public Collection<Tarjetadebito> findByNamedQuery(String queryName) {		
		return oTarjetadebitoDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Tarjetadebito> findByNamedQuery(String queryName, int resultLimit) {
		return oTarjetadebitoDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Tarjetadebito> findByNamedQuery(String Tarjetadebito, Map<String, Object> parameters) {
		return oTarjetadebitoDAO.findByNamedQuery(Tarjetadebito, parameters);
	}

	@Override
	public List<Tarjetadebito> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oTarjetadebitoDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
