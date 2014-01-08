package org.ventura.boundary.local;

import java.math.BigDecimal;

import javax.ejb.Local;

import org.ventura.boundary.remote.TasainteresServiceRemote;
import org.ventura.entity.schema.caja.TasaInteresTipoCambio;
import org.ventura.entity.tasas.Tiposervicio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;

@Local
public interface TasainteresServiceLocal extends TasainteresServiceRemote{
	
	public TasaInteresTipoCambio getTasainteres(Tiposervicio tiposervicio, Tipotasa tipotasa, Double monto) throws Exception;
	
	public BigDecimal getTasainteresCuentapersonal(TipotasaCuentasPersonalesType cuentasPersonalesType,BigDecimal monto) throws Exception;
	
	
}
