package xyz.codingmentor.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import xyz.codingmentor.entity.Users;
import xyz.codingmentor.enums.Groups;
import xyz.codingmentor.enums.Sex;
import xyz.codingmentor.query.DatabaseQuery;
import xyz.codingmentor.service.EntityFacade;

@Named
@SessionScoped
public class Profil implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    @Inject
    private DatabaseQuery databaseQuery;
    
    @Inject
    private Registration registration;

    private static final String PATH = "/path/resources/";
    private UploadedFile uploadedFile;
    private StreamedContent image;
    private Users user;
    private Users myUsername;
    private String oldPassword;
    private final static Enum[] sexes = new Enum[2];

    @PostConstruct
    public void init() {
        sexes[0] = Sex.MALE;
        sexes[1] = Sex.FEMALE;
        //modifiedUser = new Users();
    }

    public String getUserProfile(Users user) {
        //originalUser = user;
        //resetUser(modifiedUser, originalUser);
        uploadedFile = null;
        this.user = user; //resetelj a copy metódussal
        return "/profil.xhtml";
    }
    
    public String getMyProfile() {
        user = entityFacade.read(Users.class, "aron");
//        user = entityFacade.read(Users.class, myUsername);
        return "profil?faces-redirect=true";
    }
    
    public void saveUserData() {
        uploadPicture();
        entityFacade.update(user);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The modification is successful."));
    }

    public void saveUserPassword() {
        //if(hashPassword(oldPassword).equals(entityFacade.read(Users.class, user.getUsername()).getPassword())){
        if(oldPassword.equals(entityFacade.read(Users.class, user.getUsername()).getPassword())){   
            user.setPassword(hashPassword(user.getPassword()));
            entityFacade.update(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The password has been changed."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The old password is incorrect.", "Error!"));
        }
    }
    
    public void onTabChange(TabChangeEvent event) {
        try {
            uploadedFile = null;
            //user = entityFacade.read(Users.class, user.getUsername());
            image = new DefaultStreamedContent(new FileInputStream(user.getPathOfPhoto()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadPicture() {
        createDirectory();
        if(uploadedFile != null){
            try {
                InputStream inputstream = uploadedFile.getInputstream();
                String fullFileName = uploadedFile.getFileName();

                Path file = Paths.get(PATH + fullFileName);
                Files.copy(inputstream, file, StandardCopyOption.REPLACE_EXISTING);
                user.setPathOfPhoto(file.toString());
            } catch (IOException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(Users myUsername) {
        this.myUsername = myUsername;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public StreamedContent getImage() {
        try {
            if (uploadedFile == null) {
                image = new DefaultStreamedContent(new FileInputStream(user.getPathOfPhoto()));
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

    public void handleFileUpload(FileUploadEvent event) {
        uploadedFile = event.getFile();

        try {
            image = new DefaultStreamedContent(uploadedFile.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Enum[] getSexes() {
        return sexes;
    }
    
    public String hashPassword(String password){
        String hashedPassword = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String text = user.getPassword();
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashedPassword = bigInt.toString(16);

            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashedPassword;     
    }
}
