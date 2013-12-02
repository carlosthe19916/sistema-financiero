package org.ventura.dependent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.ventura.dao.CrudService;

@Named
@Dependent
public class PickListBean<E> {
	
	@EJB
	private CrudService crudService;

	private DualListModel<E> result;
	private List<E> source;
	private List<E> target;

	public PickListBean() {
            this.source = new ArrayList<E>();
            this.target = new ArrayList<E>();
            result = new DualListModel<E>(source, target);
    }
	
	public void initValuesFromNamedQueryName(String namedQueryName) {
		List<E> list = crudService.findWithNamedQuery(namedQueryName);
		this.result = (DualListModel<E>) list;
	}

	public void initValuesFromNamedQueryName(String namedQueryName, Map<String, Object> parameters) {
		List<E> list = crudService.findWithNamedQuery(namedQueryName, parameters);
		this.result = (DualListModel<E>) list;
	}

	public DualListModel<E> getResult() {
		return result;
	}
	
	public void setResult(DualListModel<E> result) {
		this.result = result;
	}
}
