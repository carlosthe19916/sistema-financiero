package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;
import org.ventura.boundary.local.SeguridadServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.seguridad.Grupo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class SeguridadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean dlgRol;
	private boolean dlgGrupo;
	private boolean dlgUsuario;
	private boolean success;
	private boolean failure;
	
	private DualListModel<Usuario> usuariosPickList;  
	private DualListModel<Grupo> gruposPickList;  
	    
	@Inject private Grupo grupoSelected;
	@Inject private Usuario usuarioSelected;
	
	@Inject private TablaBean<Usuario> tablaUsuario;
	@Inject private TablaBean<Rol> tablaRol;
	@Inject private TablaBean<Grupo> tablaGrupo;
	
	@Inject private TablaBean<Usuario> tablaUsuarioMiembros;
	
	@Inject private AgenciaBean agenciaBean;
	
	@EJB
	private SeguridadServiceLocal seguridadServiceLocal;

	public SeguridadBean() {
		dlgRol = false;
		dlgGrupo = false;
		success = false;
		failure = false;	
		
		usuariosPickList = new DualListModel<Usuario>();
		gruposPickList = new DualListModel<Grupo>();
	}
	
	@PostConstruct
	private void initialize() throws Exception {
		try {
			List<Rol> roles = seguridadServiceLocal.getRoles();
			List<Grupo> grupos = seguridadServiceLocal.getGrupos();
			List<Usuario> usuarios = seguridadServiceLocal.getUsuariosFromAgencia(agenciaBean.getAgencia());
			
			tablaRol.setRows(roles);
			tablaGrupo.setRows(grupos);
			tablaUsuario.setRows(usuarios);
		} catch (Exception e) {
			throw e;
		}
	}

	public void mostrarMiembrosDeRol(Rol rol){
		try {
			dlgRol = true;
			List<Usuario> list = seguridadServiceLocal.getUsuariosFromRol(rol, agenciaBean.getAgencia());
			tablaUsuarioMiembros.setRows(list);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void mostrarMiembrosDeGrupo(Grupo grupo){
		try {
			grupoSelected = grupo;
			dlgGrupo = true;
			List<Usuario> list = seguridadServiceLocal.getUsuariosFromGrupo(grupo, agenciaBean.getAgencia());
			tablaUsuarioMiembros.setRows(list);
			
			List<Usuario> target = list;
			List<Usuario> source = seguridadServiceLocal.getUsuariosFromAgencia(agenciaBean.getAgencia());
			source.removeAll(target);
			
			usuariosPickList.setTarget(target);
			usuariosPickList.setSource(source);			
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void mostrarGruposDeUsuario(Usuario usuario){
		try {
			usuarioSelected = usuario;
			dlgUsuario = true;
			List<Grupo> list = seguridadServiceLocal.getGrupos(usuarioSelected);
			
			List<Grupo> target = list;
			List<Grupo> source = seguridadServiceLocal.getGrupos();
			source.removeAll(target);
			
			gruposPickList.setTarget(target);
			gruposPickList.setSource(source);			
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void guardarMiembrosDeGrupo(){
		try {
			Grupo grupo = grupoSelected;
			List<Usuario> listUsuarios = usuariosPickList.getTarget();
			seguridadServiceLocal.asignarUsuarios(grupo,listUsuarios);
			success = true;
			dlgGrupo = false;
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void guardarUsuario(){
		try {
			Usuario usuario = usuarioSelected;
			List<Grupo> listGrupos = gruposPickList.getTarget();
			seguridadServiceLocal.asignarGrupos(usuario,listGrupos);
			success = true;
			dlgUsuario = false;
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public String nombreUsuario(Object obj){
		if (obj instanceof Usuario) {
			Usuario usuario = (Usuario) obj;
			Personanatural personanatural = usuario.getTrabajador().getPersonanatural();
			return personanatural.getApellidopaterno()+" "+personanatural.getApellidomaterno() +"," +personanatural.getNombres();
		} else {
			System.out.println(obj.getClass());
			return obj.getClass().toString();
		}
	}
	
	public String listarRolesOfGrupo(Grupo grupo){
		String result = "";
		if(grupo != null){
			List<Rol> rols;
			try {
				rols = seguridadServiceLocal.getRolsFromGrupo(grupo);
				for (Rol rol : rols) {
					result = rol.getNombre().toUpperCase()+", ";
				}
				return result;
			} catch (Exception e) {
				failure = true;
				JsfUtil.addErrorMessage(e.getMessage());
			}		
		} 
		return result;
	}
	
	public String listarGrupos(Usuario usuario){
		String result = "";
		if(usuario != null){
			List<Grupo> grupos;
			try {
				grupos = seguridadServiceLocal.getGrupos(usuario);
				for (Grupo grupo : grupos) {
					result = grupo.getNombre().toUpperCase()+", ";
				}
				return result;
			} catch (Exception e) {
				failure = true;
				JsfUtil.addErrorMessage(e.getMessage());
			}		
		} 
		return result;
	}
	
	public String listarRolesOfUsuario(Usuario usuario){
		String result = "";
		if(usuario != null){
			List<Rol> rols;
			try {
				rols = seguridadServiceLocal.getRoles(usuario);
				for (Rol rol : rols) {
					result = rol.getNombre().toUpperCase()+", ";
				}
				return result;
			} catch (Exception e) {
				failure = true;
				JsfUtil.addErrorMessage(e.getMessage());
			}		
		} 
		return result;
	}
	
	public TablaBean<Usuario> getTablaUsuario() {
		return tablaUsuario;
	}

	public void setTablaUsuario(TablaBean<Usuario> tablaUsuario) {
		this.tablaUsuario = tablaUsuario;
	}

	public TablaBean<Rol> getTablaRol() {
		return tablaRol;
	}

	public void setTablaRol(TablaBean<Rol> tablaRol) {
		this.tablaRol = tablaRol;
	}

	public TablaBean<Grupo> getTablaGrupo() {
		return tablaGrupo;
	}

	public void setTablaGrupo(TablaBean<Grupo> tablaGrupo) {
		this.tablaGrupo = tablaGrupo;
	}

	public TablaBean<Usuario> getTablaUsuarioMiembros() {
		return tablaUsuarioMiembros;
	}

	public void setTablaUsuarioMiembros(TablaBean<Usuario> tablaUsuarioMiembros) {
		this.tablaUsuarioMiembros = tablaUsuarioMiembros;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}

	public boolean isDlgRol() {
		return dlgRol;
	}

	public void setDlgRol(boolean dlgRol) {
		this.dlgRol = dlgRol;
	}

	public boolean isDlgGrupo() {
		return dlgGrupo;
	}

	public void setDlgGrupo(boolean dlgGrupo) {
		this.dlgGrupo = dlgGrupo;
	}

	public DualListModel<Usuario> getUsuariosPickList() {
		return usuariosPickList;
	}

	public void setUsuariosPickList(DualListModel<Usuario> usuariosPickList) {
		this.usuariosPickList = usuariosPickList;
	}

	public DualListModel<Grupo> getGruposPickList() {
		return gruposPickList;
	}

	public void setGruposPickList(DualListModel<Grupo> gruposPickList) {
		this.gruposPickList = gruposPickList;
	}

	public Grupo getGrupoSelected() {
		return grupoSelected;
	}

	public void setGrupoSelected(Grupo grupoSelected) {
		this.grupoSelected = grupoSelected;
	}

	public boolean isDlgUsuario() {
		return dlgUsuario;
	}

	public void setDlgUsuario(boolean dlgUsuario) {
		this.dlgUsuario = dlgUsuario;
	}

	public Usuario getUsuarioSelected() {
		return usuarioSelected;
	}

	public void setUsuarioSelected(Usuario usuarioSelected) {
		this.usuarioSelected = usuarioSelected;
	}

}
