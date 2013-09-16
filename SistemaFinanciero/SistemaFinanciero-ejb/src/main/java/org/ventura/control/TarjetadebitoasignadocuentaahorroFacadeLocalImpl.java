package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.TarjetadebitoasignadocuentaahorroDAO;
import org.ventura.facade.TarjetadebitoasignadocuentaahorroFacadeLocal;
import org.ventura.model.Tarjetadebitoasignadocuentaahorro;

@Stateless
public class TarjetadebitoasignadocuentaahorroFacadeLocalImpl implements TarjetadebitoasignadocuentaahorroFacadeLocal {

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
