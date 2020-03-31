package com.gp.dlx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 消息消费者，由于消费的代码被注释，10s后，消息会从正常队列TEST_DLX_QUEUE到达死信交换机DLX_EXCHANGE,然后路由到死信队列DLX_QUEUE
 *
 * @author YAOJIN18
 */
public class DlxConsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-dead-letter-exchange", "DLX_EXCHANGE");

        //声明队列
        channel.queueDeclare("TEST_DLX_QUEUE", false, false, false, arguments);
        //声明死信交换机
        channel.exchangeDeclare("DLX_EXCHANGE", "topic", false, false, null);
        //声明死信队列
        channel.queueDeclare("DLX_QUEUE", false, false, false, null);
        //绑定，此处dead letter routing key 设置为#
        channel.queueBind("DLX_QUEUE", "DLX_EXCHANGE", "#");
        System.out.println("Waiting for message...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message:" + msg);
            }
        };

        //开始获取消息
        channel.basicConsume("TEST_DLX_QUEUE", true, consumer);
    }
}
