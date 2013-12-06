package org.ventura.dependent;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.dao.CrudService;

@ManagedBean
@NoneScoped
public class ListSelectedBean<E> {
	@EJB
	private CrudService crudService;
	
	private List<E> source;
	private List<E> target;
	
	
	public ListSelectedBean() {
		// TODO Auto-generated constructor stub
	}


	public List<E> getSource() {
		return source;
	}


	public void setSource(List<E> source) {
		this.source = source;
	}


	public List<E> getTarget() {
		return target;
	}


	public void setTarget(List<E> target) {
		this.target = target;
	}
	
	public void initValuesFromNamedQueryName(String namedQueryName, Map<String, Object> parameters) {
		List<E> list = crudService.findWithNamedQuery(namedQueryName, parameters);
		this.source=list;
	}

}
