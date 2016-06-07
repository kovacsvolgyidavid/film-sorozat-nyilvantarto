
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
import xyz.codingmentor.entity.Movie;

/**
 *
 * @author Vendel
 */
public class LazyMovieDataModel extends LazyDataModel<Movie> {
 private List<Movie> datasource;
     
    public LazyMovieDataModel(List<Movie> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Movie getRowData(String rowKey) {
        for(Movie movie : datasource) {
            if(movie.getId().toString().equals(rowKey))
                return movie;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Movie movie) {
        return movie.getId();
    }
 
    @Override
    public List<Movie> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Movie> data = new ArrayList<>();
 
        //filter
        for(Movie movie : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field resultField = FieldService.getNamedField(Movie.class, filterProperty);
                        resultField.setAccessible(true);
                        String fieldValue = resultField.get(movie).toString().toLowerCase();
 
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
                data.add(movie);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorterMovie(sortField, sortOrder));
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
