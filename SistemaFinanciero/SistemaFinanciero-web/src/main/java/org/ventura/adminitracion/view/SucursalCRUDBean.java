package org.ventura.adminitracion.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.local.SucursalServiceLocal;
import org.ventura.dependent.TablaBean;
import org.ventura.entity.schema.maestro.Ubigeo;
import org.ventura.entity.schema.sucursal.Agencia;
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
	
	@Inject private TablaBean<Agencia> tablaAgencias;
	private boolean dlgAgencia;
	private boolean isEditingAgencia;
	private String denominacionAgencia;
	private String abreviaturaAgencia;
	private String codigoAgencia;
	private Agencia agencia;
	
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
		
		dlgAgencia = false;
		
		mapDepartamentos = new HashMap<String, String>();
		mapProvincias = new HashMap<String, String>();
		mapDistritos = new HashMap<String, String>();
	}
	
	@PostConstruct
	private void initialize() throws Exception {
		Map<String, String> mapDepartamentosNoOrdenados = new HashMap<String, String>();
		try {
			List<Ubigeo> listDepartamentos = maestrosServiceLocal.getDepartamentos();	
			for (Ubigeo u : listDepartamentos) {
				mapDepartamentosNoOrdenados.put(u.getIdubigeo().substring(0,2), u.getDepartamento());
			}
			//ordenando map departamentos 
			mapDepartamentos = ordenarMap(mapDepartamentosNoOrdenados);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void actualizarProvincias(){
		Map<String, String> mapProvinciasNoOrdenados = new HashMap<String, String>();
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelected);
			List<Ubigeo> list = maestrosServiceLocal.getProvincias(ubigeo);
			mapProvinciasNoOrdenados.clear();
			mapProvincias.clear();
			for (Ubigeo u : list) {
				mapProvinciasNoOrdenados.put(u.getIdubigeo().substring(2, 4), u.getProvincia());
			}
			
			//ordenar map provincias
			mapProvincias = ordenarMap(mapProvinciasNoOrdenados);
			
			idProvinciaSelected = null;
			mapDistritos.clear();
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void actualizarDistritos(){
		Map<String, String> mapDistritosNoOrdenados = new HashMap<String, String>();
		try {
			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelected+idProvinciaSelected);
			List<Ubigeo> list = maestrosServiceLocal.getDistritos(ubigeo);
			mapDistritosNoOrdenados.clear();
			mapDistritos.clear();
			for (Ubigeo u : list) {
				mapDistritosNoOrdenados.put(u.getIdubigeo().substring(4, 6), u.getDistrito());
			}
			
			//ordenar map distritos
			mapDistritos = ordenarMap(mapDistritosNoOrdenados);
			
			idDistritoSelected = null;
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public Map<String, String> ordenarMap(Map<String, String> map){
		HashMap<String, String> mapOrdenado = new LinkedHashMap<String, String>();
		List<String> mapKeys = new ArrayList<>(map.keySet());
		List<String> mapValues = new ArrayList<String>(map.values());
		
		TreeSet<String> conjuntoOrdenado = new TreeSet<String>(mapValues);
		String[] arrayOrdenado = conjuntoOrdenado.toArray(new String[conjuntoOrdenado.size()]);
		for (int i = 0; i < arrayOrdenado.length; i++) {
			mapOrdenado.put(mapKeys.get(mapValues.indexOf(arrayOrdenado[i])), arrayOrdenado[i]);
		}
		return mapOrdenado;
	}
	
	public void loadSucursalForEdit(){
		try {
			sucursal = sucursalServiceLocal.find(idsucursal);
			this.denominacion = sucursal.getDenominacion();
			this.abreviatura = sucursal.getAbreviatura();
			Ubigeo ubigeo = sucursal.getUbigeo();
			
			List<Agencia> listAgencias = sucursal.getAgencias();
			tablaAgencias.setRows(listAgencias);
			
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
			
			List<Agencia> listAgencias = tablaAgencias.getAllRows();
			sucursal.setAgencias(listAgencias);
			
			this.sucursalServiceLocal.create(sucursal);
			succes = true;		
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());			
		}	
	}
	
	public void updateSucursal() throws Exception {
		try {
			sucursal.setDenominacion(denominacion);
			sucursal.setAbreviatura(abreviatura);

			Ubigeo ubigeo = new Ubigeo();
			ubigeo.setIdubigeo(idDepartamentoSelected+idProvinciaSelected+idDistritoSelected);
			sucursal.setUbigeo(ubigeo);
			
			List<Agencia> listAgencias = tablaAgencias.getAllRows();
			sucursal.setAgencias(listAgencias);
			
			this.sucursalServiceLocal.update(sucursal);
			succes = true;		
		} catch (Exception e) {
			failure = true;
			JsfUtil.addErrorMessage(e.getMessage());			
		}	
	}

	public void saveAgencia(){
		if(isEditingAgencia == false){
			addAgencia();
		} else {
			editAgencia();
		}
		dlgAgencia = false;
		denominacionAgencia = "";
		abreviaturaAgencia = "";
		codigoAgencia ="";
		isEditingAgencia = false;
	}
	
	public void addAgencia(){
		Agencia agencia = new Agencia();
		
		agencia.setDenominacion(denominacionAgencia);
		agencia.setAbreviatura(abreviaturaAgencia);
		agencia.setCodigoagencia(codigoAgencia);
		agencia.setEstado(true);
		
		tablaAgencias.addRow(agencia);
	}
	
	public void editAgencia(){
		this.agencia.setDenominacion(denominacionAgencia);
		this.agencia.setAbreviatura(abreviaturaAgencia);
		this.agencia.setCodigoagencia(codigoAgencia);
	}
	
	public void deleteAgencia(Agencia agencia){
		tablaAgencias.removeRow(agencia);
	}
	
	public void openDialogForCreateAgencia(){
		dlgAgencia = true;
		isEditingAgencia = false;
	}
	
	public void openDialogForEditAgencia(Agencia agencia){
		dlgAgencia = true;
		isEditingAgencia = true;
		
		this.agencia = agencia;
		denominacionAgencia = agencia.getDenominacion();
		abreviaturaAgencia = agencia.getAbreviatura();
		codigoAgencia = agencia.getCodigoagencia();
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

	public TablaBean<Agencia> getTablaAgencias() {
		return tablaAgencias;
	}

	public void setTablaAgencias(TablaBean<Agencia> tablaAgencias) {
		this.tablaAgencias = tablaAgencias;
	}

	public boolean isDlgAgencia() {
		return dlgAgencia;
	}

	public void setDlgAgencia(boolean dlgAgencia) {
		this.dlgAgencia = dlgAgencia;
	}

	public String getDenominacionAgencia() {
		return denominacionAgencia;
	}

	public void setDenominacionAgencia(String denominacionAgencia) {
		this.denominacionAgencia = denominacionAgencia;
	}

	public String getAbreviaturaAgencia() {
		return abreviaturaAgencia;
	}

	public void setAbreviaturaAgencia(String abreviaturaAgencia) {
		this.abreviaturaAgencia = abreviaturaAgencia;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public boolean isEditingAgencia() {
		return isEditingAgencia;
	}

	public void setEditingAgencia(boolean isEditingAgencia) {
		this.isEditingAgencia = isEditingAgencia;
	}



	
}
