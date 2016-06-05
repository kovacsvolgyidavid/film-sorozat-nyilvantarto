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
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import xyz.codingmentor.dto.UserDTO;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.enums.Groups;
import xyz.codingmentor.enums.Sex;
import xyz.codingmentor.service.EntityFacade;

@Named
@SessionScoped
public class Registration implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    private static final String PATH = "/path/resources/";
    private UploadedFile uploadedFile;
    private StreamedContent image;
    private Enum[] sexes;
    private UserDTO dtoUser;
    private String connfirmPassword;
    
    @PostConstruct
    public void init() {
        sexes = Sex.class.getEnumConstants();
        dtoUser = new UserDTO();
    }

    public String registrate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        if (uploadedFile == null) {
            dtoUser.getUser().setPathOfPhoto("user.jpg");
        } else {
            uploadPicture();
        }

        dtoUser.getUser().setGroups(Groups.USER);
        dtoUser.getUser().setMoviePerPage(50);
        entityFacade.create(dtoUser.makeUser());
        dtoUser.setUser(new User());
        uploadedFile = null;
        facesContext.addMessage(null, new FacesMessage("The registration was successful."));
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        return "/login.xhtml?faces-redirect=true";
    }
    
    public String redirectRegistration() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "/registration.xhtml?faces-redirect=true";
    }

    public void uploadPicture() {
        createDirectory();
        try {
            InputStream inputstream = uploadedFile.getInputstream();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            Path file = Paths.get(PATH + dtoUser.getUser().getUsername() + "." + extension);
            Files.copy(inputstream, file, StandardCopyOption.REPLACE_EXISTING);
            dtoUser.getUser().setPathOfPhoto(file.toString());
        } catch (IOException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createDirectory() {
        File directory = new File(PATH);
        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (SecurityException se) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, se);
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        uploadedFile = event.getFile();
        try {
            image = new DefaultStreamedContent(uploadedFile.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetPicture() {
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
                File noPicture = new File(classLoader.getResource("/user.jpg").getFile());
                image = new DefaultStreamedContent(new FileInputStream(noPicture));
            } else {
                image = new DefaultStreamedContent(uploadedFile.getInputstream());
            }
        } catch (Exception ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public String redirectLogin() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public Enum[] getSexes() {
        return sexes;
    }

    public EntityFacade getEntityFacade() {
        return entityFacade;
    }

    public void setEntityFacade(EntityFacade entityFacade) {
        this.entityFacade = entityFacade;
    }

    public UserDTO getDtoUser() {
        return dtoUser;
    }

    public void setDtoUser(UserDTO dtoUser) {
        this.dtoUser = dtoUser;
    }

    public String getConnfirmPassword() {
        return connfirmPassword;
    }

    public void setConnfirmPassword(String connfirmPassword) {
        this.connfirmPassword = connfirmPassword;
    }       
}
