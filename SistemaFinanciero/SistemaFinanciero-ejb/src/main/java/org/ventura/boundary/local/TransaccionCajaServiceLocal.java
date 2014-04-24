package org.ventura.boundary.local;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.TransaccionCajaServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.caja.Detalletransaccioncaja;
import org.ventura.entity.schema.caja.Transaccioncaja;
import org.ventura.entity.schema.caja.Transaccioncajacaja;
import org.ventura.entity.schema.caja.Transaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.Transaccionmayorcuantia;
import org.ventura.entity.schema.caja.view.CajaMovimientoView;
import org.ventura.entity.schema.caja.view.ViewvouchercompraventaView;
import org.ventura.entity.schema.caja.view.VouchercajaCuentaaporteView;
import org.ventura.entity.schema.caja.view.VouchercajaView;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Cuentabancaria;
import org.ventura.entity.schema.seguridad.Usuario;

@Local
public interface TransaccionCajaServiceLocal extends TransaccionCajaServiceRemote{
	
	public Transaccioncuentabancaria createTransaccionCuentabancaria(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public Transaccioncompraventa createTransaccionCompraVenta(Caja caja, Transaccioncompraventa transaccioncompraventa, Usuario usuario, Transaccionmayorcuantia transasccionmayorcuantia) throws Exception;
	
	public Transaccioncuentaaporte createTransaccionCuentaaporte(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte, Usuario usuario, Transaccionmayorcuantia transasccionmayorcuantia) throws Exception;

	public VouchercajaView getVoucherTransaccionBancaria(Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public VouchercajaCuentaaporteView getVoucherTransaccionCuentaaporte(Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;
	
	public ViewvouchercompraventaView getVoucherTransaccionCompraVentaMoneda(Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	public boolean validateSaldoBovedaCaja(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	public Transaccioncompraventa find(Object id) throws Exception;
	
	
	/**
	 * Operaciones transaccionales*/
	
	public Transaccioncuentabancaria deposito(Caja caja, Cuentabancaria cuentabancaria, Transaccioncuentabancaria transaccioncuentabancaria, Map<Denominacionmoneda, Integer> detalleTransaccion, Usuario usuario, Transaccionmayorcuantia transasccionmayorcuantia) throws Exception;
	
	public Transaccioncuentabancaria retiro(Caja caja, Cuentabancaria cuentabancaria, Transaccioncuentabancaria transaccioncuentabancaria, Map<Denominacionmoneda, Integer> detalleTransaccion, Usuario usuario) throws Exception;
	
	public Transaccioncuentaaporte deposito(Caja caja, Cuentaaporte cuentaaporte, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;
	
	public Transaccioncuentaaporte retiro(Caja caja, Cuentaaporte cuentaaporte, Transaccioncuentaaporte transaccioncuentaaporte, Usuario usuario) throws Exception;	
	
	public void extornarTransaccion(CajaMovimientoView cajaMovimientoView) throws Exception;
	
	public void extornarTransaccionCuentaaporte(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;
	
	public void extornarTransaccionCuentabancaria(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public void extornarTransaccionCompraVenta(Caja caja, Transaccioncompraventa transaccioncompraventa) throws Exception;
	
	public Transaccioncajacaja crearTransaccioncajacaja(Transaccioncajacaja transaccioncajacaja, Caja origen, Caja destino, Usuario usuarioSolicita) throws Exception;
	
	public Transaccioncajacaja confirmarTransaccioncajacaja(Transaccioncajacaja transaccioncajacaja, Usuario usuarioConfirma) throws Exception;
	
	public void cancelarTransaccioncajacaja(Transaccioncajacaja transaccioncajacaja) throws Exception;
	
	/**
	 * Operaciones no transaccionales**/
	
	public List<CajaMovimientoView> getTransaccionesCajaWithHistorialActivo(Caja caja) throws Exception;
	
	public List<CajaMovimientoView> buscarTransaccionCaja(Caja caja, Integer id) throws Exception;

	public List<Transaccioncajacaja> getTransaccionesCajaCaja(Caja caja) throws Exception;

	public List<Transaccioncajacaja> getTransaccionesEnviadasCajaCaja(Caja caja) throws Exception;

	public List<Transaccioncajacaja> getTransaccionesPorConfirmarCajaCaja(Caja caja) throws Exception;

	public List<Detalletransaccioncaja> getDetalleTransaccionCaja(Transaccioncaja transaccioncaja) throws Exception;;

}
