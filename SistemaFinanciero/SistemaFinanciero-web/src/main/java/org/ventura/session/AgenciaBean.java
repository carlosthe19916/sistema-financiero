package org.ventura.session;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.entity.schema.sucursal.Agencia;
import org.ventura.entity.schema.sucursal.Sucursal;

@Named
@SessionScoped
public class AgenciaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Agencia agencia;

	@PostConstruct
	private void init() {
		agencia.setDenominacion("AYACUCHO");
		agencia.setCodigoagencia("001");
		agencia.setIdagencia(1);
		
		Sucursal sucursal = new Sucursal();
		sucursal.setIdsucursal(1);
		
		agencia.setSucursal(sucursal);
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	

}
