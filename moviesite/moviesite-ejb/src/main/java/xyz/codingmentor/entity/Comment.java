package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @Column(name = "DATE_OF_COMMENT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfComment;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOW_ID")
    private Movie show;
    
//    @ManyToOne (fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID"/*, nullable = false*/)
//    private Users users;
   
    public Comment() {
        //it is bean
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Movie getShow() {
        return show;
    }

    public void setShow(Movie show) {
        this.show = show;
    }

//    public Users getUsers() {
//        return users;
//    }
//
//    public void setUsers(Users users) {
//        this.users = users;
    }
}
