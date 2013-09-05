package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Estadotarjeta;

@Stateless
public class EstadotarjetaDAO extends AbstractDAO<Estadotarjeta>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public EstadotarjetaDAO() {
        super(Estadotarjeta.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
