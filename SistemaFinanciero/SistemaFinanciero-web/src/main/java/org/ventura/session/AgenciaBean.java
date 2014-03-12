package org.ventura.session;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.entity.schema.sucursal.Sucursal;

@Named
@SessionScoped
public class AgenciaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Agencia agencia;

	@Inject
	private UsuarioMB usuarioMB;
	
	@PostConstruct
	private void init() {
		agencia = usuarioMB.getUsuario().getTrabajador().getAgencia();
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	

}
