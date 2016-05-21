package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Movie implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    private String title;
    
    @Column(name = "PATH_OF_PHOTO")
    private String pathOfPhoto;
    
    @Column(name = "DATE_OF_RELEASE")
    @Temporal(TemporalType.DATE)
    private Date yearOfRelease;
    
    @ManyToMany
    @JoinTable (name = "MOVIE_DIRECTOR",
    joinColumns = @JoinColumn(name = "MOVIE_ID"), 
    inverseJoinColumns = @JoinColumn(name = "DIRECTOR_ID"))
    public List<Director> directors;
    
    @ManyToMany
    @JoinTable (name = "MOVIE_ACTOR",
    joinColumns = @JoinColumn(name = "MOVIE_ID"), 
    inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    public List<Actor> actors;
    
    @OneToMany (mappedBy = "show")
    private List<Comment> comments;
    
    private String descreption;

    public Movie() {
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

    public String getPathOfPhoto() {
        return pathOfPhoto;
    }

    public void setPathOfPhoto(String pathOfPhoto) {
        this.pathOfPhoto = pathOfPhoto;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Date yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }
    
}
