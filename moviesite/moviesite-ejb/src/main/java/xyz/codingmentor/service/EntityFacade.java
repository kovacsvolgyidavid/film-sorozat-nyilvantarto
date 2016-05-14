package xyz.codingmentor.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class EntityFacade<T> {

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;

    public EntityFacade() {
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public T read(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public T delete(T entity) {
        em.remove(entity);
        return entity;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public List<T> findAll(Class<T> entityClass) {
//        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
//        return em.createQuery(cq).getResultList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList();
    }
}
