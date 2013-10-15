package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.Beneficiariocuenta;
import org.ventura.entity.Titularcuenta;

public class TitularcuentaListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Titularcuenta titularCuenta) {
		
		if (titularCuenta.getCuentaahorro() != null) {
			String numeroCuentaahorro = titularCuenta.getCuentaahorro().getNumerocuentaahorro();
			titularCuenta.setNumerocuentaahorro(numeroCuentaahorro);
		}
		
		if (titularCuenta.getCuentacorriente() != null) {
			String numeroCuentaCorriente = titularCuenta.getCuentacorriente().getNumerocuentacorriente();
			titularCuenta.setNumerocuentaahorro(numeroCuentaCorriente);
		}
		
		if (titularCuenta.getCuentaplazofijo() != null) {
			String numeroCuentaplazofijo = titularCuenta.getCuentaplazofijo().getNumerocuentaplazofijo();
			titularCuenta.setNumerocuentaahorro(numeroCuentaplazofijo);
		}
	}
}
