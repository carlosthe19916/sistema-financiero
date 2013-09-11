package org.venturabank.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.dao.CrudService;

@ManagedBean
@NoneScoped
public class TablaMB<E> {

	@EJB
	private CrudService crudService;
	
	private List<E> rows;
	private E selectedRow;
	private List<E> selectedRows;

	@SuppressWarnings("unchecked")
	public TablaMB() {
		this.rows = (List<E>) new ArrayList<Object>();
		this.selectedRow = (E) new Object();
		this.selectedRows = (List<E>) new ArrayList<Object>();
	}
	
	public TablaMB(List<E> rows, E selectedRow, List<E> selectedRows) {
		this.rows = rows;
		this.selectedRow = selectedRow;
		this.selectedRows = selectedRows;
	}
	
	public void initValuesFromNamedQueryName(String namedQueryName) {					
		List<E> list = crudService.findWithNamedQuery(namedQueryName);
		this.rows = list;
	}

	public void addRow(E row){
		this.rows.add(row);
	}
	
	public void removeRow(E row){
		this.rows.remove(row);
	}
	
	public void removeRow(int index){
		this.rows.remove(index);
	}
	
	public void removeSelectedRow(){
		this.rows.remove(selectedRow);
	}
	
	public void removeSelectedRows(){
		this.rows.remove(selectedRows);
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public E getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(E selectedRow) {
		this.selectedRow = selectedRow;
	}

	public List<E> getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(List<E> selectedRows) {
		this.selectedRows = selectedRows;
	}

}
