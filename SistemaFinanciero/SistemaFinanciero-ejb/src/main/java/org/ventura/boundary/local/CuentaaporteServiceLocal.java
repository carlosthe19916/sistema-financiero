package org.ventura.boundary.local;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ventura.boundary.remote.CuentaaporteServiceRemote;
import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.view.AportesCuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.cuentapersonal.view.CuentaaporteView;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.socio.Socio;

public interface CuentaaporteServiceLocal extends CuentaaporteServiceRemote{
		
		public Cuentaaporte createCuentaAporteWithPersonanatural(Cuentaaporte cuentaaporte) throws Exception;

		public Cuentaaporte createCuentaAporteWithPersonajuridica(Cuentaaporte cuentaaporte) throws Exception;

		public Cuentaaporte find(Object id) throws Exception;

		public void delete(Cuentaaporte cuentaaporte)throws Exception;

		public void update(Cuentaaporte cuentaaporte)throws Exception;

		public Collection<Cuentaaporte> findByNamedQuery(String queryName) throws Exception;

		public Collection<Cuentaaporte> findByNamedQuery(String queryName, int resultLimit) throws Exception;

		public List<Cuentaaporte> findByNamedQuery(String Cuentaaporte, Map<String, Object> parameters) throws Exception;
		
		public List<Beneficiariocuenta> findByNamedQueryBeneficiario(String beneficiario, Map<String, Object> parameters) throws Exception;
		
		public void removeBeneficiario(String cuentaAporte, Object parameters) throws Exception;
		
		public void updateBeneficiario(Socio oSocio) throws Exception;
		
		public List<Accionista> findByNamedQueryAccionista(String accionista, Map<String, Object> parameters) throws Exception;

		public List<Cuentaaporte> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;
		
		
		public Cuentaaporte findByNumerocuenta(String numerocuenta) throws Exception;

		public List<CuentaaporteView> findByDni(String dni) throws Exception;

		public CuentaaporteView findCuentaaporteViewByNumerocuenta(String numerocuenta) throws Exception;

		public List<CuentaaporteView> findCuentaaporteViewByDni(String dni) throws Exception;

		public List<CuentaaporteView> findCuentaaporteViewByRuc(String ruc) throws Exception;
	
		public List<CuentaaporteView> findCuentaaporteViewByNombre(String nombre) throws Exception;
	
		public List<CuentaaporteView> findCuentaaporteViewByRazonsocial(String razonsocial) throws Exception;
		
		public List<AportesCuentaaporteView> getTableAportes(Integer idcuentaaporte,Date startDate, Date endDate) throws Exception;
		
		public List<AportesCuentaaporteView> getTableAportesPorpagar(Integer idcuentaaporte,Date startDate, Date endDate) throws Exception;
		

}
