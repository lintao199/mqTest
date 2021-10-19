package com.lin.rabbit.topic;

import com.lin.rabbitMQ.constant.TopicConstants;
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
 * @date 2021/10/19 10:02
 * @since v1.0
 */
public class TopicConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel ch = RabbitUtil.getChannel();
        ch.exchangeDeclare(TopicConstants.EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //自动生成对列名,
        //非持久,独占,自动删除
        String queueName = ch.queueDeclare().getQueue();

        System.out.println("输入bindingKey,用空格隔开:");
        String[] a = new Scanner(System.in).nextLine().split("\\s");

        //把该队列,绑定到 topic_logs 交换机
        //允许使用多个 bindingKey
        for (String bindingKey : a) {
            ch.queueBind(queueName, TopicConstants.EXCHANGE_NAME, bindingKey);
        }

        System.out.println("等待接收数据");

        //收到消息后用来处理消息的回调对象
        DeliverCallback callback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            String routingKey = message.getEnvelope().getRoutingKey();
            System.out.println("收到: " + routingKey + " - " + msg);
        };

        //消费者取消时的回调对象
        CancelCallback cancel = consumerTag -> {
        };

        ch.basicConsume(queueName, true, callback, cancel);
    }
}
