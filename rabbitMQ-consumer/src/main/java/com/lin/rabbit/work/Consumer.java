package com.lin.rabbit.work;

import com.lin.rabbitMQ.constant.WorkConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author: LinTao
 * @DateTime: 2021/10/14 22:48
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtil.getChannel();
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            System.out.println("收到: " + msg);
            for (int i = 0; i < msg.length(); i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            System.out.println("处理结束");
            //发送回执
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            /**
             * 问题来了，如果消费者实际已经消费完，但发送ack丢失了，怎么办？
             *
             */
        };
        CancelCallback cancelCallback = (consumerTag) -> System.out.println(consumerTag + "消费者取消消费接口回调逻辑");

        System.out.println("C1 消费者启动等待消费.................. ");

        channel.basicConsume(WorkConstants.QUEUE_NAME, WorkConstants.AUTO_ACK, deliverCallback, cancelCallback);
    }
}
