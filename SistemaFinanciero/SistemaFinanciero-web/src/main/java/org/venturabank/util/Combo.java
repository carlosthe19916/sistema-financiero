package org.venturabank.util;

import java.util.List;
import javax.ejb.EJB;
import org.ventura.dao.CrudService;

public class Combo<E> {

	@EJB
	private CrudService crudService;

	private E selectedItem;
	private List<E> items;

	public Combo() {
	}

	public Combo(E selectedItem, List<E> items) {
		this.selectedItem = selectedItem;
		this.items = items;
	}

	@SuppressWarnings("unchecked")
	public void initValues(String namedQuery) {		
		this.items = this.crudService.findWithNamedQuery(namedQuery);		
	}

	public E getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(E selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<E> getItems() {
		return items;
	}

	public void setItems(List<E> items) {
		this.items = items;
	}

}
