package com.lin.rabbit.route;

import com.lin.rabbitMQ.constant.RouteConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author LinTao
 * @version v1.0
 * @date 2021/10/19 9:11
 * @since v1.0
 */
public class RouteConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitUtil.getChannel();

        channel.exchangeDeclare(RouteConstants.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();

        System.out.println("输入接收的日志级别，用空格隔开");

        String[] a = new Scanner(System.in).next().split("\\s");

        /**
         * direct交换机不支持多个路由订阅
         */
        for (String level : a) {
            channel.queueBind(queueName, RouteConstants.EXCHANGE_NAME, level);
        }
        System.out.println("等待接收数据");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            String routingKey = message.getEnvelope().getRoutingKey();
            System.out.println("收到: " + routingKey + " - " + msg);
        };

        CancelCallback cancelCallback = c -> {

        };
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }
}
