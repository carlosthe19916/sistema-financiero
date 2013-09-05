package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Cuentacorriente;

@Stateless
public class CuentacorrienteDAO extends AbstractDAO<Cuentacorriente>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public CuentacorrienteDAO() {
        super(Cuentacorriente.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
