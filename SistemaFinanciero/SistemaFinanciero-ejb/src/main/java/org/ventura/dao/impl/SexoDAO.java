package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Sexo;

@Stateless
public class SexoDAO extends AbstractDAO<Sexo>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public SexoDAO() {
        super(Sexo.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
