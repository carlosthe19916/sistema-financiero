package org.ventura.dependent;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.entity.Personanatural;
import org.ventura.entity.Sexo;
import org.ventura.entity.Titularcuenta;

@Named
@Dependent
public class TitularesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	PersonanaturalServiceLocal personanaturalServiceLocal;

	private Integer cantidadRetirantes;

	@Inject
	private TablaBean<Titularcuenta> tablaTitulares;

	@Inject
	private ComboBean<Sexo> comboSexo;

	public TitularesBean() {
		this.cantidadRetirantes = new Integer(1);
	}

	@PostConstruct
	private void initValues() {
		comboSexo.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
	}

	public void buscarPersonanatural() {

		try {
			Titularcuenta titularcuenta = tablaTitulares.getEditingRow();
			Personanatural personanatural = titularcuenta.getPersonanatural();

			personanatural = personanaturalServiceLocal.find(personanatural
					.getDni());

			if (personanatural != null) {
				titularcuenta.setPersonanatural(personanatural);

				this.comboSexo.setItemSelected(personanatural.getSexo());

				this.tablaTitulares.setEditingRow(titularcuenta);
			} else {

				personanatural = new Personanatural();
				personanatural.setDni(tablaTitulares.getSelectedRow().getDni());
				titularcuenta.setPersonanatural(personanatural);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isValid() {
		if (cantidadRetirantes > tablaTitulares.getRows().size() + 1)
			return false;
		else
			return true;
	}

	public void addTitular() {
		Titularcuenta titularcuenta = new Titularcuenta();
		Personanatural personanatural = new Personanatural();
		titularcuenta.setPersonanatural(personanatural);

		this.tablaTitulares.addRow(titularcuenta);
	}

	public void addTitular(Personanatural personanatural) {
		Titularcuenta titularcuenta = new Titularcuenta();
		titularcuenta.setPersonanatural(personanatural);

		this.tablaTitulares.addRow(titularcuenta);
	}

	public void editTitular() {
		tablaTitulares.editRow();
	}

	public void removeTitular() {
		this.tablaTitulares.removeSelectedRow();
	}

	public void changeSexo(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Sexo sexoSelected = comboSexo.getObjectItemSelected(key);
		this.tablaTitulares.getEditingRow().getPersonanatural()
				.setSexo(sexoSelected);
	}

	public void validarTitulares() {

		List<Titularcuenta> titularcuentas = tablaTitulares.getRows();

		for (Iterator<Titularcuenta> iterator = titularcuentas.iterator(); iterator
				.hasNext();) {
			Titularcuenta titular = (Titularcuenta) iterator.next();
			Personanatural personanatural = titular.getPersonanatural();

			boolean appellidoPaterno = true;
			boolean appellidoMaterno = true;
			boolean nombres = true;

			if (personanatural.getDni() == null
					|| personanatural.getDni().isEmpty()
					|| personanatural.getDni().trim().isEmpty()
					|| personanatural.getDni().length() != 8) {
				appellidoPaterno = false;
			}
			if (personanatural.getApellidopaterno() == null
					|| personanatural.getApellidopaterno().isEmpty()
					|| personanatural.getApellidopaterno().trim().isEmpty()) {
				appellidoPaterno = false;
			}
			if (personanatural.getApellidomaterno() == null
					|| personanatural.getApellidomaterno().isEmpty()
					|| personanatural.getApellidomaterno().trim().isEmpty()) {
				appellidoMaterno = false;
			}
			if (personanatural.getNombres() == null
					|| personanatural.getNombres().isEmpty()
					|| personanatural.getNombres().trim().isEmpty()) {
				nombres = false;
			}

			if (!(appellidoPaterno && appellidoMaterno && nombres)) {
				iterator.remove();
			}

		}

	}

	public Integer getCantidadRetirantes() {
		return cantidadRetirantes;
	}

	public void setCantidadRetirantes(Integer cantidadRetirantes) {
		this.cantidadRetirantes = cantidadRetirantes;
	}

	public TablaBean<Titularcuenta> getTablaTitulares() {
		return tablaTitulares;
	}

	public void setTablaTitulares(TablaBean<Titularcuenta> tablaTitulares) {
		this.tablaTitulares = tablaTitulares;
	}

	public ComboBean<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboBean<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

}