package xyz.codingmentor.query;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import xyz.codingmentor.entity.User;
import javax.persistence.Query;

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

    @PreDestroy
    public void destruct() {
        entityManager.close();
    }
}
