package com.gp.ack;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生产者，用于测试消费者手工应答和重回队列
 *
 * @author YAOJIN18
 */
public class AckProducer {
    private final static String QUEUE_NAME = "TEST_ACK_QUEUE";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String msg = "test ack message";
        //声明队列 默认交换机Direct
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //发送消息
        for (int i = 0; i < 5; i++) {
            channel.basicPublish("", QUEUE_NAME, null, (msg + i).getBytes());
        }

        channel.close();
        conn.close();
    }

}
