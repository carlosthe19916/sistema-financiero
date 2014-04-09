package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.tipodato.Moneda;

import java.util.Date;

/**
 * The persistent class for the interesdiario database table.
 * 
 */
@Entity
@Table(name = "interesdiario", schema = "cuentapersonal")
@NamedQuery(name = "Interesdiario.findAll", query = "SELECT i FROM Interesdiario i")
@NamedQueries({
		@NamedQuery(name = Interesdiario.InteresesForDate, query = "SELECT i FROM Interesdiario i WHERE i.fecha = :fecha"),
		@NamedQuery(name = Interesdiario.InteresesForDateAndCuenta, query = "SELECT i FROM Interesdiario i WHERE i.idcuentabancaria = :idcuentabancaria AND i.fecha BETWEEN :startDate AND :endDate"),
		@NamedQuery(name = Interesdiario.InteresesMaxDate, query = "SELECT i.fecha FROM Interesdiario i WHERE i.idcuentabancaria = :idcuentabancaria AND i.fecha = (SELECT MAX(ii.fecha) FROM Interesdiario ii WHERE ii.idcuentabancaria = i.idcuentabancaria)"),
		@NamedQuery(name = Interesdiario.InteresesCapitalizable, query = "SELECT i.fecha FROM Interesdiario i WHERE i.idcuentabancaria = :idcuentabancaria AND i.fecha > (SELECT MAX(ii.fecha) FROM Interesdiario ii WHERE ii.idcuentabancaria = i.idcuentabancaria AND ii.estadocapitalizacion = TRUE)") })
public class Interesdiario implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String InteresesForDate = "org.ventura.entity.schema.cuentapersonal.Interesdiario";
	public final static String InteresesForDateAndCuenta = "org.ventura.entity.schema.cuentapersonal.InteresesForDateAndCuenta";
	public final static String InteresesMaxDate = "org.ventura.entity.schema.cuentapersonal.InteresesMaxDate";
	public final static String InteresesCapitalizable = "org.ventura.entity.schema.cuentapersonal.InteresesCapitalizable";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idinteresdiario;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "capital")) })
	private Moneda capital;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;

	@Column(nullable = false)
	private Integer idcuentabancaria;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "value", column = @Column(name = "interes")) })
	private Moneda interes;
	
	@Column
	private boolean estadocapitalizacion;

	public Interesdiario() {
	}

	public Integer getIdinteresdiario() {
		return this.idinteresdiario;
	}

	public void setIdinteresdiario(Integer idinteresdiario) {
		this.idinteresdiario = idinteresdiario;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getIdcuentabancaria() {
		return this.idcuentabancaria;
	}

	public void setIdcuentabancaria(Integer idcuentabancaria) {
		this.idcuentabancaria = idcuentabancaria;
	}

	public Moneda getCapital() {
		return capital;
	}

	public void setCapital(Moneda capital) {
		this.capital = capital;
	}

	public void setInteres(Moneda interes) {
		this.interes = interes;
	}

	public Moneda getInteres() {
		return interes;
	}

	public boolean isEstadocapitalizacion() {
		return estadocapitalizacion;
	}

	public void setEstadocapitalizacion(boolean estadocapitalizacion) {
		this.estadocapitalizacion = estadocapitalizacion;
	}

}