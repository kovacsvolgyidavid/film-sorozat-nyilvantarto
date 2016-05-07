package csapat1.codingmentor.backingbean;


import csapat1.codingmentor.entity.Actor;
import csapat1.codingmentor.entity.Comment;
import csapat1.codingmentor.entity.Director;
import csapat1.codingmentor.entity.Episode;
import csapat1.codingmentor.entity.Movie;
import csapat1.codingmentor.entity.Season;
import csapat1.codingmentor.entity.Series;
import csapat1.codingmentor.entity.User;
import csapat1.codingmentor.enums.Sex;
import csapat1.codingmentor.service.EntityFacade;
import java.util.Calendar;
<<<<<<< HEAD
import javax.annotation.PostConstruct;
=======
>>>>>>> 34fd7b9dc81641a272cbb6fc71c3c0b528088dab
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

<<<<<<< HEAD
    public DatabaseTest() {
    }

    
//    @PostConstruct
//    private void init(){
//        Calendar calendar = Calendar.getInstance();
//        
//        movie = new Movie();
//        movie.setTitle("Feast");
//        movie.setYearOfRelease(calendar);
//        movie.setPathOfPhoto("path/image.jpg");
//        entityFacade.create(movie);
//        
//        movie = entityFacade.read(Movie.class, 1L);
//    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    public void createMovie(){
=======
//    @PostConstruct
//    private void init(){
//        
//    }
    
    public void add(){
>>>>>>> 34fd7b9dc81641a272cbb6fc71c3c0b528088dab
        Calendar calendar = Calendar.getInstance();
        
        movie = new Movie();
        movie.setTitle("Feast");
        movie.setYearOfRelease(calendar);
        movie.setPathOfPhoto("path/image.jpg");
        entityFacade.create(movie);
        
<<<<<<< HEAD
        movie = entityFacade.read(Movie.class, 1L);
    }
=======
        
        user = new User();
        user.setName("Kiss Peter");
        user.setPassword("....");
        user.setPathOfPhoto("/path/photo.jpg");
        user.setSex(Sex.MALE);
        user.setUsername("Peti");
        user.setYearOfBirth(1984);
        entityFacade.create(user);
        
        
        actor = new Actor();
        actor.setName("ImAnActor");
        entityFacade.create(actor);
        
        
        comment= new Comment();
        comment.setContent("ImAComment");
        entityFacade.create(comment);
        
        
        director = new Director();
        director.setName("Director");
        entityFacade.create(director);
        
        
        series = new Series();
        series.setTitle("ImASeries");
        entityFacade.create(series);
        
        
        season = new Season();
        season.setTitle("2ndSeason");
        entityFacade.create(season);
        
        
        episode = new Episode();
        episode.setTitle("1stEpisode");
        entityFacade.create(episode);
        
        entityFacade.update(actor);  
    }  
>>>>>>> 34fd7b9dc81641a272cbb6fc71c3c0b528088dab
}
