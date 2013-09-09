package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Personajuridicacliente;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PersonajuridicaclienteDAO extends AbstractDAO<Personajuridicacliente>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public PersonajuridicaclienteDAO() {
        super(Personajuridicacliente.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
