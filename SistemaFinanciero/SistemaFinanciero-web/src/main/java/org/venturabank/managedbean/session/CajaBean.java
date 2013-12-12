package org.venturabank.managedbean.session;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.seguridad.Grupo;
import org.ventura.entity.schema.seguridad.Rol;
import org.ventura.entity.schema.seguridad.Usuario;

@Named
@SessionScoped
public class CajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioMB usuarioMB;
	
	@Inject
	private Caja caja;

	@PostConstruct
	private void init() {
		caja.setDenominacion("Agencia Quinuapata");
		caja.setIdcaja(1);
		
		initCaja();
	}
	
	private void initCaja(){
		Usuario usuario = usuarioMB.getUsuario();
		Rol rolUsuario;
		List<Grupo> grupos = usuario.getGrupos();
		for (Grupo grupo : grupos) {
			List<Rol> rols = grupo.getRols();
			for (Rol rol : rols) {
				Rol aux = new Rol();
				aux.setIdrol(2);
				aux.setDescripcion("Cajero");
				if(rol.equals(aux)){
					rolUsuario = rol;
				}
			}
		}
	}
	
	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
}
