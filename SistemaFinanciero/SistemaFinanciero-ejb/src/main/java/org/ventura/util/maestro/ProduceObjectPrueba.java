package org.ventura.util.maestro;

import javax.ejb.EJB;

import org.ventura.dao.CrudService;

public class ProduceObjectPrueba<T, V extends Enum<V>> {

	@EJB
	private CrudService crudService;

	public boolean equals(T t, V v) {
		Class<V> enumType = null;
		for (V c : enumType.getEnumConstants()) {
			System.out.println(c.name());
		}
		
		return false;
	}

	public T getObject(Enum<V> e) {
		T obj = (T) new Object();

		Class<V> enumType = null;

		Integer id = new Integer(0);

		for (V c : enumType.getEnumConstants()) {
			if (c.equals(e))
				obj = (T) crudService.find(obj.getClass(), id);
			else
				id++;
		}

		return obj;
	}

	public <T extends Enum<T>> void enumValues(Class<T> enumType) {
		for (T c : enumType.getEnumConstants()) {
			System.out.println(c.name());
		}
	}
}
