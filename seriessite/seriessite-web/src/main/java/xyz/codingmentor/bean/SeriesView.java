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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
    User user;
    

    @PostConstruct
    public void init() {//TODO: delete this
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
//        entityFacade.create(addComment); //TODO
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

    public String callStringView(Series series) {
        this.series = series;
        return "/user/seriesView.xhtml?faces-redirect=true";
    }

    public StreamedContent getUserImage(User user) {
        StreamedContent image;
        try {
            String userPhotoPath=user.getPathOfPhoto();
            image = new DefaultStreamedContent(new FileInputStream(userPhotoPath));
        } catch (Exception ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            image = null;
        }
        return image;
    }
}
