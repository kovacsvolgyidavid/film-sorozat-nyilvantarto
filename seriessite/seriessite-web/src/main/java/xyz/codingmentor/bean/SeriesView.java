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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import xyz.codingmentor.entity.Episode;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.entity.Series;

/**
 *
 * @author keni
 */
@Named
@SessionScoped
public class SeriesView implements Serializable {

    private Series series;
    private static final String PATH = "/path/resources";
    private Season actualSeason;

    @PostConstruct
    public void init() {//TODO: delete this
        series = new Series();
        series.setTitle("Breaking Bad");
        series.setPathOfPhoto(PATH + "/user.jpg");
        series.setYearOfRelease(Calendar.getInstance().getTime());
        series.setSeasons(new ArrayList<Season>());
        Season season1=new Season();
        Season season2=new Season();
        Season season3=new Season();
        Episode episode1=new Episode();
        episode1.setDateOfRelease(Calendar.getInstance().getTime());
        episode1.setTitle("Kill with fire");
        season1.setEpisodes(new ArrayList<Episode>());
        season1.getEpisodes().add(episode1);
      series.getSeasons().add(season1);
      series.getSeasons().add(season2);
      series.getSeasons().add(season3);

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

    public String getReleaseDate() {
        Calendar releaseYear = Calendar.getInstance();
        releaseYear.setTime(series.getYearOfRelease());
        return releaseYear.getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.UK);
    }

    public String getSeasonNumber() {
        if (series.getSeasons() == null) {
            return Integer.toString(0);
        }
        return Integer.toString(series.getSeasons().size());
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
    public List<Episode> getActualSeasonEpisodes(){
        if(actualSeason==null){
            return null;
        }
        return actualSeason.getEpisodes();
    }
}
