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
import org.ventura.boundary.local.TrabajadorServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.rrhh.Trabajador;
import org.ventura.entity.schema.seguridad.Grupo;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;
import org.venturabank.util.MD5Util;

@Named
@ViewScoped
public class AgenciaCRUDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean succes;
	private boolean failure;
	
	private Integer idagencia;

	public AgenciaCRUDBean() {
		
		succes = false;
		failure = false;
		
		idagencia = -1;
	}
	
	@PostConstruct
	private void initialize() throws Exception {
		try {
			
		} catch (Exception e) {
			throw e;
		}
	}

	public void loadUsuarioForEdit(){
		
	}
	
	public void createUsuario() throws Exception {
		
	}
	
	public void updateUsuario() throws Exception {
			
	}


	public void buscarTrabajador(){
		
	}
	
	

}
