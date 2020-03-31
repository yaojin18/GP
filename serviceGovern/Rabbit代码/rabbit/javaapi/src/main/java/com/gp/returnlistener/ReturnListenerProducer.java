package com.gp.returnlistener;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 当消息无法匹配到队列时，会发回给生产者
 */
public class ReturnListenerProducer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("===监听器收到无法路由，被返回的消息===");
                System.out.println("replyText: " + replyText);

            }
        });

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .build();


        channel.basicPublish("", "gp", true, properties, "test return listener".getBytes());

        channel.close();
        conn.close();


    }
}
