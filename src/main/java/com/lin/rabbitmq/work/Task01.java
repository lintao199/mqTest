package com.lin.rabbitmq.work;

import com.lin.rabbitmq.utils.RabbitUtil;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @Author: LinTao
 * @DateTime: 2021/10/14 23:22
 */
public class Task01 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtil.getChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //从控制台当中接受信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送消息完成:" + message);
        }

    }
}
