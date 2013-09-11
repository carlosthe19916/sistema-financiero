package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.TarjetadebitoasignadocuentacorrienteDAO;
import org.ventura.facade.TarjetadebitoasignadocuentacorrienteFacadeLocal;
import org.ventura.model.Tarjetadebitoasignadocuentacorriente;

@Stateless
public class TarjetadebitoasignadocuentacorrienteFacadeLocalImpl implements TarjetadebitoasignadocuentacorrienteFacadeLocal {

	@EJB
	private TarjetadebitoasignadocuentacorrienteDAO oTarjetadebitoasignadocuentacorrienteDAO;

	@Override
	public Tarjetadebitoasignadocuentacorriente create(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente) {
		oTarjetadebitoasignadocuentacorrienteDAO.create(oTarjetadebitoasignadocuentacorriente);
		return oTarjetadebitoasignadocuentacorriente;
	}

	@Override
	public Tarjetadebitoasignadocuentacorriente find(Integer id) {		
		return oTarjetadebitoasignadocuentacorrienteDAO.find(id);
	}

	@Override
	public void delete(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente) {
		oTarjetadebitoasignadocuentacorrienteDAO.delete(oTarjetadebitoasignadocuentacorriente);
	}

	@Override
	public Tarjetadebitoasignadocuentacorriente update(Tarjetadebitoasignadocuentacorriente oTarjetadebitoasignadocuentacorriente) {		
		return oTarjetadebitoasignadocuentacorrienteDAO.update(oTarjetadebitoasignadocuentacorriente);
	}

	@Override
	public Collection<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String queryName) {		
		return oTarjetadebitoasignadocuentacorrienteDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String queryName, int resultLimit) {
		return oTarjetadebitoasignadocuentacorrienteDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String Tarjetadebitoasignadocuentacorriente, Map<String, Object> parameters) {
		return oTarjetadebitoasignadocuentacorrienteDAO.findByNamedQuery(Tarjetadebitoasignadocuentacorriente, parameters);
	}

	@Override
	public List<Tarjetadebitoasignadocuentacorriente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oTarjetadebitoasignadocuentacorrienteDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
