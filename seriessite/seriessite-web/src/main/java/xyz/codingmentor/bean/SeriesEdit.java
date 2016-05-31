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
import javax.faces.event.ComponentSystemEvent;
import xyz.codingmentor.bean.picture.PictureHandler;
import xyz.codingmentor.service.ActorFacade;

@Named
@SessionScoped
public class SeriesEdit implements Serializable {

    @Inject
    private SeriesFacade seriesFacade;

    @Inject
    private ActorFacade actorFacade;

    private String actorId;
    private Series series;
    private static final Logger LOG = Logger.getLogger(SeriesEdit.class.getName());
//    private List<Actor> actorList;
    private List<Actor> actorListNotInSeries;
    private String idOfSeries;
    private PictureHandler pictureHandler;
    private Actor newActor;

    @PostConstruct
    public void init() {
        series = new Series();
        actorListNotInSeries = new ArrayList<>();
//        actorList = new ArrayList<>();
        newActor = new Actor();
        idOfSeries = "1";
        loadDatabaseData();
//        loadDatabaseData(new ActionEvent());
    }

//    public void loadDatabaseData(ComponentSystemEvent event) {
    public void loadDatabaseData() {
        if (idOfSeries != null) {
            idOfSeries = "1";
            Long id = (Long) Long.parseLong(idOfSeries);

            Long idOfSeries2 = id; //Long.parseLong(idOfSeries);

            LOG.info("idd: " + idOfSeries2);

            series = seriesFacade.findSeriesById(idOfSeries2);
//            actorList = seriesFacade.findActorsInSeries(idOfSeries2);
            actorListNotInSeries = seriesFacade.getActorListNotInSeries(idOfSeries2);
        }
    }

//    public void newLine() {
//        LOG.info(String.valueOf(actorList.size()));
//        actorList.add(new Actor());
//        LOG.info(String.valueOf(actorList.size()));
//    }

    public String goToActorEditSite() {
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> params
                = context.getExternalContext().getRequestParameterMap();
        String id = params.get("actorId");
//        LOG.info("Here go to actor side. ActorID: " + id);

//        1 is Actor edit it
        return "actorEdit.xhtml/?id=" + id + ";faces-redirect=true";
//        return "actorEdit.xhtml/?id="+1+",faces-redirect=true";
    }

    public Series getSeries() {
        if (series == null) {
            throw new IllegalArgumentException("The series is null");
        } else {
            return series;
        }
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public List<Actor> getActorListNotInSeries() {
//        LOG.info("actorListNotInSeries size is " + actorListNotInSeries.size());
        return actorListNotInSeries;
    }

    public void setActorListNotInSeries(List<Actor> actorListNotInSeries) {
        this.actorListNotInSeries = actorListNotInSeries;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
//        LOG.info("setActor function");
        this.actorId = actorId;
    }
//
//    public List<Actor> getActorList() {
//        return actorList;
//    }
//
//    public void setActorList(List<Actor> actorList) {
//        this.actorList = actorList;
//    }

    private Actor searchActorById(List<Actor> l, String actorId) {
        Long id = Long.parseLong(actorId);

        for (Actor next : actorListNotInSeries) {
            if (Objects.equals(next.getId(), id)) {
                return next;
            }
        }
        return null;
    }

    public void addExistingActorToSeries() {
        Actor actor = searchActorById(actorListNotInSeries, actorId);

//        LOG.info("addActorToSeries");
//        LOG.info("Add actor " + actor.getName() + "  to " + series.getTitle());
//        actorList.add(actor);

        
        actorListNotInSeries.remove(actor);
        
        series.getActors().add(actor);
        seriesFacade.updateSeries(series);

//        seriesFacade.addActorToSeries(series.getId(), actor.getId());
    }

    public void addNewActorToSeries() {
//        actorFacade.create(newActor);
//        actorFacade.
//        Series findSeriesById = seriesFacade.findSeriesById(series.getId());
        
        
//        actorList.add(newActor);

        series.getActors().add(newActor);
        seriesFacade.updateSeries(series);
    }

    public void removeActorFromSeries(Actor actor) {
        series.getActors().remove(actor);
        actorListNotInSeries.add(actor);
        seriesFacade.deleteActorFromSeries(series.getId(), actor.getId());
    }

    public void saveButtonAction(ActionEvent actionEvent) {
        String text = "Successful save";
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void saveSeries() {
        seriesFacade.saveSeries(series);
    }

    public PictureHandler getPictureHandler() {
        return pictureHandler;
    }

    public void setPictureHandler(PictureHandler pictureHandler) {
        this.pictureHandler = pictureHandler;
    }

    public String getIdOfSeries() {
        return idOfSeries;
    }

    public void setIdOfSeries(String idOfSeries) {
        LOG.info("setIdOfSeries");
        this.idOfSeries = idOfSeries;
        loadDatabaseData();

    }

    public Actor getNewActor() {
        return newActor;
    }

    public void setNewActor(Actor newActor) {
        this.newActor = newActor;
    }

}
