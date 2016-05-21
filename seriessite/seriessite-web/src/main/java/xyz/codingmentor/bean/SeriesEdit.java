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
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import xyz.codingmentor.entity.Actor;

@Named
@SessionScoped
public class SeriesEdit implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    private static final String PATH = "/series/";
    private UploadedFile uploadedFile;
    private StreamedContent image;
    private Actor actor;
    private Series series;
    private static final Logger LOG = Logger.getLogger(SeriesEdit.class.getName());
    private List<Actor> actorList;
    private List<Actor> actorListNotInSeries;

    @PostConstruct
    public void init() {
        actor = new Actor();
        series = new Series();
        series.setTitle("Title of title");

        Actor s1 = new Actor();
        s1.setId(1L);
        s1.setName("Béla");

        Actor s2 = new Actor();
        s2.setId(2L);
        s2.setName("János");

        Actor s3 = new Actor();
        s3.setId(3L);
        s3.setName("Pisti");

        actorList = new ArrayList<>();
        actorList.add(s1);
        actorList.add(s2);
        actorList.add(s3);

        Actor s12 = new Actor();
        s12.setId(4L);
        s12.setName("Zoli");

        Actor s22 = new Actor();
        s22.setId(5L);
        s22.setName("Zita");

        Actor s32 = new Actor();
        s32.setId(6L);
        s32.setName("Zsombor");

        actorListNotInSeries = new ArrayList<>();
        actorListNotInSeries.add(s12);
        actorListNotInSeries.add(s22);
        actorListNotInSeries.add(s32);
    }

    public String goToActorEditSite() {
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> params
                = context.getExternalContext().getRequestParameterMap();
        String id = params.get("actorId");
        LOG.info("Here go to actor side. ActorID: " + id);

//        1 is Actor edit it
        return "actorEdit.xhtml/?id=" + id + ";faces-redirect=true";
//        return "actorEdit.xhtml/?id="+1+",faces-redirect=true";
    }

    public Series getSeries() {
        return series;
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

    public void resetPicture(AjaxBehaviorEvent event) {
        uploadedFile = null;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public StreamedContent getImage() {
        LOG.info("in getImage function");
        try {
            if (uploadedFile == null) {
                LOG.info("in function StreamConent. The uploadedFile  is null " + PATH + "noimages.png");
                ClassLoader classLoader = getClass().getClassLoader();
                ///Location: film-sorozat-nyilvantarto/seriessite/seriessite-web/src/main/resources/series
//                File noPicture = new File(classLoader.getResource(PATH + "noimages.png").getFile());
                File noPicture = new File(classLoader.getResource(PATH + "noimages.png").getFile());
                LOG.info("after image load");
                image = new DefaultStreamedContent(new FileInputStream(noPicture));
            } else {
                LOG.info(this.getClass().getCanonicalName() + "else ag in getImage function");
                image = new DefaultStreamedContent(uploadedFile.getInputstream());
            }
        } catch (Exception ex) {
            LOG.info("Not found image. Exceptin in getImage function");
//            Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public void addActorToSeries() {
        LOG.info("in addActorToSeries function");
        
//        LOG.info("Here should add actor to series. Id: " + actor.getId());

    }

    public void removeActorFromSeries() {
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> params
                = context.getExternalContext().getRequestParameterMap();
        String id = params.get("actorId");
        
        LOG.info("Here should remove actor fromseries " + id + " iddd");

    }

}
