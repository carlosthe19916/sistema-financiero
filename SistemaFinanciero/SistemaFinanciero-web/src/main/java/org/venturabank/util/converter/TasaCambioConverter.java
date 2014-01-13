package org.venturabank.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.ventura.tipodato.TasaCambio;

@FacesConverter("TasaCambioConverter")
public class TasaCambioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TasaCambio tasacambio = null;
		try {
			tasacambio = new TasaCambio(value);
		} catch (Exception e) {
			tasacambio = new TasaCambio();
		}
		return tasacambio;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
