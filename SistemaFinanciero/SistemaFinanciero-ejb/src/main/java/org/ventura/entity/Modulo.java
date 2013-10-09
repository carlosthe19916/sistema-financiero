package org.ventura.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the modulo database table.
 * 
 */
@Entity
@Table(name="modulo")
@NamedQuery(name="Modulo.findAll", query="SELECT m FROM Modulo m")
public class Modulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idmodulo;

	@Column(length=200)
	private String descripcion;

	@Column(nullable=false)
	private Boolean estado;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to Menu
	@OneToMany(mappedBy="modulo")
	private List<Menu> menus;

	//bi-directional many-to-one association to Modulo
	@ManyToOne
	@JoinColumn(name="idpadre")
	private Modulo modulo;

	//bi-directional many-to-one association to Modulo
	@OneToMany(mappedBy="modulo")
	private List<Modulo> modulos;

	//bi-directional many-to-many association to Rol
	@ManyToMany
	@JoinTable(
		name="modulo_rol"
		, joinColumns={
			@JoinColumn(name="idmodulo", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="idrol", nullable=false)
			}
		)
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

}