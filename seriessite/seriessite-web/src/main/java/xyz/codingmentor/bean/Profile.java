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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import xyz.codingmentor.dto.UserDTO;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.enums.Sex;
import xyz.codingmentor.query.DatabaseQuery;
import xyz.codingmentor.service.EntityFacade;

@Named
@SessionScoped
public class Profile implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    @Inject
    private DatabaseQuery databaseQuery;

    @Inject
    private Registration registration;

    private static final String PATH = "/path/resources/";
    private UploadedFile uploadedFile;
    private StreamedContent image;
    private User user;
    private UserDTO userDTO;
    private String oldPassword;
    private final static Enum[] sexes = new Enum[2];
    
    private boolean editMyProfile;

    @PostConstruct
    public void init() {
        sexes[0] = Sex.MALE;
        sexes[1] = Sex.FEMALE;

        userDTO = new UserDTO();
        userDTO.setUser(new User());
        
        editMyProfile = true;
    }

    public String getMyProfile() {
        editMyProfile = true;
        user = entityFacade.read(User.class, Usermanagement.getUsername());
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String username = params.get("username");

//        userDTO.setUser(user);
        return "/user/profile?username=" + username + ";faces-redirect=true";
    }

    public String getUserProfile(User user) {
        editMyProfile = false;
        uploadedFile = null;
        this.user = user;
//        userDTO.setUser(user);

//        FacesContext context = FacesContext.getCurrentInstance();
//        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
//        String username = params.get("username");
//        System.out.println("Here go to user side. UserID: " + username);
//        1 is Actor edit it
        return "/user/profile.xhtml?username=" + user.getUsername() + ";faces-redirect=true";
//        return "actorEdit.xhtml/?id="+1+",faces-redirect=true";
    }

    public void saveUserData() {
        uploadPicture();
        entityFacade.update(user);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The modification was successful."));
    }

    public void savePassword() {
        String passwordFromTable;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        if(editMyProfile)
            passwordFromTable = entityFacade.read(User.class, user.getUsername()).getPassword();
        else
            passwordFromTable = entityFacade.read(User.class, facesContext.getExternalContext().getRemoteUser()).getPassword();
        

        if (hashPassword(oldPassword).equals(passwordFromTable)) { //ezt tedd egy metódusba és azt hívd meg ajax-al a kliens oldali validáláshoz
            user.setPassword(hashPassword(userDTO.getPassword()));
            entityFacade.update(user);
            facesContext.addMessage(null, new FacesMessage("The password has been changed."));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Your password is incorrect.", "Error!"));
        }
    }

    public void savePasswordAdmin() {
        user.setPassword(hashPassword(userDTO.getPassword()));
        entityFacade.update(user);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The password has been changed."));
    }

    public void saveAdminPassword() {
        System.out.println("admin vagyok");
    }

    public void onTabChange(TabChangeEvent event) {
        try {
            uploadedFile = null;

            if (user.getPathOfPhoto().equals("user.jpg")) {
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
        createDirectory();
        if (uploadedFile != null) {
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public StreamedContent getImage() {
        try {
            if (uploadedFile == null) {
                if (user.getPathOfPhoto().equals("user.jpg")) {
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

    public String hashPassword(String password) {
        String hashedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String text = password;
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashedPassword = bigInt.toString(16);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashedPassword;
    }

    public boolean isEditMyProfile() {
        return editMyProfile;
    }

    public void setEditMyProfile(boolean editMyProfile) {
        this.editMyProfile = editMyProfile;
    }
}
