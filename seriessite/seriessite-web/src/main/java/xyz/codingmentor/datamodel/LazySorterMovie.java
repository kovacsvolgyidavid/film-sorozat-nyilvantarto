
package xyz.codingmentor.datamodel;

import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;
import xyz.codingmentor.bean.FieldService;
import xyz.codingmentor.entity.Movie;

/**
 *
 * @author Vendel
 */
public class LazySorterMovie implements Comparator<Movie>{
private String sortField;
     
    private SortOrder sortOrder;

    public LazySorterMovie(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        try {
            Field resultField = FieldService.getNamedField(Movie.class,sortField);
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
