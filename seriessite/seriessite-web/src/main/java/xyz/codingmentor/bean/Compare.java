package xyz.codingmentor.bean;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.service.EntityFacade;

/**
 *
 * @author keni
 */
@Named
@SessionScoped
public class Compare implements Serializable {

    Series series1;
    Series series2;
    Season actualSeason1;
    Season actualSeason2;
    @Inject
    EntityFacade entityFacade;

    @PostConstruct
    public void init() {
        series1 = entityFacade.read(Series.class, 1L);
        series2 = entityFacade.read(Series.class, 11L);
        actualSeason1=series1.getSeasons().get(0);
    }

    public Series getSeries1() {
        return series1;
    }

    public void setSeries1(Series series1) {
        this.series1 = series1;
    }

    public Series getSeries2() {
        return series2;
    }

    public void setSeries2(Series series2) {
        this.series2 = series2;
    }

    public Season getActualSeason1() {
        return actualSeason1;
    }

    public void setActualSeason1(Season actualSeason1) {
        this.actualSeason1 = actualSeason1;
    }
     public void setActualSeason1(int i) {
        this.actualSeason1 = series1.getSeasons().get(i);
    }

    public Season getActualSeason2() {
        return actualSeason2;
    }

    public void setActualSeason2(Season actualSeason2) {
        this.actualSeason2 = actualSeason2;
    }
     public void setActualSeason2(int i) {
        this.actualSeason2 = series2.getSeasons().get(i);
    }

     public String getSeasonNumber(Series series) {
        if (series.getSeasons() == null) {
            return Integer.toString(0);
        }
        return Integer.toString(series.getSeasons().size());
    }
    public StreamedContent getSeries1Image() {
        StreamedContent image;
        try {
            image = new DefaultStreamedContent(new FileInputStream(series1.getPathOfPhoto()));
        } catch (Exception ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            image = null;
        }
        return image;
    }

    public StreamedContent getSeries2Image() {
        StreamedContent image;
        try {
            image = new DefaultStreamedContent(new FileInputStream(series2.getPathOfPhoto()));
        } catch (Exception ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            image = null;
        }
        return image;
    }

    public String getSeriesTotalEpisodes(Series series) {
        int seasonCounter=0;
        for (Season season : series.getSeasons()) {
            seasonCounter+=season.getEpisodes().size();
        }
        return Integer.toString(seasonCounter);
    }

}
