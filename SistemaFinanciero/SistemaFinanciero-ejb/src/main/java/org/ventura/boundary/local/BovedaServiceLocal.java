package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.BovedaServiceRemote;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Detallehistorialboveda;
import org.ventura.entity.schema.caja.Detalletransaccionboveda;
import org.ventura.entity.schema.caja.Historialboveda;
import org.ventura.entity.schema.caja.Transaccionboveda;

@Local
public interface BovedaServiceLocal extends BovedaServiceRemote{
	
	public Boveda create(Boveda oBoveda)throws Exception;
	
	public Boveda find(Object id)throws Exception;

	public void delete(Boveda oBoveda)throws Exception;

	public void update(Boveda oBoveda)throws Exception;

	public Collection<Boveda> findByNamedQuery(String queryName) throws Exception;

	public Collection<Boveda> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Boveda> findByNamedQuery(String Boveda, Map<String, Object> parameters) throws Exception;
	
	public List<Boveda> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;
	
	public Historialboveda getLastHistorialboveda(Boveda oBoveda) throws Exception;
	
	public Historialboveda getHistorialActive(Boveda oBoveda) throws Exception;
	
	public List<Detallehistorialboveda> getDetalleforOpenBoveda(Boveda boveda) throws Exception;

	public void openBoveda(Boveda oBoveda) throws Exception;
	
	public void openBovedaWithPendiente(Boveda oBoveda) throws Exception;
	
	public void closeBoveda(Boveda oBoveda) throws Exception;
	
	public void freezeBoveda(Boveda oBoveda) throws Exception;
	
	public void defrostBoveda(Boveda oBoveda) throws Exception;
	
	public List<Detalletransaccionboveda> getDetalletransaccionboveda(Boveda oBoveda) throws Exception;

	/**
	 * Transacciones de boveda
	 */

	public Transaccionboveda createTransaccionboveda(Boveda boveda, Transaccionboveda oTransaccionboveda) throws Exception;
	
	public Transaccionboveda findTransaccionboveda(Object id) throws Exception;

}
