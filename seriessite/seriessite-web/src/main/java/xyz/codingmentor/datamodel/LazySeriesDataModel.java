
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
import xyz.codingmentor.entity.Series;

/**
 *
 * @author Vendel
 */
public class LazySeriesDataModel extends LazyDataModel<Series> {

   private List<Series> datasource;
     
    public LazySeriesDataModel(List<Series> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public Series getRowData(String rowKey) {
        for(Series serie : datasource) {
            if(serie.getId().toString().equals(rowKey))
                return serie;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Series serie) {
        return serie.getId();
    }
 
    @Override
    public List<Series> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Series> data = new ArrayList<>();
 
        //filter
        for(Series serie : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field resultField = FieldService.getNamedField(Series.class, filterProperty);
                        resultField.setAccessible(true);
                        String fieldValue = resultField.get(serie).toString().toLowerCase();
 
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
                data.add(serie);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorterSeries(sortField, sortOrder));
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
