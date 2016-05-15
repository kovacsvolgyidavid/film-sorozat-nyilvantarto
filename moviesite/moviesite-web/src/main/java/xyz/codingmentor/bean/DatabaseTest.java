package xyz.codingmentor.bean;


import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Comment;
import xyz.codingmentor.entity.Director;
import xyz.codingmentor.entity.Episode;
import xyz.codingmentor.entity.Movie;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.service.EntityFacade;
import javax.inject.Inject;
import javax.inject.Named;


@Named
public class DatabaseTest {
    
    private Movie movie;
    private User user;
    private Actor actor;
    private Comment comment;
    private Director director;
    private Episode episode;
    private Season season;
    private Series series;
    
    @Inject
    private EntityFacade entityFacade;

//    @PostConstruct
//    private void init(){
//        
//    }
    
//    public void add(){
//        Calendar calendar = Calendar.getInstance();
//        
//        movie = new Movie();
//        movie.setTitle("Feast");
//        movie.setYearOfRelease(calendar);
//        movie.setPathOfPhoto("path/image.jpg");
//        entityFacade.create(movie);
//        
//        
//        user = new User();
//        user.setName("Kiss Peter");
//        user.setPassword("....");
//        user.setPathOfPhoto("/path/photo.jpg");
//        user.setSex(Sex.MALE);
//        user.setUsername("Peti");
//        user.setYearOfBirth(1984);
//        entityFacade.create(user);
//        
//        
//        actor = new Actor();
//        actor.setName("ImAnActor");
//        entityFacade.create(actor);
//        
//        
//        comment= new Comment();
//        comment.setContent("ImAComment");
//        entityFacade.create(comment);
//        
//        
//        director = new Director();
//        director.setName("Director");
//        entityFacade.create(director);
//        
//        
//        series = new Series();
//        series.setTitle("ImASeries");
//        entityFacade.create(series);
//        
//        
//        season = new Season();
//        season.setTitle("2ndSeason");
//        entityFacade.create(season);
//        
//        
//        episode = new Episode();
//        episode.setTitle("1stEpisode");
//        entityFacade.create(episode);
//        
//        entityFacade.update(actor);  
//    }  
}
