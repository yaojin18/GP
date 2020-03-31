package com.gp.rabbit.amqp.admin;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gp.rabbit.amqp")
public class AdminTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AdminTest.class);
        RabbitAdmin admin = context.getBean(RabbitAdmin.class);

        admin.declareExchange(new DirectExchange("GP_ADMIN_EXCHANGE", false, false));
        admin.declareQueue(new Queue("GP_ADMIN_QUEUE", false, false, false));

        admin.declareBinding(new Binding("GP_ADMIN_QUEUE", Binding.DestinationType.QUEUE,
                "GP_ADMIN_EXCHANGE", "admin", null));


    }
}
