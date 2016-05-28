/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.TabChangeEvent;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.service.ActorFacade;
import xyz.codingmentor.service.SeriesFacade;

@Named
@RequestScoped
public class Queries {
    private Date date;
    private int numberOfEpisodes;
    
    private List<Actor> actors;
    private List<Series> series;
    
    @Inject
    private ActorFacade actorFacade;
    
    @Inject
    private SeriesFacade seriesFacade;
    
    @PostConstruct
    public void init(){
        actors = new ArrayList<>();
        series = new ArrayList<>();
    }
      
    public void actorsFromSeriesAfterGivenDate() {
        actors = actorFacade.actorsFromSeriesAfterGivenDate(date);
        if(actors.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is no such actor.", "Error!"));
        }
    }
    
    public void seriesByDirectorOriginalNameEqualsName() {
        series = seriesFacade.seriesByDirectorOriginalNameEqualsName();
        if(series.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is no such series.", "Error!"));
        }
    }
    
    public void seriesWithMoreEpisode() {
        series = seriesFacade.seriesWithMoreEpisode(numberOfEpisodes);
        if(series.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is no such series.", "Error!"));
        }
    }
    
    public void seriesCommentedAfterGivenDate() {
        series = seriesFacade.seriesCommentedAfterGivenDate(date);
        if(series.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is no such series.", "Error!"));
        }
    }
    
    public void onTabChange(TabChangeEvent event) {
        actors.clear();
        series.clear();
        numberOfEpisodes = 0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }
    
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
