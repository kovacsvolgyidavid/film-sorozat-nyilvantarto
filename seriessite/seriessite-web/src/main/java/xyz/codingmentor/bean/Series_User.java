/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Director;
import xyz.codingmentor.entity.Episode;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.entity.Series;
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
    private StreamedContent image;
    private List<Series> comparingSeries;
    private String blockStyle = "margin: 0 auto;";
    private int numberOfEpisodes = 0;

    @PostConstruct
    public void init() {
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

    public void setComparing(Series serie) {
        if (comparingSeries.size() < 2) {
            comparingSeries.add(serie);
            blockStyle = "margin: 0 auto; background-color: greenyellow";
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Comparing " + serie.getTitle() + " to... Please select another item!"));
        }
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
}
