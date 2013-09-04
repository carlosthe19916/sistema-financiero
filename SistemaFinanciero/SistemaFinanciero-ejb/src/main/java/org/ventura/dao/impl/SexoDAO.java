package org.ventura.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ventura.facade.FacadeDAO;
import org.ventura.model.Sexo;

public class SexoDAO implements FacadeDAO<Integer, Sexo> {

	@PersistenceContext(unitName = "SistemaFinancieroPU")
    private EntityManager em;

    public Sexo create(Sexo t) {
        this.em.persist(t);
        return t;
    }

    public void delete(Sexo t) {
        t = this.em.merge(t);
        this.em.remove(t);
    }

    public Sexo find(Integer id) {
        return this.em.find(Sexo.class, id);
    }

    public Sexo update(Sexo t) {
        return this.em.merge(t);
    }

    public List<Sexo> findByNamedQuery(String namedQueryName){
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }
    
    public List<Sexo> findByNamedQuery(String namedQueryName, Map<String,Object> parameters){
        return findByNamedQuery(namedQueryName, parameters, 0);
    }

    public List<Sexo> findByNamedQuery(String queryName, int resultLimit) {
        return this.em.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();    
    }
    
   public List<Sexo> findByNamedQuery(String namedQueryName, Map<String,Object> parameters,int resultLimit){
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = this.em.createNamedQuery(namedQueryName);
        if(resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

}
