package org.ventura.boundary.local;

import java.math.BigDecimal;

import javax.ejb.Local;

import org.ventura.boundary.remote.TasainteresServiceRemote;
<<<<<<< HEAD
import org.ventura.entity.schema.caja.Moneda;
import org.ventura.entity.schema.caja.TasaInteresTipoCambio;
import org.ventura.entity.schema.maestro.Tipomoneda;
=======
>>>>>>> branch 'master' of https://code.google.com/p/sistema-financiero/
import org.ventura.entity.tasas.Tiposervicio;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.TasaCambio;
import org.ventura.util.maestro.TipoCambioCompraVentaType;
import org.ventura.util.maestro.TipotasaCuentasPersonalesType;

@Local
public interface TasainteresServiceLocal extends TasainteresServiceRemote{
	
	public TasaCambio getTasainteres(Tiposervicio tiposervicio, Tipotasa tipotasa, Double monto) throws Exception;
	
	public BigDecimal getTasainteresCuentapersonal(TipotasaCuentasPersonalesType cuentasPersonalesType,BigDecimal monto) throws Exception;
	
<<<<<<< HEAD
	public BigDecimal getTea(Tipomoneda tipomoneda, Integer periodo,BigDecimal monto) throws Exception;
	
	public BigDecimal getTrea(Tipomoneda tipomoneda, Integer periodo,BigDecimal monto) throws Exception;
	
	public BigDecimal getTasainteresCuentaahorro(Tipomoneda tipomoneda, BigDecimal monto) throws Exception;
	
	public BigDecimal getTasainteresCuentacorriente(Tipomoneda tipomoneda, BigDecimal monto) throws Exception;
	
=======
	public TasaCambio getTipoCambioCompraVenta(TipoCambioCompraVentaType compraVentaType, BigDecimal monto) throws Exception;
>>>>>>> branch 'master' of https://code.google.com/p/sistema-financiero/
}
