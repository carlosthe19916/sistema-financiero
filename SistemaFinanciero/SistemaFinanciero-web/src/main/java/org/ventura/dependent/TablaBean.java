package org.ventura.dependent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

import org.ventura.dao.CrudService;

@Named
@Dependent
public class TablaBean<E> {

	@EJB
	private CrudService crudService;

	private List<E> rows;
	private E selectedRow;
	private E editingRow;
	private List<E> selectedRows;
	private List<E> frozenRows;

	@SuppressWarnings("unchecked")
	public TablaBean() {
		this.rows = (List<E>) new ArrayList<Object>();
		this.selectedRow = (E) new Object();
		this.editingRow = null;
		this.selectedRows = (List<E>) new ArrayList<Object>();
		this.frozenRows = (List<E>) new ArrayList<Object>();
	}

	public TablaBean(List<E> rows, E selectedRow, E editingRow, List<E> selectedRows, List<E> frozenRows) {
		this.rows = rows;
		this.selectedRow = selectedRow;
		this.editingRow = editingRow;
		this.selectedRows = selectedRows;
		this.frozenRows = frozenRows;
	}

	public void initValuesFromNamedQueryName(String namedQueryName) {
		List<E> list = crudService.findWithNamedQuery(namedQueryName);
		this.rows = list;
	}

	public void editRow() {
		this.editingRow = this.selectedRow;
	}

	public void rowEditCancel() {
		this.clearEditinRow();
	}

	public void finishEditRow() {
		this.clearEditinRow();
	}
	
	public void rowSelect() {
		this.editingRow = this.selectedRow;
	}

	public void rowUnselect() {
		System.out.println("unselect");
	}

	public void clearEditinRow() {
		this.editingRow = null;
	}

	public void clearRows() {
		this.rows = new ArrayList<E>();
	}
	
	public void addRow(E row) {
		this.rows.add(row);
	}

	public void removeRow(E row) {
		this.rows.remove(row);
	}

	public void removeRow(int index) {
		this.rows.remove(index);
	}

	public void removeSelectedRow() {
		this.rows.remove(selectedRow);
	}

	public void removeSelectedRows() {
		this.rows.remove(selectedRows);
	}

	public List<E> getRows() {
		return rows;
	}
	
	public List<E> getAllRows() {
		List<E> rows = this.rows;
		List<E> frozenRows = this.frozenRows;
		List<E> result = new ArrayList<E>();
		for (Iterator<E> iterator = rows.iterator(); iterator.hasNext();) {
			E e = (E) iterator.next();
			result.add(e);
		};
		for (Iterator<E> iterator = frozenRows.iterator(); iterator.hasNext();) {
			E e = (E) iterator.next();
			result.add(e);
		};
		return result;
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

	public E getEditingRow() {
		return editingRow;
	}

	public void setEditingRow(E editingRow) {
		this.editingRow = editingRow;
	}

	public List<E> getFrozenRows() {
		return frozenRows;
	}

	public void setFrozenRows(List<E> frozenRows) {
		this.frozenRows = frozenRows;
	}

}
