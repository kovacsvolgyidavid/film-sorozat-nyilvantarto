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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import xyz.codingmentor.entity.Actor;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import xyz.codingmentor.service.ActorFacade;

@Named
@SessionScoped
public class ActorEdit implements Serializable {

    @Inject
    private ActorFacade actorFacade;

    private static final String PATH = "/actors/";
    private UploadedFile uploadedFile;
    private StreamedContent image;
    private Actor actor;
    private String selectedSeriesId;
    private static final Logger LOG = Logger.getLogger(ActorEdit.class.getName());
    private List<Series> seriesInWichActorDontPlay;

    @PostConstruct
    public void init() {
        actor = new Actor();
        seriesInWichActorDontPlay = new ArrayList<>();


        Long idOfActor = 1L;
        actor = actorFacade.findActorById(idOfActor);

    }

    public String goToSeriesEditSite() {
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> params
                = context.getExternalContext().getRequestParameterMap();
        String id = params.get("seriesId");
        return "seriesEdit.xhtml/?id=" + id + ";faces-redirect=true";
    }

    

    private Series searchSeriesById(List<Series> l, String seriesId) {
        Long id = Long.parseLong(seriesId);

        for (Series next : l) {
            if (Objects.equals(next.getId(), id)) {
                return next;
            }
        }
        return null;
    }

    public void addSeriesToActor() {
        Series series = searchSeriesById(seriesInWichActorDontPlay, selectedSeriesId);

        LOG.info(" in addSeriesToActor  " + series.getId());
        
        actor.getSeries().add(series);
        seriesInWichActorDontPlay.remove(series);
        
         printActorSeries(actor);        
    }

    public void removeSeriesFromActor(Series series) {
        actor.getSeries().remove(series);
        seriesInWichActorDontPlay.add(series);
    }

    public void saveButtonAction(ActionEvent actionEvent) {
        String text = "Successful save";
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void printActorSeries(Actor actor) {
        LOG.info("printActorSeries. Actor id:" + actor.getId());
        for (Series series : actor.getSeries()) {
            LOG.info(series.toString());
        }
    }

    public void saveActor() {
        LOG.info("In saveActor ");
        printActorSeries(actor);
        actorFacade.create(actor);
    }

    public ActorFacade getActorFacade() {
        return actorFacade;
    }

    public void setActorFacade(ActorFacade actorFacade) {
        this.actorFacade = actorFacade;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public String getSelectedSeriesId() {
        return selectedSeriesId;
    }

    public void setSelectedSeriesId(String selectedSeriesId) {
        this.selectedSeriesId = selectedSeriesId;
    }

    public List<Series> getSeriesInWichActorDontPlay() {
        return seriesInWichActorDontPlay;
    }

    public void setSeriesInWichActorDontPlay(List<Series> seriesInWichActorDontPlay) {
        this.seriesInWichActorDontPlay = seriesInWichActorDontPlay;
    }

}
