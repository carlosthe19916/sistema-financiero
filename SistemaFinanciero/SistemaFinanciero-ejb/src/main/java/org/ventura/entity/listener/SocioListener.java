package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.Socio;

public class SocioListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Socio socio) {
		if (socio.getCuentaaporte() != null) {
			Integer idcuentaaporte = socio.getCuentaaporte().getIdcuentaaporte();
			socio.setIdcuentaaporte(idcuentaaporte);
		}
	}
}
