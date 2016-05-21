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
        List<Actor> actorList1 = new ArrayList<>();
        List<Actor> actorList2 = new ArrayList<>();
        actorList1.add(actor1);
        actorList1.add(actor2);
        actorList1.add(actor3);
        actorList2.add(actor3);
        actorList2.add(actor2);
        actorList2.add(actor1);

        series = new ArrayList();
        Series series1 = new Series();
        Series series2 = new Series();
        series1.setTitle("testtitle1");
        series1.setActors(actorList1);
        series1.setPathOfPhoto("C:\\path\\resources\\testpic.jpg");
        series2.setTitle("testtitle2");
        series2.setActors(actorList2);
        series1.setPathOfPhoto("C:\\path\\resources\\testpic.jpg");
        series.add(series1);
        series.add(series2);
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

}
