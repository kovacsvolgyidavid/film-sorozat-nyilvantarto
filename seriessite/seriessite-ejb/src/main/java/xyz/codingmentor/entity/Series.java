package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
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

    @OneToMany(mappedBy = "series")
    private List<Season> seasons;

    @Column(length = 1000)
    private String description;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "SERIES_ACTOR", 
            joinColumns = @JoinColumn(name = "SERIES_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    private List<Actor> actors;
    
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
  

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

  

    
}
