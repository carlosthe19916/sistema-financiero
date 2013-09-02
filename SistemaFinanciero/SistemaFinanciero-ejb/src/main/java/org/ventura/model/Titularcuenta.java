package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the titularcuenta database table.
 * 
 */
@Entity
@NamedQuery(name="Titularcuenta.findAll", query="SELECT t FROM Titularcuenta t")
public class Titularcuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idtitularcuenta;

	private String dni;

	//bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name="numerocuentaahorro")
	private Cuentaahorro cuentaahorro;

	//bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name="numerocuentacorriente")
	private Cuentacorriente cuentacorriente;

	//bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name="numerocuentaplazofijo")
	private Cuentaplazofijo cuentaplazofijo;

	//bi-directional many-to-one association to Titularcuentahistorial
	@OneToMany(mappedBy="titularcuenta")
	private List<Titularcuentahistorial> titularcuentahistorials;

	public Titularcuenta() {
	}

	public Integer getIdtitularcuenta() {
		return this.idtitularcuenta;
	}

	public void setIdtitularcuenta(Integer idtitularcuenta) {
		this.idtitularcuenta = idtitularcuenta;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Cuentaahorro getCuentaahorro() {
		return this.cuentaahorro;
	}

	public void setCuentaahorro(Cuentaahorro cuentaahorro) {
		this.cuentaahorro = cuentaahorro;
	}

	public Cuentacorriente getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(Cuentacorriente cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	public Cuentaplazofijo getCuentaplazofijo() {
		return this.cuentaplazofijo;
	}

	public void setCuentaplazofijo(Cuentaplazofijo cuentaplazofijo) {
		this.cuentaplazofijo = cuentaplazofijo;
	}

	public List<Titularcuentahistorial> getTitularcuentahistorials() {
		return this.titularcuentahistorials;
	}

	public void setTitularcuentahistorials(List<Titularcuentahistorial> titularcuentahistorials) {
		this.titularcuentahistorials = titularcuentahistorials;
	}

	public Titularcuentahistorial addTitularcuentahistorial(Titularcuentahistorial titularcuentahistorial) {
		getTitularcuentahistorials().add(titularcuentahistorial);
		titularcuentahistorial.setTitularcuenta(this);

		return titularcuentahistorial;
	}

	public Titularcuentahistorial removeTitularcuentahistorial(Titularcuentahistorial titularcuentahistorial) {
		getTitularcuentahistorials().remove(titularcuentahistorial);
		titularcuentahistorial.setTitularcuenta(null);

		return titularcuentahistorial;
	}

}