package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "Series.findSeriesByName", 
        query = "SELECT s FROM Series s WHERE s.title = :title"),
    @NamedQuery(name = "Series.findSeriesById", 
        query = "SELECT s FROM Series s WHERE s.id = :id"),
    @NamedQuery(name = "Series.findActorsBySeriesId", 
        query = "SELECT s.actors FROM Series s WHERE s.id = :id"),
    @NamedQuery(name = "Series.findActorsNotInSeriesBySeriesId", 
        query = "SELECT s.actors FROM Series s WHERE s.id != :id")
}) 

public class Series extends Movie implements Serializable {

    @OneToMany(mappedBy = "series")
    private List<Season> seasons;

    @Column(length=1000) 
    private String description;
    
    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public Series() {
        //it is bean
    }

    @Override
    public String toString() {
        return "Series{" + "seasons=" + seasons + '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTitle() {
        return super.getTitle(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
