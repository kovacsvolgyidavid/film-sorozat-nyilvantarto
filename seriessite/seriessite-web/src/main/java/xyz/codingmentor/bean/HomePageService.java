

package xyz.codingmentor.bean;

import xyz.codingmentor.datamodel.LazyDirectorDataModel;
import xyz.codingmentor.datamodel.LazyMovieDataModel;
import xyz.codingmentor.datamodel.LazyActorDataModel;
import xyz.codingmentor.datamodel.LazySeriesDataModel;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Director;
import xyz.codingmentor.entity.Movie;
import xyz.codingmentor.entity.Series;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.query.DatabaseQuery;
import xyz.codingmentor.service.EntityFacade;

/**
 *
 * @author Vendel
 */
@Named
@SessionScoped
public class  HomePageService implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    @Inject
    private DatabaseQuery databaseQuery;
    private List<Series> series;
    private List<Actor> actors;
    private List<Movie> movies;
    private List<Director> directors;
    private final String blockStyle = "margin: 0 auto";
    private LazyDataModel<Series> lazyModelSeries;
    private LazyDataModel<Movie> lazyModelMovie;
    private LazyDataModel<Actor> lazyModelActor;
    private LazyDataModel<Director> lazyModelDirector;

    private User actualUser;
    private static final Logger LOG = Logger.getLogger(HomePageService.class.getName());

    @PostConstruct
    public void init() {
        String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        actualUser = databaseQuery.findUserByUsername(username);
        actors = entityFacade.findAll(Actor.class);
        directors = entityFacade.findAll(Director.class);
        movies = entityFacade.findAll(Movie.class);
        series = entityFacade.findAll(Series.class);
        lazyModelSeries= new LazySeriesDataModel(series,actualUser.getFavourites());
        lazyModelMovie= new LazyMovieDataModel(movies);
        lazyModelActor= new LazyActorDataModel(actors);
        lazyModelDirector= new LazyDirectorDataModel(directors);
        //Separate movies from series..todo
    }

    public LazyDataModel<Series> getLazyModelSeries() {
        return lazyModelSeries;
    }

    public void setLazyModelSeries(LazyDataModel<Series> lazyModelSeries) {
        this.lazyModelSeries = lazyModelSeries;
    }

    public LazyDataModel<Movie> getLazyModelMovie() {
        return lazyModelMovie;
    }

    public void setLazyModelMovie(LazyDataModel<Movie> lazyModelMovie) {
        this.lazyModelMovie = lazyModelMovie;
    }

    public LazyDataModel<Actor> getLazyModelActor() {
        return lazyModelActor;
    }

    public void setLazyModelActor(LazyDataModel<Actor> lazyModelActor) {
        this.lazyModelActor = lazyModelActor;
    }

    public LazyDataModel<Director> getLazyModelDirector() {
        return lazyModelDirector;
    }

    public void setLazyModelDirector(LazyDataModel<Director> lazyModelDirector) {
        this.lazyModelDirector = lazyModelDirector;
    }
    
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public String getBlockStyle() {
        return blockStyle;
    }

    public int getNumberOfEpisodes(Series serie) {
        int num = 0;
        for (int i = 0; i < serie.getSeasons().size(); i++) {
            num += serie.getSeasons().get(i).getEpisodes().size();
        }
        return num;
    }

    public String getPromoVideoLink(Series serie) {
        return serie.getSeasons().get(0).getLinkOfPromoVideo();
    }

    public void changeFavourites(Series serie) {

        if (!actualUser.getFavourites().contains(serie)) {
            actualUser.getFavourites().add(serie);
            entityFacade.update(actualUser);
        } else {
            actualUser.getFavourites().remove(serie);
            entityFacade.update(actualUser);
        }
    }

    public User getActualUser() {
        return actualUser;
    }

    public void deleteSerie(Series serie) {
        List<User> allusers = entityFacade.findAll(User.class);
        for (User alluser : allusers) {
            alluser.getFavourites().clear();
            entityFacade.update(alluser);
        }
        series.remove(serie);
        entityFacade.delete(serie);
    }

    public void deleteActor(Actor actor) {

        for (Movie movie : movies) {
            movie.getMovieactors().remove(actor);
            entityFacade.update(movie);
        }

        for (Series serie : series) {
            serie.getActors().remove(actor);
            serie.getMovieactors().remove(actor);
            entityFacade.update(serie);
        }

        actors.remove(actor);
        entityFacade.delete(actor);
    }

    public void deleteDirector(Director director) {

        for (Movie movie : movies) {
            movie.getDirectors().remove(director);
            entityFacade.update(movie);
        }

        for (Series serie : series) {
            serie.getSeriesdirectors().remove(director);
            serie.getDirectors().remove(director);
            entityFacade.update(serie);
        }

        directors.remove(director);
        entityFacade.delete(director);
    }

    public void deleteMovie(Movie movie) {
        movies.remove(movie);
        entityFacade.delete(movie);
    }

}
