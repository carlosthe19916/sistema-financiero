package org.ventura.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ventura.entity.listener.AporteListener;
import org.ventura.entity.listener.CuentaahorrohistorialListener;


@Entity
@Table(name = "aporte", schema = "cuentapersonal")
@EntityListeners( { CuentaahorrohistorialListener.class })
@NamedQuery(name = "Aporte.findAll", query = "SELECT c FROM Aporte c")
public class Aporte implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idaporte;

	@Column(nullable = false, length = 14)
	private String numerocuentaaporte;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fechaaporte;
	
	@Column(nullable = false)
	private double monto;

	// bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name = "numerocuentaaporte", nullable = false, insertable = false, updatable = false)
	private Cuentaaporte cuentaaporte;

	public Aporte() {
	}

	public Integer getIdaporte() {
		return idaporte;
	}

	public void setIdaporte(Integer idaporte) {
		this.idaporte = idaporte;
	}

	public String getNumerocuentaaporte() {
		return numerocuentaaporte;
	}

	public void setNumerocuentaaporte(String numerocuentaaporte) {
		this.numerocuentaaporte = numerocuentaaporte;
	}

	public Date getFechaaporte() {
		return fechaaporte;
	}

	public void setFechaaporte(Date fechaaporte) {
		this.fechaaporte = fechaaporte;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
	}

	

}
