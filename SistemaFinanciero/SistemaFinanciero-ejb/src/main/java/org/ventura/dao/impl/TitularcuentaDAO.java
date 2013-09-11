package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Titularcuenta;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TitularcuentaDAO extends AbstractDAO<Titularcuenta>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TitularcuentaDAO() {
        super(Titularcuenta.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
