package xyz.codingmentor.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.Actor;

@Stateless
public class ActorFacade {

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;

    public ActorFacade() {
    }

    public Actor findSeriesById(Long id) {
        TypedQuery<Actor> act = em.createQuery("SELECT a FROM Actor a WHERE a.id = :id", Actor.class);
        act.setParameter("id", id);
        return act.getSingleResult();
    }

    public List<Actor> findAllActors() {
        TypedQuery<Actor> act = em.createQuery("SELECT a FROM Actor a", Actor.class);
        return act.getResultList();
    }
}
