package com.lin.rabbit.work;

import com.lin.rabbitMQ.constant.WorkConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * @Author: LinTao
 * @DateTime: 2021/10/14 23:22
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtil.getChannel();
        channel.queueDeclare(WorkConstants.QUEUE_NAME, WorkConstants.DURABLE, WorkConstants.EXCLUSIVE, WorkConstants.AUTO_DELETE, null);
        //从控制台当中接受信息
        while (true) {
            System.out.print("输入消息: ");
            String msg = new Scanner(System.in).nextLine();
            if ("exit".equals(msg)) {
                break;
            }

            //第三个参数设置消息持久化
            channel.basicPublish("", WorkConstants.QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("UTF-8"));
            System.out.println("消息已发送: "+msg);
        }


    }
}
