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
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import xyz.codingmentor.entity.Actor;
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

    @PostConstruct
    public void init() {
        /*databaseQuery.createExampleSeries();
        series = entityFacade.findAll(Series.class);
         */

        //TEST INPUT
        Actor actor1 = new Actor();
        Actor actor2 = new Actor();
        Actor actor3 = new Actor();
        actor1.setName("Johnie Depp");
        actor2.setName("Jack Sparrow");
        actor3.setName("Penge Pityu");
        
        series = new ArrayList();
        Series series1 = new Series();
        Series series2 = new Series();
        series1.setTitle("testtitle1");
        series1.getActors().add(actor1);
        series1.getActors().add(actor2);
        series1.getActors().add(actor3);
        series1.setPathOfPhoto("C:\\path\\resources\\testpic.jpg");
        series2.setTitle("testtitle2");
        series2.getActors().add(actor3);
        series2.getActors().add(actor2);
        series2.getActors().add(actor1);
        series2.setPathOfPhoto("C:\\path\\resources\\testpic.jpg");
        series.add(series1);
        series.add(series2);

        comparingSeries = new ArrayList();
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

}
