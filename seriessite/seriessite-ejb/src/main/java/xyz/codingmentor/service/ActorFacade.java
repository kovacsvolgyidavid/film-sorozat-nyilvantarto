package xyz.codingmentor.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Series;

@Stateless
public class ActorFacade implements Serializable{

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;
    
    @Inject
    private EntityFacade entityFacade;

    private static final Logger LOG = Logger.getLogger(ActorFacade.class.getName());

    public ActorFacade() {
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

    public List<Series> findSeriesInWichActorDontPlay(Long actorId) {

        TypedQuery<Series> findAllSeries = em.createNamedQuery("Series.findAll", Series.class);
        List<Series> seriesAll = findAllSeries.getResultList();
        Actor findActorById = findActorById(actorId);
        List<Series> series = findActorById.getSeries();
        seriesAll.removeAll(series);

        return seriesAll;

    }
    

    public List<Actor> actorsFromSeriesAfterGivenDate(Date date) {
        Query q = em.createNamedQuery("actorsFromSeriesAfterGivenDate");
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 10, 10);
        
        q.setParameter("date", date, TemporalType.DATE);
        return q.getResultList();
    }
    
    public void create(Actor actor){
        em.persist(actor);
        LOG.info("Persisit ok");
    }
    
    public Actor read(Long id) {
        return em.find(Actor.class, id);
    }
    
    public Actor update(Actor actor) {
        return em.merge(actor);
    }


}
