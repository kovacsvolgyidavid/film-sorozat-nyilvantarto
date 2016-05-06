package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Actor extends Person implements Serializable {
    
    @Column(name="PLACE_OF_BIRTH")
    private String placeOfBirth;
    
    @Column(name="OFFICIAL_WEBSITE")
    private String officialWebsite;

    @ManyToMany(mappedBy = "actors", cascade = CascadeType.ALL)
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
        return officialWebsite;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
