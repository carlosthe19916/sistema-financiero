package org.ventura.boundary.local;

import java.math.BigDecimal;

import javax.ejb.Local;

import org.ventura.boundary.remote.TasainteresServiceRemote;

import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.tasas.Tiposervicio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.TasaCambio;
import org.ventura.util.maestro.TipoCambioCompraVentaType;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;

@Local
public interface TasainteresServiceLocal extends TasainteresServiceRemote{
	
	public TasaCambio getTasainteres(Tiposervicio tiposervicio, Tipotasa tipotasa, Double monto) throws Exception;
	
	public BigDecimal getTasainteresCuentapersonal(TipotasaCuentasPersonalesType cuentasPersonalesType,BigDecimal monto) throws Exception;

	public BigDecimal getTea(Tipomoneda tipomoneda, Integer periodo,BigDecimal monto) throws Exception;
	
	public BigDecimal getTrea(Tipomoneda tipomoneda, Integer periodo,BigDecimal monto) throws Exception;
	
	public BigDecimal getTasainteresCuentaahorro(Tipomoneda tipomoneda) throws Exception;
	
	public BigDecimal getTasainteresCuentacorriente(Tipomoneda tipomoneda) throws Exception;
	
	/*operaciones*/
	
	public BigDecimal getInteresGeneradoPlazofijo(BigDecimal montoApertura, Integer cantidadDias, BigDecimal tea) throws Exception;

}
