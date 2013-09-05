package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Tarjetadebito;

@Stateless
public class TarjetadebitoDAO extends AbstractDAO<Tarjetadebito>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TarjetadebitoDAO() {
        super(Tarjetadebito.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
