package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;
    
    private String content;

    @Temporal(TemporalType.DATE)
    private Date dateOfComment;
    
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "series_fk", nullable = false)
    private Series series;
    
    //ez így nem megy Film-re nem hivatkozhat mivel nem entitás
    /*@OneToOne (fetch = FetchType.LAZY)    
    @JoinColumn(name = "film_fk", nullable = false)
    private Film film;*/
   

    public Comment() {
        //it is bean
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    /*public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }*/  
}
