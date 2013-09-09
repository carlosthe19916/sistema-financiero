package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Cuentaplazofijo;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CuentaplazofijoDAO extends AbstractDAO<Cuentaplazofijo>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public CuentaplazofijoDAO() {
        super(Cuentaplazofijo.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
