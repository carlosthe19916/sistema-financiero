package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.Titularcuenta;

public class TitularcuentaListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Titularcuenta titularCuenta) {
		
		if (titularCuenta.getCuentaahorro() != null) {
			Integer idcuentaahorro = titularCuenta.getCuentaahorro().getIdcuentaahorro();
			titularCuenta.setIdcuentaahorro(idcuentaahorro);;
		}
		
		if (titularCuenta.getCuentacorriente() != null) {
			Integer idcuentacorriente = titularCuenta.getCuentacorriente().getIdcuentacorriente();
			titularCuenta.setIdcuentacorriente(idcuentacorriente);;
		}
		
		if (titularCuenta.getCuentaplazofijo() != null) {
			Integer idcuentaplazofijo = titularCuenta.getCuentaplazofijo().getIdcuentaplazofijo();
			titularCuenta.setIdcuentaplazofijo(idcuentaplazofijo);
		}
	}
}
