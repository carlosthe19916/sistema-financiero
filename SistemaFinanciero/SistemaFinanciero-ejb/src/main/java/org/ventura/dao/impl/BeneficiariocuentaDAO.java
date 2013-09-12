package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Beneficiariocuenta;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class BeneficiariocuentaDAO extends AbstractDAO<Beneficiariocuenta>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public BeneficiariocuentaDAO() {
        super(Beneficiariocuenta.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
