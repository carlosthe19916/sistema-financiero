package org.ventura.boundary.local;

import javax.ejb.Local;

import org.ventura.boundary.remote.TipocambioServiceRemote;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.tasas.Tipotasa;
import org.ventura.tipodato.Moneda;
import org.ventura.tipodato.TasaCambio;

@Local
public interface TipocambioServiceLocal extends TipocambioServiceRemote{
	
	public TasaCambio getTipoCambioCompraVenta(Tipotasa tipotasa, Moneda monto, Tipomoneda monedaRecibida, Tipomoneda monedaEntregado) throws Exception;

}

