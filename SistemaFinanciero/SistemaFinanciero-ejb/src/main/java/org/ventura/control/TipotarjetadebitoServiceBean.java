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

import org.ventura.boundary.local.TipotarjetadebitoServiceLocal;
import org.ventura.boundary.remote.TipotarjetadebitoServiceRemote;
import org.ventura.dao.impl.TipotarjetadebitoDAO;
import org.ventura.entity.Tipotarjetadebito;

@Stateless
@Local(TipotarjetadebitoServiceLocal.class)
@Remote(TipotarjetadebitoServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TipotarjetadebitoServiceBean implements TipotarjetadebitoServiceLocal {

	@EJB
	private TipotarjetadebitoDAO oTipotarjetadebitoDAO;

	@Override
	public Tipotarjetadebito create(Tipotarjetadebito oTipotarjetadebito) {
		oTipotarjetadebitoDAO.create(oTipotarjetadebito);
		return oTipotarjetadebito;
	}

	@Override
	public Tipotarjetadebito find(Integer id) {		
		return oTipotarjetadebitoDAO.find(id);
	}

	@Override
	public void delete(Tipotarjetadebito oTipotarjetadebito) {
		oTipotarjetadebitoDAO.delete(oTipotarjetadebito);
	}

	@Override
	public Tipotarjetadebito update(Tipotarjetadebito oTipotarjetadebito) {		
		return oTipotarjetadebitoDAO.update(oTipotarjetadebito);
	}

	@Override
	public Collection<Tipotarjetadebito> findByNamedQuery(String queryName) {		
		return oTipotarjetadebitoDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Tipotarjetadebito> findByNamedQuery(String queryName, int resultLimit) {
		return oTipotarjetadebitoDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Tipotarjetadebito> findByNamedQuery(String Tipotarjetadebito, Map<String, Object> parameters) {
		return oTipotarjetadebitoDAO.findByNamedQuery(Tipotarjetadebito, parameters);
	}

	@Override
	public List<Tipotarjetadebito> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oTipotarjetadebitoDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
