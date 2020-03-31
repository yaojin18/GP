package com.gp.limit;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * 消费消费者，测试消费端限流，先启动
 */
public class LimitConsumer2 {

    private final static String QUEUE_NAME = "TEST_LIMIT_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        Connection conn = factory.newConnection();
        final Channel channel = conn.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "UTF-8");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Consumer2 Received msg: " + msg);
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        };

        //非自动确认消息的前提下，如果一定数目的消息（通过consumer或者channel设置Qos值）未被确认前，不进行消费新的消息
        //因为consumer2的处理速率很慢，收到两条消息后都没有发送ack,队列不会再发送消息给consumer2
        channel.basicQos(2);
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }


}
