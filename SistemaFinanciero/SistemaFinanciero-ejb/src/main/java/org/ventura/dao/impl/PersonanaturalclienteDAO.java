package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Personanaturalcliente;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PersonanaturalclienteDAO extends AbstractDAO<Personanaturalcliente>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public PersonanaturalclienteDAO() {
        super(Personanaturalcliente.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
