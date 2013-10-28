package org.ventura.boundary.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.ventura.boundary.remote.CuentaaporteServiceRemote;
import org.ventura.entity.Cuentaaporte;

public interface CuentaaporteServiceLocal extends CuentaaporteServiceRemote{
		
		public Cuentaaporte createCuentaAporteWithPersonanatural(Cuentaaporte cuentaaporte) throws Exception;

		public Cuentaaporte createCuentaAporteWithPersonajuridica(Cuentaaporte cuentaaporte) throws Exception;

		public Cuentaaporte find(Object id) throws Exception;

		public void delete(Cuentaaporte cuentaaporte)throws Exception;

		public void update(Cuentaaporte cuentaaporte)throws Exception;

		public Collection<Cuentaaporte> findByNamedQuery(String queryName) throws Exception;

		public Collection<Cuentaaporte> findByNamedQuery(String queryName, int resultLimit) throws Exception;

		public List<Cuentaaporte> findByNamedQuery(String Cuentaaporte, Map<String, Object> parameters) throws Exception;

		public List<Cuentaaporte> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;

}
