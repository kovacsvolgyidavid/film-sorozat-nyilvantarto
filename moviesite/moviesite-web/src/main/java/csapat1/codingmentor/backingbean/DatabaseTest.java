package csapat1.codingmentor.backingbean;


import csapat1.codingmentor.entity.Movie;
import csapat1.codingmentor.service.EntityFacade;
import java.util.Calendar;
import javax.inject.Inject;
import javax.inject.Named;



@Named
public class DatabaseTest {
    
    private Movie movie;
    
    @Inject
    private EntityFacade entityFacade;

    public DatabaseTest() {
    }

    
    @PostConstruct
    private void init(){
        Calendar calendar = Calendar.getInstance();
        
        movie = new Movie();
        movie.setTitle("Feast");
        movie.setYearOfRelease(calendar);
        movie.setPathOfPhoto("path/image.jpg");
        entityFacade.create(movie);
        
        movie = entityFacade.read(Movie.class, 1L);
    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    public void createMovie(){
        Calendar calendar = Calendar.getInstance();
        
        movie = new Movie();
        movie.setTitle("Feast");
        movie.setYearOfRelease(calendar);
        movie.setPathOfPhoto("path/image.jpg");
        entityFacade.create(movie);
        
        movie = entityFacade.read(Movie.class, 1L);
    }
}
