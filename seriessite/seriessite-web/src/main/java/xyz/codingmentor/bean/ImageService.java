package xyz.codingmentor.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Dávid Kovácsvölgyi <kovacsvolgyi.david@gmail.com>
 */
@Named
@ApplicationScoped
public class ImageService {

    private StreamedContent image;

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String path = context.getExternalContext().getRequestParameterMap().get("path");
            if (path.equals("user.jpg") || path.equals("")) {
                ClassLoader classLoader = getClass().getClassLoader();
                File noPicture = new File(classLoader.getResource("/user.jpg").getFile());
                image = new DefaultStreamedContent(new FileInputStream(noPicture));
            } else {
                image = new DefaultStreamedContent(new FileInputStream(path));
            }
            return image;
        }
    }
}
