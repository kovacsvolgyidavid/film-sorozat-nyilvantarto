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

        Director director = new Director();
        director.setName("Andy Vajna");

        List<Director> directors = new ArrayList<>();
        directors.add(director);

        Episode testepisode1 = new Episode();
        Episode testepisode2 = new Episode();
        Episode testepisode3 = new Episode();

        List<Episode> episodes = new ArrayList<>();
        episodes.add(testepisode1);
        episodes.add(testepisode2);
        episodes.add(testepisode3);

        Season testseason1 = new Season();
        testseason1.setEpisodes(episodes);
        testseason1.setLinkOfPromoVideo("http://www.youtube.com/v/g9JguCjLrCw");

        Season testseason2 = new Season();
        testseason2.setEpisodes(episodes);
        testseason2.setLinkOfPromoVideo("http://www.youtube.com/v/g9JguCjLrCw");
        ArrayList<Actor> actorsList1 = new ArrayList<>();
        ArrayList<Actor> actorsList2 = new ArrayList<>();

        ArrayList<Season> seasons = new ArrayList<>();
        seasons.add(testseason1);
        seasons.add(testseason2);

        actorsList1.add(actor1);
        actorsList1.add(actor2);
        actorsList1.add(actor3);

        actorsList2.add(actor3);
        actorsList2.add(actor2);
        actorsList2.add(actor1);

        series = new ArrayList();

        Series series1 = new Series();
        Series series2 = new Series();

        series1.setTitle("testtitle1");
        series1.setActors(actorsList1);
        series1.setDirectors(directors);

        DateFormat df = new SimpleDateFormat("yyyy");
        try {
            Date date = df.parse("2011");
            series1.setYearOfRelease(date);
        } catch (ParseException ex) {
            Logger.getLogger(Series_User.class.getName()).log(Level.SEVERE, null, ex);
        }

        series1.setPathOfPhoto("C:\\path\\resources\\testpic.jpg");
        series1.setSeasons(seasons);

        series2.setTitle("testtitle2");
        try {
            Date date = df.parse("2011");
            series2.setYearOfRelease(date);
        } catch (ParseException ex) {
            Logger.getLogger(Series_User.class.getName()).log(Level.SEVERE, null, ex);
        }
        series2.setActors(actorsList2);
        series2.setDirectors(directors);
        series2.setPathOfPhoto("C:\\path\\resources\\testpic.jpg");
        series2.setSeasons(seasons);

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
