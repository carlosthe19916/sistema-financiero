package org.venturabank.managedbean.session;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.LoginServiceLocal;
import org.ventura.entity.Usuario;

@Named
@SessionScoped
@ManagedBean(name="usuarioMB")
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private LoginServiceLocal loginServiceLocal;
	
	@Inject
	private Usuario usuario;
	
	
	@PostConstruct
	private void init() {
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (principal != null) {
			usuario.setUsername(principal.getName());	
			usuario = loginServiceLocal.findUserByNamedQuery(usuario);
			
			if(usuario == null){
				System.out.println("ffff");
			}
		} else {
			logout();
		}
	}
	
	public void logout() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.invalidateSession();
			externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
