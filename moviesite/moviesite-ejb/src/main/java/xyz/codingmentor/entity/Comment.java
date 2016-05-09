package xyz.codingmentor.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private Calendar dateOfComment;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOW_ID"/*, nullable = false*/)
    private Movie shows;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID"/*, nullable = false*/)
    private User users;
   
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

    public String getDateOfComment() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(dateOfComment.getTime());
    }

    public void setDateOfComment(Calendar dateOfComment) {
        this.dateOfComment = dateOfComment;
    }

    public Movie getShows() {
        return shows;
    }

    public void setShows(Movie shows) {
        this.shows = shows;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }  
}
