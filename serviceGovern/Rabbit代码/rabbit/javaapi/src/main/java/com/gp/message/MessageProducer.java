package com.gp.message;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 在消息中添加更多的属性
 */
public class MessageProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("name", "gp");
        headers.put("level", "top");

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000") // ttl 过期时间
                .headers(headers)
                .priority(5)//优先级，默认为5，配合队列的x-max-priority属性使用
                .messageId(String.valueOf(UUID.randomUUID()))
                .build();
        String msg = "Hello world, Rabbit MQ";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicPublish("", QUEUE_NAME, properties, msg.getBytes());
        channel.close();
        conn.close();


    }
}
