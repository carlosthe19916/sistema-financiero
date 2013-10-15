package org.ventura.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ventura.util.exception.IllegalEntityException;
import org.ventura.util.exception.NonexistentEntityException;
import org.ventura.util.exception.PreexistingEntityException;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

public abstract class AbstractDAO<T> {

	private Class<T> entityClass;

	public AbstractDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	protected abstract Log getLogger();

	public void create(T entity) throws PreexistingEntityException, IllegalEntityException, RollbackFailureException, Exception {
		try {
			getEntityManager().persist(entity);
		} catch (EntityExistsException exception) {
			throw new PreexistingEntityException("Entity: " + entity + " already exists", exception);
		} catch (IllegalArgumentException  exception) {
			throw new IllegalEntityException("Entity: " + entity + " illegal entity", exception);
		} catch (TransactionRequiredException  exception) {
			throw exception;
		} catch (Exception exception) {
			throw new RollbackFailureException("Entity: " + entity + " was Rolled Back: create exception", exception);
		}
	}

	public void delete(T entity) throws IllegalEntityException, TransactionRequiredException, RollbackFailureException, Exception{
		try {
			getEntityManager().remove(getEntityManager().merge(entity));
		} catch (IllegalArgumentException  exception) {
			throw new IllegalEntityException("Entity: " + entity + " illegal entity", exception);
		} catch (TransactionRequiredException  exception) {
			throw exception;
		} catch (Exception exception) {
			throw new RollbackFailureException("Entity: " + entity + " was Rolled Back: delete exception", exception);
		}
	}

	public T find(Object id) throws IllegalEntityException, NonexistentEntityException, Exception {
		T t = null;
		try {
			t = getEntityManager().find(entityClass, id);
		} catch (IllegalArgumentException  exception) {
			throw new IllegalEntityException("Entity with Id: " + id + " is not a valid Key", exception);
		} catch (Exception exception) {
			throw new NonexistentEntityException("Entity with Id: " + id + " was not found", exception);
		}
		return t;
	}

	public T update(T entity) throws RollbackFailureException, Exception {
		T t = null;
		try {
			t = getEntityManager().merge(entity);
		} catch (IllegalArgumentException  exception) {
			throw new IllegalEntityException("Entity: " + entity + " illegal entity", exception);
		} catch (TransactionRequiredException  exception) {
			throw exception;
		} catch (Exception exception) {
			throw new RollbackFailureException("Entity: " + entity + " was Rolled Back: update exception", exception);
		}
		return t;
	}

	public List<T> findAll() throws RollbackFailureException, Exception {
		List<T> list = null;
		try {
			CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
			cq.select(cq.from(entityClass));
			list = getEntityManager().createQuery(cq).getResultList();
		} catch (IllegalArgumentException  exception) {
			throw new IllegalEntityException("Entity with class: " + getClass() + " is not a valid class", exception);
		}  catch (Exception exception) {
			throw new NonexistentEntityException("Entity with class: " + getClass() + " was not found", exception);
		}

		return list;

	}

	public List<T> findRange(int[] range) throws RollbackFailureException, Exception {
		List<T> list = null;
		try {
			javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
			cq.select(cq.from(entityClass));
			javax.persistence.Query q = getEntityManager().createQuery(cq);
			q.setMaxResults(range[1] - range[0]);
			q.setFirstResult(range[0]);
			list = q.getResultList();
		} catch (IllegalArgumentException  exception) {
			throw new IllegalEntityException("Entity with class: " + getClass() + " is not a valid class", exception);
		}  catch (Exception exception) {
			throw new NonexistentEntityException("Entity with class: " + getClass() + " was not found", exception);
		}

		return list;

	}

	public List<T> findByNamedQuery(String namedQueryName) throws RollbackFailureException, Exception {
		List<T> list = null;
		try {
			list = getEntityManager().createNamedQuery(namedQueryName).getResultList();
		} catch (Exception exception) {
			throw new RollbackFailureException("Entity: " + " rollback", exception);
		}
		return list;
	}

	public List<T> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters) throws RollbackFailureException {
		List<T> list = null;
		try {
			list = findByNamedQuery(namedQueryName, parameters, 0);
		} catch (Exception exception) {
			throw new RollbackFailureException("Entity: " + " rollback", exception);
		}
		return list;

	}

	public List<T> findByNamedQuery(String queryName, int resultLimit) throws RollbackFailureException, Exception {
		List<T> list = null;
		try {
			list = getEntityManager().createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
		} catch (Exception exception) {
			throw new RollbackFailureException("Entity: " + " rollback", exception);
		}
		return list;
	}

	public List<T> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws RollbackFailureException, Exception {

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

		} catch (Exception exception) {
			throw new RollbackFailureException("Entity: " + " rollback", exception);
		}

		return list;

	}

	public int count() throws RollbackFailureException {
		int count = 0;
		try {

			CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
			Root<T> rt = cq.from(entityClass);
			cq.select(getEntityManager().getCriteriaBuilder().count(rt));
			javax.persistence.Query q = getEntityManager().createQuery(cq);
			count = ((Long) q.getSingleResult()).intValue();

		} catch (Exception exception) {
			throw new RollbackFailureException("Entity: " + " rollback", exception);
		}

		return count;

	}

}