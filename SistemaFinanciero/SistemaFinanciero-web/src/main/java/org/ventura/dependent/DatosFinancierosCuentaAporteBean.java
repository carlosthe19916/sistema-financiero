package org.ventura.dependent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.GeneratedEstadocuenta;
import org.ventura.entity.GeneratedTipomoneda;
import org.ventura.entity.GeneratedEstadocuenta.EstadocuentaType;
import org.ventura.entity.GeneratedTipomoneda.TipomonedaType;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Estadocuenta;
import org.ventura.entity.schema.maestro.Tipomoneda;

@Named
@Dependent
public class DatosFinancierosCuentaAporteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Cuentaaporte cuentaaporte;
	
	@Inject
	@GeneratedEstadocuenta(strategy = EstadocuentaType.ACTIVO)
	private Estadocuenta estadocuenta;

	@Inject
	@GeneratedTipomoneda(strategy = TipomonedaType.NUEVO_SOL)
	private Tipomoneda tipomoneda;
	
	public DatosFinancierosCuentaAporteBean() {	
	}

	@PostConstruct
	private void initValues() {	
		this.cuentaaporte.setEstadocuenta(estadocuenta);
		this.cuentaaporte.setTipomoneda(tipomoneda);
		this.cuentaaporte.setFechaapertura(Calendar.getInstance().getTime());
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