package org.venturabank.managedbean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.event.ValueChangeEvent;

import org.ventura.facade.CuentaahorrohistorialFacadeLocal;
import org.ventura.model.Cuentaahorro;
import org.ventura.model.Cuentaahorrohistorial;
import org.ventura.model.Tipoempresa;
import org.ventura.model.Tipomoneda;
import org.venturabank.util.ComboMB;


@NoneScoped
@ManagedBean

public class DatosFinancierosCuentaAhorroMB {
	
	@ManagedProperty(value = "#{comboMB}")
	private ComboMB<Tipomoneda> comboTipomoneda;
	
	private Cuentaahorrohistorial oCuentaahorrohistorial;
	private Cuentaahorro ocuentaahorro;
	@EJB
	CuentaahorrohistorialFacadeLocal cuentaaahorrohistorialFacadeLocal;
	
	
	public DatosFinancierosCuentaAhorroMB() {
		oCuentaahorrohistorial=new Cuentaahorrohistorial();
	}
	
	@PostConstruct
	private void initValues() {
		//comboTipomoneda.initValuesFromNamedQueryName(Tipomoneda.ALL_ACTIVE);
	}
	
	public void changeTipomoneda(ValueChangeEvent event) {
		Integer key = (Integer) event.getNewValue();
		
	}
	
	public void insertarPersonaJuridica(){
		
	}

	public ComboMB<Tipomoneda> getComboTipomoneda() {
		return comboTipomoneda;
	}

	public void setComboTipomoneda(ComboMB<Tipomoneda> comboTipomoneda) {
		this.comboTipomoneda = comboTipomoneda;
	}

	public Cuentaahorrohistorial getoCuentaahorrohistorial() {
		return oCuentaahorrohistorial;
	}

	public void setoCuentaahorrohistorial(Cuentaahorrohistorial oCuentaahorrohistorial) {
		this.oCuentaahorrohistorial = oCuentaahorrohistorial;
	}

}
