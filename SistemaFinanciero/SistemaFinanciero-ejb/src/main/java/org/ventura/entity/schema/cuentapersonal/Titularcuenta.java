package org.ventura.entity.schema.cuentapersonal;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.TitularcuentaListener;
import org.ventura.entity.schema.persona.Personanatural;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the titularcuenta database table.
 * 
 */
@Entity
@Table(name = "titularcuenta", schema = "cuentapersonal")
@EntityListeners({ TitularcuentaListener.class })
@NamedQuery(name = "Titularcuenta.findAll", query = "SELECT t FROM Titularcuenta t")
@NamedQueries({
		@NamedQuery(name = Titularcuenta.V, query = "Select s From Titularcuenta s"),
		@NamedQuery(name = Titularcuenta.VA, query = "Select c From Titularcuenta c where c.dni=:valor"),
		@NamedQuery(name = Titularcuenta.FindAllForCuentaahorro, query = "SELECT t FROM Titularcuenta t INNER JOIN t.cuentaahorro c WHERE c.idcuentaahorro =:idcuentaahorro"),
		@NamedQuery(name = Titularcuenta.FindAllForCuentaplazofijo, query = "SELECT t FROM Titularcuenta t INNER JOIN t.cuentaplazofijo c WHERE c.idcuentaplazofijo =:idcuentaplazofijo")})
public class Titularcuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String V = "org.ventura.model.Titularcuenta.V";
	public final static String VA = "org.ventura.model.Titularcuenta.VA";
	public final static String FindAllForCuentaahorro = "org.ventura.model.Titularcuenta.FindAllForCuentaahorro";
	public final static String FindAllForCuentaplazofijo = "org.ventura.model.Titularcuenta.FindAllForCuentaplazofijo";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtitularcuenta;

	@Column(nullable = false, length = 8)
	private String dni;

	@Column
	private Integer idcuentaahorro;
	@Column
	private Integer idcuentacorriente;
	@Column
	private Integer idcuentaplazofijo;

	// bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name = "idcuentaahorro", insertable = false, updatable = false)
	private Cuentaahorro cuentaahorro;

	// bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name = "idcuentacorriente", insertable = false, updatable = false)
	private Cuentacorriente cuentacorriente;

	// bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name = "idcuentaplazofijo", insertable = false, updatable = false)
	private Cuentaplazofijo cuentaplazofijo;

	// bi-directional many-to-one association to Titularcuentahistorial
	@OneToMany(mappedBy = "titularcuenta", cascade = { CascadeType.ALL })
	private List<Titularcuentahistorial> titularhitorial;

	// bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name = "dni", insertable = false, updatable = false)
	private Personanatural personanatural;

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
		if (cuentaahorro != null) {
			this.idcuentaahorro = cuentaahorro.getIdcuentaahorro();
		} else {
			this.idcuentaahorro = null;
		}
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

	public Titularcuentahistorial addTitularhistorial(Titularcuentahistorial titularcuentahistorial) {
		if(titularhitorial == null){
			titularhitorial = new ArrayList<Titularcuentahistorial>();
		} 		
		titularhitorial.add(titularcuentahistorial);
		titularcuentahistorial.setTitularcuenta(this);
		return titularcuentahistorial;
	}

	public Titularcuentahistorial removeTitularhistorial(Titularcuentahistorial titularcuentahistorial) {
		if(titularhitorial != null){
			titularhitorial.remove(titularcuentahistorial);
			titularcuentahistorial.setTitularcuenta(null);	
		}
		return titularcuentahistorial;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
		if (personanatural != null) {
			this.dni = personanatural.getDni();
		}
	}

	public Integer getIdcuentaahorro() {
		return idcuentaahorro;
	}

	public void setIdcuentaahorro(Integer idcuentaahorro) {
		this.idcuentaahorro = idcuentaahorro;
	}

	public Integer getIdcuentacorriente() {
		return idcuentacorriente;
	}

	public void setIdcuentacorriente(Integer idcuentacorriente) {
		this.idcuentacorriente = idcuentacorriente;
	}

	public Integer getIdcuentaplazofijo() {
		return idcuentaplazofijo;
	}

	public void setIdcuentaplazofijo(Integer idcuentaplazofijo) {
		this.idcuentaplazofijo = idcuentaplazofijo;
	}

	public List<Titularcuentahistorial> getTitularhitorial() {
		return titularhitorial;
	}

	public void setTitularhitorial(List<Titularcuentahistorial> titularhitorial) {
		this.titularhitorial = titularhitorial;
	}

}