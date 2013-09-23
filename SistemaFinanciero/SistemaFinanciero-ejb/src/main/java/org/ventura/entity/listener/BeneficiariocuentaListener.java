package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.Beneficiariocuenta;

public class BeneficiariocuentaListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Beneficiariocuenta beneficiariocuenta) {

		if (beneficiariocuenta.getCuentaahorro() != null) {
			String numeroCuentaahorro = beneficiariocuenta.getCuentaahorro().getNumerocuentaahorro();
			beneficiariocuenta.setNumerocuentaahorro(numeroCuentaahorro);
		}
		
		if (beneficiariocuenta.getCuentacorriente() != null) {
			String numeroCuentaCorriente = beneficiariocuenta.getCuentacorriente().getNumerocuentacorriente();
			beneficiariocuenta.setNumerocuentaahorro(numeroCuentaCorriente);
		}
		
		if (beneficiariocuenta.getCuentaplazofijo() != null) {
			String numeroCuentaplazofijo = beneficiariocuenta.getCuentaplazofijo().getNumerocuentaplazofijo();
			beneficiariocuenta.setNumerocuentaahorro(numeroCuentaplazofijo);
		}
	}
}
