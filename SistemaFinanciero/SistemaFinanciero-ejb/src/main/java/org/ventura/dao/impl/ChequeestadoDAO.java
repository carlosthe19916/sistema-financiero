package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Chequeestado;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ChequeestadoDAO extends AbstractDAO<Chequeestado>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public ChequeestadoDAO() {
        super(Chequeestado.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
