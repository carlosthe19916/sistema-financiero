package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.ventura.boundary.local.VariableSistemaServiceLocal;
import org.ventura.entity.schema.maestro.VariableSistema;
import org.ventura.util.maestro.VariableSistemaType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class AdministrarVariableSistemaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean failure;
	private boolean success;
	
	private VariableSistema montoMaximoAporteMayorEdad;
	private VariableSistema montoMaximoAporteMenorEdad;
	
	private VariableSistema montoMaximoTransaccionDolar;
	private VariableSistema montoMaximoTransaccionNuevoSol;
	private VariableSistema montoMaximoTransaccionEuro;
	
	private BigDecimal tasaCambioDolar;
	private BigDecimal tasaCambioEuro;
	
	@EJB private VariableSistemaServiceLocal variableSistemaServiceLocal;

	public AdministrarVariableSistemaBean() {
		failure = false;
		success = false;
	}
	
	@PostConstruct
	private void initialize() throws Exception {
		try {
			montoMaximoAporteMenorEdad = variableSistemaServiceLocal.getVariableSistema(VariableSistemaType.MONTO_APORTE_MENOR_EDAD);
			montoMaximoAporteMayorEdad = variableSistemaServiceLocal.getVariableSistema(VariableSistemaType.MONTO_APORTE_MAYOR_EDAD);
			
			montoMaximoTransaccionDolar = variableSistemaServiceLocal.getVariableSistema(VariableSistemaType.MONTO_MAXIMO_TRANSACCION_DOLAR);
			montoMaximoTransaccionNuevoSol = variableSistemaServiceLocal.getVariableSistema(VariableSistemaType.MONTO_MAXIMO_TRANSACCION_NUEVO_SOL);
			montoMaximoTransaccionEuro = variableSistemaServiceLocal.getVariableSistema(VariableSistemaType.MONTO_MAXIMO_TRANSACCION_EURO);
		} catch (Exception e) {
			throw e;
		}
	}

	public void updateMontosTransaccion() {
		try {
			variableSistemaServiceLocal.updateVariableSistema(montoMaximoTransaccionNuevoSol);
			variableSistemaServiceLocal.updateVariableSistema(montoMaximoTransaccionDolar);
			variableSistemaServiceLocal.updateVariableSistema(montoMaximoTransaccionEuro);
						
			JsfUtil.addSuccessMessage("Montos actualizados");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void updateMontosAportes() {
		try {
			recalcularMontosTransaccion();
			
			variableSistemaServiceLocal.updateVariableSistema(montoMaximoAporteMenorEdad);
			variableSistemaServiceLocal.updateVariableSistema(montoMaximoAporteMayorEdad);
						
			JsfUtil.addSuccessMessage("Montos actualizados");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void recalcularMontosTransaccion() {
		try {
			montoMaximoTransaccionDolar.setValor(montoMaximoTransaccionNuevoSol.getValor().divide(tasaCambioDolar,RoundingMode.HALF_UP));
			montoMaximoTransaccionEuro.setValor(montoMaximoTransaccionNuevoSol.getValor().divide(tasaCambioEuro,RoundingMode.HALF_UP));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public VariableSistema getMontoMaximoTransaccionDolar() {
		return montoMaximoTransaccionDolar;
	}

	public void setMontoMaximoTransaccionDolar(
			VariableSistema montoMaximoTransaccionDolar) {
		this.montoMaximoTransaccionDolar = montoMaximoTransaccionDolar;
	}

	public VariableSistema getMontoMaximoTransaccionNuevoSol() {
		return montoMaximoTransaccionNuevoSol;
	}

	public void setMontoMaximoTransaccionNuevoSol(
			VariableSistema montoMaximoTransaccionNuevoSol) {
		this.montoMaximoTransaccionNuevoSol = montoMaximoTransaccionNuevoSol;
	}

	public VariableSistema getMontoMaximoTransaccionEuro() {
		return montoMaximoTransaccionEuro;
	}

	public void setMontoMaximoTransaccionEuro(
			VariableSistema montoMaximoTransaccionEuro) {
		this.montoMaximoTransaccionEuro = montoMaximoTransaccionEuro;
	}

	public BigDecimal getTasaCambioDolar() {
		return tasaCambioDolar;
	}

	public void setTasaCambioDolar(BigDecimal tasaCambioDolar) {
		this.tasaCambioDolar = tasaCambioDolar;
	}

	public BigDecimal getTasaCambioEuro() {
		return tasaCambioEuro;
	}

	public void setTasaCambioEuro(BigDecimal tasaCambioEuro) {
		this.tasaCambioEuro = tasaCambioEuro;
	}

	public VariableSistema getMontoMaximoAporteMayorEdad() {
		return montoMaximoAporteMayorEdad;
	}

	public void setMontoMaximoAporteMayorEdad(
			VariableSistema montoMaximoAporteMayorEdad) {
		this.montoMaximoAporteMayorEdad = montoMaximoAporteMayorEdad;
	}

	public VariableSistema getMontoMaximoAporteMenorEdad() {
		return montoMaximoAporteMenorEdad;
	}

	public void setMontoMaximoAporteMenorEdad(
			VariableSistema montoMaximoAporteMenorEdad) {
		this.montoMaximoAporteMenorEdad = montoMaximoAporteMenorEdad;
	}

}
