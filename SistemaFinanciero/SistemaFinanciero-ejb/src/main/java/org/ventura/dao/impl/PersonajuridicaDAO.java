package org.ventura.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ventura.dao.AbstractDAO;
import org.ventura.model.Personajuridica;

@Stateless
public class PersonajuridicaDAO extends AbstractDAO<Personajuridica>{

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

	public PersonajuridicaDAO() {
        super(Personajuridica.class);
    }
	
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
