package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String content;

    @Temporal(TemporalType.DATE)
    private Date dateOfComment;
    
    private Long idOfSeries;

    public Comment() {
        //it is bean
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateOfComment() {
        return dateOfComment;
    }

    public void setDateOfComment(Date dateOfComment) {
        this.dateOfComment = dateOfComment;
    }

    public Long getIdOfSeries() {
        return idOfSeries;
    }

    public void setIdOfSeries(Long idOfSeries) {
        this.idOfSeries = idOfSeries;
    }

    
}
