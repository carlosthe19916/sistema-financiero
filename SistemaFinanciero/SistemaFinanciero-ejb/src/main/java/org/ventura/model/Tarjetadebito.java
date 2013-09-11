package org.ventura.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tarjetadebito database table.
 * 
 */
@Entity
@Table(name="tarjetadebito",schema="cuentapersonal")
@NamedQuery(name="Tarjetadebito.findAll", query="SELECT t FROM Tarjetadebito t")
public class Tarjetadebito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=16)
	private String numerotargeta;

	//bi-directional many-to-one association to Estadotarjeta
	@ManyToOne
	@JoinColumn(name="idestadotargeta", nullable=false)
	private Estadotarjeta estadotarjeta;

	//bi-directional many-to-one association to Tipotarjetadebito
	@ManyToOne
	@JoinColumn(name="idtargetadebitotipo", nullable=false)
	private Tipotarjetadebito tipotarjetadebito;

	//bi-directional many-to-one association to Tarjetadebitoasignadocuentaahorro
	@OneToMany(mappedBy="tarjetadebito")
	private List<Tarjetadebitoasignadocuentaahorro> tarjetadebitoasignadocuentaahorros;

	//bi-directional many-to-one association to Tarjetadebitoasignadocuentacorriente
	@OneToMany(mappedBy="tarjetadebito")
	private List<Tarjetadebitoasignadocuentacorriente> tarjetadebitoasignadocuentacorrientes;

	public Tarjetadebito() {
	}

	public String getNumerotargeta() {
		return this.numerotargeta;
	}

	public void setNumerotargeta(String numerotargeta) {
		this.numerotargeta = numerotargeta;
	}

	public Estadotarjeta getEstadotarjeta() {
		return this.estadotarjeta;
	}

	public void setEstadotarjeta(Estadotarjeta estadotarjeta) {
		this.estadotarjeta = estadotarjeta;
	}

	public Tipotarjetadebito getTipotarjetadebito() {
		return this.tipotarjetadebito;
	}

	public void setTipotarjetadebito(Tipotarjetadebito tipotarjetadebito) {
		this.tipotarjetadebito = tipotarjetadebito;
	}

	public List<Tarjetadebitoasignadocuentaahorro> getTarjetadebitoasignadocuentaahorros() {
		return this.tarjetadebitoasignadocuentaahorros;
	}

	public void setTarjetadebitoasignadocuentaahorros(List<Tarjetadebitoasignadocuentaahorro> tarjetadebitoasignadocuentaahorros) {
		this.tarjetadebitoasignadocuentaahorros = tarjetadebitoasignadocuentaahorros;
	}

	public Tarjetadebitoasignadocuentaahorro addTarjetadebitoasignadocuentaahorro(Tarjetadebitoasignadocuentaahorro tarjetadebitoasignadocuentaahorro) {
		getTarjetadebitoasignadocuentaahorros().add(tarjetadebitoasignadocuentaahorro);
		tarjetadebitoasignadocuentaahorro.setTarjetadebito(this);

		return tarjetadebitoasignadocuentaahorro;
	}

	public Tarjetadebitoasignadocuentaahorro removeTarjetadebitoasignadocuentaahorro(Tarjetadebitoasignadocuentaahorro tarjetadebitoasignadocuentaahorro) {
		getTarjetadebitoasignadocuentaahorros().remove(tarjetadebitoasignadocuentaahorro);
		tarjetadebitoasignadocuentaahorro.setTarjetadebito(null);

		return tarjetadebitoasignadocuentaahorro;
	}

	public List<Tarjetadebitoasignadocuentacorriente> getTarjetadebitoasignadocuentacorrientes() {
		return this.tarjetadebitoasignadocuentacorrientes;
	}

	public void setTarjetadebitoasignadocuentacorrientes(List<Tarjetadebitoasignadocuentacorriente> tarjetadebitoasignadocuentacorrientes) {
		this.tarjetadebitoasignadocuentacorrientes = tarjetadebitoasignadocuentacorrientes;
	}

	public Tarjetadebitoasignadocuentacorriente addTarjetadebitoasignadocuentacorriente(Tarjetadebitoasignadocuentacorriente tarjetadebitoasignadocuentacorriente) {
		getTarjetadebitoasignadocuentacorrientes().add(tarjetadebitoasignadocuentacorriente);
		tarjetadebitoasignadocuentacorriente.setTarjetadebito(this);

		return tarjetadebitoasignadocuentacorriente;
	}

	public Tarjetadebitoasignadocuentacorriente removeTarjetadebitoasignadocuentacorriente(Tarjetadebitoasignadocuentacorriente tarjetadebitoasignadocuentacorriente) {
		getTarjetadebitoasignadocuentacorrientes().remove(tarjetadebitoasignadocuentacorriente);
		tarjetadebitoasignadocuentacorriente.setTarjetadebito(null);

		return tarjetadebitoasignadocuentacorriente;
	}

}