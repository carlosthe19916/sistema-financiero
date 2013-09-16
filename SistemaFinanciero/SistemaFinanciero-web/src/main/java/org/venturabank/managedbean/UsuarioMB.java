package org.venturabank.managedbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.ventura.model.Usuario;

@ManagedBean(name="usuarioMB")
@SessionScoped
public class UsuarioMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	
	public UsuarioMB() {
		// TODO Auto-generated constructor stub
		this.usuario = new Usuario();
		this.usuario.setNombreusuario("admin");
		this.usuario.setPassword("123");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
