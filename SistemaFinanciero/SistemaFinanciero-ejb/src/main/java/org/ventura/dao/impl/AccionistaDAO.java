package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Accionista;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccionistaDAO extends AbstractDAO<Accionista>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public AccionistaDAO() {
        super(Accionista.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
