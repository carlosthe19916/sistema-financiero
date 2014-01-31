package org.ventura.boundary.local;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ventura.boundary.remote.CuentaaporteServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.persona.Tipodocumento;

public interface CuentaaporteServiceLocal extends CuentaaporteServiceRemote{
			
	public List<CuentaaporteView> findCuentaaporteView(Tipodocumento tipodocumento, String campoBusqueda) throws Exception;
	
	public List<CuentaaporteView> findCuentaaporteView(String campoBusqueda)throws Exception;
	
	public CuentaaporteView findCuentaaporteViewByNumerocuenta(String numerocuenta) throws Exception;

	public List<AportesCuentaaporteView> getTableAportesPorpagar(Integer idcuentaaporte,Date startDate, Date endDate) throws Exception;
	
	public Transaccioncuentaaporte cancelarCuentaaporte(Caja caja, Cuentaaporte cuentaaporte, Date fechaCancelacion) throws Exception;
	
	
	public Cuentaaporte findByNumerocuenta(String numerocuenta) throws Exception;	
	
	
	
	public Cuentaaporte create(Cuentaaporte cuentaaporte) throws Exception;

	public Cuentaaporte find(Object id) throws Exception;

	public void delete(Cuentaaporte cuentaaporte)throws Exception;

	public void update(Cuentaaporte cuentaaporte)throws Exception;

	public Collection<Cuentaaporte> findByNamedQuery(String queryName) throws Exception;

	public Collection<Cuentaaporte> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Cuentaaporte> findByNamedQuery(String Cuentaaporte, Map<String, Object> parameters) throws Exception;				

	public List<Cuentaaporte> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;
				
}
