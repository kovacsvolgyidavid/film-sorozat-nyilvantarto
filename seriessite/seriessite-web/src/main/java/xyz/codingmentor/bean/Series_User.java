/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
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

    private List<Series> series;
    private Series serie;
    private StreamedContent image;
    private List<Series> comparingSeries;
    private String blockStyle = "margin: 0 auto";
    private User actualUser;

    @PostConstruct
    public void init() {
        //TEST INPUT
        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        actualUser = databaseQuery.findUserByUsername(username);
        comparingSeries = new ArrayList();
        databaseQuery.createTestSeries();
        series = entityFacade.findAll(Series.class);

    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public StreamedContent getImage(Series serie) {
        if (serie != null) {
            try {
                image = new DefaultStreamedContent(new FileInputStream(serie.getPathOfPhoto()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Series_User.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return image;
    }

    public String setComparing(Series serie) {
        
            if (comparingSeries.isEmpty()) {
                comparingSeries.add(serie);
                blockStyle = "margin: 0 auto; background-color: greenyellow";
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Comparing " + serie.getTitle() + " to... Please select another item!"));
            } else if (comparingSeries.size() == 1) {
                comparingSeries.add(serie);
                return "user.xhtml?faces-redirect=true";
            }
        return "series-user.xhtml?faces-redirect=true";
    }

    public void removeComparing(Series serie) {
        comparingSeries.remove(serie);
        blockStyle = "margin: 0 auto;";
    }

    public List<Series> getComparingSeries() {
        return comparingSeries;
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
        series.remove(serie);
        entityFacade.delete(serie);
    }

}
