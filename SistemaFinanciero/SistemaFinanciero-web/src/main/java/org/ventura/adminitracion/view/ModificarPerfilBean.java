package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;
import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.SeguridadServiceLocal;
import org.ventura.entity.schema.rrhh.Trabajador;
import org.ventura.entity.schema.seguridad.Grupo;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.ventura.session.UsuarioMB;
import org.venturabank.util.JsfUtil;
import org.venturabank.util.MD5Util;

@Named
@ViewScoped
public class ModificarPerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject private UsuarioMB usuarioMB;
	@Inject private AgenciaBean agenciaBean;

	private Trabajador trabajador;
	
	private Agencia agencia;
	private String username;
	private String password;
	private DualListModel<Grupo> gruposPickList;  

	private Usuario usuario;
	private Integer idusuario;
	
	private boolean passwordChanged;
	private boolean succes;
	private boolean failure;
	
	@EJB private SeguridadServiceLocal seguridadServiceLocal;
	@EJB private MaestrosServiceLocal maestrosServiceLocal;

	public ModificarPerfilBean() {
		passwordChanged = false;
		succes = false;
		failure = false;
		idusuario = -1;
	}
	
	@PostConstruct
	private void initialize() throws Exception {
		try {
			this.agencia = agenciaBean.getAgencia();
			List<Grupo> grupos = seguridadServiceLocal.getGrupos();
						
			gruposPickList = new DualListModel<Grupo>();
			gruposPickList.setSource(grupos);
		} catch (Exception e) {
			throw e;
		}
	}

	public void loadUsuarioForEdit(){
		try {
			if(idusuario != null && idusuario != -1){
				usuario = seguridadServiceLocal.findUsuario(idusuario);
				this.username = usuario.getUsername();
				
				this.trabajador = usuario.getTrabajador();
				
				List<Grupo> gruposAsignados = usuario.getGrupos();
				List<Grupo> gruposNoAsignados = seguridadServiceLocal.getGrupos();
				gruposNoAsignados.removeAll(gruposAsignados);
				
				gruposPickList.setTarget(gruposAsignados);
				gruposPickList.setSource(gruposNoAsignados);
				
			} else {
				JsfUtil.addErrorMessage("No se encontró el usuario");
			}	
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al cargar usuario");
		}		
	}
	
	public void updateUsuario() throws Exception {
		try {
			if(succes == false){
				usuario.setUsername(username);
				if(passwordChanged == true){
					usuario.setPassword(MD5Util.getMD5(password));
				}

				usuario.setTrabajador(trabajador);
				List<Grupo> grupos = gruposPickList.getTarget();
				usuario.setGrupos(grupos);

				this.seguridadServiceLocal.update(usuario);
				
				succes = true;
				JsfUtil.addSuccessMessage("Usuario actualizado");
				usuarioMB.logout();
			}		
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());		
		}		
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DualListModel<Grupo> getGruposPickList() {
		return gruposPickList;
	}

	public void setGruposPickList(DualListModel<Grupo> gruposPickList) {
		this.gruposPickList = gruposPickList;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}

	public boolean isSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

}
