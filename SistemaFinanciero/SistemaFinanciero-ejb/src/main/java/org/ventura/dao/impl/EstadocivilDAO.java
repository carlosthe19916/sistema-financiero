package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Estadocivil;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EstadocivilDAO extends AbstractDAO<Estadocivil>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public EstadocivilDAO() {
        super(Estadocivil.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
