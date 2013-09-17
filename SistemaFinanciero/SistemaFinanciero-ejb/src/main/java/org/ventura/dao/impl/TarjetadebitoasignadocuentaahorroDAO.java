package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.Tarjetadebitoasignadocuentaahorro;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TarjetadebitoasignadocuentaahorroDAO extends AbstractDAO<Tarjetadebitoasignadocuentaahorro>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TarjetadebitoasignadocuentaahorroDAO() {
        super(Tarjetadebitoasignadocuentaahorro.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
