package org.ventura.entity.schema.caja;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.seguridad.Usuario;

import java.util.List;

/**
 * The persistent class for the caja database table.
 * 
 */
@Entity
@Table(name = "caja", schema = "caja")
@NamedQuery(name = "Caja.findAll", query = "SELECT c FROM Caja c")
@NamedQueries({
		@NamedQuery(name = Caja.findAllByBovedaAndState, query = "SELECT c FROM Caja c INNER JOIN c.bovedas b INNER JOIN c.hitorialcajas hc WHERE b.idboveda = :idboveda AND c.estado = true and c.estadoapertura = :estadoapertura AND hc.estadomovimiento = :estadomovimiento AND hc.idcreacion = (SELECT MAX(hc.idcreacion) FROM Historialcaja hc WHERE hc.caja.idcaja = c.idcaja)"),
		@NamedQuery(name = Caja.ALL_ACTIVE_BY_AGENCIA, query = "Select c from Caja c inner join c.bovedas b where c.estado = true and b.agencia.idagencia = :idagencia group by c.idcaja"),
		@NamedQuery(name = Caja.ALL_FOR_USUARIO, query = "SELECT c FROM Caja c INNER JOIN c.usuarios u WHERE u.idusuario = :idusuario"),
		@NamedQuery(name = Caja.f_idagencia_idestadoapertura, query = "SELECT DISTINCT c FROM Caja c INNER JOIN c.bovedas b INNER JOIN b.agencia a WHERE a.idagencia = :idagencia AND c.estado = TRUE AND c.estadoapertura.idestadoapertura = :idestadoapertura") })
public class Caja implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findAllByBovedaAndState = "org.ventura.entity.schema.caja.Caja.findAllByBovedaAndState";
	public final static String ALL_ACTIVE_BY_AGENCIA = "org.ventura.entity.schema.caja.Caja.ALL_ACTIVE_BY_AGENCIA";
	public final static String ALL_FOR_USUARIO = "org.ventura.entity.schema.caja.caja.ALL_FOR_USUARIO";
	public final static String f_idagencia_idestadoapertura = "org.ventura.entity.schema.caja.caja.f_idagencia_idestadoapertura";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer idcaja;

	@Column(nullable = false, length = 30)
	private String abreviatura;

	@Column(nullable = false, length = 100)
	private String denominacion;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "boveda_caja", schema = "caja", joinColumns = { @JoinColumn(name = "idcaja") }, inverseJoinColumns = { @JoinColumn(name = "idboveda") })
	private List<Boveda> bovedas;

	@ManyToMany
	@JoinTable(name = "caja_usuario", schema = "caja", joinColumns = { @JoinColumn(name = "idcaja") }, inverseJoinColumns = { @JoinColumn(name = "idusuario") })
	private List<Usuario> usuarios;

	// @ManyToMany(mappedBy="cajas")
	// private List<Boveda> bovedas;

	@ManyToOne
	@JoinColumn(name = "idestadoapertura", nullable = false)
	private Estadoapertura estadoapertura;

	@OneToMany(mappedBy = "caja")
	private List<Historialcaja> hitorialcajas;

	public Caja() {
	}

	public Integer getIdcaja() {
		return this.idcaja;
	}

	public void setIdcaja(Integer idcaja) {
		this.idcaja = idcaja;
	}

	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Boveda> getBovedas() {
		return this.bovedas;
	}

	public void setBovedas(List<Boveda> bovedas) {
		this.bovedas = bovedas;
	}

	public Estadoapertura getEstadoapertura() {
		return estadoapertura;
	}

	public void setEstadoapertura(Estadoapertura estadoapertura) {
		this.estadoapertura = estadoapertura;
	}

	public List<Historialcaja> getHitorialcajas() {
		return hitorialcajas;
	}

	public void setHitorialcajas(List<Historialcaja> hitorialcajas) {
		this.hitorialcajas = hitorialcajas;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Caja)) {
			return false;
		}
		final Caja other = (Caja) obj;
		return other.getIdcaja().equals(this.idcaja) ? true : false;
	}

	@Override
	public int hashCode() {
		return idcaja;
	}
}