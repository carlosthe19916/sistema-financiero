package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Estadocuenta;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EstadocuentaDAO extends AbstractDAO<Estadocuenta>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public EstadocuentaDAO() {
        super(Estadocuenta.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
