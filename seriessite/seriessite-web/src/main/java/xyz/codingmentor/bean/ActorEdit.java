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
//    private Series series;
    private static final Logger LOG = Logger.getLogger(ActorEdit.class.getName());
    private List<Series> seriesList;
    private List<Series> seriesInWichActorDontPlay;

    @PostConstruct
    public void init() {
        actor = new Actor();
        seriesInWichActorDontPlay = new ArrayList<>();
        seriesList = new ArrayList<>();

        Long idOfActor = 1L;
        actor = actorFacade.findActorById(idOfActor);
        seriesList = actorFacade.findSeriesByActorId(idOfActor);
        seriesInWichActorDontPlay = actorFacade.findSeriesInWichActorDontPlay(idOfActor);

    }

    public String goToSeriesEditSite() {
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> params
                = context.getExternalContext().getRequestParameterMap();
        String id = params.get("seriesId");
        return "seriesEdit.xhtml/?id=" + id + ";faces-redirect=true";
    }

    public void saveImageToDirectory(String nameOfImage) {
        createDirectory();

        try {
            InputStream inputstream = uploadedFile.getInputstream();
//            String fullFileName = uploadedFile.getFileName();
            Path file = Paths.get(PATH + nameOfImage);

            Files.copy(inputstream, file, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            Logger.getLogger(ActorEdit.class.getName()).log(Level.SEVERE, null, ex);
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
        LOG.info("in getImage function");
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
            LOG.info("Not found image. Exceptin in getImage function");
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

        LOG.info(" in addSeriesToActor" + series.getId());
        seriesList.add(series);
        seriesInWichActorDontPlay.remove(series);
        actorFacade.addSeriesToActor(series.getId(), actor.getId());
    }

    public void removeSeriesFromActor(Series series) {
//        LOG.info("removeActorFromSeries");

        seriesList.remove(series);
        seriesInWichActorDontPlay.add(series);
        actorFacade.deleteSeriesFromActor(series.getId(), actor.getId());

    }

    public void saveButtonAction(ActionEvent actionEvent) {
        String text = "Successful save";
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    
    public void saveSeries() {
        actorFacade.saveActor(actor);
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

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public List<Series> getSeriesInWichActorDontPlay() {
        return seriesInWichActorDontPlay;
    }

    public void setSeriesInWichActorDontPlay(List<Series> seriesInWichActorDontPlay) {
        this.seriesInWichActorDontPlay = seriesInWichActorDontPlay;
    }
    
    
    
}
