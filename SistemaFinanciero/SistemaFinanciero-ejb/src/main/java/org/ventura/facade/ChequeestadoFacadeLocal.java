package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import org.ventura.model.Chequeestado;

@Local
public interface ChequeestadoFacadeLocal {
	
	public Chequeestado create(Chequeestado oChequeestado);

	public Chequeestado find(Integer id);

	public void delete(Chequeestado oChequeestado);

	public Chequeestado update(Chequeestado oChequeestado);

	public Collection<Chequeestado> findByNamedQuery(String queryName);

	public Collection<Chequeestado> findByNamedQuery(String queryName, int resultLimit);

	public List<Chequeestado> findByNamedQuery(String Chequeestado, Map<String, Object> parameters);

	public List<Chequeestado> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
