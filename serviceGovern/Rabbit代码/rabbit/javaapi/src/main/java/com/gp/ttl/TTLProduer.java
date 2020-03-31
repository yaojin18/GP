package com.gp.ttl;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.*;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息生产者，通过TTL测试死信队列
 */
public class TTLProduer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String msg = "Hello world, Rabbit MQ, DLX Msg";

        Map<String, Object> argss = new HashMap<String, Object>();
        argss.put("x-message-ttl", 6000);

        channel.queueDeclare("TEST_TTL_QUEUE", false, false, false, argss);

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();

        channel.basicPublish("", "TEST_TTL_QUEUE", properties, msg.getBytes());

        connection.close();
        channel.close();

    }

}
