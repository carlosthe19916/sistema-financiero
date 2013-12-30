package org.venturabank.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.ventura.entity.schema.caja.TasaInteresTipoCambio;

@FacesConverter("TasaInteresTipoCambioConverter")
public class TasaInteresTipoCambioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TasaInteresTipoCambio tasainterestipocambio = null;
		try {
			tasainterestipocambio = new TasaInteresTipoCambio(value);
		} catch (Exception e) {
			tasainterestipocambio = new TasaInteresTipoCambio();
		}
		return tasainterestipocambio;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
