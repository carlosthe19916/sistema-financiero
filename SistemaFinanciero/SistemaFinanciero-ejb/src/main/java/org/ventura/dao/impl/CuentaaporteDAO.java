package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Cuentaaporte;
import org.ventura.util.logger.Log;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CuentaaporteDAO extends AbstractDAO<Cuentaaporte>{

	@Inject
	private Log log;
	
	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public CuentaaporteDAO() {
        super(Cuentaaporte.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

	@Override
	protected Log getLogger() {
		return log;
	}

}
