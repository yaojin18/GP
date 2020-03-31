package com.gp.limit;

import java.io.IOException;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 测试消费端限流，先启动
 *
 * @author YAOJIN18
 */
public class LimitConsumer {

    private final static String QUEUE_NAME = "TEST_LIMIT_QUEUE";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        Connection conn = factory.newConnection();

        final Channel channel = conn.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");

                System.out.println("Consumer1 received message: " + msg);
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        };

        //非自动确认消息的前提下，
        channel.basicQos(2);
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }

}
