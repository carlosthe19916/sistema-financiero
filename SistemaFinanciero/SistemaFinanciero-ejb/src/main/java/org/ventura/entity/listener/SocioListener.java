package org.ventura.entity.listener;

import javax.persistence.PrePersist;

import org.ventura.entity.schema.socio.Socio;

public class SocioListener {

	@PrePersist
	private void prePersist(Socio socio){
	}
}
