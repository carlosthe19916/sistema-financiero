package org.ventura.entity.schema.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Table(name = "menu", schema = "seguridad")
@NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
@NamedQueries({
		@NamedQuery(name = Menu.ALL, query = "SELECT m FROM Menu m"),
		@NamedQuery(name = Menu.ALL_ACTIVE, query = "Select m From Menu m WHERE m.estado=true"),
		@NamedQuery(name = Menu.ALL_FOR_USER, query = "Select DISTINCT me From Menu me INNER JOIN me.modulo mo INNER JOIN mo.rols r INNER JOIN r.grupos g INNER JOIN g.usuarios u WHERE me.estado = true AND u.username = :username ORDER BY me.idmenu") })
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String ALL = "org.ventura.model.Menu.ALL";
	public final static String ALL_ACTIVE = "org.ventura.model.Menu.ALL_ACTIVE";
	public final static String ALL_FOR_USER = "org.ventura.model.Menu.ALL_FOR_USER";

	@Id
	@Column(unique = true, nullable = false)
	private Integer idmenu;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false, length = 50)
	private String nombre;

	@Column(length = 500)
	private String url;

	// bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name = "idpadre")
	private Menu menu;

	// bi-directional many-to-one association to Menu
	@OneToMany(mappedBy = "menu")
	private List<Menu> menus;

	// bi-directional many-to-one association to Modulo
	@ManyToOne
	@JoinColumn(name = "idmodulo", nullable = false)
	private Modulo modulo;

	// bi-directional many-to-many association to Pagina
	@ManyToMany
	@JoinTable(name = "menu_pagina", schema = "seguridad", joinColumns = { @JoinColumn(name = "idmenu", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "idpagina", nullable = false) })
	private List<Pagina> paginas;

	public Menu() {
	}

	public Integer getIdmenu() {
		return this.idmenu;
	}

	public void setIdmenu(Integer idmenu) {
		this.idmenu = idmenu;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Menu addMenus(Menu menus) {
		getMenus().add(menus);
		menus.setMenu(this);

		return menus;
	}

	public Menu removeMenus(Menu menus) {
		getMenus().remove(menus);
		menus.setMenu(null);

		return menus;
	}

	public Modulo getModulo() {
		return this.modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<Pagina> getPaginas() {
		return this.paginas;
	}

	public void setPaginas(List<Pagina> paginas) {
		this.paginas = paginas;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Menu)) {
			return false;
		}
		// a room can be uniquely identified by it's number and the building it
		// belongs to
		final Menu other = (Menu) obj;
		return other.getIdmenu().equals(idmenu) ? true : false;
	}

	@Override
	public int hashCode() {
		return idmenu;
	}

}