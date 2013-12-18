package org.ventura.boundary.local;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.CajaServiceRemote;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detallehistorialcaja;
import org.ventura.entity.schema.caja.Historialboveda;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.util.exception.RollbackFailureException;

@Local
public interface CajaServiceLocal extends CajaServiceRemote{
	
	public Caja create(Caja oCaja)throws Exception;
	
	public Caja find(Object id)throws Exception;

	public void delete(Caja oCaja)throws Exception;

	public void update(Caja oCaja)throws Exception;
	
	public void openCaja(Caja oCaja) throws Exception;
	
	public void closeCaja(Caja caja) throws Exception;
	
	public void defrostCaja(Caja oCaja) throws Exception;

	public Collection<Caja> findByNamedQuery(String queryName) throws Exception;

	public Collection<Caja> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Caja> findByNamedQuery(String Caja, Map<String, Object> parameters) throws Exception;
	
	public List<Caja> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;
	
	public Historialcaja getHistorialcajaLastActive(Caja oCaja) throws RollbackFailureException, Exception;
	
	public Historialcaja getHistorialcajaLastNoActive(Caja caja) throws Exception;
	
	public List<Boveda> getBovedas(Caja oCaja) throws Exception;

	public HashMap<Tipomoneda, List<Detallehistorialcaja>> getDetalleforOpenCaja(Caja caja) throws Exception;
	
	public HashMap<Tipomoneda,List<Detallehistorialcaja>> getDetallehistorialcajaLastActive(Caja caja) throws Exception;
	
	public HashMap<Tipomoneda,List<Detallehistorialcaja>> getDetallehistorialcajaLastNoActive(Caja caja) throws Exception;
}
