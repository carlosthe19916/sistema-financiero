package org.ventura.session;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.LoginServiceLocal;
import org.ventura.entity.schema.seguridad.Modulo;
import org.venturabank.util.JsfUtil;

@Named
@SessionScoped
public class ModuloMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private LoginServiceLocal loginServiceLocal;
	
	@Inject
	private UsuarioMB usuarioMB;
	
	private List<Modulo> modulosUsuario;

	public ModuloMB() {
		this.modulosUsuario = new ArrayList<Modulo>();
	}

	@PostConstruct
	private void init() {
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (principal != null) {
			try {
				modulosUsuario = (List<Modulo>) loginServiceLocal.getModule(usuarioMB.getUsuario());
			} catch (Exception e) {
				JsfUtil.addErrorMessage(e.getMessage());
			}
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
