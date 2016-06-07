package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import java.util.logging.Logger;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@NamedQueries({
    @NamedQuery(name = "Series.findSeriesByName",
            query = "SELECT s FROM Series s WHERE s.title = :title"),
    @NamedQuery(name = "Series.findSeriesById",
            query = "SELECT s FROM Series s WHERE s.id = :id"),
    @NamedQuery(name = "Series.findActorsBySeriesId",
            query = "SELECT s.actors FROM Series s WHERE s.id = :id"),
    @NamedQuery(name = "Series.findDirectorsBySeriesId",
            query = "SELECT s.directors FROM Series s WHERE s.id = :id"),
    @NamedQuery(name = "Series.findAll",
            query = "SELECT s FROM Series s")
})
public class Series extends Movie implements Serializable {
    
    @OneToMany(mappedBy = "series",cascade = CascadeType.ALL)
    private List<Season> seasons;

    @Column(length = 1000)
    private String description;

    private static final Logger LOG = Logger.getLogger(Series.class.getName());
    
    @ManyToMany( cascade = {CascadeType.PERSIST,CascadeType.MERGE } )
    @JoinTable(name = "SERIES_ACTOR", 
            joinColumns = @JoinColumn(name = "SERIES_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    private List<Actor> actors;
    
    @ManyToMany
    private List<Director> seriesdirectors;

    public Series() {
        //it is bean
    }

    
    public List<Actor> getActors() {
        return actors;
    }


    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public String toString() {
        return getId() + "    " + getTitle();
    }
  

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        LOG.info("setDescription");
        this.description = description;
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    public List<Director> getSeriesdirectors() {
        return seriesdirectors;
    }

    public void setSeriesdirectors(List<Director> seriesdirectors) {
        this.seriesdirectors = seriesdirectors;
    }

    

    
}
