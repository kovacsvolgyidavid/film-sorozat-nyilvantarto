/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import xyz.codingmentor.entity.Director;
import xyz.codingmentor.query.DatabaseQuery;
import xyz.codingmentor.service.EntityFacade;

/**
 *
 * @author Vendel
 */
@Named
@SessionScoped
public class Directors implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    @Inject
    private DatabaseQuery databaseQuery;

    private List<Director> directors;
    private StreamedContent image;

    @PostConstruct
    public void init() {
        directors = entityFacade.findAll(Director.class);
    }

   public StreamedContent getImage(Director director) {
        if (director != null) {
            try {
                if (director.getPathOfPhoto() != null) {
                    if(SystemUtils.IS_OS_WINDOWS){
                        image = new DefaultStreamedContent(new FileInputStream("C:" + director.getPathOfPhoto()));
                    }
                    else if(SystemUtils.IS_OS_LINUX){
                        image = new DefaultStreamedContent(new FileInputStream("home/" + director.getPathOfPhoto()));
                    }    
                } else {
                    image = new DefaultStreamedContent(new FileInputStream("/main/resources/directors/noimages.png"));
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Directors.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return image;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void deleteDirector(Director director) {

    }
}
