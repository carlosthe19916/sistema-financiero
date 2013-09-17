package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Tipotarjetadebito;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipotarjetadebitoDAO extends AbstractDAO<Tipotarjetadebito>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TipotarjetadebitoDAO() {
        super(Tipotarjetadebito.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
