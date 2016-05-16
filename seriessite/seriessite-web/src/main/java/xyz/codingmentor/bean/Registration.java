package xyz.codingmentor.bean;
//Szia hogy vagy ? reméleme jól :( - faszság komment
import java.io.File;
import java.io.FileInputStream;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import xyz.codingmentor.enums.Groups;
import xyz.codingmentor.entity.Users;
import xyz.codingmentor.query.DatabaseQuery;
import xyz.codingmentor.service.EntityFacade;

@Named
@SessionScoped
public class Registration implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    @Inject
    private DatabaseQuery databaseQuery;

    private static final String PATH = "/path/resources/";
    private UploadedFile uploadedFile;
    private StreamedContent image;
    private Users user;

    @PostConstruct
    public void init() {
        user = new Users();
    }

    public void signIn() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
//        TypedQuery<User> username = entityFacade.getEntityManager().createNamedQuery("findUserByUsername", User.class);
//        username.setParameter("username", user.getUsername());

//        try {
//            username.getSingleResult();
        if (databaseQuery.findUserByUsername(user.getUsername()) != null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This uername is already taken!", "Error!"));
        } else {
            if (uploadedFile == null) {
                user.setPathOfPhoto(PATH + "user.jpg");
            } else {
                uploadPicture();
            }

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String text = user.getPassword();
                md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                String output = bigInt.toString(16);

                user.setPassword(output);

            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                System.out.println("Error");//TODO: itt majd valami logger kell
            }

            user.getGroups().add(Groups.USER);
            user.setMoviePerPage(50);
            entityFacade.create(user);
            user = new Users();
            uploadedFile = null;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The registration is successful."));
        }

//        } catch (NoResultException noResultException) {
        //}
    }

    public void uploadPicture() {
        createDirectory();

        try {
            InputStream inputstream = uploadedFile.getInputstream();
            String fullFileName = uploadedFile.getFileName();

            Path file = Paths.get(PATH + fullFileName);
            Files.copy(inputstream, file, StandardCopyOption.REPLACE_EXISTING);
            user.setPathOfPhoto(file.toString());
        } catch (IOException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }

//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(uploadedFile.getFileName() + " is successfully uploaded."));
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

    public void handleFileUpload(FileUploadEvent event) {
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
        try {
            if (uploadedFile == null) {
                image = new DefaultStreamedContent(new FileInputStream(PATH + "user.jpg"));
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

    public Users getUser() {
        return user;
    }

    public void setUsers(Users user) {
        this.user = user;
    }

    public String ujOldal() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
}
