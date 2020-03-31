package com.gp.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import com.gp.util.ResourceUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 测试生产者的confim模式
 *
 * @author YAOJIN18
 */
public class AsyncConfirmProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String msg = "Hello world, Rabbit MQ, Async Confim";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //维护未确认消息的deliveryTag
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //这里不会打印所有响应的ack，ack可能有多个，有可能一次确认多条，也有可能一次确认一条
        //异步监听确认和未确认的消息
        //重复运行前需要停掉之前的生产者，清空队列
        channel.addConfirmListener(new ConfirmListener() {

            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("Broker未确认消息，标识：" + deliveryTag);

                if (multiple) {
                    //headSet表示后面参数之前的所有元素，全部删除
                    confirmSet.headSet(deliveryTag + 1L).clear();
                } else {
                    confirmSet.remove(deliveryTag);
                }

                //这里添加重发的方法

            }

            public void handleAck(long deliveryTag, boolean multiple) throws IOException {

                //如果true表示批量执行了deliveryTag这个值以前（小于deliveryTag）的所有消息，如果为false的话表示单条确认
                System.out.println(String.format("Broker已确认消息，标识：%d,多个消息：%b", deliveryTag, multiple));
                if (multiple) {
                    //headSet表示后面参数之前的所有元素，全部删除
                    confirmSet.headSet(deliveryTag + 1L).clear();
                } else {
                    confirmSet.remove(deliveryTag);
                }

                System.out.println("未确认的消息" + confirmSet);
            }
        });

        //开启发送方确认模式
        channel.confirmSelect();
        for (int i = 0; i < 10; i++) {
            long nextSeqNo = channel.getNextPublishSeqNo();
            //发送消息
            channel.basicPublish("", QUEUE_NAME, null, (msg + "-" + i).getBytes());
            confirmSet.add(nextSeqNo);
        }

        System.out.println("所有消息：" + confirmSet);

        //不能直接关闭，不然可能无法收到后面的ACK
//		channel.close();
//		conn.close();
    }

}
