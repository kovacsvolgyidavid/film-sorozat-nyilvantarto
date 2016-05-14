package xyz.codingmentor.query;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import xyz.codingmentor.entity.Users;
import javax.persistence.Query;
import xyz.codingmentor.service.EntityFacade;

@Stateless
public class DatabaseQuery {

//    @PersistenceContext(unitName = "MoviePU")
//    private EntityManager entityManager;
    
//    @Inject
//    private EntityFacade entityFacade;

    private Query query;

    public Users findUserByUsername(String username) {
//        query = entityManager.createQuery(
//                "SELECT u FROM Users u WHERE u.username LIKE " + "'" + username + "'");
//
//        try {
//            return (Users) query.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
        return null;
    }

//    @PreDestroy
//    public void destruct() {
//        entityManager.close();
//    }
}
