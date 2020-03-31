package com.gp.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 普通确认模式
 *
 * @author YAOJIN18
 */
public class NormalConfirmProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String msg = "Hello world, Rabbit MQ, Normal Confirm";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //开启发送方确认模式
        channel.confirmSelect();

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        if (channel.waitForConfirms()) {
            System.out.println("消息发送成功");
        }

        channel.close();
        conn.close();

    }
}
