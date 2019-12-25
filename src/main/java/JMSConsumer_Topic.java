import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wwj
 * @date 2019/12/25----19:41
 */
public class JMSConsumer_Topic {
    public static final String DEFAULT_BROKER_BIND_URL = "tcp://192.168.25.128:61616";
    public static final String TOPIC_NAME = "Topic01";

    public static void main(String[] args) throws JMSException
    {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener((message) -> {
            if(null != message && message instanceof TextMessage)
            {
                TextMessage textMessage = (TextMessage) message;
                try
                {
                    System.out.println("***收到topic："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
