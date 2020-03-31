package com.gp.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生产者，测试confirm模式
 *
 * @author YAOJIN18
 */
public class BatchConfirmProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String msg = "Hello world, Rabbit MQ, Batch Confirm";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        try {
            channel.confirmSelect();
            for (int i = 0; i < 5; i++) {
                // 发送消息
                channel.basicPublish("", QUEUE_NAME, null, (msg + "-" + i).getBytes());
            }

            // 批量确认结果，ack如果是multiple=true,代表ack里面的deliveryTag之前的消息都被确认了
            // 直到所有消息都发布，只要有一个未被broker确认就会IOException
            channel.waitForConfirmsOrDie();
            System.out.println("消息发送完毕，批量确认成功");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        channel.close();
        conn.close();
    }
}
