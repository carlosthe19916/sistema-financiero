package org.ventura.flow;

import java.util.Date;
import java.util.List;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;

import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.cuentapersonal.Titular;

@Named
@FlowScoped("imprimirAperturaCuenta-flow")
public class ImprimirAperturaCuentaBean {

	private String tipocuenta;
	private String numerocuenta;
	private String moneda;
	private Date fechaapertura;
	
	private boolean isPersonanatural;
	private boolean isPersonajuridica;
	
	private String dniPersonanatural;
	private String nombrecompletoPersonanatural;
	private String sexoPersonanatural;
	private Date fechanacimientoPersonanatural;
	private String estadocivilPersonanatural;
	
	private String ruc;
	private String razonsocial;
	private Date fechaconstitucion;
	private String dniPersonajuridica;
	private String nombrecompletoPersonajuridica;
	private Date fechanacimientoPersonajuridica;
	private String sexoPersonajuridica;
	
	private List<Titular> titulares;
	private List<Beneficiario> beneficiarios;

	public String getReturnValue() {
		return "/index";
	}
	
	public String getTipocuenta() {
		return tipocuenta;
	}

	public void setTipocuenta(String tipocuenta) {
		this.tipocuenta = tipocuenta;
	}

	public String getNumerocuenta() {
		return numerocuenta;
	}

	public void setNumerocuenta(String numerocuenta) {
		this.numerocuenta = numerocuenta;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Date getFechaapertura() {
		return fechaapertura;
	}

	public void setFechaapertura(Date fechaapertura) {
		this.fechaapertura = fechaapertura;
	}

	public String getDniPersonanatural() {
		return dniPersonanatural;
	}

	public void setDniPersonanatural(String dniPersonanatural) {
		this.dniPersonanatural = dniPersonanatural;
	}

	public String getNombrecompletoPersonanatural() {
		return nombrecompletoPersonanatural;
	}

	public void setNombrecompletoPersonanatural(String nombrecompletoPersonanatural) {
		this.nombrecompletoPersonanatural = nombrecompletoPersonanatural;
	}

	public String getSexoPersonanatural() {
		return sexoPersonanatural;
	}

	public void setSexoPersonanatural(String sexoPersonanatural) {
		this.sexoPersonanatural = sexoPersonanatural;
	}

	public Date getFechanacimientoPersonanatural() {
		return fechanacimientoPersonanatural;
	}

	public void setFechanacimientoPersonanatural(Date fechanacimientoPersonanatural) {
		this.fechanacimientoPersonanatural = fechanacimientoPersonanatural;
	}

	public String getEstadocivilPersonanatural() {
		return estadocivilPersonanatural;
	}

	public void setEstadocivilPersonanatural(String estadocivilPersonanatural) {
		this.estadocivilPersonanatural = estadocivilPersonanatural;
	}

	public List<Titular> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<Titular> titulares) {
		this.titulares = titulares;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonsocial() {
		return razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}

	public Date getFechaconstitucion() {
		return fechaconstitucion;
	}

	public void setFechaconstitucion(Date fechaconstitucion) {
		this.fechaconstitucion = fechaconstitucion;
	}

	public String getDniPersonajuridica() {
		return dniPersonajuridica;
	}

	public void setDniPersonajuridica(String dniPersonajuridica) {
		this.dniPersonajuridica = dniPersonajuridica;
	}

	public String getNombrecompletoPersonajuridica() {
		return nombrecompletoPersonajuridica;
	}

	public void setNombrecompletoPersonajuridica(
			String nombrecompletoPersonajuridica) {
		this.nombrecompletoPersonajuridica = nombrecompletoPersonajuridica;
	}

	public Date getFechanacimientoPersonajuridica() {
		return fechanacimientoPersonajuridica;
	}

	public void setFechanacimientoPersonajuridica(
			Date fechanacimientoPersonajuridica) {
		this.fechanacimientoPersonajuridica = fechanacimientoPersonajuridica;
	}

	public String getSexoPersonajuridica() {
		return sexoPersonajuridica;
	}

	public void setSexoPersonajuridica(String sexoPersonajuridica) {
		this.sexoPersonajuridica = sexoPersonajuridica;
	}

	public boolean isPersonanatural() {
		return isPersonanatural;
	}

	public void setPersonanatural(boolean isPersonanatural) {
		this.isPersonanatural = isPersonanatural;
	}

	public boolean isPersonajuridica() {
		return isPersonajuridica;
	}

	public void setPersonajuridica(boolean isPersonajuridica) {
		this.isPersonajuridica = isPersonajuridica;
	}

}
