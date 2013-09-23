package org.venturabank.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.dao.CrudService;

@ManagedBean
@NoneScoped
public class ComboMB<E> {

	@EJB
	private CrudService crudService;
	
	private Map<Integer, E> items;
	private Integer itemSelected;

	public ComboMB() {
		// TODO Auto-generated constructor stub
		this.items = (Map<Integer, E>) new HashMap<Integer, Object>();
		this.itemSelected = new Integer(-1);
	}
	
	public ComboMB(Map<Integer, E> items, Integer itemSelected) {
		// TODO Auto-generated constructor stub
		this.items = items;
		this.itemSelected = itemSelected;
	}
	
	public void initValuesFromNamedQueryName(String namedQueryName) {					
		List<E> list = crudService.findWithNamedQuery(namedQueryName);
		Map<Integer, E> map = new HashMap<Integer, E>();
		
		for (E i : list) 
			map.put(i.hashCode(),i);
		
		this.items = map;
	}

	public E getObjectItemSelected(Integer key) {
		return this.items.get(key);
	}

	public Map<Integer, E> getItems() {
		return items;
	}

	public void setItems(Map<Integer, E> items) {
		this.items = items;
	}

	public Integer getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(Integer itemSelected) {
		this.itemSelected = itemSelected;
	}
	
}
