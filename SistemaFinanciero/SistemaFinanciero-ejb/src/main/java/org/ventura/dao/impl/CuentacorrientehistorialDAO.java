package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Cuentacorrientehistorial;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CuentacorrientehistorialDAO extends AbstractDAO<Cuentacorrientehistorial>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public CuentacorrientehistorialDAO() {
        super(Cuentacorrientehistorial.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
