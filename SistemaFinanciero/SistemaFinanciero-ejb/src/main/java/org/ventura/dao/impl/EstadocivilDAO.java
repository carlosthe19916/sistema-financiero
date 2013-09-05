package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Estadocivil;

@Stateless
public class EstadocivilDAO extends AbstractDAO<Estadocivil>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public EstadocivilDAO() {
        super(Estadocivil.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
