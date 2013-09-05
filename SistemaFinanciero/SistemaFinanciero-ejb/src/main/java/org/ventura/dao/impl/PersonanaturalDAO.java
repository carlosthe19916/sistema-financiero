package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Personanatural;

@Stateless
public class PersonanaturalDAO extends AbstractDAO<Personanatural>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public PersonanaturalDAO() {
        super(Personanatural.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
