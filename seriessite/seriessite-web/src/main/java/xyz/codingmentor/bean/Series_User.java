/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.SystemUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.query.DatabaseQuery;
import xyz.codingmentor.service.EntityFacade;

/**
 *
 * @author Vendel
 */
@Named
@SessionScoped
public class Series_User implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    @Inject
    private DatabaseQuery databaseQuery;
    
    @Inject
    private Compare compare;

    private List<Series> series;
    private Series serie;

    private String blockStyle = "margin: 0 auto";
    private String PATH = "/series/";
    private User actualUser;
    private static final Logger LOG = Logger.getLogger(Series_User.class.getName());

    @PostConstruct
    public void init() {
        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        actualUser = databaseQuery.findUserByUsername(username);
        series = entityFacade.findAll(Series.class);
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public StreamedContent getImage(Series serie) throws FileNotFoundException {
        if (serie != null) {
           // FacesContext context = FacesContext.getCurrentInstance();
            if (serie.getPathOfPhoto() != null) {
                if (SystemUtils.IS_OS_WINDOWS) {
                    return new DefaultStreamedContent(new FileInputStream("C:" + serie.getPathOfPhoto()));
                } else if (SystemUtils.IS_OS_LINUX) {
                    return new DefaultStreamedContent(new FileInputStream("/home" + serie.getPathOfPhoto()));
                }
            }
            return new DefaultStreamedContent(new FileInputStream(PATH + "noimages.png"));
        }
        return null;
    }


    public String getBlockStyle() {
        return blockStyle;
    }

    public int getNumberOfEpisodes(Series serie) {
        int num = 0;
        for (int i = 0; i < serie.getSeasons().size(); i++) {
            num += serie.getSeasons().get(i).getEpisodes().size();
        }
        return num;
    }

    public String getPromoVideoLink(Series serie) {
        return serie.getSeasons().get(0).getLinkOfPromoVideo();
    }

    public void changeFavourites(Series serie) {

        if (!actualUser.getFavourites().contains(serie)) {
            actualUser.getFavourites().add(serie);
            entityFacade.update(actualUser);
        } else {
            actualUser.getFavourites().remove(serie);
            entityFacade.update(actualUser);
        }
    }

    public User getActualUser() {
        return actualUser;
    }

    public void deleteSerie(Series serie) {
        List<User> allusers = entityFacade.findAll(User.class);
        for (User alluser : allusers) {
            alluser.getFavourites().clear();
            entityFacade.update(alluser);
        }
        /*List<Actor> actors = entityFacade.findAll(Actor.class);
        for (Actor actor : actors) {
            actor.getSeries().remove(serie);
            entityFacade.update(actor);
        }
        serie.getActors().clear();
        entityFacade.update(serie); */
        series.remove(serie);
        entityFacade.delete(serie);
    }

}
