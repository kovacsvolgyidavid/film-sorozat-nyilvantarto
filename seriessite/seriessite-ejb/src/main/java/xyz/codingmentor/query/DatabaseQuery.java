package xyz.codingmentor.query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import xyz.codingmentor.entity.User;
import javax.persistence.Query;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Director;
import xyz.codingmentor.entity.Episode;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.entity.Series;

@Stateless
public class DatabaseQuery {

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager entityManager;

    private Query query;

    public User findUserByUsername(String username) {
        query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username LIKE " + "'" + username + "'");

        try {
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    //Test input
    public void createTestSeries() {
        //Actors for serie1
        Actor actor1 = new Actor();
        Actor actor2 = new Actor();
        Actor actor3 = new Actor();

        actor1.setName("Johnie Depp");
        actor2.setName("Jack Sparrow");
        actor3.setName("Penge Pityu");

        List<Actor> actors1 = new ArrayList<>();
        actors1.add(actor1);
        actors1.add(actor2);
        actors1.add(actor3);
        //Directors for serie1
        Director director1 = new Director();
        director1.setName("Andy Vajna");

        List<Director> directors1 = new ArrayList<>();
        directors1.add(director1);
        //Episodes for serie1 season
        Episode episode1 = new Episode();
        episode1.setSerialNumber("12345");
        episode1.setTitle("episode1");

        Episode episode2 = new Episode();
        episode2.setSerialNumber("23456");
        episode2.setTitle("episode2");

        List<Episode> episodes1 = new ArrayList<>();
        episodes1.add(episode1);
        episodes1.add(episode2);
        //Seasons for serie1
        Season season1 = new Season();
        DateFormat df = new SimpleDateFormat("yyyy");
        try {
            Date date = df.parse("2011");
            season1.setDateOfRelease(date);
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        season1.setEpisodes(episodes1);
        
        Season season2 = new Season();
        df = new SimpleDateFormat("yyyy");
        try {
            Date date = df.parse("2010");
            season2.setDateOfRelease(date);
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        season2.setEpisodes(episodes1);
        
        List<Season> seasons1 = new ArrayList<>();
        seasons1.add(season1);
        seasons1.add(season2);
        //First serie
        Series serie1 = new Series();
        serie1.setActors(actors1);
        serie1.setDescription("Example description of the serie1");
        serie1.setDirectors(directors1);
        serie1.setPathOfPhoto("C:\\path\\resources\\testpic.jpg");
        serie1.setSeasons(seasons1);
        serie1.setTitle("Test Title 1");
        df = new SimpleDateFormat("yyyy");
        try {
            Date date = df.parse("2011");
            serie1.setYearOfRelease(date);
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        entityManager.persist(serie1);
        //Second serie
        /*
        Series serie2 = new Series();
        serie2.setActors(actors2);
        serie2.setDescription("Example description of the serie2");
        serie2.setDirectors(directors2);
        serie2.setPathOfPhoto("C:\\path\\resources\\testpic.jpg");
        serie1.setSeasons(seasons2);
        serie1.setTitle("Test Title 2");
        df = new SimpleDateFormat("yyyy");
        try {
            Date date = df.parse("1998");
            serie2.setYearOfRelease(date);
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }

    @PreDestroy
    public void destruct() {
        entityManager.close();
    }
}
