/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.Series;

@Stateless
public class SeriesFacade {
        @PersistenceContext (unitName = "MoviePU")
    private EntityManager em;

    public SeriesFacade() {
    }
        
    public Series findSeriesById(Long id){
        TypedQuery<Series> ser = em.createQuery("SELECT s FROM Series s WHERE s.id = :id", Series.class);
        ser.setParameter("id", id);
        return ser.getSingleResult();
       
    }
}
