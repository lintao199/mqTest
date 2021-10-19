package com.lin.rabbit.route;

import com.lin.rabbitMQ.constant.RouteConstants;
import com.lin.rabbitMQ.constant.SubscribeConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author LinTao
 * @version v1.0
 * @date 2021/10/19 9:06
 * @since v1.0
 */
public class RouteProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitUtil.getChannel();
        String[] a = {"warning", "info", "error"};
        channel.exchangeDeclare(RouteConstants.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        while (true) {
            System.out.println("输入消息:");
            String msg = new Scanner(System.in).nextLine();
            if ("exit".equals(msg)) {
                break;
            }
            String level = a[new Random().nextInt(a.length)];
            channel.basicPublish(RouteConstants.EXCHANGE_NAME, level, null, msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送完成:" + msg + " ;level:" + level);
        }
        channel.close();
    }
}
