package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Tipoempresa;

@Stateless
public class TipoempresaDAO extends AbstractDAO<Tipoempresa>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TipoempresaDAO() {
        super(Tipoempresa.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
