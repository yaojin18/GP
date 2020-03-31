package com.gp.dlx;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生产者，通过TTL测试死信队列
 *
 * @author YAOJIN18
 */
public class DlxProducer {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();
        String msg = "Hello world, Rabbit MQ, DLX Msg";

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();

        channel.basicPublish("", "TEST_DLX_QUEUE", properties, msg.getBytes());

        channel.close();
        conn.close();
    }
}
