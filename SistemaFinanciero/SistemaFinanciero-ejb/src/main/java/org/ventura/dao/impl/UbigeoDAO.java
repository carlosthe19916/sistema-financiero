package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Ubigeo;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class UbigeoDAO extends AbstractDAO<Ubigeo>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public UbigeoDAO() {
        super(Ubigeo.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
