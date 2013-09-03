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
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, length=16)
	private String numerotargeta;

	//bi-directional many-to-one association to Targetadebitoasignadocuentacorriente
	@OneToMany(mappedBy="tarjetadebito")
	private List<Targetadebitoasignadocuentacorriente> targetadebitoasignadocuentacorrientes;

	//bi-directional many-to-one association to Estadotargeta
	@ManyToOne
	@JoinColumn(name="idestadotargeta", nullable=false)
	private Estadotargeta estadotargeta;

	//bi-directional many-to-one association to Tipotarjetadebito
	@ManyToOne
	@JoinColumn(name="idtargetadebitotipo", nullable=false)
	private Tipotarjetadebito tipotarjetadebito;

	//bi-directional many-to-one association to Tarjetadebitoasignadocuentaahorro
	@OneToMany(mappedBy="tarjetadebito")
	private List<Tarjetadebitoasignadocuentaahorro> tarjetadebitoasignadocuentaahorros;

	public Tarjetadebito() {
	}

	public String getNumerotargeta() {
		return this.numerotargeta;
	}

	public void setNumerotargeta(String numerotargeta) {
		this.numerotargeta = numerotargeta;
	}

	public List<Targetadebitoasignadocuentacorriente> getTargetadebitoasignadocuentacorrientes() {
		return this.targetadebitoasignadocuentacorrientes;
	}

	public void setTargetadebitoasignadocuentacorrientes(List<Targetadebitoasignadocuentacorriente> targetadebitoasignadocuentacorrientes) {
		this.targetadebitoasignadocuentacorrientes = targetadebitoasignadocuentacorrientes;
	}

	public Targetadebitoasignadocuentacorriente addTargetadebitoasignadocuentacorriente(Targetadebitoasignadocuentacorriente targetadebitoasignadocuentacorriente) {
		getTargetadebitoasignadocuentacorrientes().add(targetadebitoasignadocuentacorriente);
		targetadebitoasignadocuentacorriente.setTarjetadebito(this);

		return targetadebitoasignadocuentacorriente;
	}

	public Targetadebitoasignadocuentacorriente removeTargetadebitoasignadocuentacorriente(Targetadebitoasignadocuentacorriente targetadebitoasignadocuentacorriente) {
		getTargetadebitoasignadocuentacorrientes().remove(targetadebitoasignadocuentacorriente);
		targetadebitoasignadocuentacorriente.setTarjetadebito(null);

		return targetadebitoasignadocuentacorriente;
	}

	public Estadotargeta getEstadotargeta() {
		return this.estadotargeta;
	}

	public void setEstadotargeta(Estadotargeta estadotargeta) {
		this.estadotargeta = estadotargeta;
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

}