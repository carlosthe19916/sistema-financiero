package org.ventura.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.ventura.entity.schema.caja.Detalleaperturacierreboveda;

public class DetalleaerturacierrebovedaListener {

	@PreUpdate
	@PrePersist
	public void updateFeatureCode(Detalleaperturacierreboveda detalleaperturacierreboveda) {
		if(detalleaperturacierreboveda.getDetallehistorialboveda() != null){
			Integer idDetallehistorialboveda = detalleaperturacierreboveda.getDetallehistorialboveda().getIddetallehistorialboveda();
			detalleaperturacierreboveda.setIddetallehistorialboveda(idDetallehistorialboveda);	
		}
	}
}
