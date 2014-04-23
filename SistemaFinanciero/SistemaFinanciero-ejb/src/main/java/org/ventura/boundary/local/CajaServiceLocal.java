package org.ventura.boundary.local;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ventura.boundary.remote.CajaServiceRemote;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detallehistorialcaja;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.PendienteCaja;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Tipotransaccioncompraventa;
import org.ventura.entity.schema.caja.Transaccioncuentaaporte;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.schema.caja.view.CajaTransaccionesBovedaView;
import org.ventura.entity.schema.caja.view.PendientesView;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.tipodato.Moneda;
import org.ventura.util.exception.RollbackFailureException;

/**
 * @author Jhon
 *
 */
@Local
public interface CajaServiceLocal extends CajaServiceRemote{
	
	public Caja create(Caja oCaja)throws Exception;
	
	public Caja find(Object id)throws Exception;

	public void update(Caja oCaja)throws Exception;
	
	public void openCaja(Caja oCaja) throws Exception;
	
	public void closeCaja(Caja caja, List<Detallehistorialcaja> detalleSoles, List<Detallehistorialcaja> detalleDolares, List<Detallehistorialcaja> detalleEuros) throws Exception;
	
	public void closeCaja(Caja caja, Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaCierre) throws Exception;
	
	public Map<Tipomoneda, BigDecimal> verificarSaldosCaja(Caja caja, Map<Tipomoneda, List<Detallehistorialcaja>> detalle) throws Exception;;
	
	public void freezeCaja(Caja oCaja) throws Exception;
	
	public void defrostCaja(Caja oCaja) throws Exception;
	
	public void inactive(Caja oCaja)throws Exception;

	public Collection<Caja> findByNamedQuery(String queryName) throws Exception;

	public Collection<Caja> findByNamedQuery(String queryName, int resultLimit) throws Exception;

	public List<Caja> findByNamedQuery(String Caja, Map<String, Object> parameters) throws Exception;
	
	public List<Caja> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws Exception;
	
	public Historialcaja getHistorialcajaLastActive(Caja oCaja) throws RollbackFailureException, Exception;
	
	public List<Boveda> getBovedas(Caja oCaja) throws Exception;
	
	public HashMap<Tipomoneda,List<Detallehistorialcaja>> getDetallehistorialcajaLastActive(Caja caja) throws Exception;
	
	public HashMap<Tipomoneda,List<Detallehistorialcaja>> getDetallehistorialcajaInZero(Caja caja) throws Exception;
	
	public Map<Integer, Moneda> compareSaldoTotalCajaSoles(Caja caja) throws Exception;
	
	public Map<Integer, Moneda> compareSaldoTotalCajaDolares(Caja caja) throws Exception;
	
	public Map<Integer, Moneda> compareSaldoTotalCajaEuros(Caja caja) throws Exception;
	
	/**
	 * Transaccional**/
	public PendienteCaja crearPendiente(Caja caja, PendienteCaja pendienteCaja) throws Exception;
	
	public void updateSaldo(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
	
	public void updateSaldo(Caja caja, Transaccioncuentaaporte transaccioncuentaaporte) throws Exception;
	
	/**
	 * No transaccional*/
	public List<CajaTransaccionesBovedaView> getTransaccionesDelDia(Caja caja) throws Exception;
	
	/*Usuarios*/
	public List<Usuario> getUsuariosFromCaja(Caja oCaja) throws Exception;
	
	/*Pendientes Caja*/
	public List<PendientesView> getPendientesCaja(Agencia agencia) throws Exception;
	
	public PendientesView finPendienteCaja(Object id) throws Exception;
	
	/*Resumen Operaciones*/
	public int countTransaccionCuentaAporte(Caja caja, Tipotransaccion tipoTransaccion) throws Exception;
	
	public int countTransaccionCuentaBancaria(Caja caja, Tipocuentabancaria tipoCuentaBancaria, Tipotransaccion tipoTransaccion) throws Exception;
	
	public int countTransaccionCompraVenta(Caja caja, Tipotransaccioncompraventa tipotransaccioncompraventa) throws Exception;
}
