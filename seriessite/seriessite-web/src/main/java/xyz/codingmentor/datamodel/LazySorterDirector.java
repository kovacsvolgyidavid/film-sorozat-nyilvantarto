/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.datamodel;

import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;
import xyz.codingmentor.bean.FieldService;
import xyz.codingmentor.entity.Director;

/**
 *
 * @author Vendel
 */
public class LazySorterDirector implements Comparator<Director>{
 
    private String sortField;
     
    private SortOrder sortOrder;

    public LazySorterDirector(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
    @Override
    public int compare(Director o1, Director o2) {
        try {
            Field resultField = FieldService.getNamedField(Director.class,sortField);
            resultField.setAccessible(true);
            Object value1 = resultField.get(o1);
            Object value2 = resultField.get(o2);
            int value = ((Comparable)value1).compareTo(value2);          
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
