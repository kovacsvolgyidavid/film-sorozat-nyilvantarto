/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Comment;
import xyz.codingmentor.entity.Director;
import xyz.codingmentor.entity.Episode;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.service.EntityFacade;

/**
 *
 * @author keni
 */
@Named
@SessionScoped
public class SeriesView implements Serializable {

    private Series series;
    private static final String PATH = "\\path\\resources\\";
    private Season actualSeason;
    private String comment;
    @Inject
    private EntityFacade entityFacade;
    private User user;
    private Long seriesId;

    @PostConstruct
    public void init() {
        this.user = entityFacade.read(User.class, Usermanagement.getUsername());

    }

    public String getTitle() {
        return series.getTitle();
    }

    public StreamedContent getImage() {
        StreamedContent image;
        try {
            image = new DefaultStreamedContent(new FileInputStream(series.getPathOfPhoto()));
        } catch (Exception ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            image = null;
        }     
        return image;
    }

    public Series getSeries() {
        return this.series;
    }

    public String getSeasonNumber() {
        if (series.getSeasons() == null) {
            return Integer.toString(0);
        }
        return Integer.toString(series.getSeasons().size());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEpisodeNumber() {
        int episodeNum = 0;
        if (series.getSeasons() == null) {
            return Integer.toString(0);
        }
        //TODO: ezzel megcsinálni ha 1.8-as java lesz series.getSeasons().forEach((p)->{episodeNum+=p.getEpisodes().size()});
        for (Season season : series.getSeasons()) {
            if (season.getEpisodes() != null) {
                episodeNum += season.getEpisodes().size();
            }
        }
        return Integer.toString(episodeNum);
    }

    public String getActualSeason() {
        return Integer.toString(series.getSeasons().indexOf(actualSeason));
    }

    public void setActualSeason(int i) {
        actualSeason = series.getSeasons().get(i);
    }

    public List<Episode> getActualSeasonEpisodes() {
        if (actualSeason == null) {
            return null;
        }
        return actualSeason.getEpisodes();
    }

    public List<Actor> getActors() {
        return series.getActors();
    }

    public List<Director> getDirectors() {
        return series.getDirectors();
    }

    public void addComment() {
        Comment addComment = new Comment();
        addComment.setContent(comment);
        addComment.setDateOfComment(Calendar.getInstance().getTime());
        addComment.setUser(user);
        addComment.setShow(series);
        series.getComments().add(addComment);
        entityFacade.create(addComment); //TODO
        comment = "";
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Comment> getComments() {
        return series.getComments();
    }

    public String goToSeriesViewSite() {
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> params
                = context.getExternalContext().getRequestParameterMap();
        String id = params.get("seriesid");
//        LOG.info("Here go to actor side. ActorID: " + id);

//        1 is Actor edit it
        return "/user/seriesView.xhtml;faces-redirect=true";
//        return "actorEdit.xhtml/?id="+1+",faces-redirect=true";
    }

    public StreamedContent getUserImage(User user) {
        StreamedContent image;
        try {
            String userPhotoPath = PATH+user.getPathOfPhoto();
            image = new DefaultStreamedContent(new FileInputStream(userPhotoPath));
        } catch (Exception ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            image = null;
        }
        return image;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long SeriesId) {
        this.seriesId = SeriesId;
    }

    public void loadDatabaseData() {
        if (this.seriesId == null) {
            throw new IllegalArgumentException("Error while trying to find your series");
        }
        series = entityFacade.read(Series.class, this.seriesId);
        if (series == null) {
            throw new IllegalArgumentException("There is no such Series");
        }
    }

    public String deleteSeries() {
        this.entityFacade.delete(series);
        return "/user/series-user.xhtml;faces-redirect=true";
    }

    public boolean isNoSeasonSelected() {
        return this.actualSeason != null;
    }

    public String goToSeriesView(Long id) {
        this.series = entityFacade.read(series.getClass(), id);
        return "/user/seriesView.xhtml;faces-redirect=true";
    }

    public Series returnSeries() {
        return entityFacade.read(Series.class, 1L);
    }

}
