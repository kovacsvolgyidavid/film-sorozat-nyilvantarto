
package xyz.codingmentor.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.primefaces.model.UploadedFile;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.service.EntityFacade;

@Named
@RequestScoped
public class Registration {
    
    @Inject
    private EntityFacade entityFacade;
    private UploadedFile uploadedFile;
    private User user;
    
    @PostConstruct
    private void init(){
        user = new User();
    }
    
    public void signIn(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        TypedQuery<User> username = entityFacade.getEntityManager().createNamedQuery("findUserByUsername", User.class);
        username.setParameter("username", user.getUsername());
        
        try {
            username.getSingleResult();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This uername is already taken!", "Error!"));
        } catch (NoResultException noResultException) {
            if (uploadedFile.getFileName().equals("")) {
                user.setPathOfPhoto("\\path\\resources\\user.jpg");
            } else {
                uploadPicture();
            }

            user.setRank("User");
            user.setMoviePerPage(50);
            entityFacade.create(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The registration is successful."));
        }
    }
    
    public void uploadPicture() {
        createDirectory();

        FacesMessage message = new FacesMessage(uploadedFile.getFileName() + " is successfully uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);

        InputStream input;
        try {
            input = uploadedFile.getInputstream();
            Path file = Paths.get("\\path\\resources\\" + uploadedFile.getFileName());
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            user.setPathOfPhoto(file.toString());
        } catch (IOException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createDirectory() {
        File directory = new File("\\path\\resources");

        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (SecurityException se) {
                //handle it
            }
        }
    }
    
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }   
}
