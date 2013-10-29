package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.schema.cuentapersonal.Beneficiariocuenta;

public class BeneficiariocuentaListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Beneficiariocuenta beneficiariocuenta) {

		if (beneficiariocuenta.getCuentaaporte() != null) {
			Integer idcuentaaporte = beneficiariocuenta.getCuentaaporte().getIdcuentaaporte();
			beneficiariocuenta.setIdcuentaaporte(idcuentaaporte);
		}
		
		if (beneficiariocuenta.getCuentaahorro() != null) {
			Integer idcuentaahorro = beneficiariocuenta.getCuentaahorro().getIdcuentaahorro();
			beneficiariocuenta.setIdcuentaahorro(idcuentaahorro);;
		}
		
		if (beneficiariocuenta.getCuentacorriente() != null) {
			Integer idcuentacorriente = beneficiariocuenta.getCuentacorriente().getIdcuentacorriente();
			beneficiariocuenta.setIdcuentacorriente(idcuentacorriente);;
		}
		
		if (beneficiariocuenta.getCuentaplazofijo() != null) {
			Integer idcuentaplazofijo = beneficiariocuenta.getCuentaplazofijo().getIdcuentaplazofijo();
			beneficiariocuenta.setIdcuentaplazofijo(idcuentaplazofijo);;
		}
	}
}
