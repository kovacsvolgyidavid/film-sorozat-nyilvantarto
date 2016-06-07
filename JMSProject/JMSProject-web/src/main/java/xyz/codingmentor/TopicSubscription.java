package xyz.codingmentor;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.codingmentor.collective.dto.ProductDTO;
import xyz.codingmentor.collective.enums.Type;
import xyz.codingmentor.jms.TopicService;

@Named
@SessionScoped
public class TopicSubscription implements Serializable {

    @Inject
    TopicService topicService;

    private ProductDTO productDTO;
    
    private Type type;
    private Enum[] types;

    @PostConstruct
    public void init() {
        types = Type.class.getEnumConstants();
        productDTO = new ProductDTO();
    }

    public String subscribeForTopicDelete() {
        topicService.setReadTopicCreate(false);
        topicService.setReadTopicDelete(true);
        return "result.xhtml?faces-redirect=true";
    }

    public String subscribeForTopicCreate() {
        topicService.setReadTopicDelete(false);
        topicService.setReadTopicCreate(true);
        topicService.setExpectedProductDTO(productDTO);
        return "result.xhtml?faces-redirect=true";
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public Enum[] getTypes() {
        return types;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }
}
