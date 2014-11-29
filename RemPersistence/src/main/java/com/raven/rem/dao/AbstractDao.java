/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 *
 * @author Mehmet Adem Şengül
 * @param <T>
 */
public abstract class AbstractDao<T> {

	private static final Integer DEFAULT_FIRST_RESULT_INDEX = 0;
	private Class<T> entityClass;

	public AbstractDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public void create(T entity) {
		getEntityManager().persist(entity);
	}

	public T createAndReturn(T entity) {
		getEntityManager().persist(entity);
		getEntityManager().flush();
		return entity;
	}

	public T updateAndReturn(T entity) {
		getEntityManager().merge(entity);
		getEntityManager().flush();
		return entity;
	}

	public void update(T entity) {
		getEntityManager().merge(entity);
	}

	public T saveOrUpdate(T entity) {
		entity = getEntityManager().merge(entity);
		return entity;
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public void flush() {
		getEntityManager().flush();
	}

	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public void detach(T entity) {
		getEntityManager().detach(entity);
	}

	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	public List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0] + 1);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public List<T> findRangeByNamedQuery(int[] range, String namedQuery) {
		Query q = getEntityManager().createNamedQuery(namedQuery);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public List<T> findRangeByNamedQuery(int[] range, String namedQuery, Map parameters) {
		Query q = getEntityManager().createNamedQuery(namedQuery);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public List<T> findRangeByQuery(int[] range, String query) {
		Query q = getEntityManager().createQuery(query);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public List<T> findRangeByQuery(int[] range, String query, Map parameters) {
		Query q = getEntityManager().createQuery(query);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public T findByNamedQuery(String namedQuery, LockModeType lockModeType) throws Exception {
		Query q = getEntityManager().createNamedQuery(namedQuery);
		if (lockModeType != null) {
			q.setLockMode(lockModeType);
		}
		List<T> tempList = (List<T>) q.getResultList();
		if (tempList.isEmpty()) {
			return null;
		}
		return tempList.get(0);
	}

	public List<T> findListByNamedQuery(String namedQuery) throws Exception {
		Query q = getEntityManager().createNamedQuery(namedQuery);
		return q.getResultList();
	}

	public T findByNamedQuery(String namedQuery, Map parameters, LockModeType lockModeType) throws Exception {
		Query q = getEntityManager().createNamedQuery(namedQuery);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		if (lockModeType != null) {
			q.setLockMode(lockModeType);
		}
		List<T> tempList = (List<T>) q.getResultList();
		if (tempList.isEmpty()) {
			return null;
		}
		return tempList.get(0);
	}

	public List<T> findListByNamedQuery(String namedQuery, Map parameters) throws Exception {
		Query q = getEntityManager().createNamedQuery(namedQuery);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		return q.getResultList();
	}

	public T findByQuery(String query, LockModeType lockModeType) throws Exception {
		Query q = getEntityManager().createQuery(query);
		if (lockModeType != null) {
			q.setLockMode(lockModeType);
		}
		List<T> tempList = (List<T>) q.getResultList();
		if (tempList.isEmpty()) {
			return null;
		}
		return tempList.get(0);
	}

	public T findByQuery(String query, Map parameters, LockModeType lockModeType) throws Exception {
		Query q = getEntityManager().createQuery(query);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		if (lockModeType != null) {
			q.setLockMode(lockModeType);
		}
		List<T> tempList = (List<T>) q.getResultList();
		if (tempList.isEmpty()) {
			return null;
		}
		return tempList.get(0);
	}

	public Object findValueByQuery(String query, LockModeType lockModeType) throws Exception {
		Query q = getEntityManager().createQuery(query);
		if (lockModeType != null) {
			q.setLockMode(lockModeType);
		}

		List tempList = q.getResultList();
		if (tempList.isEmpty()) {
			return null;
		}
		return tempList.get(0);
	}

	public Object findValueByQuery(String query, Map parameters, LockModeType lockModeType) throws Exception {
		Query q = getEntityManager().createQuery(query);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		if (lockModeType != null) {
			q.setLockMode(lockModeType);
		}
		List tempList = q.getResultList();
		if (tempList.isEmpty()) {
			return null;
		}
		return tempList.get(0);
	}

	public List<T> findListByQuery(String query) throws Exception {
		Query q = getEntityManager().createQuery(query);
		return q.getResultList();
	}

	public List<T> findListByQuery(String query, Map parameters) throws Exception {
		Query q = getEntityManager().createQuery(query);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		return q.getResultList();
	}

	public List findListByNativeQuery(String query) throws Exception {
		Query q = getEntityManager().createNativeQuery(query);
		return q.getResultList();
	}

	public List findListByNativeQuery(String query, Map parameters) throws Exception {
		Query q = getEntityManager().createNativeQuery(query);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		return q.getResultList();
	}

	public Integer executeNativeQuery(String sql, Map parameters) throws Exception {
		Query query = getEntityManager().createNativeQuery(sql);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			query.setParameter(Integer.parseInt(entry.getKey().toString()), entry.getValue());
		}
		return query.executeUpdate();
	}

	public Object findByNativeQuery(String query, LockModeType lockModeType) throws Exception {
		Query q = getEntityManager().createNativeQuery(query);
		if (lockModeType != null) {
			q.setLockMode(lockModeType);
		}
		List tempList = q.getResultList();
		if (tempList.isEmpty()) {
			return null;
		}
		return tempList.get(0);
	}

	public Object findByNativeQuery(String query, Map parameters, LockModeType lockModeType) throws Exception {
		Query q = getEntityManager().createNativeQuery(query);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		if (lockModeType != null) {
			q.setLockMode(lockModeType);
		}
		List tempList = q.getResultList();
		if (tempList.isEmpty()) {
			return null;
		}
		return tempList.get(0);
	}

	public List findRangeByNativeQuery(int[] range, String query) throws Exception {
		Query q = getEntityManager().createNativeQuery(query);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public List findRangeByNativeQuery(int[] range, String query, Map parameters) throws Exception {
		Query q = getEntityManager().createNativeQuery(query);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public int count() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	public int countByNamedQuery(String namedQuery, Map parameters) throws Exception {
		Query q = getEntityManager().createNamedQuery(namedQuery);
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			q.setParameter(entry.getKey().toString(), entry.getValue());
		}

		return q.getResultList().size();
	}

}
