package xyz.codingmentor.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import xyz.codingmentor.collective.dtio.ProductDTO;

@MessageDriven(mappedName = "jms/TopicCreate")
public class TopicCreateListener implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(TopicCreateListener.class.getName());

    @Inject
    TopicService topicService;

    @Override
    public void onMessage(Message message) {

        try {
            ProductDTO productDTO = message.getBody(ProductDTO.class);
            if (topicService.isReadTopicCreate() && 
                    productDTO.getPrice() < topicService.getExpectedProductDTO().getPrice() &&
                    productDTO.getType().equals(topicService.getExpectedProductDTO().getType())) {

                topicService.addProductDTO(productDTO);

                LOGGER.info("====================================\n"
                        + "One product has been created:\n"
                        + "Price: " + productDTO.getPrice() + "\n"
                        + "Type: " + productDTO.getType());
            }

        } catch (JMSException ex) {
            Logger.getLogger(TopicDeleteListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
