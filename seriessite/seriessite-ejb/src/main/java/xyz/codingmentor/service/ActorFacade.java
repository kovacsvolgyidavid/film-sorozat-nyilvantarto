package xyz.codingmentor.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Series;

@Stateless
public class ActorFacade {

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;

    private static final Logger LOG = Logger.getLogger(ActorFacade.class.getName());

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

    public List<Series> findAllSeries() {
        TypedQuery<Series> series = em.createNamedQuery("Series.findAll", Series.class);
        return series.getResultList();
    }

    public List<Series> findSeriesInWichActorDontPlay(Long actorId) {

        TypedQuery<Series> findAllSeries = em.createNamedQuery("Series.findAll", Series.class);
        List<Series> seriesAll = findAllSeries.getResultList();
        Actor findActorById = findActorById(actorId);
        List<Series> series = findActorById.getSeries();
        seriesAll.removeAll(series);

        return seriesAll;

    }
    
    private void printActorSeriessss(Actor actor) {
        LOG.info("printActorSeriessss. Actor id:" + actor.getId());
        for (Series series : actor.getSeries()) {
            LOG.info(series.toString());
        }
    }

    private List<Long> seriesIds(List<Series> seriesList) {
        List<Long> idList = new ArrayList<>();
        LOG.info("seriesIds");
        for (Series series : seriesList) {
            idList.add(series.getId());
        }
        return idList;
    }

    public void saveActor(Actor actor) {
        LOG.info("saveActor id: " + actor.getId());
        Actor findActorInDataBase = em.find(Actor.class, actor.getId());

        printActorSeriessss(findActorInDataBase);
        List<Long> oldSeriesIds = seriesIds(findActorInDataBase.getSeries());
        LOG.info(oldSeriesIds.toString());
        
        printActorSeriessss(actor);
        List<Long> newSeriesIds = seriesIds(actor.getSeries());
        LOG.info(newSeriesIds.toString());

        LOG.info("----------------");

        for (Long id : newSeriesIds) {
             LOG.info("id: " + id);
            if( ! oldSeriesIds.contains(id)){
                Series series = em.find(Series.class, id);
                series.getActors().add(actor);
                em.merge(series);
                LOG.info("Seriesid: " + series.getId());
            }
        }

    }
    
    public List<Actor> actorsFromSeriesAfterGivenDate(Date date) {
        Query q = em.createNamedQuery("actorsFromSeriesAfterGivenDate");
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 10, 10);
        
        q.setParameter("date", date, TemporalType.DATE);
        return q.getResultList();
    }
}
