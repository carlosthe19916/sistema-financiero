package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Chequera;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ChequeraDAO extends AbstractDAO<Chequera>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public ChequeraDAO() {
        super(Chequera.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
