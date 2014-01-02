package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the beneficiariocuenta database table.
 * 
 */
@Entity
@Table(name = "beneficiario", schema = "cuentapersonal")
@NamedQueries({ @NamedQuery(name = "Beneficiariocuenta.findAll", query = "SELECT b FROM Beneficiario b") })
public class Beneficiario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idbeneficiario;

	@Column
	private String dni;

	@Column(nullable = false)
	private String apellidopaterno;

	@Column(nullable = false)
	private String apellidomaterno;

	@Column(nullable = false)
	private String nombres;

	@Column(nullable = false)
	private Integer porcentajebeneficio;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne
	@JoinColumn(name = "idcuentabancaria")
	private Cuentabancaria cuentabancaria;

	public Beneficiario() {
	}

	public Integer getIdbeneficiario() {
		return idbeneficiario;
	}

	public void setIdbeneficiario(Integer idbeneficiario) {
		this.idbeneficiario = idbeneficiario;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellidopaterno() {
		return apellidopaterno;
	}

	public void setApellidopaterno(String apellidopaterno) {
		this.apellidopaterno = apellidopaterno;
	}

	public String getApellidomaterno() {
		return apellidomaterno;
	}

	public void setApellidomaterno(String apellidomaterno) {
		this.apellidomaterno = apellidomaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Integer getPorcentajebeneficio() {
		return porcentajebeneficio;
	}

	public void setPorcentajebeneficio(Integer porcentajebeneficio) {
		this.porcentajebeneficio = porcentajebeneficio;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Beneficiario)) {
			return false;
		}
		final Beneficiario other = (Beneficiario) obj;
		return other.getIdbeneficiario() == this.idbeneficiario ? true : false;
	}

	@Override
	public int hashCode() {
		return idbeneficiario;
	}

}