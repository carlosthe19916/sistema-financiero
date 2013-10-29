package org.ventura.boundary.local;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.FuntionCuentasServiceRemote;
import org.ventura.entity.schema.socio.FunctionCuentas;

@Local
public interface FunctionCuentasServiceLocal extends FuntionCuentasServiceRemote{

	public List<FunctionCuentas> findByNamedQuery(String FunctionCuentas, Map<String, Object> parameters)throws Exception;

}
