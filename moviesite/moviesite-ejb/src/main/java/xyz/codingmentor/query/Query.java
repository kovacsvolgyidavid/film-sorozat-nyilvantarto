package xyz.codingmentor.query;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class Query {
    @PersistenceContext (unitName = "MoviePU")
    private EntityManager em;
    
}
