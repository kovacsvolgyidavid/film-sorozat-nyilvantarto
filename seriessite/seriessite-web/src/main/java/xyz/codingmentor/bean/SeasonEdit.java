package xyz.codingmentor.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.service.EntityFacade;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.service.SeriesFacade;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import xyz.codingmentor.entity.Episode;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.service.SeasonFacade;

@Named
@SessionScoped
public class SeasonEdit implements Serializable {

    @Inject
    private SeasonFacade seasonFacade;

    private static final Logger LOG = Logger.getLogger(SeasonEdit.class.getName());
    private Season season;
    private Episode selectEpisode;
    private List<Episode> episodeList;
    private String seriestitle;

    @PostConstruct
    public void init() {
        season = new Season();
        episodeList = new ArrayList<>();
        selectEpisode = new Episode();

        Long idOfSeason = 1L;
        season = seasonFacade.findSeasonById(idOfSeason);
        episodeList = seasonFacade.findAllEpisode();
        
        
        Long seriesNameBySeasonId = seasonFacade.getSeriesNameBySeasonId(idOfSeason);
        seriestitle = seriesNameBySeasonId.toString();

    }

    
//    private Actor searchActorById(List<Actor> l, String actorId){
//        Long id=Long.parseLong(actorId);
//        
//        for (Actor next : actorListNotInSeries) {
//            if(Objects.equals(next.getId(), id)){
//                return next;
//            }
//        }
//        return null;
//    }



        public void saveButtonAction(ActionEvent actionEvent) {
        String text = "Successful save";
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
        
        public void saveSeason(){
            seasonFacade.saveSeason(season);
        }
        
         public void saveEpisode(){
            seasonFacade.saveEpisode(selectEpisode);
        }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Episode getSelectEpisode() {
        return selectEpisode;
    }

    public void setSelectEpisode(Episode selectEpisode) {
        this.selectEpisode = selectEpisode;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public String getSeriestitle() {
        return seriestitle;
    }

    public void setSeriestitle(String seriestitle) {
        this.seriestitle = seriestitle;
    }
         
         
}
