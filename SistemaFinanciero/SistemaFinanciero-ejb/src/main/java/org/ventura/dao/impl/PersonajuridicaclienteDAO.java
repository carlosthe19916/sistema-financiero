package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Personajuridicacliente;

@Stateless
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
