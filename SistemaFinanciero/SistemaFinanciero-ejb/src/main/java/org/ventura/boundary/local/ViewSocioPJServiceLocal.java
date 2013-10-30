package org.ventura.boundary.local;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.ViewSocioPNServiceRemote;
import org.ventura.entity.schema.socio.ViewSocioPJ;
import org.ventura.entity.schema.socio.ViewSocioPN;

@Local
public interface ViewSocioPJServiceLocal extends ViewSocioPNServiceRemote{

	public List<ViewSocioPJ> findByNamedQuery(String viewSocioPJ, Map<String, Object> parameters)throws Exception;

	public List<ViewSocioPJ> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
