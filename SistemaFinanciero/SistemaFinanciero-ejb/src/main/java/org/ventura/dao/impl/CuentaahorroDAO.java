package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Cuentaahorro;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CuentaahorroDAO extends AbstractDAO<Cuentaahorro>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public CuentaahorroDAO() {
        super(Cuentaahorro.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
