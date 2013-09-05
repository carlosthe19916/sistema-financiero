package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Tipotarjetadebito;

@Stateless
public class TipotarjetadebitoDAO extends AbstractDAO<Tipotarjetadebito>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TipotarjetadebitoDAO() {
        super(Tipotarjetadebito.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
