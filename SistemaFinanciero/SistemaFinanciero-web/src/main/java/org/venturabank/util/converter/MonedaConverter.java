package org.venturabank.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.ventura.entity.schema.caja.Moneda;

@FacesConverter("MonedaConverter")
public class MonedaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Moneda moneda = null;
		try {
			moneda = new Moneda(value);
		} catch (Exception e) {
			moneda = new Moneda();
		}

		return moneda;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
