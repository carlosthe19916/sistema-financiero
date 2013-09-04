package org.ventura.facade;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.model.Sexo;

@Local
public interface SexoFacadeLocal {
	public Sexo create(Sexo t);

	public Sexo find(Integer id);

	public void delete(Sexo t);

	public Sexo update(Sexo t);

	public Collection<Sexo> findByNamedQuery(String queryName);

	public Collection<Sexo> findByNamedQuery(String queryName, int resultLimit);

	public List<Sexo> findByNamedQuery(String Sexo,
			Map<String, Object> parameters);

	public List<Sexo> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit);
}
