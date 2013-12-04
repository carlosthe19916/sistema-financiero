package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.util.logger.Log;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CajaDAO extends AbstractDAO<Caja>{
	
	@Inject
	Log log;

	@PersistenceContext(unitName = "SistemaFinancieroPU")
	private EntityManager em;
	
	public CajaDAO() {
		super(Caja.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	protected Log getLogger() {
		// TODO Auto-generated method stub
		return log;
	}

}
