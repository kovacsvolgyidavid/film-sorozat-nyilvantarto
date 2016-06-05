package xyz.codingmentor.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import xyz.codingmentor.collective.dtio.ProductDTO;

@MessageDriven(mappedName = "jms/TopicDelete")
public class TopicDeleteListener implements MessageListener{
    @Inject
    TopicService topicService;
    private static final Logger LOGGER = Logger.getLogger(TopicDeleteListener.class.getName()); 
       
    @Override
    public void onMessage(Message message) {
        if(topicService.isReadTopicDelete()){
            try {
                ProductDTO productDTO = message.getBody(ProductDTO.class);
                topicService.addProductDTO(productDTO);

                LOGGER.info("One product has been deleted:\n" +
                        "Price: " + productDTO.getPrice() + "\n" +
                        "Type: " + productDTO.getType());
            } catch (JMSException ex) {
                Logger.getLogger(TopicDeleteListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
