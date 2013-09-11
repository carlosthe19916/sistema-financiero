package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Cheque;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ChequeDAO extends AbstractDAO<Cheque>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public ChequeDAO() {
        super(Cheque.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
