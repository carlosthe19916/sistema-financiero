package org.ventura.boundary.local;

import javax.ejb.Local;

import org.ventura.boundary.remote.TransaccionCajaServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.VouchercajaView;

@Local
public interface TransaccionCajaServiceLocal extends TransaccionCajaServiceRemote{
	
	public Transaccioncuentabancaria createTransaccionCuentabancaria(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public Transaccioncompraventa createTransaccionCompraVenta(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	public Transaccioncuentaaporte createTransaccionCuentaaporte(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;

	public VouchercajaView getVoucherTransaccionBancaria(Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public VouchercajaView getVoucherTransaccionCompraVentaMoneda(Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	public boolean validateSaldoBovedaCaja(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception;
}
