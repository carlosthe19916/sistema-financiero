package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Sexo;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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
