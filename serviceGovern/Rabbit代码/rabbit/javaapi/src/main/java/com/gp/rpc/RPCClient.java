package com.gp.rpc;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RPCClient {

    private final static String REQUEST_QUEUE_NAME = "RPC_REQUEST";
    private final static String RESPONSE_QUEUE_NAME = "RPC_RESPONSE";
    private Consumer consumer;
    private Channel channel;

    public RPCClient() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
        channel.queueDeclare(RESPONSE_QUEUE_NAME, true, false, false, null);

    }


    public String getSquare(String msg) throws IOException, InterruptedException {
        //定义消息属性中的correlationId
        String correlationId = UUID.randomUUID().toString();

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .correlationId(correlationId)
                .replyTo(RESPONSE_QUEUE_NAME)
                .build();

        channel.basicPublish("", REQUEST_QUEUE_NAME, properties, msg.getBytes());
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                response.offer(new String(body, "UTF-8"));
            }
        };

        channel.basicConsume(RESPONSE_QUEUE_NAME, true, consumer);

        return response.take();
    }

    public static void main(String[] args) throws Exception {
        RPCClient rpcClient = new RPCClient();
        String result = rpcClient.getSquare("4");
        System.out.println("response is : " + result);


    }


}
