package org.ventura.caja.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Detallehistorialcaja;
import org.ventura.entity.schema.caja.Estadoapertura;
import org.ventura.entity.schema.caja.Historialcaja;
import org.ventura.entity.schema.caja.PendienteCaja;
import org.ventura.entity.schema.caja.Tipocuentabancaria;
import org.ventura.entity.schema.caja.Tipotransaccion;
import org.ventura.entity.schema.caja.Tipotransaccioncompraventa;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.ventura.session.CajaBean;
import org.ventura.tipodato.Moneda;
import org.ventura.util.maestro.EstadoAperturaType;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipoTransaccionCompraVentaType;
import org.ventura.util.maestro.TipoTransaccionType;
import org.ventura.util.maestro.TipocuentabancariaType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AbrirCajaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private boolean failure;
	
	private boolean dlgVerificarSaldos;
	
	//pendiente caja
	private boolean dlgCrearPendiente;
	private boolean successPendiente;
	private BigDecimal montoPendiente;
	private Tipomoneda tipomonedaPendiente;
	private String observacionPendiente;
	private Date fechaPendiente;
	
	private Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaApertura;
	private Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaCierre;
	
	private Map<Tipomoneda, BigDecimal> mapDiferenciaSaldos;
	private Moneda totalCaja;
	
	//Resumen operaciones
	private Date fechaResumenOp;
	//transacciones depositos
	private int totalDepositos;
	private int totalDepositosAporte;
	private int totalDepositosAhorro;
	private int totalDepositosPlazoFijo;
	private int totalDepositosCorriente;
	//transacciones retiros
	private int totalRetiros;
	private int totalRetirosAporte;
	private int totalRetirosAhorro;
	private int totalRetirosPlazoFijo;
	private int totalRetirosCorriente;
	//transacciones compra - venta
	private int totalCompraVenta;
	private int totalCompra;
	private int totalVenta;
	//mayor cuantia en depositos, retiros y compra venta
	private int totalMayorCuantia;
	private int totalDepositosMayorCuantia;
	private int mayorCuantiaDepositosAporte;
	private int mayorCuantiaDepositosCuentaBancaria;
	private int totalRetirosMayorCuantia;
	private int mayorCuantiaRetirosAporte;
	private int mayorCuantiaRetirosCuentaBancaria;
	private int totalCVMayorCuantia;
	private int mayorCuantiaCompra;
	private int mayorCuantiaVenta;
	//caja - caja
	private int totalTransCajaCaja;
	private int totalTransCajaBoveda;
	//pendientes
	private int totalSobrantes;
	private int totalFaltantes;
	
	@Inject private CajaBean cajaBean;
	@Inject private Caja caja;
	@Inject private Historialcaja historialcaja;
	@Inject private AgenciaBean agenciaBean;
	@Inject private Agencia agencia;
	
	@Inject private LoginBean loginBean;
	
	@EJB private CajaServiceLocal cajaServiceLocal;
	
	private PendienteCaja pendientecaja;
	
	private boolean print;
	
	public AbrirCajaBean() {
		pendientecaja = new PendienteCaja();
		success = false;
		failure = false;
		successPendiente = false;
		dlgVerificarSaldos = false;
		dlgCrearPendiente = false;
		mapDiferenciaSaldos = new HashMap<Tipomoneda, BigDecimal>();
		totalCaja = new Moneda();
		print = false;
	}
	
	@PostConstruct
	public void initialize() throws Exception{		
		try {
			caja = cajaBean.getCaja();
			agencia = agenciaBean.getAgencia();
			
			Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.CERRADO);
			Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
			if (estadoapertura.equals(estadoapertura2)) {								
				throw new Exception("Caja CERRADA, no se puede cerrar nuevamente");
			}
			
			historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
			
			mapDetalleHistorialcajaApertura = cajaServiceLocal.getDetallehistorialcajaLastActive(caja);
			mapDetalleHistorialcajaCierre = cajaServiceLocal.getDetallehistorialcajaInZero(caja);
			
			for (Tipomoneda t : mapDetalleHistorialcajaCierre.keySet()) {
				mapDiferenciaSaldos.put(t, BigDecimal.ZERO);
			}
			
			fechaResumenOp = Calendar.getInstance().getTime();
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	
	public List<Detallehistorialcaja> retornarDetalle(Tipomoneda tipomoneda){
		return mapDetalleHistorialcajaCierre.get(tipomoneda);
	}
	
	public void verificarSaldos(){		
		try {								
			mapDiferenciaSaldos = cajaServiceLocal.verificarSaldosCaja(caja, mapDetalleHistorialcajaCierre);		
			setDlgVerificarSaldos(true);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void closeCaja() throws Exception {
		Caja caja = this.caja;	
		try {
			if(success == false){
				Estadoapertura estadoapertura = ProduceObject.getEstadoapertura(EstadoAperturaType.ABIERTO);
				Estadoapertura estadoapertura2 = this.caja.getEstadoapertura();
				if (estadoapertura.equals(estadoapertura2)) {								
					this.cajaServiceLocal.closeCaja(caja, mapDetalleHistorialcajaCierre);
					success = true;
					setDlgVerificarSaldos(false);
					resumenOperaciones();
				} else {
					failure = true;
					JsfUtil.addErrorMessage("Caja Cerrada, Imposible cerrar caja");			
				}
			}
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	// metodos de resumen de operaciones
	public void resumenOperaciones(){
		//operaciones en depositos y retiros en aportes y cuenta bancaria
		depositosAporte();
		depositosCuentaAhorro();
		depositosCuentaPlazoFijo();
		depositosCuentaCorriente();
		retirosAporte();
		retirosCuentaAhorro();
		retirosCuentaPlazoFijo();
		retirosCuentaCorriente();
		//totales
		calcularTotalDepositos();
		calcularTotalRetiros();
		
		//operaciones en compra venta
		transaccionesCompra();
		transaccionesVenta();
		calcularTotalTransaccionesCompraVenta();
		
		//operaciones mayor cuantia
		mayorCuantiaDepositosAporte();
		mayorCuantiaDepositosCuentaBancaria();
		totalMayorCuantiaDepositos();
		mayorCuantiaRetirosAporte();
		mayorCuantiaRetirosCuentaBancaria();
		totalMayorCuantiaRetiros();
		mayorCuantiaTransaccionesCompra();
		mayorCuantiaTransaccionesVenta();
		totalMayorCuantiaCompraVenta();
		calcularTotalMayorCuantia();
		
		//transacciones caja - caja
		
		//transacciones caja - boveda
		
		//pendientes
	}
	
	//operaciones de deposito y retiro de aportes y cuenta bancaria
	public void depositosAporte() {
		try {
			Tipotransaccion deposito = ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO);
			totalDepositosAporte = cajaServiceLocal.countTransaccionCuentaAporte(caja, deposito);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void depositosCuentaAhorro() {
		try {
			Tipotransaccion deposito = ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO);
			Tipocuentabancaria ahorro = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_AHORRO);
			totalDepositosAhorro = cajaServiceLocal.countTransaccionCuentaBancaria(caja, ahorro, deposito);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void depositosCuentaPlazoFijo() {
		try {
			Tipotransaccion deposito = ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO);
			Tipocuentabancaria plazoFijo = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			totalDepositosPlazoFijo = cajaServiceLocal.countTransaccionCuentaBancaria(caja, plazoFijo, deposito);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void depositosCuentaCorriente() {
		try {
			Tipotransaccion deposito = ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO);
			Tipocuentabancaria cuentaCorriente = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_CORRIENTE);
			totalDepositosCorriente = cajaServiceLocal.countTransaccionCuentaBancaria(caja, cuentaCorriente, deposito);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void calcularTotalDepositos(){
		totalDepositos = totalDepositosAporte + totalDepositosAhorro + totalDepositosPlazoFijo + totalDepositosCorriente;
	}
	
	public void retirosAporte() {
		try {
			Tipotransaccion retiro = ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO);
			totalRetirosAporte = cajaServiceLocal.countTransaccionCuentaAporte(caja, retiro);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void retirosCuentaAhorro() {
		try {
			Tipotransaccion retiro = ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO);
			Tipocuentabancaria ahorro = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_AHORRO);
			totalRetirosAhorro = cajaServiceLocal.countTransaccionCuentaBancaria(caja, ahorro, retiro);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void retirosCuentaPlazoFijo() {
		try {
			Tipotransaccion retiro = ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO);
			Tipocuentabancaria plazoFijo = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_PLAZO_FIJO);
			totalRetirosPlazoFijo = cajaServiceLocal.countTransaccionCuentaBancaria(caja, plazoFijo, retiro);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void retirosCuentaCorriente() {
		try {
			Tipotransaccion retiro = ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO);
			Tipocuentabancaria cuentaCorriente = ProduceObject.getTipocuentabancaria(TipocuentabancariaType.CUENTA_CORRIENTE);
			totalRetirosCorriente = cajaServiceLocal.countTransaccionCuentaBancaria(caja, cuentaCorriente, retiro);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void calcularTotalRetiros(){
		totalRetiros = totalRetirosAporte + totalRetirosAhorro + totalRetirosPlazoFijo + totalRetirosCorriente;
	}
	
	//operaciones de compra venta
	public void transaccionesCompra() {
		try {
			Tipotransaccioncompraventa compra = ProduceObject.getTipotransaccioncompraventa(TipoTransaccionCompraVentaType.COMPRA);
			totalCompra = cajaServiceLocal.countTransaccionCompraVenta(caja, compra);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void transaccionesVenta() {
		try {
			Tipotransaccioncompraventa venta = ProduceObject.getTipotransaccioncompraventa(TipoTransaccionCompraVentaType.VENTA);
			totalVenta = cajaServiceLocal.countTransaccionCompraVenta(caja, venta);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void calcularTotalTransaccionesCompraVenta(){
		totalCompraVenta = totalCompra + totalVenta;
	}
	
	//operaciones de mayor cuantía en depositos, retiros y c/v
	public void mayorCuantiaDepositosAporte() {
		try {
			Moneda monto = new Moneda("10");
			Tipotransaccion deposito = ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO);
			mayorCuantiaDepositosAporte = cajaServiceLocal.countMayorCuantiaAportes(caja, deposito, monto);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void mayorCuantiaDepositosCuentaBancaria() {
		try {
			Moneda monto = new Moneda("10");
			Tipotransaccion deposito = ProduceObject.getTipotransaccion(TipoTransaccionType.DEPOSITO);
			mayorCuantiaDepositosCuentaBancaria = cajaServiceLocal.countMayorCuantiaCBancaria(caja, deposito, monto);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void totalMayorCuantiaDepositos(){
		totalDepositosMayorCuantia = mayorCuantiaDepositosAporte + mayorCuantiaDepositosCuentaBancaria;
	}
	
	public void mayorCuantiaRetirosAporte() {
		try {
			Moneda monto = new Moneda("10");
			Tipotransaccion retiro = ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO);
			mayorCuantiaRetirosAporte = cajaServiceLocal.countMayorCuantiaAportes(caja, retiro, monto);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void mayorCuantiaRetirosCuentaBancaria() {
		try {
			Moneda monto = new Moneda("10");
			Tipotransaccion retiro = ProduceObject.getTipotransaccion(TipoTransaccionType.RETIRO);
			mayorCuantiaRetirosCuentaBancaria = cajaServiceLocal.countMayorCuantiaCBancaria(caja, retiro, monto);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void totalMayorCuantiaRetiros(){
		totalRetirosMayorCuantia = mayorCuantiaRetirosAporte + mayorCuantiaRetirosCuentaBancaria;
	}
	
	public void mayorCuantiaTransaccionesCompra() {
		try {
			Moneda monto = new Moneda("10");
			Tipotransaccioncompraventa compra = ProduceObject.getTipotransaccioncompraventa(TipoTransaccionCompraVentaType.COMPRA);
			mayorCuantiaCompra = cajaServiceLocal.countMayorCuantiaCompra(caja, compra, monto);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void mayorCuantiaTransaccionesVenta() {
		try {
			Moneda monto = new Moneda("10");
			Tipotransaccioncompraventa venta = ProduceObject.getTipotransaccioncompraventa(TipoTransaccionCompraVentaType.VENTA);
			mayorCuantiaVenta = cajaServiceLocal.countMayorCuantiaVenta(caja, venta, monto);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void totalMayorCuantiaCompraVenta(){
		totalCVMayorCuantia = mayorCuantiaCompra + mayorCuantiaVenta;
	}
	
	
	public void calcularTotalMayorCuantia(){
		totalMayorCuantia = totalDepositosMayorCuantia + totalRetirosMayorCuantia + totalCVMayorCuantia;
	}
	

	
	
	public String getTotal(Tipomoneda key){
		List<Detallehistorialcaja> list = retornarDetalle(key);
		Moneda total = new Moneda();
		if(list != null){
			for (Detallehistorialcaja detallehistorialcaja : list) {		
				totalCaja = totalCaja.add(detallehistorialcaja.getSubtotal());
				total = total.add(detallehistorialcaja.getSubtotal());
			}
		}
		
		return total.toString();
	}
	
	public String getTotalAbrir(Tipomoneda key){
		List<Detallehistorialcaja> list = mapDetalleHistorialcajaApertura.get(key);
		Moneda total = new Moneda();
		if(list != null){
			for (Detallehistorialcaja detallehistorialcaja : list) {		
				totalCaja = totalCaja.add(detallehistorialcaja.getSubtotal());
				total = total.add(detallehistorialcaja.getSubtotal());
			}
		}
		
		return total.toString();
	}
	
	public void crearPendiente(){
		try {
			PendienteCaja pendienteCaja = new PendienteCaja();
			pendienteCaja.setMonto(new Moneda(montoPendiente));
			pendienteCaja.setObservacion(observacionPendiente);
			pendienteCaja.setTipomoneda(tipomonedaPendiente);
			pendienteCaja.setFecha(fechaPendiente);
			setPendientecaja(cajaServiceLocal.crearPendiente(caja, pendienteCaja));
			
			successPendiente = true;
			JsfUtil.addSuccessMessage("Pendiente creado");
			setDlgCrearPendiente(false);
			verificarSaldos();
			setPrint(true);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void cancelarDlgPrintPendiente(){
		print = false;
	}
	
	public void congelar(){
		try {
			cajaServiceLocal.freezeCaja(caja);
			historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void descongelar(){
		try {
			cajaServiceLocal.defrostCaja(caja);
			historialcaja = cajaServiceLocal.getHistorialcajaLastActive(caja);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void cargarPendiente(Tipomoneda tipomoneda){
		dlgCrearPendiente = true;
		montoPendiente = mapDiferenciaSaldos.get(tipomoneda);
		tipomonedaPendiente = tipomoneda;
		observacionPendiente = "";
		fechaPendiente = Calendar.getInstance().getTime();
	}
	
	public void cancelarPendiente(){
		dlgCrearPendiente = false;
		montoPendiente = null;
		tipomonedaPendiente = null;
		observacionPendiente = "";
	}
	
	public String getMensajeSaldo(BigDecimal valor) {
		String msg = "";
		if(valor.compareTo(BigDecimal.ZERO) == 0){
			msg = "Cuadre de caja correcto";
		} else {
			if(valor.compareTo(BigDecimal.ZERO) == 1){
				msg = "Saldo de caja sobrante";
			} else {
				msg = "Saldo de caja faltante";
			}
		}
		return msg;
	}
	
	public String getMensajeTipoPendiente() {
		String msg = "";
		if(montoPendiente.compareTo(BigDecimal.ZERO) == 0){
			msg = "Cuadre de caja correcto";
		} else {
			if(montoPendiente.compareTo(BigDecimal.ZERO) == 1){
				msg = "SOBRANTE";
			} else {
				msg = "FALTANTE";
			}
		}
		return msg;
	}
	
	public int getCompareBigdecimal(BigDecimal valor){
		return valor.compareTo(BigDecimal.ZERO);
	}
	
	public CajaBean getCajaBean() {
		return cajaBean;
	}
	
	public void setCajaBean(CajaBean cajaBean) {
		this.cajaBean = cajaBean;
	}
	
	public Caja getCaja() {
		return caja;
	}
	
	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Map<Tipomoneda, List<Detallehistorialcaja>> getMapDetalleHistorialcajaApertura() {
		return mapDetalleHistorialcajaApertura;
	}

	public void setMapDetalleHistorialcajaApertura(
			Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaApertura) {
		this.mapDetalleHistorialcajaApertura = mapDetalleHistorialcajaApertura;
	}

	public Map<Tipomoneda, List<Detallehistorialcaja>> getMapDetalleHistorialcajaCierre() {
		return mapDetalleHistorialcajaCierre;
	}

	public void setMapDetalleHistorialcajaCierre(
			Map<Tipomoneda, List<Detallehistorialcaja>> mapDetalleHistorialcajaCierre) {
		this.mapDetalleHistorialcajaCierre = mapDetalleHistorialcajaCierre;
	}

	public boolean isDlgVerificarSaldos() {
		return dlgVerificarSaldos;
	}

	public void setDlgVerificarSaldos(boolean dlgVerificarSaldos) {
		this.dlgVerificarSaldos = dlgVerificarSaldos;
	}

	public Map<Tipomoneda, BigDecimal> getMapDiferenciaSaldos() {
		return mapDiferenciaSaldos;
	}

	public void setMapDiferenciaSaldos(
			Map<Tipomoneda, BigDecimal> mapDiferenciaSaldos) {
		this.mapDiferenciaSaldos = mapDiferenciaSaldos;
	}

	public boolean isDlgCrearPendiente() {
		return dlgCrearPendiente;
	}

	public void setDlgCrearPendiente(boolean dlgCrearPendiente) {
		this.dlgCrearPendiente = dlgCrearPendiente;
	}

	public BigDecimal getMontoPendiente() {
		return montoPendiente;
	}

	public void setMontoPendiente(BigDecimal montoPendiente) {
		this.montoPendiente = montoPendiente;
	}

	public Tipomoneda getTipomonedaPendiente() {
		return tipomonedaPendiente;
	}

	public void setTipomonedaPendiente(Tipomoneda tipomonedaPendiente) {
		this.tipomonedaPendiente = tipomonedaPendiente;
	}

	public String getObservacionPendiente() {
		return observacionPendiente;
	}

	public void setObservacionPendiente(String observacionPendiente) {
		this.observacionPendiente = observacionPendiente;
	}

	public boolean isSuccessPendiente() {
		return successPendiente;
	}

	public void setSuccessPendiente(boolean successPendiente) {
		this.successPendiente = successPendiente;
	}

	public Historialcaja getHistorialcaja() {
		return historialcaja;
	}

	public void setHistorialcaja(Historialcaja historialcaja) {
		this.historialcaja = historialcaja;
	}

	public Date getFechaPendiente() {
		return fechaPendiente;
	}

	public void setFechaPendiente(Date fechaPendiente) {
		this.fechaPendiente = fechaPendiente;
	}

	public Moneda getTotalCaja() {
		return totalCaja;
	}

	public void setTotalCaja(Moneda totalCaja) {
		this.totalCaja = totalCaja;
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public PendienteCaja getPendientecaja() {
		return pendientecaja;
	}

	public void setPendientecaja(PendienteCaja pendientecaja) {
		this.pendientecaja = pendientecaja;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Date getFechaResumenOp() {
		return fechaResumenOp;
	}

	public void setFechaResumenOp(Date fechaResumenOp) {
		this.fechaResumenOp = fechaResumenOp;
	}

	public int getTotalDepositosAporte() {
		return totalDepositosAporte;
	}

	public void setTotalDepositosAporte(int totalDepositosAporte) {
		this.totalDepositosAporte = totalDepositosAporte;
	}

	public int getTotalDepositos() {
		return totalDepositos;
	}

	public void setTotalDepositos(int totalDepositos) {
		this.totalDepositos = totalDepositos;
	}

	public int getTotalRetirosAporte() {
		return totalRetirosAporte;
	}

	public void setTotalRetirosAporte(int totalRetirosAporte) {
		this.totalRetirosAporte = totalRetirosAporte;
	}

	public int getTotalDepositosAhorro() {
		return totalDepositosAhorro;
	}

	public void setTotalDepositosAhorro(int totalDepositosAhorro) {
		this.totalDepositosAhorro = totalDepositosAhorro;
	}

	public int getTotalDepositosPlazoFijo() {
		return totalDepositosPlazoFijo;
	}

	public void setTotalDepositosPlazoFijo(int totalDepositosPlazoFijo) {
		this.totalDepositosPlazoFijo = totalDepositosPlazoFijo;
	}

	public int getTotalDepositosCorriente() {
		return totalDepositosCorriente;
	}

	public void setTotalDepositosCorriente(int totalDepositosCorriente) {
		this.totalDepositosCorriente = totalDepositosCorriente;
	}

	public int getTotalRetiros() {
		return totalRetiros;
	}

	public void setTotalRetiros(int totalRetiros) {
		this.totalRetiros = totalRetiros;
	}

	public int getTotalRetirosAhorro() {
		return totalRetirosAhorro;
	}

	public void setTotalRetirosAhorro(int totalRetirosAhorro) {
		this.totalRetirosAhorro = totalRetirosAhorro;
	}

	public int getTotalRetirosPlazoFijo() {
		return totalRetirosPlazoFijo;
	}

	public void setTotalRetirosPlazoFijo(int totalRetirosPlazoFijo) {
		this.totalRetirosPlazoFijo = totalRetirosPlazoFijo;
	}

	public int getTotalRetirosCorriente() {
		return totalRetirosCorriente;
	}

	public void setTotalRetirosCorriente(int totalRetirosCorriente) {
		this.totalRetirosCorriente = totalRetirosCorriente;
	}

	public int getTotalCompraVenta() {
		return totalCompraVenta;
	}

	public void setTotalCompraVenta(int totalCompraVenta) {
		this.totalCompraVenta = totalCompraVenta;
	}

	public int getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(int totalCompra) {
		this.totalCompra = totalCompra;
	}

	public int getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(int totalVenta) {
		this.totalVenta = totalVenta;
	}

	public int getTotalMayorCuantia() {
		return totalMayorCuantia;
	}

	public void setTotalMayorCuantia(int totalMayorCuantia) {
		this.totalMayorCuantia = totalMayorCuantia;
	}

	public int getTotalDepositosMayorCuantia() {
		return totalDepositosMayorCuantia;
	}

	public void setTotalDepositosMayorCuantia(int totalDepositosMayorCuantia) {
		this.totalDepositosMayorCuantia = totalDepositosMayorCuantia;
	}

	public int getTotalRetirosMayorCuantia() {
		return totalRetirosMayorCuantia;
	}

	public void setTotalRetirosMayorCuantia(int totalRetirosMayorCuantia) {
		this.totalRetirosMayorCuantia = totalRetirosMayorCuantia;
	}

	public int getTotalCVMayorCuantia() {
		return totalCVMayorCuantia;
	}

	public void setTotalCVMayorCuantia(int totalCVMayorCuantia) {
		this.totalCVMayorCuantia = totalCVMayorCuantia;
	}

	public int getTotalTransCajaCaja() {
		return totalTransCajaCaja;
	}

	public void setTotalTransCajaCaja(int totalTransCajaCaja) {
		this.totalTransCajaCaja = totalTransCajaCaja;
	}

	public int getTotalTransCajaBoveda() {
		return totalTransCajaBoveda;
	}

	public void setTotalTransCajaBoveda(int totalTransCajaBoveda) {
		this.totalTransCajaBoveda = totalTransCajaBoveda;
	}

	public int getTotalSobrantes() {
		return totalSobrantes;
	}

	public void setTotalSobrantes(int totalSobrantes) {
		this.totalSobrantes = totalSobrantes;
	}

	public int getTotalFaltantes() {
		return totalFaltantes;
	}

	public void setTotalFaltantes(int totalFaltantes) {
		this.totalFaltantes = totalFaltantes;
	}

	public int getMayorCuantiaDepositosAporte() {
		return mayorCuantiaDepositosAporte;
	}

	public void setMayorCuantiaDepositosAporte(int mayorCuantiaDepositosAporte) {
		this.mayorCuantiaDepositosAporte = mayorCuantiaDepositosAporte;
	}

	public int getMayorCuantiaDepositosCuentaBancaria() {
		return mayorCuantiaDepositosCuentaBancaria;
	}

	public void setMayorCuantiaDepositosCuentaBancaria(
			int mayorCuantiaDepositosCuentaBancaria) {
		this.mayorCuantiaDepositosCuentaBancaria = mayorCuantiaDepositosCuentaBancaria;
	}

	public int getMayorCuantiaRetirosAporte() {
		return mayorCuantiaRetirosAporte;
	}

	public void setMayorCuantiaRetirosAporte(int mayorCuantiaRetirosAporte) {
		this.mayorCuantiaRetirosAporte = mayorCuantiaRetirosAporte;
	}

	public int getMayorCuantiaRetirosCuentaBancaria() {
		return mayorCuantiaRetirosCuentaBancaria;
	}

	public void setMayorCuantiaRetirosCuentaBancaria(
			int mayorCuantiaRetirosCuentaBancaria) {
		this.mayorCuantiaRetirosCuentaBancaria = mayorCuantiaRetirosCuentaBancaria;
	}

	public int getMayorCuantiaCompra() {
		return mayorCuantiaCompra;
	}

	public void setMayorCuantiaCompra(int mayorCuantiaCompra) {
		this.mayorCuantiaCompra = mayorCuantiaCompra;
	}

	public int getMayorCuantiaVenta() {
		return mayorCuantiaVenta;
	}

	public void setMayorCuantiaVenta(int mayorCuantiaVenta) {
		this.mayorCuantiaVenta = mayorCuantiaVenta;
	}
}
