package org.ventura.boundary.local;

import javax.ejb.Local;

import org.ventura.boundary.remote.TransaccionCajaServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.ViewvouchercompraventaView;
import org.ventura.entity.schema.caja.view.VouchercajaCuentaaporteView;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;

@Local
public interface TransaccionCajaServiceLocal extends TransaccionCajaServiceRemote{
	
	public Transaccioncuentabancaria createTransaccionCuentabancaria(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public Transaccioncompraventa createTransaccionCompraVenta(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	public Transaccioncuentaaporte createTransaccionCuentaaporte(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;

	public VouchercajaView getVoucherTransaccionBancaria(Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public VouchercajaCuentaaporteView getVoucherTransaccionCuentaaporte(Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;
	
	public ViewvouchercompraventaView getVoucherTransaccionCompraVentaMoneda(Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	public boolean validateSaldoBovedaCaja(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	public Transaccioncompraventa find(Object id) throws Exception;
	
	public void extornarTransaccionCompraVenta(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	/**
	 * Operaciones transaccionales*/
	
	public Transaccioncuentabancaria deposito(Caja caja, Cuentabancaria cuentabancaria, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public Transaccioncuentabancaria retiro(Caja caja, Cuentabancaria cuentabancaria, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public Transaccioncuentaaporte deposito(Caja caja, Cuentaaporte cuentaaporte, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;
	
	public Transaccioncuentaaporte retiro(Caja caja, Cuentaaporte cuentaaporte, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;
	
	/**
	 * Operaciones no transaccionales**/
}
