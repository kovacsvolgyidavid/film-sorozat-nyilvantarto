package xyz.codingmentor.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class EntityFacade {

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;

    public EntityFacade() {
    }

    public <T> void create(T entity) {
        em.persist(entity);
    }

    public <T> T read(Class<T> clazz, Object id) {
        return em.find(clazz, id);
    }

    public <T> T update(T entity) {
        return em.merge(entity);
    }

    public <T> T delete(T entity) {
        em.remove(entity);
        return entity;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public <T> List<T> findAll(Class<T> entityClass) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }
}
