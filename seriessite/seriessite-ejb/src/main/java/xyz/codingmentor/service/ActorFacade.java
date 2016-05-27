package xyz.codingmentor.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Series;

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

    public Actor findActorById(Long actorId) {
        TypedQuery<Actor> actor = em.createNamedQuery("Actor.findActorById", Actor.class);
        actor.setParameter("id", actorId);
        return actor.getSingleResult();
    }

    public List<Series> findSeriesByActorId(Long actorId) {
        TypedQuery<Series> actor = em.createNamedQuery("Actor.findSeriesByActorId", Series.class);
        actor.setParameter("id", actorId);
        return actor.getResultList();
    }

    public void deleteSeriesFromActor(Long seriesId, Long actorId) {
        Series series = em.find(Series.class, seriesId);
        Actor actor = em.find(Actor.class, actorId);
        actor.getMovies().remove(series);
    }

    public void addSeriesToActor(Long seriesId, Long actorId) {
        Series series = em.find(Series.class, seriesId);
        Actor actor = em.find(Actor.class, actorId);
        actor.getMovies().add(series);
    }

    public List<Series> findSeriesInWichActorDontPlay(Long actorId) {

        TypedQuery<Series> findAllSeries = em.createNamedQuery("Series.findAll", Series.class);
        List<Series> seriesAll = findAllSeries.getResultList();

        List<Series> series = findSeriesByActorId(actorId);

        // Remove all elements in firstList from secondList
        seriesAll.removeAll(series);

        return seriesAll;

    }

    public void saveActor(Actor actor) {
        em.merge(actor);
    }
}
