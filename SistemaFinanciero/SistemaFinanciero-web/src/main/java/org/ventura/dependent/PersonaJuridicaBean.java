package org.ventura.dependent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.PersonajuridicaServiceLocal;
import org.ventura.boundary.local.PersonanaturalServiceLocal;
import org.ventura.entity.schema.maestro.Estadocivil;
import org.ventura.entity.schema.maestro.Sexo;
import org.ventura.entity.schema.persona.Accionista;
import org.ventura.entity.schema.persona.AccionistaPK;
import org.ventura.entity.schema.persona.Personajuridica;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.persona.Tipoempresa;

@Named
@Dependent
public class PersonaJuridicaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Personajuridica oPersonajuridica;
	
	@EJB
	PersonajuridicaServiceLocal personaJuridicaFacadeLocal;
	
	@EJB
	PersonanaturalServiceLocal personanaturalServiceLocal;
	
	@Inject
	private ComboBean<Tipoempresa> comboTipoempresa;
	@Inject
	private ComboBean<Sexo> comboSexo;
	@Inject
	private ComboBean<Sexo> comboSexoAccionista;
	@Inject
	private ComboBean<Estadocivil> comboEstadocivil;
	@Inject
	private TablaBean<Accionista> tablaAccionistas;
	
	private boolean isEditing;
	private boolean isEditingPersonanatural;
	
	public PersonaJuridicaBean() {
		oPersonajuridica = new Personajuridica();		
	}

	@PostConstruct
	private void initValues() {
		getComboTipoempresa().initValuesFromNamedQueryName(Tipoempresa.ALL_ACTIVE);
		getComboSexo().initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
		comboSexoAccionista.initValuesFromNamedQueryName(Sexo.ALL_ACTIVE);
		getComboEstadocivil().initValuesFromNamedQueryName(Estadocivil.ALL_ACTIVE);		
		oPersonajuridica.setListAccionista(new ArrayList<Accionista>());
		oPersonajuridica.setPersonanatural(new Personanatural());
	}
	
	public void buscarPersonaJuririca(){
		Personajuridica personajuridica;
		try {
			personajuridica = personaJuridicaFacadeLocal.find(oPersonajuridica.getRuc());
			
			if (personajuridica != null) {
				this.oPersonajuridica = personajuridica;
				this.comboTipoempresa.setItemSelected(personajuridica.getTipoempresa());
				
				this.comboSexo.setItemSelected(personajuridica.getPersonanatural().getSexo());
				this.comboEstadocivil.setItemSelected(personajuridica.getPersonanatural().getEstadocivil());
				this.tablaAccionistas.setRows(oPersonajuridica.getListAccionista());
			} else {
				personajuridica = new Personajuridica();
				personajuridica.setRuc(getoPersonajuridica().getRuc());
				personajuridica.setPersonanatural(new Personanatural());
				
				this.oPersonajuridica = personajuridica;
				this.tablaAccionistas.clearRows();
				
				this.comboTipoempresa.setItemSelected(-1);
				
				this.changeEditingState();
			}
			
		} catch (Exception e) {
			
		}		
	}
	
	public void buscarPersonanatural(){
		Personanatural personanatural;
		try {
			personanatural = personanaturalServiceLocal.find(oPersonajuridica.getDnirepresentantelegal());			
			if (personanatural != null) {
				this.oPersonajuridica.setPersonanatural(personanatural);
				this.comboSexo.setItemSelected(personanatural.getSexo());
				this.comboEstadocivil.setItemSelected(personanatural.getEstadocivil());				
			} else {
				personanatural = new Personanatural();
				personanatural.setDni(oPersonajuridica.getDnirepresentantelegal());
				
				this.oPersonajuridica.setPersonanatural(personanatural);
				this.comboSexo.setItemSelected(-1);
				this.comboEstadocivil.setItemSelected(-1);
				
				this.changeEditingPersonanaturalState();
			}
		} catch (Exception e) {
			
		}	
	}
	
	public void buscarAccionista(){	
		try {
			Accionista accionista = tablaAccionistas.getEditingRow();
			Personanatural personanatural = accionista.getPersonanatural();
			
			personanatural = personanaturalServiceLocal.find(personanatural.getDni());
			
			if (personanatural != null) {
				accionista.setPersonanatural(personanatural);
				
				this.comboSexoAccionista.setItemSelected(personanatural.getSexo());
				
				this.tablaAccionistas.setEditingRow(accionista);					
			} else {
				personanatural = new Personanatural();			
				personanatural.setDni(tablaAccionistas.getSelectedRow().getPersonanatural().getDni());
				accionista.setPersonanatural(personanatural);		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public boolean isValid(){
		return oPersonajuridica.isValid() ? true : false;
	}
	
	public void addAccionista() {
		Accionista accionista = new Accionista();
		Personanatural personanatural = new Personanatural();	
		accionista.setPersonanatural(personanatural);	
		tablaAccionistas.addRow(accionista);			
	}

	public void removeAccionista() {
		this.getTablaAccionistas().removeSelectedRow();
	}
	
	public void editTitular() {
		
	}
	
	public void changeTipoempresa(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.oPersonajuridica.setTipoempresa(getComboTipoempresa().getObjectItemSelected(key));
	}
	
	public void changeSexo(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.oPersonajuridica.getPersonanatural().setSexo(getComboSexo().getObjectItemSelected(key));
	}
	
	public void changeSexoAccionista(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		Sexo sexoSelected = comboSexoAccionista.getObjectItemSelected(key);
		this.tablaAccionistas.getEditingRow().getPersonanatural().setSexo(sexoSelected);		
	}
	
	public void changeEstadoCivil(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		this.oPersonajuridica.getPersonanatural().setEstadocivil(getComboEstadocivil().getObjectItemSelected(key));
	}
	
	public String razonSocial(boolean finsocial) {
		if (finsocial)
			return "Con fines de lucro";
		else
			return "Sin fines de lucro";

	}
	
	public Personajuridica getPersonajuridicaProsesed() {
		Personajuridica personajuridica = getoPersonajuridica();
		List<Accionista> accionistas = tablaAccionistas.getRows();
		personajuridica.setListAccionista(accionistas);

		for (Iterator<Accionista> iterator = accionistas.iterator(); iterator.hasNext();) {
			Accionista accionista = iterator.next();
			AccionistaPK accionistaPK = new AccionistaPK();
			accionistaPK.setDni(accionista.getPersonanatural().getDni());
			accionistaPK.setRuc(personajuridica.getRuc());
			accionista.setId(accionistaPK);
			accionista.setEstado(true);
		}
		return personajuridica;
	}
	
	public Personajuridica getoPersonajuridica() {
		return oPersonajuridica;
	}

	public void setoPersonajuridica(Personajuridica oPersonajuridica) {
		this.oPersonajuridica = oPersonajuridica;
	}

	public void changeEditingState() {
		this.isEditing = !isEditing;
	}
	
	public void changeEditingPersonanaturalState() {
		this.isEditingPersonanatural = !isEditingPersonanatural;
	}
	
	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public boolean isEditingPersonanatural() {
		return isEditingPersonanatural;
	}

	public void setEditingPersonanatural(boolean isEditingPersonanatural) {
		this.isEditingPersonanatural = isEditingPersonanatural;
	}

	public ComboBean<Tipoempresa> getComboTipoempresa() {
		return comboTipoempresa;
	}

	public void setComboTipoempresa(ComboBean<Tipoempresa> comboTipoempresa) {
		this.comboTipoempresa = comboTipoempresa;
	}

	public ComboBean<Sexo> getComboSexo() {
		return comboSexo;
	}

	public void setComboSexo(ComboBean<Sexo> comboSexo) {
		this.comboSexo = comboSexo;
	}

	public ComboBean<Sexo> getComboSexoAccionista() {
		return comboSexoAccionista;
	}

	public void setComboSexoAccionista(ComboBean<Sexo> comboSexoAccionista) {
		this.comboSexoAccionista = comboSexoAccionista;
	}

	public ComboBean<Estadocivil> getComboEstadocivil() {
		return comboEstadocivil;
	}

	public void setComboEstadocivil(ComboBean<Estadocivil> comboEstadocivil) {
		this.comboEstadocivil = comboEstadocivil;
	}

	public TablaBean<Accionista> getTablaAccionistas() {
		return tablaAccionistas;
	}

	public void setTablaAccionistas(TablaBean<Accionista> tablaAccionistas) {
		this.tablaAccionistas = tablaAccionistas;
	}		

}
