package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.boundary.remote.ChequeestadoServiceRemote;
import org.ventura.entity.Chequeestado;

@Local
public interface ChequeestadoServiceLocal extends ChequeestadoServiceRemote{
	
	public Chequeestado create(Chequeestado oChequeestado);

	public Chequeestado find(Integer id);

	public void delete(Chequeestado oChequeestado);

	public Chequeestado update(Chequeestado oChequeestado);

	public Collection<Chequeestado> findByNamedQuery(String queryName);

	public Collection<Chequeestado> findByNamedQuery(String queryName, int resultLimit);

	public List<Chequeestado> findByNamedQuery(String Chequeestado, Map<String, Object> parameters);

	public List<Chequeestado> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
