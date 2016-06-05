package xyz.codingmentor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.codingmentor.collective.dtio.ProductDTO;
import xyz.codingmentor.jms.TopicService;

@Named
@SessionScoped
public class Result implements Serializable{
    @Inject
    TopicService topicService;
    
    private List<ProductDTO> productDTOs;
    
    @PostConstruct
    public void init(){
        productDTOs = new ArrayList<>();
    }
    
    public void referesh(){
        productDTOs = topicService.getProductDTOs();
    }
    
    public String back(){
        topicService.setExpectedProductDTO(new ProductDTO());
        topicService.setReadTopicCreate(false);
        topicService.setReadTopicDelete(false);
        topicService.getProductDTOs().clear();
        return "topicsubscription.xhtml?faces-redirect=true";
    }

    public List<ProductDTO> getProductDTOs() {
        return productDTOs;
    }

    public void setProductDTOs(List<ProductDTO> productDTOs) {
        this.productDTOs = productDTOs;
    }   
}
