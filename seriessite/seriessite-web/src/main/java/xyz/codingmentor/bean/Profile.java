package xyz.codingmentor.bean;

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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import xyz.codingmentor.dto.UserDTO;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.enums.Sex;
import xyz.codingmentor.service.EntityFacade;

@Named
@SessionScoped
public class Profile implements Serializable {
    @Inject
    private EntityFacade entityFacade;
    private static final String PATH = "/path/resources/users/";
    private UploadedFile uploadedFile;
    private StreamedContent image;
    private User user;
    private UserDTO userDTO;
    private Enum[] sexes;
    private String username; 
    private boolean editingMyProfile;
    private boolean editingAllowed;

    @PostConstruct
    public void init() {
        sexes = Sex.class.getEnumConstants();
        userDTO = new UserDTO();
        userDTO.setUser(new User());
        editingMyProfile = true;
    }

    public String goToProfilePage() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String un = params.get("username");
        return "/user/profile?username=" + un + "&faces-redirect=true";
    }
    
    public void loadUserFromDatabase(boolean admin){
        user = entityFacade.read(User.class, username);
        
        if(username.equals(Usermanagement.getUsername())){
           editingAllowed = true;
           editingMyProfile = true;
        } else if(admin){
           editingAllowed = true;
           editingMyProfile = false;
        } else {
           editingAllowed = false;
           editingMyProfile = false;
        }   
    }

    public void saveUserData() {
        uploadPicture();
        entityFacade.update(user);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The modification was successful."));       
    }
    
    public void savePassword() {
        userDTO.setUser(user);
        entityFacade.update(userDTO.makeUser());
        user = userDTO.getUser();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The password has been changed."));
        RequestContext.getCurrentInstance().update("form:tabView:passwordTab");
    }

    public void onTabChange() {
        try {
            uploadedFile = null;

            if ("user.jpg".equals(user.getPathOfPhoto())) {
                ClassLoader classLoader = getClass().getClassLoader();
                File noPicture = new File(classLoader.getResource("/user.jpg").getFile());
                image = new DefaultStreamedContent(new FileInputStream(noPicture));
            } else {
                image = new DefaultStreamedContent(new FileInputStream(user.getPathOfPhoto()));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadPicture() {
        if (uploadedFile != null) {
            try {
                InputStream inputstream = uploadedFile.getInputstream();
                String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
                Path file = Paths.get(PATH + user.getUsername() + "." + extension);
                Files.copy(inputstream, file, StandardCopyOption.REPLACE_EXISTING);
                user.setPathOfPhoto(file.toString());
            } catch (IOException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public StreamedContent getImage() {
        try {
            if (uploadedFile == null) {
                if ("user.jpg".equals(user.getPathOfPhoto())) {
                    ClassLoader classLoader = getClass().getClassLoader();
                    File noPicture = new File(classLoader.getResource("/user.jpg").getFile());
                    image = new DefaultStreamedContent(new FileInputStream(noPicture));
                } else {
                    image = new DefaultStreamedContent(new FileInputStream(user.getPathOfPhoto()));
                }
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

    public boolean isEditingMyProfile() {
        return editingMyProfile;
    }

    public void setEditingMyProfile(boolean editingMyProfile) {
        this.editingMyProfile = editingMyProfile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEditingAllowed() {
        return editingAllowed;
    }

    public void setEditingAllowed(boolean editingAllowed) {
        this.editingAllowed = editingAllowed;
    }  
}