package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.ventura.model.Tipoempresa;

@Remote
public interface TipoempresaFacadeLocal {
	
	public Tipoempresa create(Tipoempresa oTipoempresa);

	public Tipoempresa find(Integer id);

	public void delete(Tipoempresa oTipoempresa);

	public Tipoempresa update(Tipoempresa oTipoempresa);

	public Collection<Tipoempresa> findByNamedQuery(String queryName);

	public Collection<Tipoempresa> findByNamedQuery(String queryName, int resultLimit);

	public List<Tipoempresa> findByNamedQuery(String Tipoempresa, Map<String, Object> parameters);

	public List<Tipoempresa> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
