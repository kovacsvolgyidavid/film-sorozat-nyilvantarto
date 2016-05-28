package xyz.codingmentor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Movie;
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

//    public List<Series> findSeriesByActorId(Long actorId) {
////        TypedQuery<Series> actor = em.createNamedQuery("Actor.findSeriesByActorId", Series.class);
////        actor.setParameter("id", actorId);
////        LOG.info("in findSeriesByActorId size: " + actor.getResultList().size() );
////        return actor.getResultList();
//
//        TypedQuery<Actor> actor = em.createNamedQuery("Actor.findActorById", Actor.class);
//        actor.setParameter("id", actorId);
//        LOG.info("in findSeriesByActorId1 size: " + actor.getSingleResult().getSeries().size() );
//        
////        List<Movie> s = actor.getSingleResult().getSeries();
////        List<Series> tmp = new ArrayList<>();
////        for (Movie movie : s) {
////            if(movie instanceof Series){
////                tmp.add((Series) movie);
////            }else{
////                LOG.info("(movie instanceof");
////            }
////        }
////        LOG.info("in findSeriesByActorId2 size: " + tmp.size());
//        
////        return tmp;
//          return actor.getSingleResult().getSeries();
//
//    }
    public List<Series> findAllSeries() {
        TypedQuery<Series> series = em.createNamedQuery("Series.findAll", Series.class);
        return series.getResultList();
    }

//    public void deleteSeriesFromActor(Long seriesId, Long actorId) {
//        Series series = em.find(Series.class, seriesId);
//        Actor actor = em.find(Actor.class, actorId);
//        actor.getSeries().remove(series);
//    }
//
//    public void addSeriesToActor(Long seriesId, Long actorId) {
//        Series series = em.find(Series.class, seriesId);
//        Actor actor = em.find(Actor.class, actorId);
//        actor.getSeries().add(series);
//    }
//    public List<Series> findSeriesInWichActorDontPlay(Long actorId) {
//
//        TypedQuery<Series> findAllSeries = em.createNamedQuery("Series.findAll", Series.class);
//        List<Series> seriesAll = findAllSeries.getResultList();
//
////        List<Series> series = findSeriesByActorId(actorId);
//        Actor findActorById = findActorById(actorId);
//        List<Series> series = findActorById.getSeries();
//
//        // Remove all elements in firstList from secondList
//        seriesAll.removeAll(series);
//
//        return seriesAll;
//
//    }
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
//        printActorSeries2(actor);
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
}
