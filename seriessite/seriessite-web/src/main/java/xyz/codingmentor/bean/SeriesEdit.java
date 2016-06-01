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
import org.apache.commons.lang3.SystemUtils;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import org.primefaces.context.RequestContext;
import xyz.codingmentor.bean.picture.NewClass;
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
        LOG.info("init() function");
        series = new Series();
        actorListNotInSeries = new ArrayList<>();
        newActor = new Actor();
//        actorList = new ArrayList<>();
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
            actorListNotInSeries = seriesFacade.getActorListNotInSeries(idOfSeries2);
        }
    }

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
        return actorListNotInSeries;
    }

    public void setActorListNotInSeries(List<Actor> actorListNotInSeries) {
        this.actorListNotInSeries = actorListNotInSeries;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        LOG.info("setActorId function");
        this.actorId = actorId;
    }

    private Actor searchActorById(List<Actor> l, String actorId) {
        Long id = (Long) Long.parseLong(actorId);

        for (Actor next : actorListNotInSeries) {
            if (Objects.equals(next.getId(), id)) {
                return next;
            }
        }
        return null;
    }

    public void addExistingActorToSeries() {
        LOG.info("addExistingActorToSeries");
        Actor actor = searchActorById(actorListNotInSeries, actorId);
        actorListNotInSeries.remove(actor);
        series.getActors().add(actor);

    }

    public void addNewActorToSeries() {
        LOG.info("addNewActorToSeries " + newActor.getName());
        series.getActors().add(newActor);
//        newActor = new Actor();
//        refreshDialog();
    }

//    public void resetNewActor() {
//        LOG.info("resetNewActor");
//        newActor = new Actor();
//        refreshDialog();
//    }
    
    public void initialNewActor(){
        LOG.info("initalNewActor");
        newActor = new Actor();
    }
    
//    private void refreshDialog(){
//        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("form2:newActorDialog");
//        RequestContext.getCurrentInstance().update("form2:newActorDialog");
//    }

    public void removeActorFromSeries(Actor actor) {
        series.getActors().remove(actor);
        actorListNotInSeries.add(actor);
    }

    public void saveSeries() {
        seriesFacade.saveSeries(series);
        LOG.info("linux? " + SystemUtils.IS_OS_LINUX);
        LOG.info("windows? " + SystemUtils.IS_OS_WINDOWS);
    }

    public void resetSeries() {
        series = seriesFacade.findSeriesById(series.getId());
    }

    public void saveButtonListener(ActionEvent actionEvent) {
        String text = "Successful save";
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
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
        LOG.info("getNewActor name: " + newActor.getName());
        return newActor;
    }

    public void setNewActor(Actor newActor) {
        this.newActor = newActor;
    }
}
