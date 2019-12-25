import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wwj
 * @date 2019/12/25----19:23
 */
public class JMSProduce_Topic {
    public static final String DEFAULT_BROKER_BIND_URL = "tcp://192.168.25.128:61616";
    public static final String TOPIC_NAME = "Topic01";

    public static void main(String[] args) throws JMSException
    {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);

        for (int i = 1; i <=3; i++)
        {
            TextMessage textMessage = session.createTextMessage("topicMsg--" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("********主题消息发送到MQ完成");

    }

}
