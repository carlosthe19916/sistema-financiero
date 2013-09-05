package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Tipomoneda;

@Stateless
public class TipomonedaDAO extends AbstractDAO<Tipomoneda>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TipomonedaDAO() {
        super(Tipomoneda.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
