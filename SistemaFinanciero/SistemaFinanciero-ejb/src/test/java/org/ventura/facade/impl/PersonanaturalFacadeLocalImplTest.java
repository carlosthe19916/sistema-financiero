package org.ventura.facade.impl;

import javax.ejb.EJB;

import org.testng.annotations.Test;
import org.ventura.dao.impl.PersonanaturalDAO;
import org.ventura.model.Personanatural;

public class PersonanaturalFacadeLocalImplTest {
	
	@EJB
	PersonanaturalDAO dao;

	@Test
	public void create() {
		Personanatural personanatural = new Personanatural();
		personanatural.setApellidopaterno("feria");
		personanatural.setApellidomaterno("vila");
		personanatural.setNombres("carlos esteban");
		personanatural.setDni("4677954");

		//PersonanaturalDAO dao = new PersonanaturalDAO();
		System.out.println(dao);
		
		dao.create(personanatural);

		System.out.println("correcto");
	}

}
