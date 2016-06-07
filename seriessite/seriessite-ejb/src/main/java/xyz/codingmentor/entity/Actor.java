package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Actor.findAll",
            query = "SELECT a FROM Actor a"),
    @NamedQuery(name = "Actor.findActorById",
            query = "SELECT a FROM Actor a WHERE a.id = :id"),
    @NamedQuery(name = "Actor.findSeriesByActorId",
            query = "SELECT a.series FROM Actor a WHERE a.id = :id"),
    @NamedQuery(name = "actorsFromSeriesAfterDate",
            query = "SELECT s FROM Series s WHERE s.yearOfRelease < :date"),
    @NamedQuery(name = "actorsFromSeriesAfterGivenDate",
            query = "SELECT DISTINCT a FROM Actor a JOIN a.series s WHERE s.yearOfRelease > :date "
    )
})
public class Actor extends Person implements Serializable {

    @Column(name = "PLACE_OF_BIRTH")
    private String placeOfBirth;

    @Column(name = "OFFICIAL_WEBSITE")
    private String officialWebsite;

    @ManyToMany(mappedBy = "actors")
    private List<Series> series;
    
  
    @ManyToMany(cascade=CascadeType.MERGE, mappedBy = "movieactors")
    private List<Movie> movies;

    public Actor() {
        //it is bean
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getOfficialWebsite() {
        if(officialWebsite!=null)
        return officialWebsite;
        else return "null";
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return  "Person{" + "id=" + getId() + ", name=" + getName() + ", sex=" + getSex() + ", dateOfBirth=" + getDateOfBirth() + ", pathOfPhoto=" + getPathOfPhoto() + '}' +
"Id: "+ getId() + " Actor{" + "placeOfBirth=" + placeOfBirth + ", officialWebsite=" + officialWebsite + ", series=" + series + '}';
    }


    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    
    
    
    
}
