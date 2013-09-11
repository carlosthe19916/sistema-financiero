package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Titularcuentahistorial;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TitularcuentahistorialDAO extends AbstractDAO<Titularcuentahistorial>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TitularcuentahistorialDAO() {
        super(Titularcuentahistorial.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
