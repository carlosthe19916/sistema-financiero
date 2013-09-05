package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Frecuenciacapitalizacion;

@Stateless
public class FrecuenciacapitalizacionDAO extends AbstractDAO<Frecuenciacapitalizacion>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public FrecuenciacapitalizacionDAO() {
        super(Frecuenciacapitalizacion.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
