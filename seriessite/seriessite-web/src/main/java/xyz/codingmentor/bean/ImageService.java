package xyz.codingmentor.bean;

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
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String path = context.getExternalContext().getRequestParameterMap().get("path");
            image = new DefaultStreamedContent(new FileInputStream(path));
            if (image != null) {
                return image;
            } else {
                return new DefaultStreamedContent(new FileInputStream("/noimages.png"));
            }
        }
    }

}
