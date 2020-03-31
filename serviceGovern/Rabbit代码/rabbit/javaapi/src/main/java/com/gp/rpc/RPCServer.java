package com.gp.rpc;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class RPCServer {
    private final static String REQUEST_QUEUE_NAME = "RPC_REQUEST";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        Connection conn = factory.newConnection();
        final Channel channel = conn.createChannel();

        channel.queueDeclare(REQUEST_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                AMQP.BasicProperties replyProperties = new AMQP.BasicProperties.Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();

                String replyQueue = properties.getReplyTo();
                String msg = new String(body, "UTF-8");

                Double mSquare = Math.pow(Integer.parseInt(msg), 2);
                String repMsg = String.valueOf(mSquare);

                channel.basicPublish("", replyQueue, replyProperties, repMsg.getBytes());
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(REQUEST_QUEUE_NAME, false, consumer);


    }
}
