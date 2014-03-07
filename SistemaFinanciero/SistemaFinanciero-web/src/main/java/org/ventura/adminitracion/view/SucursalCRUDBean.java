package org.ventura.adminitracion.view;

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

import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.dependent.ComboBean;
import org.ventura.entity.schema.caja.Boveda;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.maestro.Ubigeo;
import org.ventura.entity.schema.seguridad.Usuario;
import org.ventura.entity.schema.sucursal.Sucursal;
import org.venturabank.util.JsfUtil;

@Named
@ViewScoped
public class SucursalCRUDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean succes;
	private boolean failure;
	
	private String abreviatura;
	private String denominacion;
	private boolean estado;
	
	private Map<String, String> mapDepartamentos;
	private Map<String, String> mapProvincias;
	private Map<String, String> mapDistritos;
	private String idDepartamentoSelected;
	private String idProvinciaSelected;
	private String idDistritoSelected;
	
	private Integer idsucursal;
	@Inject private Sucursal sucursal;
	
	@EJB private MaestrosServiceLocal maestrosServiceLocal;
	@EJB private SucursalServiceLocal sucursalServiceLocal;
	
	public SucursalCRUDBean() {
		succes = false;
		failure = false;
		idsucursal = -1;
		
		mapDepartamentos = new HashMap<String, String>();
		mapProvincias = new HashMap<String, String>();
		mapDistritos = new HashMap<String, String>();
	}
	
	@PostConstruct
	private void initialize() throws Exception {
		try {
			List<Ubigeo> listDepartamentos = maestrosServiceLocal.getDepartamentos();	
			for (Ubigeo u : listDepartamentos) {
				mapDepartamentos.put(u.getIdubigeo().substring(0,2), u.getDepartamento());
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void loadSucursalForEdit(){
		try {
			sucursal = sucursalServiceLocal.find(idsucursal);
			this.denominacion = sucursal.getDenominacion();
			this.abreviatura = sucursal.getAbreviatura();
			Ubigeo ubigeo = sucursal.getUbigeo();
			
			this.idDepartamentoSelected = ubigeo.getIdubigeo().substring(0,2);
			actualizarProvincias();
			this.idProvinciaSelected = ubigeo.getIdubigeo().substring(2,4);
			actualizarDistritos();
			this.idDistritoSelected = ubigeo.getIdubigeo().substring(4,6);
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void createSucursal() throws Exception {
		Sucursal sucursal;
		try {
			sucursal = new Sucursal();
			sucursal.setDenominacion(denominacion);
			sucursal.setAbreviatura(abreviatura);

			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelected+idProvinciaSelected+idDistritoSelected);
			sucursal.setUbigeo(ubigeo);
			
			this.sucursalServiceLocal.create(sucursal);
			succes = true;		
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());			
		}	
	}
	
	public void updateUsuario() throws Exception {
		try {
			sucursal.setDenominacion(denominacion);
			sucursal.setAbreviatura(abreviatura);

			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelected+idProvinciaSelected+idDistritoSelected);
			sucursal.setUbigeo(ubigeo);
			
			this.sucursalServiceLocal.update(sucursal);
			succes = true;		
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());			
		}	
	}

	
	public void actualizarProvincias(){
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelected);
			List<Ubigeo> list = maestrosServiceLocal.getProvincias(ubigeo);
			mapProvincias.clear();
			for (Ubigeo u : list) {
				mapProvincias.put(u.getIdubigeo().substring(2, 4), u.getProvincia());
			}
			
			idProvinciaSelected = null;
			mapDistritos.clear();
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void actualizarDistritos(){
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelected+idProvinciaSelected);
			List<Ubigeo> list = maestrosServiceLocal.getDistritos(ubigeo);
			mapDistritos.clear();
			for (Ubigeo u : list) {
				mapDistritos.put(u.getIdubigeo().substring(4, 6), u.getDistrito());
			}
			
			idDistritoSelected = null;
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Map<String, String> getMapDepartamentos() {
		return mapDepartamentos;
	}

	public void setMapDepartamentos(Map<String, String> mapDepartamentos) {
		this.mapDepartamentos = mapDepartamentos;
	}

	public Map<String, String> getMapProvincias() {
		return mapProvincias;
	}

	public void setMapProvincias(Map<String, String> mapProvincias) {
		this.mapProvincias = mapProvincias;
	}

	public Map<String, String> getMapDistritos() {
		return mapDistritos;
	}

	public void setMapDistritos(Map<String, String> mapDistritos) {
		this.mapDistritos = mapDistritos;
	}

	public String getIdDepartamentoSelected() {
		return idDepartamentoSelected;
	}

	public void setIdDepartamentoSelected(String idDepartamentoSelected) {
		this.idDepartamentoSelected = idDepartamentoSelected;
	}

	public String getIdProvinciaSelected() {
		return idProvinciaSelected;
	}

	public void setIdProvinciaSelected(String idProvinciaSelected) {
		this.idProvinciaSelected = idProvinciaSelected;
	}

	public String getIdDistritoSelected() {
		return idDistritoSelected;
	}

	public void setIdDistritoSelected(String idDistritoSelected) {
		this.idDistritoSelected = idDistritoSelected;
	}

	public Integer getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}



	
}
