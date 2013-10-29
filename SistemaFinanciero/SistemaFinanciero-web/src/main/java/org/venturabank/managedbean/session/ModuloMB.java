package org.venturabank.managedbean.session;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.ventura.boundary.local.LoginServiceLocal;
import org.ventura.entity.schema.seguridad.Modulo;

@SessionScoped
@ManagedBean
public class ModuloMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private LoginServiceLocal loginServiceLocal;
	
	@ManagedProperty(value = "#{usuarioMB}")
	private UsuarioMB usuarioMB;
	
	private List<Modulo> modulosUsuario;

	public ModuloMB() {
		this.modulosUsuario = new ArrayList<Modulo>();
	}

	@PostConstruct
	private void init() {
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (principal != null) {
			modulosUsuario = (List<Modulo>) loginServiceLocal.getModule(usuarioMB.getUsuario());
		} 
	}

	public List<Modulo> getModulosUsuario() {
		return modulosUsuario;
	}

	public void setModulosUsuario(List<Modulo> modulosUsuario) {
		this.modulosUsuario = modulosUsuario;
	}

	public UsuarioMB getUsuarioMB() {
		return usuarioMB;
	}

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}
}
