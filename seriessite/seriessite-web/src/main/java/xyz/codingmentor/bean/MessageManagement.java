package xyz.codingmentor.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.codingmentor.collective.dtio.ProductDTO;
import xyz.codingmentor.entity.Product;
import xyz.codingmentor.collective.enums.Type;
import xyz.codingmentor.jms.TopicService;
import xyz.codingmentor.service.EntityFacade;

@Named
@SessionScoped
public class MessageManagement implements Serializable {

    @Inject
    private EntityFacade entityFacade;

    private Enum[] types;
    private Product product;
    private Type type;
    private List<Product> products;
    
    private ProductDTO productDTO;
    
    @Inject
    TopicService topicService;

    @PostConstruct
    public void init() {
        product = new Product();
        types = Type.class.getEnumConstants();
        products = entityFacade.findAll(Product.class);
    }
    
    public void addProduct() {
        products.add(product);
        entityFacade.create(product);

        productDTO = new ProductDTO(product.getPrice(), product.getType().toString());
        topicService.sendMessageToEntityCreateTopic(productDTO);
        
        product = new Product();
    }
    
    public void deleteProduct(Product product) {
        products.remove(product);
        Product deletedProduct = entityFacade.read(Product.class, product.getId());
        entityFacade.delete(deletedProduct);

        productDTO = new ProductDTO(product.getPrice(), product.getType().toString());
        topicService.sendMessageToEntityDeleteTopic(productDTO);
    }    

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Enum[] getTypes() {
        return types;
    }
}
