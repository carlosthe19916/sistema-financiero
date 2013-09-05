package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Cuentaahorro;

@Stateless
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
