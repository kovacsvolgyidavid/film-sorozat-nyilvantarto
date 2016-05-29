/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Episode;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.entity.Series;

@Stateless
public class SeasonFacade {

    private static final Logger LOG = Logger.getLogger(SeasonFacade.class.getName());

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;

    public SeasonFacade() {
    }

    public Season findSeasonById(Long seasonId) {
        LOG.info("id: " + seasonId);
        TypedQuery<Season> season = em.createNamedQuery("Season.findSeasonById", Season.class);
        season.setParameter("id", seasonId);
        return season.getSingleResult();

    }

    public List<Episode> findAllEpisode() {
        TypedQuery<Episode> episode = em.createNamedQuery("Episode.findAll", Episode.class);
        return episode.getResultList();

    }

    public Episode findEpisodeById(Long episodeId) {
        TypedQuery<Episode> episode = em.createNamedQuery("Episode.findEpisodeById", Episode.class);
        episode.setParameter("id", episodeId);
        return episode.getSingleResult();

    }

    public String getSeriesTitleBySeasonId(Long idOfSeason) {
        TypedQuery<Long> query = em.createNamedQuery("Season.findSeriesIdBySeasonId", Long.class);
        query.setParameter("id", idOfSeason);
        Long seriesId = query.getSingleResult();
        
         TypedQuery<Series> series = em.createNamedQuery("Series.findSeriesById", Series.class);
        series.setParameter("id", seriesId);
        return series.getSingleResult().getTitle();
    }

//
//    public List<Actor> findActorsInSeries(Long seriesId) {
//        TypedQuery<Actor> actors = em.createNamedQuery("Series.findActorsBySeriesId", Actor.class);
//        actors.setParameter("id", seriesId);
//        return actors.getResultList();
//    }
//    public List<Actor> findActorsNotInSeries(Long seriesId) {
//        TypedQuery<Actor> actors = em.createNamedQuery("Series.findActorsNotInSeriesBySeriesId", Actor.class);
//        actors.setParameter("id", seriesId);
//        return actors.getResultList();
//    }
//    public void deleteActorFromSeries(Long seriesId, Long actorId) {
//        Series series = em.find(Series.class, seriesId);
//        Actor actor = em.find(Actor.class, actorId);
//        series.getActors().remove(actor);
//    }
//
//    public void addActorToSeries(Long seriesId, Long actorId) {
//        Series series = em.find(Series.class, seriesId);
//        Actor actor = em.find(Actor.class, actorId);
//        series.getActors().add(actor);
//    }
//
//
//    public List<Actor> getActorListNotInSeries(Long seriesId) {
//
//        TypedQuery<Actor> findAllActor = em.createNamedQuery("Actor.findAll", Actor.class);
//        List<Actor> actorsAll = findAllActor.getResultList();
//
////        LOG.info("getActorListNotInSeries Size of actorsAll: " + actorsAll.size());
//
//        TypedQuery<Actor> findActorsInSeries = em.createNamedQuery("Series.findActorsBySeriesId", Actor.class);
//        findActorsInSeries.setParameter("id", seriesId);
//        List<Actor> actorsInSeries = findActorsInSeries.getResultList();
//
////        LOG.info("getActorListNotInSeries Size of actorsInSeries: " + actorsInSeries.size());
//
//        // Remove all elements in firstList from secondList
//        actorsAll.removeAll(actorsInSeries);
//
////        LOG.info("getActorListNotInSeries Size of : " + actorsAll.size());
//
//        return actorsAll;
//
//    }
//
    public void saveEpisode(Episode episode) {
        em.merge(episode);
    }

    public void saveSeason(Season season) {
        em.merge(season);
    }

}
