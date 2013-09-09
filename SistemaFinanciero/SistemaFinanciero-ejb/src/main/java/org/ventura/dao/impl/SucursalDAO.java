package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Sucursal;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SucursalDAO extends AbstractDAO<Sucursal>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public SucursalDAO() {
        super(Sucursal.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
