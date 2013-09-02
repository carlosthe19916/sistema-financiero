package org.venturabank.managedbean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;


@ManagedBean(name ="personaNaturalManagedBean")
@NoneScoped
public class PersonaNaturalManagedBean {

	private String dni;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private Date fechaNacimiento;
	private String ocupacion;
	private String telefono;
	private String celular;
	private String email;
	private String direccion;
	private String referencia;
	private String urlFirma;

	public PersonaNaturalManagedBean() {
		// TODO Auto-generated constructor stub
	}

	public String getNombreCompleto() {
		String apellidoPaterno;
		String apellidoMaterno;
		String nombres;
		
		apellidoPaterno = (this.getApellidoPaterno() == null) ? "" : this.getApellidoPaterno();		
		apellidoMaterno = (this.getApellidoMaterno() == null) ? "" : this.getApellidoMaterno();
		nombres = (this.getNombres() == null) ? "" : this.getNombres();
							
		return apellidoPaterno + " " + apellidoMaterno + "," + nombres;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getUrlFirma() {
		return urlFirma;
	}

	public void setUrlFirma(String urlFirma) {
		this.urlFirma = urlFirma;
	}

}
