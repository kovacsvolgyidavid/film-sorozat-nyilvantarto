package csapat1.codingmentor.backingbean;


import csapat1.codingmentor.entity.Movie;
import csapat1.codingmentor.service.EntityFacade;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;


@Named
public class DatabaseTest {
    
    private Movie movie;
    
    @Inject
    private EntityFacade entityFacade;

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
}
