/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.StreamedContent;
import xyz.codingmentor.entity.Movie;
import xyz.codingmentor.query.DatabaseQuery;
import xyz.codingmentor.service.EntityFacade;

/**
 *
 * @author Vendel
 */
@Named
@SessionScoped
public class Movies implements Serializable{
    @Inject
    private EntityFacade entityFacade;

    @Inject
    private DatabaseQuery databaseQuery;
    
    private List<Movie> movies;
    private StreamedContent image;
    
    public StreamedContent getImage(Movie movie){
        return image;
    }

    public EntityFacade getEntityFacade() {
        return entityFacade;
    }

    public void setEntityFacade(EntityFacade entityFacade) {
        this.entityFacade = entityFacade;
    }

    public DatabaseQuery getDatabaseQuery() {
        return databaseQuery;
    }

    public void setDatabaseQuery(DatabaseQuery databaseQuery) {
        this.databaseQuery = databaseQuery;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    

    public StreamedContent getImage() {
        return image;
    }

    public void setImage(StreamedContent image) {
        this.image = image;
    }
    
    
}
