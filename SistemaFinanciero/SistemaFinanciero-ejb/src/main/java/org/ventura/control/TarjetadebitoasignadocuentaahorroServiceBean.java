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

import org.ventura.boundary.local.TarjetadebitoasignadocuentaahorroServiceLocal;
import org.ventura.boundary.remote.TarjetadebitoasignadocuentaahorroServiceRemote;
import org.ventura.dao.impl.TarjetadebitoasignadocuentaahorroDAO;
import org.ventura.entity.Tarjetadebitoasignadocuentaahorro;

@Stateless
@Local(TarjetadebitoasignadocuentaahorroServiceLocal.class)
@Remote(TarjetadebitoasignadocuentaahorroServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TarjetadebitoasignadocuentaahorroServiceBean implements TarjetadebitoasignadocuentaahorroServiceLocal {

	@EJB
	private TarjetadebitoasignadocuentaahorroDAO oTarjetadebitoasignadocuentaahorroDAO;

	@Override
	public Tarjetadebitoasignadocuentaahorro create(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro) {
		oTarjetadebitoasignadocuentaahorroDAO.create(oTarjetadebitoasignadocuentaahorro);
		return oTarjetadebitoasignadocuentaahorro;
	}

	@Override
	public Tarjetadebitoasignadocuentaahorro find(Integer id) {		
		return oTarjetadebitoasignadocuentaahorroDAO.find(id);
	}

	@Override
	public void delete(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro) {
		oTarjetadebitoasignadocuentaahorroDAO.delete(oTarjetadebitoasignadocuentaahorro);
	}

	@Override
	public Tarjetadebitoasignadocuentaahorro update(Tarjetadebitoasignadocuentaahorro oTarjetadebitoasignadocuentaahorro) {		
		return oTarjetadebitoasignadocuentaahorroDAO.update(oTarjetadebitoasignadocuentaahorro);
	}

	@Override
	public Collection<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String queryName) {		
		return oTarjetadebitoasignadocuentaahorroDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String queryName, int resultLimit) {
		return oTarjetadebitoasignadocuentaahorroDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String Tarjetadebitoasignadocuentaahorro, Map<String, Object> parameters) {
		return oTarjetadebitoasignadocuentaahorroDAO.findByNamedQuery(Tarjetadebitoasignadocuentaahorro, parameters);
	}

	@Override
	public List<Tarjetadebitoasignadocuentaahorro> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oTarjetadebitoasignadocuentaahorroDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
