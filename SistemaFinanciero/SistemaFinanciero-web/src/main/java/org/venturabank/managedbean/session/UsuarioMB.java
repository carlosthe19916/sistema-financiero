package org.venturabank.managedbean.session;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.ventura.entity.Menu;
import org.ventura.entity.Usuario;

@Named
@SessionScoped
@ManagedBean
public class UsuarioMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
		
	public void login(){
		//request.login(username, password);

	    //return "index.jsf?faces-redirect=true";
	}
	
	public String logout() {
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    return "login?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
