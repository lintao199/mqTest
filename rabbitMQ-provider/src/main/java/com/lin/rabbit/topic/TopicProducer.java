package com.lin.rabbit.topic;

import com.lin.rabbitMQ.constant.TopicConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author LinTao
 * @version v1.0
 * @date 2021/10/19 10:02
 * @since v1.0
 */
public class TopicProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitUtil.getChannel();
        channel.exchangeDeclare(TopicConstants.EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        while (true) {
            System.out.println("请输入消息");
            String msg = new Scanner(System.in).nextLine();
            if ("exit".equals(msg)) {
                break;
            }
            System.out.println("请输入routeKey:");
            String routeKey = new Scanner(System.in).nextLine();


            channel.basicPublish(TopicConstants.EXCHANGE_NAME, routeKey, null, msg.getBytes());
        }

        channel.close();

    }
}
