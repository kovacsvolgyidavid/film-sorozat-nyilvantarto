package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Series implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    private String title;
    private String  idOfDirectory;
    private String pathOfPhoto;


    public Series() {
        //it is bean
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdOfDirectory() {
        return idOfDirectory;
    }

    public void setIdOfDirectory(String idOfDirectory) {
        this.idOfDirectory = idOfDirectory;
    }

    public String getPathOfPhoto() {
        return pathOfPhoto;
    }

    public void setPathOfPhoto(String pathOfPhoto) {
        this.pathOfPhoto = pathOfPhoto;
    }

   
   

}
