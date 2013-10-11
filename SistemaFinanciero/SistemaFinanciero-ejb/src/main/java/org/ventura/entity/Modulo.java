package org.ventura.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the modulo database table.
 * 
 */
@Entity
@Table(name = "modulo", schema = "seguridad")
@NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m")
@NamedQueries({
		@NamedQuery(name = Modulo.ALL, query = "Select m From Modulo m"),
		@NamedQuery(name = Modulo.ALL_ACTIVE, query = "Select m From Modulo m WHERE m.estado=true"),
		@NamedQuery(name = Modulo.ALL_FOR_USER, query = "Select m From Modulo m INNER JOIN m.rols r INNER JOIN r.grupos g INNER JOIN g.usuarios u WHERE m.estado = true AND m.modulo = null AND u.username = :username") })
public class Modulo implements Serializable {

	public final static String ALL = "org.ventura.model.Modulo.ALL";
	public final static String ALL_ACTIVE = "org.ventura.model.Modulo.ALL_ACTIVE";
	public final static String ALL_FOR_USER = "org.ventura.model.Modulo.ALL_FOR_USER";
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idmodulo;

	@Column(length = 200)
	private String descripcion;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false, length = 50)
	private String nombre;

	// bi-directional many-to-one association to Menu
	@OneToMany(mappedBy = "modulo")
	private List<Menu> menus;

	// bi-directional many-to-one association to Modulo
	@ManyToOne
	@JoinColumn(name = "idpadre")
	private Modulo modulo;

	// bi-directional many-to-one association to Modulo
	@OneToMany(mappedBy = "modulo")
	private List<Modulo> modulos;

	// bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(name = "modulo_rol", schema = "seguridad", joinColumns = { @JoinColumn(name = "idmodulo", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idrol", nullable = false) })
	private List<Rol> rols;

	public Modulo() {
	}

	public Integer getIdmodulo() {
		return this.idmodulo;
	}

	public void setIdmodulo(Integer idmodulo) {
		this.idmodulo = idmodulo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Menu addMenus(Menu menus) {
		getMenus().add(menus);
		menus.setModulo(this);

		return menus;
	}

	public Menu removeMenus(Menu menus) {
		getMenus().remove(menus);
		menus.setModulo(null);

		return menus;
	}

	public Modulo getModulo() {
		return this.modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<Modulo> getModulos() {
		return this.modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	public Modulo addModulo(Modulo modulo) {
		getModulos().add(modulo);
		modulo.setModulo(this);

		return modulo;
	}

	public Modulo removeModulo(Modulo modulo) {
		getModulos().remove(modulo);
		modulo.setModulo(null);

		return modulo;
	}

	public List<Rol> getRols() {
		return this.rols;
	}

	public void setRols(List<Rol> rols) {
		this.rols = rols;
	}

	public String getShortModuleName() {
		String shortModuleName = this.nombre;

		final StringBuilder result = new StringBuilder(shortModuleName.length());
		String[] words = shortModuleName.split("\\s");
		for (int i = 0, l = words.length; i < l; ++i) {
			if (i > 0)
				result.append(" ");
			result.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1));
		}
		
		shortModuleName = shortModuleName.replaceAll("\\s","");
		shortModuleName= shortModuleName.substring(0, 1).toLowerCase() + shortModuleName.substring(1);
		return shortModuleName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Modulo)) {
            return false;
        }
        // a room can be uniquely identified by it's number and the building it belongs to
        final Modulo other = (Modulo) obj;
        return other.getIdmodulo() == idmodulo ? true:false;
	}
	
	@Override
    public int hashCode() {
        return idmodulo;
    }

}