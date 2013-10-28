package org.ventura.boundary.local;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.ViewSocioServiceRemote;
import org.ventura.entity.ViewSocioPN;

@Local
public interface ViewSocioServiceLocal extends ViewSocioServiceRemote{

	public List<ViewSocioPN> findByNamedQuery(String ViewSocioPN, Map<String, Object> parameters)throws Exception;

	public List<ViewSocioPN> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit)throws Exception;

}
