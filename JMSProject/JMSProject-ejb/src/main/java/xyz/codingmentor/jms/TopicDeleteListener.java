package xyz.codingmentor.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import xyz.codingmentor.collective.dto.ProductDTO;

@MessageDriven(mappedName = "jms/TopicDelete")
public class TopicDeleteListener implements MessageListener{
    private static final Logger LOGGER = Logger.getLogger(TopicDeleteListener.class.getName());   
    @Inject
    private TopicService topicService;
       
    @Override
    public void onMessage(Message message) {
        if(topicService.isReadTopicDelete()){
            try {
                ProductDTO productDTO = message.getBody(ProductDTO.class);
                topicService.addProductDTO(productDTO);
                LOGGER.info("====================================");
                LOGGER.info("One product has been deleted:");
                LOGGER.info("Price: " + productDTO.getPrice());
                LOGGER.info("Type: " + productDTO.getType());
                LOGGER.info("====================================");
            } catch (JMSException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    } 
}
