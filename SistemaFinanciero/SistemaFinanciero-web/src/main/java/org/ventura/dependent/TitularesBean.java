package org.ventura.dependent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.entity.schema.cuentapersonal.Titularcuenta;
import org.ventura.entity.schema.cuentapersonal.Titularcuentahistorial;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Personanatural;

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
			personanatural = personanaturalServiceLocal.find(personanatural.getDni());
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
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "System Error", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public boolean isValid() {
		if (cantidadRetirantes <= tablaTitulares.getAllRows().size() && cantidadRetirantes >= 1)
			return true;
		else
			return false;
	}

	public void addTitular() {
		Titularcuenta titularcuenta = new Titularcuenta();
		Titularcuentahistorial titularcuentahistorial = new Titularcuentahistorial();
		titularcuentahistorial.setEstado(true);
		titularcuentahistorial.setFechaactiva(Calendar.getInstance().getTime());
		titularcuentahistorial.setTitularcuenta(titularcuenta);
		
		Personanatural personanatural = new Personanatural();
		titularcuenta.setPersonanatural(personanatural);
		titularcuenta.addTitularhistorial(titularcuentahistorial);
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
		this.tablaTitulares.getEditingRow().getPersonanatural().setSexo(sexoSelected);
	}

	public void validarTitulares() {
		List<Titularcuenta> titularcuentas = tablaTitulares.getRows();
		for (Iterator<Titularcuenta> iterator = titularcuentas.iterator(); iterator.hasNext();) {
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
		
		if(cantidadRetirantes < 1 || cantidadRetirantes > tablaTitulares.getAllRows().size()){
			FacesContext context = FacesContext.getCurrentInstance();			
			context.validationFailed();	
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cantidad de Retirantes", "Porcentaje de Beneficios no suman 100%");
			context.addMessage(null, message);
		}
		
	}

	public List<Titularcuenta> getListTitulares(){
		List<Titularcuenta> titularcuentas = tablaTitulares.getAllRows();
		for (Iterator<Titularcuenta> iterator = titularcuentas.iterator(); iterator.hasNext();) {
			Titularcuenta titular = iterator.next();
			String dni = titular.getPersonanatural().getDni();
			titular.setDni(dni);
			
			List<Titularcuentahistorial> historial = titular.getTitularhitorial();
			if(historial == null){
				historial = new ArrayList<Titularcuentahistorial>();
				titular.setTitularhitorial(historial);
			}		
		}
		return titularcuentas;
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
