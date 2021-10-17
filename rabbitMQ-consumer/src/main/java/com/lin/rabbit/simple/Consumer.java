package com.lin.rabbit.simple;

import com.lin.rabbitMQ.constant.SimpleConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: LinTao
 * @DateTime: 2021/10/14 20:43
 * 消费者
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitUtil.getChannel();
        System.out.println("等待接收消息.........");

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String s = new String(message.getBody());
                System.out.println(s);
            }
        };
        CancelCallback cancelCallback = consumerTag -> System.out.println("消息消费被中断");

        channel.basicConsume(SimpleConstants.QUEUE_NAME, SimpleConstants.AUTO_ACK, deliverCallback, cancelCallback);

    }
}
