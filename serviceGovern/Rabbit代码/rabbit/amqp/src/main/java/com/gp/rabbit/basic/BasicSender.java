package com.gp.rabbit.basic;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gp.rabbit.basic")
public class BasicSender {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasicSender.class);
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        rabbitTemplate.convertAndSend("", "GP_BASIC_FIRST_QUEUE", "----A Direct Msg");
        rabbitTemplate.convertAndSend("GP_BASIC_TOPIC_EXCHANGE", "sh.gp.s", "---a topic msg");

    }
}
