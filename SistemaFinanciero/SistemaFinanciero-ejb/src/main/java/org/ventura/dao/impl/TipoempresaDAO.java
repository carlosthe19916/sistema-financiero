package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Tipoempresa;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TipoempresaDAO extends AbstractDAO<Tipoempresa>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TipoempresaDAO() {
        super(Tipoempresa.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
