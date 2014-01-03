package org.ventura.dependent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.ventura.dao.CrudService;

@Named
@Dependent
public class ComboBean<E> {

	@EJB
	private CrudService crudService;

	private Map<Integer, E> items;
	private Integer itemSelected;

	public ComboBean() {
		this.items = (Map<Integer, E>) new HashMap<Integer, Object>();
		this.itemSelected = new Integer(-1);
	}

	public ComboBean(Map<Integer, E> items, Integer itemSelected) {
		// TODO Auto-generated constructor stub
		this.items = items;
		this.itemSelected = itemSelected;
	}

	public void initValuesFromNamedQueryName(String namedQueryName) {
		List<E> list = crudService.findWithNamedQuery(namedQueryName);
		Map<Integer, E> map = new HashMap<Integer, E>();

		for (E i : list)
			map.put(i.hashCode(), i);

		this.items = map;
	}

	public void initValuesFromNamedQueryName(String namedQueryName,
			Map<String, Object> parameters) {
		List<E> list = crudService.findWithNamedQuery(namedQueryName,
				parameters);
		Map<Integer, E> map = new HashMap<Integer, E>();

		for (E i : list)
			map.put(i.hashCode(), i);

		this.items = map;
	}
	
	public void clean(){
		this.items.clear();
		this.itemSelected = new Integer(-1);
	}
	
	public void reset() {
		this.itemSelected = new Integer(-1);
	}

	public E getObjectItemSelected(Integer key) {
		return this.items.get(key);
	}
	
	public E getObjectItemSelected() {
		Integer key = this.itemSelected;
		if(key != null){
			return this.items.get(key);
		} else {
			return null;
		}	
	}

	public Map<Integer, E> getItems() {
		return items;
	}

	public void setItems(List<E> list) {
		Map<Integer, E> map = new HashMap<Integer, E>();
		for (E i : list)
			map.put(i.hashCode(), i);
		this.items = map;
	}
	
	public void setItems(Map<Integer, E> items) {
		this.items = items;
	}

	public void putItem(Integer key, E value) {
		this.items.put(key, value);
	}

	public Integer getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected(Integer itemSelected) {
		this.itemSelected = itemSelected;
	}

	public void setItemSelected(E e) {
		if (e != null) {
			this.itemSelected = e.hashCode();
		} else {
			this.itemSelected = new Integer(-1);
		}
	}
}
