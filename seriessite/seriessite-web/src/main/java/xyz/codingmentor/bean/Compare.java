/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import xyz.codingmentor.entity.Season;
import xyz.codingmentor.entity.Series;

/**
 *
 * @author keni
 */
@Named
@SessionScoped
public class Compare implements Serializable{
    Series series1;
    Series series2;
    Season actualSeason1;  
    Season actualSeason2;
    
}
