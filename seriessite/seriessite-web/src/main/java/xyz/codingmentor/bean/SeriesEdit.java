package xyz.codingmentor.bean;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.service.SeriesFacade;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.SystemUtils;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import xyz.codingmentor.bean.picture.PictureHandler;
import xyz.codingmentor.entity.Episode;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.enums.Sex;
import xyz.codingmentor.service.ActorFacade;

@Named
@SessionScoped
public class SeriesEdit implements Serializable {

    @Inject
    private SeriesFacade seriesFacade;

    @Inject
    private ActorFacade actorFacade;

    private Series series;
    private String idOfSeries;

    private static final Logger LOG = Logger.getLogger(SeriesEdit.class.getName());
    private PictureHandler pictureHandler;

    private Actor newActor;
    private List<Actor> actorListNotInSeries;
    private String actorId;

    private Season newSeason;
    private Episode newEpisode;
    private Season selectedSeason;
    private Integer activeIndexToTabView;

    @PostConstruct
    public void init() {
        LOG.info("init() function");

        newActor = new Actor();
        newSeason = new Season();
        newEpisode = new Episode();

        selectedSeason = new Season();

        pictureHandler = new PictureHandler("/series/");
        if (SystemUtils.IS_OS_LINUX) {
            LOG.info("I am Linux");
            String homeDirectoryNameInUbuntu = System.getProperty("user.home");
            PATH = homeDirectoryNameInUbuntu + "/series/";
            LOG.info("Path: " + PATH.toString());

        }
        activeIndexToTabView = 0;
    }

    public void loadDatabaseData() {
//        http://www.codebulb.ch/2015/05/restful-jsf-with-post-redirect-get-part-4.html
        LOG.info("loadDatabaseData 1");

        if (series != null) {
            return;
        }

        if (idOfSeries != null) {
            LOG.info("loadDatabaseData 2");
            Long id = (Long) Long.parseLong(idOfSeries);

            Long idOfSeries2 = id;

            LOG.info("idd: " + idOfSeries2);

            series = seriesFacade.findSeriesById(idOfSeries2);
            actorListNotInSeries = seriesFacade.getActorListNotInSeries(idOfSeries2);
        }
        return;
    }

    public String goToSeriesEditSite() {
        LOG.info("goToSeriesEditSite");
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> params
                = context.getExternalContext().getRequestParameterMap();
        String id = params.get("seriesid");

        return "/admin/seriesEdit.xhtml/?seriesid=" + id + "&faces-redirect=true";
    }

