package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.schema.persona.Personanatural;

import java.util.Date;

/**
 * The persistent class for the titularcuenta database table.
 * 
 */
@Entity
@Table(name = "titular", schema = "cuentapersonal")
@NamedQuery(name = "Titularcuenta.findAll", query = "SELECT t FROM Titular t")
@NamedQueries({
/*
 * @NamedQuery(name = Titular.V, query = "Select s From Titular s"),
 * 
 * @NamedQuery(name = Titular.VA, query =
 * "Select c From Titular c where c.dni=:valor")
 *//* , */
/*
 * @NamedQuery(name = Titular.FindAllForCuentaahorro, query =
 * "SELECT t FROM Titular t INNER JOIN t.cuentaahorro c WHERE c.idcuentaahorro =:idcuentaahorro"
 * ),
 * 
 * @NamedQuery(name = Titular.FindAllForCuentacorriente, query =
 * "SELECT t FROM Titular t INNER JOIN t.cuentacorriente c WHERE c.idcuentacorriente =:idcuentacorriente"
 * ),
 * 
 * @NamedQuery(name = Titular.FindAllForCuentaplazofijo, query =
 * "SELECT t FROM Titular t INNER JOIN t.cuentaplazofijo c WHERE c.idcuentaplazofijo =:idcuentaplazofijo"
 * )
 */})
public class Titular implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String V = "org.ventura.model.Titularcuenta.V";
	public final static String VA = "org.ventura.model.Titularcuenta.VA";
	public final static String FindAllForCuentaahorro = "org.ventura.model.Titularcuenta.FindAllForCuentaahorro";
	public final static String FindAllForCuentacorriente = "org.ventura.model.Titularcuenta.FindAllForCuentacorriente";
	public final static String FindAllForCuentaplazofijo = "org.ventura.model.Titularcuenta.FindAllForCuentaplazofijo";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtitular;

	@Column(nullable = false)
	private Boolean estado;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaactiva;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechainactiva;

	@ManyToOne
	@JoinColumn(name = "idcuentabancaria", nullable = false)
	private Cuentabancaria cuentabancaria;

	@ManyToOne
	@JoinColumn(name = "idpersonanatural", nullable = false)
	private Personanatural personanatural;

	public Titular() {
	}

	public Integer getIdtitular() {
		return idtitular;
	}

	public void setIdtitular(Integer idtitular) {
		this.idtitular = idtitular;
	}

	public Date getFechaactiva() {
		return fechaactiva;
	}

	public void setFechaactiva(Date fechaactiva) {
		this.fechaactiva = fechaactiva;
	}

	public Date getFechainactiva() {
		return fechainactiva;
	}

	public void setFechainactiva(Date fechainactiva) {
		this.fechainactiva = fechainactiva;
	}

	public Cuentabancaria getCuentabancaria() {
		return cuentabancaria;
	}

	public void setCuentabancaria(Cuentabancaria cuentabancaria) {
		this.cuentabancaria = cuentabancaria;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Titular)) {
			return false;
		}
		final Titular other = (Titular) obj;
		return other.getIdtitular() == this.idtitular ? true : false;
	}

	@Override
	public int hashCode() {
		return idtitular;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}