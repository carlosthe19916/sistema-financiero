package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Retirointere;

@Stateless
public class RetirointereDAO extends AbstractDAO<Retirointere>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public RetirointereDAO() {
        super(Retirointere.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
