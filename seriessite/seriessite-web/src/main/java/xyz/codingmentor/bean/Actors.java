package xyz.codingmentor.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.SystemUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.query.DatabaseQuery;
import xyz.codingmentor.service.EntityFacade;

/**
 *
 * @author Vendel
 */
@Named
@SessionScoped
public class Actors implements Serializable {
     @Inject
    private EntityFacade entityFacade;

    @Inject
    private DatabaseQuery databaseQuery;

    private List<Actor> actors;
    private StreamedContent image;
   
    @PostConstruct
    public void init() {
        actors = entityFacade.findAll(Actor.class);
    }
    
    public StreamedContent getImage(Actor actor) {
        if (actor != null) {
            try {
                if (actor.getPathOfPhoto() != null) {
                    if(SystemUtils.IS_OS_WINDOWS){
                        image = new DefaultStreamedContent(new FileInputStream("C:" + actor.getPathOfPhoto()));
                    }
                    else if(SystemUtils.IS_OS_LINUX){
                        image = new DefaultStreamedContent(new FileInputStream("home/" + actor.getPathOfPhoto()));
                    }    
                } else {
                    image = new DefaultStreamedContent(new FileInputStream("/main/resources/actors/noimages.png"));
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Actors.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return image;
    }

    public List<Actor> getActors() {
        return actors;
    }
    
    public void deleteActor(Actor actor){
        List<Series> allseries = entityFacade.findAll(Series.class);
        for (Series serie : allseries) {
            serie.getActors().remove(actor);
            entityFacade.update(serie);
        }
        actors.remove(actor);
        entityFacade.delete(actor);
    }
}
