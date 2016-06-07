/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean.picture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import xyz.codingmentor.bean.Registration;


public class PictureHandler {

    private static final Logger LOG = Logger.getLogger(PictureHandler.class.getName());
    
    private static String PATH; //= "/series/";
    private UploadedFile uploadedFile;
    private StreamedContent image;

    public PictureHandler(String path) {
        
        PATH=path;
    }
    
    

    public void saveImageToDirectory(String nameOfImage) {
        createDirectory();

        try {
            InputStream inputstream = uploadedFile.getInputstream();
//            String fullFileName = uploadedFile.getFileName();
            Path file = Paths.get(PATH + nameOfImage);
            LOG.info("saveImageToDirectory " + file.toString());
            LOG.info("saveImageToDirectory " + file.getFileName().toString());

            Files.copy(inputstream, file, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
                        LOG.info("saveImageToDirectory excaption");

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
        LOG.info("imageUpload");
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
                LOG.info("getImage " + noPicture.toString());
                LOG.info("getImage " + noPicture.getPath().toString());
                image = new DefaultStreamedContent(new FileInputStream(noPicture));
            } else {
                image = new DefaultStreamedContent(uploadedFile.getInputstream());
            }
        } catch (Exception ex) {
//            LOG.info("Not found image. Exceptin in getImage function");
//            Logger.getLogger(SeriesEdit.class.getName()).log(Level.SEVERE, null, ex);
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

}
