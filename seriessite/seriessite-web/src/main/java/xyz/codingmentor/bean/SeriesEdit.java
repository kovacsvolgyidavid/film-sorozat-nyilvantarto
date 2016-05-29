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

@Named
@SessionScoped
public class SeriesEdit implements Serializable {

    @Inject
    private SeriesFacade seriesFacade;

    private static final String PATH = "/series/";
    private UploadedFile uploadedFile;
    private StreamedContent image;
    private String actorId;
    private Series series;
    private static final Logger LOG = Logger.getLogger(SeriesEdit.class.getName());
    private List<Actor> actorList;
    private List<Actor> actorListNotInSeries;
    private String idOfSeries;

    public String getIdOfSeries() {
        return idOfSeries;
    }

    public void setIdOfSeries(String idOfSeries) {
        this.idOfSeries = idOfSeries;

    }

    @PostConstruct
    public void init() {
        series = new Series();
        actorListNotInSeries = new ArrayList<>();
        actorList = new ArrayList<>();
    }

    public void loadDatabaseData(ComponentSystemEvent event) {
        if (idOfSeries != null) {
            Long id = (Long) Long.parseLong(idOfSeries);
            
            Long idOfSeries2 = id; //Long.parseLong(idOfSeries);

            LOG.info("idd: " + idOfSeries2);

            series = seriesFacade.findSeriesById(idOfSeries2);
            actorList = seriesFacade.findActorsInSeries(idOfSeries2);
            actorListNotInSeries = seriesFacade.getActorListNotInSeries(idOfSeries2);
        }
    }
//
//    public void enterSeries(Long id) {
////        FacesContext context = FacesContext.getCurrentInstance();
////
////        Map<String, String> params
////                = context.getExternalContext().getRequestParameterMap();
////        String id = params.get("seriesId");
////        series = newSeries;
//
//        Long idOfSeries2 = id; //Long.parseLong(idOfSeries);
//
//        LOG.info("idd: " + idOfSeries2);
////        series = seriesInput;
//
//        series = seriesFacade.findSeriesById(idOfSeries2);
//        actorList = seriesFacade.findActorsInSeries(idOfSeries2);
//        actorListNotInSeries = seriesFacade.getActorListNotInSeries(idOfSeries2);
//
////        return "seriesEdit.xhtml/?id=" + series.getId() + ";faces-redirect=true";
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

    public void saveImageToDirectory(String nameOfImage) {
        createDirectory();

        try {
            InputStream inputstream = uploadedFile.getInputstream();
//            String fullFileName = uploadedFile.getFileName();
            Path file = Paths.get(PATH + nameOfImage);

            Files.copy(inputstream, file, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(uploadedFile.getFileName() + " is successfully uploaded."));
    }

    public void createDirectory() {
        File directory = new File(PATH);

        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (SecurityException se) {
                //handle it
            }
        }
    }

    public void imageUpload(FileUploadEvent event) {
        uploadedFile = event.getFile();

        try {
            image = new DefaultStreamedContent(uploadedFile.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public StreamedContent getImage() {
//        LOG.info("in getImage function");
        try {
            if (uploadedFile == null) {
//                LOG.info("in function StreamConent. The uploadedFile  is null " + PATH + "noimages.png");
                ClassLoader classLoader = getClass().getClassLoader();
                File noPicture = new File(classLoader.getResource(PATH + "noimages.png").getFile());
//                LOG.info("after image load");
                image = new DefaultStreamedContent(new FileInputStream(noPicture));
            } else {
                LOG.info(this.getClass().getCanonicalName() + "else ag in getImage function");
                image = new DefaultStreamedContent(uploadedFile.getInputstream());
            }
        } catch (Exception ex) {
//            LOG.info("Not found image. Exceptin in getImage function");
//            Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }

    public void resetPicture(AjaxBehaviorEvent event) {
        uploadedFile = null;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
//        LOG.info("setActor function");
        this.actorId = actorId;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    private Actor searchActorById(List<Actor> l, String actorId) {
        Long id = Long.parseLong(actorId);

        for (Actor next : actorListNotInSeries) {
            if (Objects.equals(next.getId(), id)) {
                return next;
            }
        }
        return null;
    }

    public void addActorToSeries() {
        Actor actor = searchActorById(actorListNotInSeries, actorId);

//        LOG.info("addActorToSeries");
//        LOG.info("Add actor " + actor.getName() + "  to " + series.getTitle());
        actorList.add(actor);
        actorListNotInSeries.remove(actor);
        seriesFacade.addActorToSeries(series.getId(), actor.getId());
    }

    public void removeActorFromSeries(Actor actor) {
//        LOG.info("removeActorFromSeries");

        actorList.remove(actor);
        actorListNotInSeries.add(actor);
        seriesFacade.deleteActorFromSeries(series.getId(), actor.getId());

//        LOG.info("Remove " + actor.getId() + " id of actor from " + series.getTitle());
    }

    public void saveButtonAction(ActionEvent actionEvent) {
        String text = "Successful save";
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void saveSeries() {
        seriesFacade.saveSeries(series);
    }
}
