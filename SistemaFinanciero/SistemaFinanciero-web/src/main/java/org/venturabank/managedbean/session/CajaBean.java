package org.venturabank.managedbean.session;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.LoginServiceLocal;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.seguridad.Usuario;

@Named
@SessionScoped
public class CajaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private LoginServiceLocal loginServiceLocal;
	
	@Inject
	private UsuarioMB usuarioMB;
	
	@Inject
	private Usuario usuario;
	
	@Inject
	private Caja caja;

	@PostConstruct
	private void init() {
		usuario = usuarioMB.getUsuario();
		initCaja();
	}
	
	private void initCaja(){
		List<Caja> cajas;
		try {
			cajas = loginServiceLocal.getCajas(usuario);
			if(cajas != null){
				if(cajas.size() == 1){
					caja = cajas.get(0);
				} else {
					if(cajas.size() == 0){
						caja = null;
					} else {
						throw new Exception("El usuario tiene una cantidad de cajas no validas");
					}		
				}
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*Usuario usuario = usuarioMB.getUsuario();
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
		}*/
	}
	
	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
}
