package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Cuentaahorrohistorial;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CuentaahorrohistorialDAO extends AbstractDAO<Cuentaahorrohistorial>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public CuentaahorrohistorialDAO() {
        super(Cuentaahorrohistorial.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
