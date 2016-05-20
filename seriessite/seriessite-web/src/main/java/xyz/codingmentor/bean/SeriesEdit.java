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

    //Files are located in seriessite-web/src/main/resources/series
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
        s12.setId(1L);
        s12.setName("Zoli");

        Actor s22 = new Actor();
        s22.setId(2L);
        s22.setName("Zita");
        
        Actor s32 = new Actor();
        s32.setId(3L);
        s32.setName("Zsombor");

        actorListNotInSeries = new ArrayList<>();
        actorListNotInSeries.add(s12);
        actorListNotInSeries.add(s22);
        actorListNotInSeries.add(s32);
    }
    
    public String goToActorEditSite() {
        //1 is Actor edit it
        return "actorEdit.xhtml/?id="+1+",faces-redirect=true";
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
    
    
    

//    public void signIn() {
        //        FacesContext facesContext = FacesContext.getCurrentInstance();
        //
        //        if (databaseQuery.findUserByUsername(user.getUsername()) != null) {
        //            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This uername is already taken!", "Error!"));
        //        } else {
        //            if (uploadedFile == null) {
        //                user.setPathOfPhoto(PATH + "user.jpg");
        //            } else {
//                           actor.setPathOfPhoto(nameOfImage);
        //                saveImageToDirectory("imageName");
        //            }
        //
        //            try {
        //                MessageDigest md = MessageDigest.getInstance("SHA-256");
        //                String text = user.getPassword();
        //                md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        //                byte[] digest = md.digest();
        //                BigInteger bigInt = new BigInteger(1, digest);
        //                String output = bigInt.toString(16);
        //
        //                user.setPassword(output);
        //
        //            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
        //                System.out.println("Error");//TODO: itt majd valami logger kell
        //            }
        //
        //            user.getGroups().add(Groups.USER);
        //            user.setMoviePerPage(50);
    
        //            entityFacade.create(user);
        //            user = new Users();
        //            uploadedFile = null;
        //
        //            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The registration is successful."));
        //        }
        //
        ////        } catch (NoResultException noResultException) {
        //        //}
        //    }
        //
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

//        try {
//            image = new DefaultStreamedContent(uploadedFile.getInputstream());
//        } catch (IOException ex) {
//            Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
        try {
            if (uploadedFile == null) {
                ClassLoader classLoader = getClass().getClassLoader();
                ///Location: film-sorozat-nyilvantarto/seriessite/seriessite-web/src/main/resources/series
                //http://www.mkyong.com/java/java-read-a-file-from-resources-folder/
                File noPicture = new File(classLoader.getResource("series/noimages.png").getFile());

                LOG.info(noPicture.getParent().toString());
                LOG.info(noPicture.getAbsolutePath().toString());
                LOG.info(noPicture.getPath());
                image = new DefaultStreamedContent(new FileInputStream(noPicture));
            } else {
                LOG.info(this.getClass().getCanonicalName()+ "else ag in getImage function");
                image = new DefaultStreamedContent(uploadedFile.getInputstream());
            }
        } catch (Exception ex) {
            LOG.info("Exceptin in getImage function");
            Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, ex);
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


    public void addActorToSeries(){
        LOG.info("Here should add actor to series");
        
    }
    
    public void removeActorFromSeries(){
        LOG.info("Here should remove actor fromseries");
        
    }



    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
        actor = (Actor) event.getObject();
        LOG.info(actor.toString());
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

//    public String ujOldal() {
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//        return "login?faces-redirect=true";
//    }
}
