/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ventura.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import sun.rmi.runtime.Log;

/**
 * 
 * @author Carlos-PC
 */
public abstract class AbstractDAO<T> {

	private Class<T> entityClass;

	public AbstractDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public void create(T entity) {
		try {
			getEntityManager().persist(entity);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}
	}

	public void delete(T entity) {
		try {
			getEntityManager().remove(getEntityManager().merge(entity));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}
	}

	public T find(Object id) {
		T t = null;
		try {
			t = getEntityManager().find(entityClass, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return t;
	}

	public T update(T entity) {
		T t = null;
		try {
			t = getEntityManager().merge(entity);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return t;
	}

	public List<T> findAll() {
		List<T> list = null;
		try {
			CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
					.createQuery();
			cq.select(cq.from(entityClass));
			list = getEntityManager().createQuery(cq).getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return list;

	}

	public List<T> findRange(int[] range) {
		List<T> list = null;
		try {
			javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
					.getCriteriaBuilder().createQuery();
			cq.select(cq.from(entityClass));
			javax.persistence.Query q = getEntityManager().createQuery(cq);
			q.setMaxResults(range[1] - range[0]);
			q.setFirstResult(range[0]);
			list = q.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return list;

	}

	public List<T> findByNamedQuery(String namedQueryName) {
		List<T> list = null;
		try {
			list = getEntityManager().createNamedQuery(namedQueryName)
					.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return list;

	}

	public List<T> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters) {
		List<T> list = null;
		try {
			list = findByNamedQuery(namedQueryName, parameters, 0);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return list;

	}

	public List<T> findByNamedQuery(String queryName, int resultLimit) {
		List<T> list = null;
		try {
			list = getEntityManager().createNamedQuery(queryName)
					.setMaxResults(resultLimit).getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return list;

	}

	public List<T> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {

		List<T> list = null;
		try {
			Set<Entry<String, Object>> rawParameters = parameters.entrySet();
			Query query = getEntityManager().createNamedQuery(namedQueryName);

			if (resultLimit > 0)
				query.setMaxResults(resultLimit);

			for (Entry<String, Object> entry : rawParameters) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			list = query.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return list;

	}

	public int count() {
		int count = 0;
		try {

			CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
					.createQuery();
			Root<T> rt = cq.from(entityClass);
			cq.select(getEntityManager().getCriteriaBuilder().count(rt));
			javax.persistence.Query q = getEntityManager().createQuery(cq);
			count = ((Long) q.getSingleResult()).intValue();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// getEntityManager().close();
		}

		return count;

	}

}