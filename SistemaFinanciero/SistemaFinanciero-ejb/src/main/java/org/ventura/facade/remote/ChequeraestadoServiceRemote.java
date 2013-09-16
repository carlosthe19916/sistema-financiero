package org.ventura.facade.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.model.Chequeraestado;

@Remote
public interface ChequeraestadoServiceRemote {
	
	public Chequeraestado create(Chequeraestado oChequeraestado);

	public Chequeraestado find(Integer id);

	public void delete(Chequeraestado oChequeraestado);

	public Chequeraestado update(Chequeraestado oChequeraestado);

	public Collection<Chequeraestado> findByNamedQuery(String queryName);

	public Collection<Chequeraestado> findByNamedQuery(String queryName, int resultLimit);

	public List<Chequeraestado> findByNamedQuery(String Chequeraestado, Map<String, Object> parameters);

	public List<Chequeraestado> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
