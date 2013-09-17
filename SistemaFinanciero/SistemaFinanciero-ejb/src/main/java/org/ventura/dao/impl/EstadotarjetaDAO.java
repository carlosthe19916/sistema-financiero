package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Estadotarjeta;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EstadotarjetaDAO extends AbstractDAO<Estadotarjeta>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public EstadotarjetaDAO() {
        super(Estadotarjeta.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
