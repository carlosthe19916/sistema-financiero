package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Chequeraestado;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ChequeraestadoDAO extends AbstractDAO<Chequeraestado>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public ChequeraestadoDAO() {
        super(Chequeraestado.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
