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
            query = "SELECT a FROM Actor a JOIN a.series s WHERE s.yearOfRelease > :date "
    ),
    
    @NamedQuery(name = "seriesByDirectorOriginalNameEqualsName",
            query = "SELECT DISTINCT s FROM  Series s JOIN s.directors d WHERE d.originalName LIKE d.name "
    ),
        
    @NamedQuery(name = "seriesWithMoreEpisode",
            query = "Select s FROM Series s WHERE(Select Count(e) from Episode e, Season sn WHERE s.id = sn.series.id  AND sn.id = e.season.id) > :number"
    ),
        
    @NamedQuery(name = "seriesCommentedAfterGivenDate",
            query = "Select s FROM Series s JOIN s.comments c WHERE c.dateOfComment > :date"
    )        

})
public class Actor extends Person implements Serializable {

    @Column(name = "PLACE_OF_BIRTH")
    private String placeOfBirth;

    @Column(name = "OFFICIAL_WEBSITE")
    private String officialWebsite;

    @ManyToMany(cascade=CascadeType.MERGE, mappedBy = "actors")
    private List<Series> series;

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

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }


}
