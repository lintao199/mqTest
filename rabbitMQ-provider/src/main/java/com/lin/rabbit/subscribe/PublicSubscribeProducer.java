package com.lin.rabbit.subscribe;

import com.lin.rabbitMQ.constant.SubscribeConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author LinTao
 * @version v1.0
 * @date 2021/10/15 14:51
 * @since v1.0
 */
public class PublicSubscribeProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitUtil.getChannel();
        channel.exchangeDeclare(SubscribeConstants.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        while (true) {
            System.out.println("输入消息:");
            String msg = new Scanner(System.in).nextLine();
            if ("exit".equals(msg)) {
                break;
            }
            channel.basicPublish(SubscribeConstants.EXCHANGE_NAME, "", null, msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送完成:" + msg);
        }
        channel.close();
    }
}