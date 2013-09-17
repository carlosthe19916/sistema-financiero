package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Retirointere;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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
