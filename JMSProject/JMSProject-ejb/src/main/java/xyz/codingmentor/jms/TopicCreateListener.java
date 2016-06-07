package xyz.codingmentor.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import xyz.codingmentor.collective.dto.ProductDTO;

@MessageDriven(mappedName = "jms/TopicCreate")
public class TopicCreateListener implements MessageListener {
    private static final Logger LOGGER = Logger.getLogger(TopicCreateListener.class.getName());
    
    @Inject
    private TopicService topicService;
    
    @Override
    public void onMessage(Message message) {

        try {
            ProductDTO productDTO = message.getBody(ProductDTO.class);
            if (topicService.isReadTopicCreate() && 
                    productDTO.getPrice() < topicService.getExpectedProductDTO().getPrice() &&
                    productDTO.getType().equals(topicService.getExpectedProductDTO().getType())) {
                topicService.addProductDTO(productDTO);
                LOGGER.info("====================================");
                LOGGER.info("One product has been created:");
                LOGGER.info("Price: " + productDTO.getPrice());
                LOGGER.info("Type: " + productDTO.getType());
                LOGGER.info("====================================");
            }

        } catch (JMSException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
