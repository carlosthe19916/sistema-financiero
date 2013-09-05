package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Accionista;

@Stateless
public class AccionistaDAO extends AbstractDAO<Accionista>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public AccionistaDAO() {
        super(Accionista.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
