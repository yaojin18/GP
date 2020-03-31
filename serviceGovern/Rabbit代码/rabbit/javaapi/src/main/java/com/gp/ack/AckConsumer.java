package com.gp.ack;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 消息消费者，用于测试消费者手工应答和重回队列
 *
 * @author YAOJIN18
 */
public class AckConsumer {

    private final static String QUEUE_NAME = "TEST_ACK_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        Connection conn = factory.newConnection();
        final Channel channel = conn.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 创建消费者，并接收消息
        Consumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws java.io.IOException {

                String msg = new String(body, "UTF-8");
                if (msg.contains("拒收")) {
                    // 拒绝消息
                    channel.basicReject(envelope.getDeliveryTag(), false);
                } else if (msg.contains("异常")) {
                    channel.basicNack(envelope.getDeliveryTag(), true, false);
                } else {
                    channel.basicAck(envelope.getDeliveryTag(), true);
                }

            }
        };

        // 开启手动应答
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

}
