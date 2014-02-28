package org.ventura.caja.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;
import org.ventura.boundary.local.BovedaServiceLocal;
import org.ventura.boundary.local.CajaServiceLocal;
import org.ventura.boundary.local.SeguridadServiceLocal;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.session.AgenciaBean;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class CajaCRUDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CajaServiceLocal cajaServiceLocal;
	@EJB
	private BovedaServiceLocal bovedaServiceLocal;
	@EJB
	private SeguridadServiceLocal seguridadServiceLocal;
	
	
	@Inject
	private AgenciaBean agenciaBean;

	private Agencia agencia;
	private String denominacion;
	private String abreviatura;
	
	private Caja caja;
	private Integer idcaja;

	private DualListModel<Boveda> dualListModelBoveda;
	private DualListModel<Usuario> dualListModelUsuario;
	
	public CajaCRUDBean() {
		//dualList for Boveda
		dualListModelBoveda = new DualListModel<Boveda>();
		dualListModelBoveda.setSource(new ArrayList<Boveda>());
		dualListModelBoveda.setTarget(new ArrayList<Boveda>());
		
		//dualList for Usuario
		dualListModelUsuario = new DualListModel<Usuario>();
		dualListModelUsuario.setSource(new ArrayList<Usuario>());
		dualListModelUsuario.setTarget(new ArrayList<Usuario>());
	}
	
	@PostConstruct
	private void initialize() {
		this.agencia = agenciaBean.getAgencia();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idagencia", agencia.getIdagencia());
		try {
			//for boveda
			List<Boveda> source = bovedaServiceLocal.findByNamedQuery(Boveda.ALL_ACTIVE_BY_AGENCIA, parameters);
			List<Boveda> target = new ArrayList<Boveda>();
			dualListModelBoveda.setSource(source);
			dualListModelBoveda.setTarget(target);

			//for boveda
			List<Usuario> sourceUsuario = seguridadServiceLocal.findByNamedQuery(Usuario.ALL_USER_ACTIVE_BY_AGENCIA, parameters);
			List<Usuario> targetUsuario = new ArrayList<Usuario>();
			dualListModelUsuario.setSource(sourceUsuario);
			dualListModelUsuario.setTarget(targetUsuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String nombreUsuario(Object obj){
		if (obj instanceof Usuario) {
			Usuario usuario = (Usuario) obj;
			Personanatural personanatural = usuario.getTrabajador().getPersonanatural();
			return personanatural.getApellidopaterno()+" "+personanatural.getApellidomaterno() +", " +personanatural.getNombres()+" ("+usuario.getUsername()+")";
		} else {
			return obj.getClass().toString();
		}
	}

	public void loadCajaForEdit(){
		try {
			if(idcaja != null && idcaja != -1){
				caja = cajaServiceLocal.find(idcaja);
				this.denominacion = caja.getDenominacion();
				this.abreviatura = caja.getAbreviatura();
				
				List<Boveda> target = cajaServiceLocal.getBovedas(caja);
				List<Boveda> source = dualListModelBoveda.getSource();
		
				source.removeAll(target);
				dualListModelBoveda.setTarget(target);
				
				dualListModelBoveda = new DualListModel<Boveda>(source,target);
			} else {
				JsfUtil.addErrorMessage("No se pudo recuperar Caja");
			}			
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al recuperar Caja");
		}
	}
	
	public void createCaja() throws Exception {
		Caja caja;
		try {
			caja = new Caja();
			caja.setDenominacion(denominacion);
			caja.setAbreviatura(abreviatura);

			List<Boveda> bovedas = dualListModelBoveda.getTarget();
			caja.setBovedas(bovedas);
			
			this.cajaServiceLocal.create(caja);

			JsfUtil.addSuccessMessage("Caja Creada");

		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al crear Caja");			
		}		
	}
	
	public String updateCaja() throws Exception {
		Caja caja = this.caja;
		try {
			caja.setDenominacion(denominacion);
			caja.setAbreviatura(abreviatura);
			
			List<Boveda> bovedas = dualListModelBoveda.getTarget();
			caja.setBovedas(bovedas);
			this.cajaServiceLocal.update(caja);

			JsfUtil.addSuccessMessage("Caja Actualizada");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Error al actualizar Caja");
			return "failure";
		}
		return "success";
	}

	public boolean isValidBean(){
		if(idcaja == null || idcaja == -1){
			return false;
		} else {
			return true;
		}
	}
	
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public DualListModel<Boveda> getDualListModelBoveda() {
		return dualListModelBoveda;
	}

	public void setDualListModelBoveda(DualListModel<Boveda> dualListModelBoveda) {
		this.dualListModelBoveda = dualListModelBoveda;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Integer getIdcaja() {
		return idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
	}

	public DualListModel<Usuario> getDualListModelUsuario() {
		return dualListModelUsuario;
	}

	public void setDualListModelUsuario(DualListModel<Usuario> dualListModelUsuario) {
		this.dualListModelUsuario = dualListModelUsuario;
	}

}