    public String goToActorEditSite() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String id = params.get("actorId");
        return "actorEdit.xhtml/?id=" + id + ";faces-redirect=true";
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
        if (actorId == "" || actorId == null) {
            LOG.info("addExistingActorToSeries. actorId is \"\" or null");
        }
        Actor actor = searchActorById(actorListNotInSeries, actorId);
        actorListNotInSeries.remove(actor);
        series.getActors().add(actor);

    }

    public void addNewActorToSeries() {
        LOG.info("addNewActorToSeries ");
        actorFacade.create(newActor);
        series.getActors().add(newActor);
        LOG.info("addNewActorToSeries end");

    }

    public void saveActor(Actor actor) {
        actorFacade.update(actor);
    }

    public void initialNewActor() {
        LOG.info("initalNewActor");
        newActor = new Actor();
    }

    public void initialNewSeason() {
        LOG.info("initalNewSeason");
        newSeason = new Season();
    }

    public void initialNewEpisode(Season season) {
        LOG.info("initalNewSeason");
        newEpisode = new Episode();
        selectedSeason = season;
    }

    public void removeActorFromSeries(Actor actor) {
        series.getActors().remove(actor);
        actorListNotInSeries.add(actor);
    }

    public void saveSeries() {
        LOG.info("saveSeries");
        String nameOfPhoto = series.getTitle() + series.getId();
//        saveImageToDirectory(nameOfPhoto);
        series.setPathOfPhoto(nameOfPhoto);
        seriesFacade.saveSeries(series);
        LOG.info("linux? " + SystemUtils.IS_OS_LINUX);
        LOG.info("windows? " + SystemUtils.IS_OS_WINDOWS);
    }

    public void resetActor(Actor actor) {
        Actor findActorById = actorFacade.findActorById(actor.getId());

        List<Actor> actors = series.getActors();
        for (int i = 0; i < actors.size(); i++) {
            Actor a = actors.get(i);
            if (a.getId() == actor.getId()) {
                actors.add(i, findActorById);
            }

        }
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
        LOG.info("setIdOfSeries. Id is " + idOfSeries);
        this.idOfSeries = idOfSeries;
//        loadDatabaseData();

    }

    public Actor getNewActor() {
//        LOG.info("getNewActor name: " + newActor.getName());
        return newActor;
    }

    public void setNewActor(Actor newActor) {
        this.newActor = newActor;
    }

    public void removeEpisodeFromSeason(Season season, Episode episode) {
        for (Season s : series.getSeasons()) {
            if (s.equals(season)) {
                s.getEpisodes().remove(episode);
                return;
            }

        }
        LOG.info("Error in removeEpisodeFromSeason. Not found episode in season");
    }

    public void deleteSeasion(Season season) {
        LOG.info("deleteSeasion");
        LOG.info("akt season" + season.toString());
        for (Season s : series.getSeasons()) {
            LOG.info("in for, season" + season.toString());
            if (s.equals(season)) {
                series.getSeasons().remove(season);
                return;
            }

        }
        LOG.info("Error in deleteSeasion. Not found season in series");
    }

    public Season getNewSeason() {
        return newSeason;
    }

    public void setNewSeason(Season newSeason) {
        this.newSeason = newSeason;
    }

    public void addNewSeason() {
        LOG.info("addSeason fg");
        series.getSeasons().add(newSeason);
    }

    public void addNewEpisode() {
        LOG.info("addNewEpisode");
        //        newEpisode.setSeason(selectedSeason);

        List<Season> seasons = series.getSeasons();
        for (Season season : seasons) {
            if (season.equals(selectedSeason)) {
                if (null == season.getEpisodes()) {
                    LOG.info("addNewEpisode. Season.getEpisodes() was null ");
                    season.setEpisodes(new ArrayList<Episode>());
                }
                season.getEpisodes().add(newEpisode);
                return;
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////
    private static String PATH = "/series/";
    private UploadedFile uploadedFile;
    private StreamedContent image;

    public void saveImageToDirectory(String nameOfImage) {
        LOG.info("saveImageToDirectory");
        createDirectory();

        try {
            Path file = Paths.get(PATH + nameOfImage);
            LOG.info("saveImageToDirectory " + file.toString());
            LOG.info("saveImageToDirectory " + file.getFileName().toString());
            InputStream inputstream = uploadedFile.getInputstream();
            Files.copy(inputstream, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            LOG.info("saveImageToDirectory Exception" + ex.toString());

        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(uploadedFile.getFileName() + " is successfully uploaded."));
    }

    public void createDirectory() {
        LOG.info("createDirectory");
        File directory = new File(PATH);

        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (SecurityException se) {
                Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, se);
            }
        }
    }

    public void imageUpload(FileUploadEvent event) {
        LOG.info("imageUpload");
        uploadedFile = event.getFile();
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        LOG.info("setUploadedFile");
        this.uploadedFile = uploadedFile;
    }

    public StreamedContent getImage() {
        LOG.info("getImage function");

        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            LOG.info("generate right URL");
            //http://stackoverflow.com/questions/8207325/display-dynamic-image-from-database-with-pgraphicimage-and-streamedcontent
            return new DefaultStreamedContent();
        }
        try {
            if (uploadedFile != null) {
                return new DefaultStreamedContent(uploadedFile.getInputstream());
            }
            if (!series.getPathOfPhoto().equals("")) {
                LOG.info("Series has path of photo:   " + series.getPathOfPhoto() + " ");

                File file = new File(PATH + series.getPathOfPhoto());
                LOG.info("FilePath: " + file.getAbsolutePath());

                FileInputStream fis;

                fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                StreamedContent content = new DefaultStreamedContent(bis);
                return content;

            } else {
                ClassLoader classLoader = getClass().getClassLoader();
                File noPicture = new File(classLoader.getResource("/series/noimages.png").getFile());
                LOG.info("getImage noPicture:   " + noPicture.toString());
                return new DefaultStreamedContent(new FileInputStream(noPicture));
            }
        } catch (FileNotFoundException ex) {
            LOG.info("File not found exception");
            Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            LOG.info("IOException by file load");
            Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public Season getSelectedSeason() {
        return selectedSeason;
    }

    public void setSelectedSeason(Season selectedSeason) {
        this.selectedSeason = selectedSeason;
    }

    public void resetUploadedFile(AjaxBehaviorEvent event) {
        uploadedFile = null;
    }

    public UploadedFile getUploadedFile() {

        return uploadedFile;
    }

    public Episode getNewEpisode() {
        return newEpisode;
    }

    public void setNewEpisode(Episode newEpisode) {
        this.newEpisode = newEpisode;
    }

    public Sex getMale() {
        return Sex.MALE;
    }

    public Sex getFemale() {
        return Sex.FEMALE;
    }

    public Integer getActiveIndexToTabView() {
        return activeIndexToTabView;
    }

    public void setActiveIndexToTabView(Integer activeIndexToTabView) {
        this.activeIndexToTabView = activeIndexToTabView;
    }

    public void onTabChange(TabChangeEvent event) {
        TabView tabView = (TabView) event.getComponent();
        activeIndexToTabView = tabView.getChildren().indexOf(event.getTab());
    }

}
