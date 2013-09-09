package org.venturabank.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.ventura.facade.EstadocivilFacadeLocal;
import org.ventura.facade.PersonanaturalFacadeLocal;
import org.ventura.facade.SexoFacadeLocal;
import org.ventura.model.Estadocivil;
import org.ventura.model.Personanatural;
import org.ventura.model.Sexo;

@ManagedBean
@NoneScoped
public class PersonaNaturalMB implements Serializable {

	@EJB
	SexoFacadeLocal sexoFacadeLocal;
	@EJB
	private EstadocivilFacadeLocal estadoCivilFacadeLoal;
	@EJB
	PersonanaturalFacadeLocal personaNauralFacadeLocal;

	
	private static final long serialVersionUID = 1L;

	private Personanatural oPersonaNatural;
		
	private Sexo oSexoSeleccionado;
	private List<Sexo> listaSexos;
	
	private Estadocivil oEstadoCivilSeleccionado;
	private List<Estadocivil> listaEstadoCivil;


	// constructor
	public PersonaNaturalMB() {
		this.oPersonaNatural = new Personanatural();
		setoSexoSeleccionado(new Sexo());
		setoEstadoCivilSeleccionado(new Estadocivil());
	}
	
	
	public void metodoPrueba(){
		System.out.println("Holaaaaaaaaaaaaaaa");
		System.out.println(oPersonaNatural.getDni());
		System.out.println(oPersonaNatural.getApellidopaterno());
		System.out.println(oPersonaNatural.getApellidomaterno());
		System.out.println(oPersonaNatural.getNombres());
		System.out.println(oPersonaNatural.getFechanacimiento());
		System.out.println(oPersonaNatural.getIdsexo());
		
		personaNauralFacadeLocal.create(oPersonaNatural);
		
	}
	
	
	public void initListSexo(){
		setListaSexos((List<Sexo>) sexoFacadeLocal.findAll());
	}
	
	public void initListaEstadoCivil(){
		setListaEstadoCivil((List<Estadocivil>) estadoCivilFacadeLoal.findAll());
	}
	

	public Personanatural getPersonanatural() {
		return oPersonaNatural;
	}

	public Sexo getoSexoSeleccionado() {
		return oSexoSeleccionado;
	}


	public void setoSexoSeleccionado(Sexo oSexoSeleccionado) {
		this.oSexoSeleccionado = oSexoSeleccionado;
	}


	public void setPersonanatural(Personanatural personanatural) {
		this.oPersonaNatural = personanatural;
	}


	public Estadocivil getoEstadoCivilSeleccionado() {
		return oEstadoCivilSeleccionado;
	}


	public void setoEstadoCivilSeleccionado(Estadocivil oEstadoCivilSeleccionado) {
		this.oEstadoCivilSeleccionado = oEstadoCivilSeleccionado;
	}


	public List<Sexo> getListaSexos() {
		initListSexo();
		return listaSexos;
	}


	public void setListaSexos(List<Sexo> listaSexos) {
		this.listaSexos = listaSexos;
	}


	public List<Estadocivil> getListaEstadoCivil() {
		initListaEstadoCivil();
		return listaEstadoCivil;
	}


	public void setListaEstadoCivil(List<Estadocivil> listaEstadoCivil) {
		this.listaEstadoCivil = listaEstadoCivil;
	}

}
