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

import org.ventura.boundary.local.TarjetadebitoasignadocuentacorrienteServiceLocal;
import org.ventura.boundary.remote.TarjetadebitoasignadocuentacorrienteServiceRemote;
import org.ventura.dao.impl.TarjetadebitoasignadocuentacorrienteDAO;
import org.ventura.entity.Tarjetadebitoasignadocuentacorriente;

@Stateless
@Local(TarjetadebitoasignadocuentacorrienteServiceLocal.class)
@Remote(TarjetadebitoasignadocuentacorrienteServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TarjetadebitoasignadocuentacorrienteServiceBean implements TarjetadebitoasignadocuentacorrienteServiceLocal {

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
