import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author wwj
 * @date 2019/12/25----19:11
 */
public class JMSConsumer {
    public static final String DEFAULT_BROKER_BIND_URL = "tcp://192.168.25.128:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException
    {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);

        /*同步阻塞方式receive() ，订阅者或接收者调用MessageConsumer的receive()方法来接收消息，
        receive()将一直阻塞
        receive(long timeout) 按照给定的时间阻塞，到时间自动退出*/

        TextMessage textMessage = null;
        while(true)
        {
            textMessage = (TextMessage) messageConsumer.receive();
            if(null != textMessage)
            {
                System.out.println("***接受到的消息："+textMessage.getText());
            }else{
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();
    }

}
