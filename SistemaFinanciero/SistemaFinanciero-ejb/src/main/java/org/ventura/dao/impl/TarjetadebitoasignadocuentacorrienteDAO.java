package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Tarjetadebitoasignadocuentacorriente;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TarjetadebitoasignadocuentacorrienteDAO extends AbstractDAO<Tarjetadebitoasignadocuentacorriente>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public TarjetadebitoasignadocuentacorrienteDAO() {
        super(Tarjetadebitoasignadocuentacorriente.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
