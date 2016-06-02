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
    
    private List<ProductDTO> products;
    
    @PostConstruct
    public void init(){
        products = new ArrayList<>();
    }
    
    public void referesh(){
        products = topicService.getDeletedProductDTOs();
    }
    
    public String back(){
        topicService.setExpectedProductDTO(new ProductDTO());
        topicService.setReadTopicCreate(false);
        topicService.setReadTopicDelete(false);
        topicService.getDeletedProductDTOs().clear();
        return "index.xhtml?faces-redirect=true";
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }   
}
