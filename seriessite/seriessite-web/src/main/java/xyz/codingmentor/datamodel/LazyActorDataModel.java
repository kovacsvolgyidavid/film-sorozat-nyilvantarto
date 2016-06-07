/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.datamodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import xyz.codingmentor.bean.FieldService;
import xyz.codingmentor.entity.Actor;
import xyz.codingmentor.entity.Series;

/**
 *
 * @author Vendel
 */
public class LazyActorDataModel extends LazyDataModel<Actor> {
private List<Actor> datasource;
     
    public LazyActorDataModel(List<Actor> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Actor getRowData(String rowKey) {
        for(Actor actor : datasource) {
            if(actor.getId().toString().equals(rowKey))
                return actor;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Actor actor) {
        return actor.getId();
    }
 
    @Override
    public List<Actor> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Actor> data = new ArrayList<>();
 
        //filter
        for(Actor actor : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field resultField = FieldService.getNamedField(Actor.class, filterProperty);
                        resultField.setAccessible(true);
                        String fieldValue = resultField.get(actor).toString().toLowerCase();
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString().toLowerCase())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(SecurityException | IllegalAccessException| IllegalArgumentException e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(actor);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorterActor(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}
