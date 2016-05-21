package xyz.codingmentor.query;

import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import xyz.codingmentor.entity.User;
import javax.persistence.Query;
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
    
    public List<Series> findAllSeries(){
        query = entityManager.createQuery("SELECT s FROM Series s");
        try{
            return(List<Series>) query.getResultList();
        } catch(Exception e){
            return null;
        }
    }
    //This is for testing
    public void createExampleSeries(){
        Series series1 = new Series();
        Series series2 = new Series();
        series1.setTitle("testtitle1");
        series2.setTitle("testtitle2");
        entityManager.persist(series1);
        entityManager.persist(series2);
    }
    @PreDestroy
    public void destruct() {
        entityManager.close();
    }
}
