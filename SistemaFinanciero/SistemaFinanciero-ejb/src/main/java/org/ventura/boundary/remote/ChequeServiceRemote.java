package org.ventura.boundary.remote;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.entity.Cheque;

@Remote
public interface ChequeServiceRemote {
	
	public Cheque create(Cheque oCheque);

	public Cheque find(Integer id);

	public void delete(Cheque oCheque);

	public Cheque update(Cheque oCheque);

	public Collection<Cheque> findByNamedQuery(String queryName);

	public Collection<Cheque> findByNamedQuery(String queryName, int resultLimit);

	public List<Cheque> findByNamedQuery(String Cheque, Map<String, Object> parameters);

	public List<Cheque> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
