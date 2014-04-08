package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;
import org.ventura.boundary.local.SeguridadServiceLocal;
import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.seguridad.Grupo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.UsuarioMB;
import org.ventura.util.maestro.ProduceObject;
import org.ventura.util.maestro.TipodocumentoType;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class SeguridadAdminBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject private UsuarioMB usuarioMB;
	
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
	
	@Inject private ComboBean<Agencia> comboAgencia;
	
	@EJB
	private SeguridadServiceLocal seguridadServiceLocal;
	@EJB
	private SucursalServiceLocal sucursalServiceLocal;
	
	public SeguridadAdminBean() {
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
			
			List<Usuario> usuarios = new ArrayList<Usuario>();
		
			tablaRol.setRows(roles);
			tablaGrupo.setRows(grupos);
			tablaUsuario.setRows(usuarios);
			
			List<Agencia> listAgencias = sucursalServiceLocal.getAllAgenciasActive();
			comboAgencia.setItems(listAgencias);
		} catch (Exception e) {
			throw e;
		}
	}

	public void mostrarMiembrosDeRol(Rol rol){
		try {
			dlgRol = true;
			List<Usuario> list = null;

			list = seguridadServiceLocal.getUsuariosFromRol(rol, comboAgencia.getObjectItemSelected());
			 
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
			List<Usuario> list = seguridadServiceLocal.getUsuariosFromGrupo(grupo, comboAgencia.getObjectItemSelected());
			tablaUsuarioMiembros.setRows(list);
			
			List<Usuario> target = list;
			List<Usuario> source = null;
			
			source = seguridadServiceLocal.getUsuariosFromAgencia(comboAgencia.getObjectItemSelected());
			 
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
			return personanatural.getApellidopaterno()+" "+personanatural.getApellidomaterno() +"," +personanatural.getNombres()+"("+usuario.getUsername() +")";
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
				for (int i = 0; i < grupos.size(); i++) {
					Grupo grupo = grupos.get(i);
					if(i < grupos.size()-1)
						result = result + grupo.getNombre().toUpperCase()+", ";
					else 
						result = result + grupo.getNombre().toUpperCase();
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
				for (int i = 0; i < rols.size(); i++) {
					Rol rol = rols.get(i);
					if(i < rols.size()-1)
						result = result + rol.getNombre().toUpperCase()+", ";
					else 
						result = result + rol.getNombre().toUpperCase();
				}
				return result;
			} catch (Exception e) {
				failure = true;
				JsfUtil.addErrorMessage(e.getMessage());
			}		
		} 
		return result;
	}
	
	public void changeAgencia() {
		Agencia agencia = comboAgencia.getObjectItemSelected();
		try {
			List<Usuario> usuarios;
			if (agencia != null) {			
				usuarios = seguridadServiceLocal.getUsuariosFromAgencia(agencia);
			}
			else {
				usuarios = new ArrayList<Usuario>();
			}
			tablaUsuario.setRows(usuarios);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}	
	}
	
	public void changeComboAgencia(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Agencia agencia = comboAgencia.getObjectItemSelected(key);
		if(agencia != null){
			comboAgencia.setItemSelected(key);
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

	public ComboBean<Agencia> getComboAgencia() {
		return comboAgencia;
	}

	public void setComboAgencia(ComboBean<Agencia> comboAgencia) {
		this.comboAgencia = comboAgencia;
	}

}
