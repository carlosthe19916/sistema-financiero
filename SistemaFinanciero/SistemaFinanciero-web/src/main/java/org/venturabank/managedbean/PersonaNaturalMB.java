package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.ventura.facade.SexoFacadeLocal;
import org.ventura.model.Sexo;

@ManagedBean
@SessionScoped
public class PersonaNaturalMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	SexoFacadeLocal sexoFacadeLocal;
	


}
