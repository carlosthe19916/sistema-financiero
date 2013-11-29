package org.ventura.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.remote.CajaServiceRemote;
import org.ventura.entity.schema.caja.Caja;

@Named
@Stateless
@Local(CajaServiceLocal.class)
@Remote(CajaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiveBean implements CajaServiceLocal{

	public CajaServiveBean() {
		
	}

	@Override
	public Caja create(Caja oCaja) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Caja find(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Caja oCaja) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Caja oCaja) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Caja> findByNamedQuery(String queryName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Caja> findByNamedQuery(String queryName, int resultLimit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caja> findByNamedQuery(String Boveda,
			Map<String, Object> parameters) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Caja> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
