package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Tipomoneda;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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
