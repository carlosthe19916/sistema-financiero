package org.ventura.entity.schema.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the pagina database table.
 * 
 */
@Entity
@Table(name = "pagina", schema = "seguridad")
@NamedQuery(name = "Pagina.findAll", query = "SELECT p FROM Pagina p")
public class Pagina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true, nullable = false)
	private Integer idpagina;

	@Column(length = 200)
	private String descripcion;

	@Column(nullable = false)
	private Boolean estado;

	@Column(length = 200)
	private String nombre;

	@Column(length = 500)
	private String url;

	// bi-directional many-to-many association to Menu
	@ManyToMany(mappedBy = "paginas")
	private List<Menu> menus;

	public Pagina() {
	}

	public Integer getIdpagina() {
		return this.idpagina;
	}

	public void setIdpagina(Integer idpagina) {
		this.idpagina = idpagina;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Pagina)) {
            return false;
        }
        // a room can be uniquely identified by it's number and the building it belongs to
        final Pagina other = (Pagina) obj;
        return other.getIdpagina().equals(idpagina) ? true:false;
	}
	
	@Override
    public int hashCode() {
        return idpagina;
    }
	
}