package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
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
    
    @Column(name = "YEAR_OF_RELEASE")
    @Temporal(TemporalType.DATE)
    private Calendar yearOfRelease;
    
    @ManyToMany
    @JoinTable (name = "MOVIE_DIRECTOR",
    joinColumns = @JoinColumn(name = "MOVIE_ID"), 
    inverseJoinColumns = @JoinColumn(name = "DIRECTOR_ID"))
    public Set<Director> directors;
    
    @ManyToMany
    @JoinTable (name = "MOVIE_ACTOR",
    joinColumns = @JoinColumn(name = "MOVIE_ID"), 
    inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    public Set<Actor> actors;
    
    @OneToMany (mappedBy = "shows")
    private Set<Comment> comments;

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

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }  

    public String getYearOfRelease() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(yearOfRelease.getTime());
    }

    public void setYearOfRelease(Calendar yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }  
}
