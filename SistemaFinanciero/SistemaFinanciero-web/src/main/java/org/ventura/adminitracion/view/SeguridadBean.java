package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;
import org.ventura.boundary.local.SeguridadServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.caja.Boveda;
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
	private boolean success;
	private boolean failure;
	
	private DualListModel<Usuario> usuariosPickList;  
	private DualListModel<Grupo> gruposPickList;  
	    
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
			tablaRol.setRows(roles);
			tablaGrupo.setRows(grupos);
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
			dlgGrupo = true;
			List<Usuario> list = seguridadServiceLocal.getUsuariosFromGrupo(grupo, agenciaBean.getAgencia());
			tablaUsuarioMiembros.setRows(list);
			
			List<Usuario> target = list;
			List<Usuario> source = seguridadServiceLocal.getUsuariosFromAgencia(agenciaBean.getAgencia());
	
			//source.removeAll(target);
			//usuariosPickList.setTarget(target);		
			usuariosPickList = new DualListModel<Usuario>(source,target);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
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

}
