package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.ventura.entity.listener.TitularcuentaListener;

import java.util.List;

/**
 * The persistent class for the titularcuenta database table.
 * 
 */
@Entity
@Table(name = "titularcuenta", schema = "cuentapersonal")
@EntityListeners( { TitularcuentaListener.class})
@NamedQuery(name = "Titularcuenta.findAll", query = "SELECT t FROM Titularcuenta t")

@NamedQueries({@NamedQuery(name = Titularcuenta.V, query = "Select s From Titularcuenta s"),
	   @NamedQuery(name =  Titularcuenta.VA, query = "Select c From Titularcuenta c where c.dni=:valor")})
public class Titularcuenta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final static String V = "org.ventura.model.Titularcuenta.V";
	public final static String VA = "org.ventura.model.Titularcuenta.VA";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtitularcuenta;

	@Column(nullable = false, length = 8)
	private String dni;

	@Column(length = 14)
	private String numerocuentaahorro;

	@Column(length = 14)
	private String numerocuentacorriente;

	@Column(length = 14)
	private String numerocuentaplazofijo;

	// bi-directional many-to-one association to Cuentaahorro
	@ManyToOne
	@JoinColumn(name = "numerocuentaahorro", insertable = false, updatable = false)
	private Cuentaahorro cuentaahorro;

		
	// bi-directional many-to-one association to Cuentacorriente
	@ManyToOne
	@JoinColumn(name = "numerocuentacorriente", insertable = false, updatable = false)
	private Cuentacorriente cuentacorriente;

	// bi-directional many-to-one association to Cuentaplazofijo
	@ManyToOne
	@JoinColumn(name = "numerocuentaplazofijo", insertable = false, updatable = false)
	private Cuentaplazofijo cuentaplazofijo;

	// bi-directional many-to-one association to Titularcuentahistorial
	@OneToMany(mappedBy = "titularcuenta", cascade = { CascadeType.ALL })
	private List<Titularcuentahistorial> titularcuentahistorials;

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

	public void setTitularcuentahistorials(
			List<Titularcuentahistorial> titularcuentahistorials) {
		this.titularcuentahistorials = titularcuentahistorials;
	}

	public Titularcuentahistorial addTitularcuentahistorial(
			Titularcuentahistorial titularcuentahistorial) {
		getTitularcuentahistorials().add(titularcuentahistorial);
		titularcuentahistorial.setTitularcuenta(this);

		return titularcuentahistorial;
	}

	public Titularcuentahistorial removeTitularcuentahistorial(
			Titularcuentahistorial titularcuentahistorial) {
		getTitularcuentahistorials().remove(titularcuentahistorial);
		titularcuentahistorial.setTitularcuenta(null);

		return titularcuentahistorial;
	}

	public Personanatural getPersonanatural() {
		return personanatural;
	}

	public void setPersonanatural(Personanatural personanatural) {
		this.personanatural = personanatural;
		if(personanatural!=null){
			this.dni = personanatural.getDni();
		}
	}

	public String getNumerocuentaahorro() {
		return numerocuentaahorro;
	}

	public void setNumerocuentaahorro(String numerocuentaahorro) {
		this.numerocuentaahorro = numerocuentaahorro;
	}

	public String getNumerocuentacorriente() {
		return numerocuentacorriente;
	}

	public void setNumerocuentacorriente(String numerocuentacorriente) {
		this.numerocuentacorriente = numerocuentacorriente;
	}

	public String getNumerocuentaplazofijo() {
		return numerocuentaplazofijo;
	}

	public void setNumerocuentaplazofijo(String numerocuentaplazofijo) {
		this.numerocuentaplazofijo = numerocuentaplazofijo;
	}
/*	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Titularcuenta)) {
            return false;
        }
        // a room can be uniquely identified by it's number and the building it belongs to
        final Titularcuenta other = (Titularcuenta) obj;
        if(idtitularcuenta != null) {
        	return other.getIdtitularcuenta() == idtitularcuenta ? true:false;
        } else{
        	return other.getDni() == dni ? true:false;
        }
        
	}
	
	@Override
    public int hashCode() {
		if(idtitularcuenta != null) {
        	return idtitularcuenta;
        } else{
        	return dni.hashCode();
        }
    }
*/

	
}