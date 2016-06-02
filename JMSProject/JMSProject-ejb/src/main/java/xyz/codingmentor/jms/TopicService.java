package xyz.codingmentor.jms;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Topic;
import xyz.codingmentor.collective.dtio.ProductDTO;

@Singleton
public class TopicService {
    private static final Logger LOGGER = Logger.getLogger(TopicService.class.getName());
    private boolean readTopicDelete = false;
    private boolean readTopicCreate = false;
    private ProductDTO expectedProductDTO;
    
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "jms/TopicCreate")
    private Topic topicCreate;

    @Resource(lookup = "jms/TopicDelete")
    private Topic topicDelete;

    private List<ProductDTO> deletedProductDTOs = new ArrayList();
    
    @PostConstruct
    public void init(){
        expectedProductDTO = new ProductDTO();
    }

    public boolean isReadTopicCreate() {
        return readTopicCreate;
    }

    public void setReadTopicCreate(boolean readTopicCreate) {
        this.readTopicCreate = readTopicCreate;
    }
    
    public boolean isReadTopicDelete() {
        return readTopicDelete;
    }

    public void setReadTopicDelete(boolean readTopicDelete) {
        this.readTopicDelete = readTopicDelete;
    }
    
    public void sendMessageToEntityDeleteTopic(ProductDTO productDTO) {
        jmsContext.createProducer().send(topicDelete, productDTO);
    }

    public void sendMessageToEntityCreateTopic(ProductDTO productDTO) {
        jmsContext.createProducer().send(topicCreate, productDTO);
    }

    public List<ProductDTO> getDeletedProductDTOs() {
        return deletedProductDTOs;
    }

    public void addDeletedproductDTO(ProductDTO productDTO) {
        deletedProductDTOs.add(productDTO);
    } 

    public ProductDTO getExpectedProductDTO() {
        return expectedProductDTO;
    }
    
    public void setExpectedProductDTO(ProductDTO productDTO){
        expectedProductDTO = productDTO;
    }
}
