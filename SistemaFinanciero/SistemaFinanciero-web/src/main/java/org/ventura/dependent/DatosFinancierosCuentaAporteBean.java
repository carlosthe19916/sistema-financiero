package org.ventura.dependent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.CuentaaporteServiceLocal;
import org.ventura.entity.Cuentaaporte;
import org.ventura.entity.Estadocuenta;
import org.ventura.entity.Tipomoneda;

@Named
@Dependent
public class DatosFinancierosCuentaAporteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	CuentaaporteServiceLocal cuentaaporteServiceLocal;

	@Inject
	private Cuentaaporte cuentaaporte;

	public DatosFinancierosCuentaAporteBean() {	
	}

	@PostConstruct
	private void initValues() {	
		Estadocuenta estadocuenta = new Estadocuenta();
		estadocuenta.setIdestadocuenta(1);
		estadocuenta.setDenominacion("ACTIVO");
		estadocuenta.setEstado(true);
		this.cuentaaporte.setEstadocuenta(estadocuenta);
		
		Tipomoneda tipomoneda = new Tipomoneda();
		tipomoneda.setIdtipomoneda(1);
		tipomoneda.setEstado(true);
		tipomoneda.setDenominacion("NUEVOS SOLES");
		this.cuentaaporte.setTipomoneda(tipomoneda);
	}
	
	public boolean isValid(){
		return cuentaaporte.getIdtipomoneda() != null ? true : false;
	}

	public Cuentaaporte getCuentaaporte() {
		return cuentaaporte;
	}

	public void setCuentaaporte(Cuentaaporte cuentaaporte) {
		this.cuentaaporte = cuentaaporte;
	}

	public String fechaActual(){
		java.util.Date date = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		return formateador.format(date);
	}

}