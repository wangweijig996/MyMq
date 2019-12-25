import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wwj
 * @date 2019/12/25----19:03
 */
public class JMSProduce {
    public static final String DEFAULT_BROKER_BIND_URL = "tcp://192.168.25.128:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException
    {
        //1 创建连接工场,使用默认用户名密码，编码不再体现
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(DEFAULT_BROKER_BIND_URL);
        //2 获得连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 创建会话,此步骤有两个参数，第一个是否以事务的方式提交，第二个默认的签收方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //5 创建生产者
        MessageProducer messageProducer = session.createProducer(queue);

        for (int i = 1; i <=3; i++)
        {
            //6 创建消息
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            //7 通过消息生产者发布消息
            messageProducer.send(textMessage);
        }
        //8 关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****run is ok");

    }

}
