package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.entity.schema.caja.Detalletransaccioncaja;
import org.ventura.util.logger.Log;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class DetalletransaccioncajaDAO extends
		AbstractDAO<Detalletransaccioncaja> {

	@Inject
	Log log;

	@PersistenceContext(unitName = "SistemaFinancieroPU")
	private EntityManager em;

	public DetalletransaccioncajaDAO() {
		super(Detalletransaccioncaja.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Log getLogger() {
		return log;
	}

}
