package com.lin.rabbit.simple;

import com.lin.rabbitMQ.constant.SimpleConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: LinTao
 * @DateTime: 2021/10/14 20:08
 * 生产者：发消息
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitUtil.getChannel();
        /**
         * 生成一个队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化 默认消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费 是否进行共享 true 可以多个消费者消费
         * 4.是否自动删除 最后一个消费者端开连接以后 该队列是否自动删除 true 自动删除
         * 5.其他参数
         */
        channel.queueDeclare(SimpleConstants.QUEUE_NAME, SimpleConstants.DURABLE, SimpleConstants.EXCLUSIVE, SimpleConstants.AUTO_DELETE, null);
        String message = "hello world";

        /**
         * 1.发送到哪个交换机
         * 2.路由的key值，本次是队列的Name
         * 3.其他参数值
         * 4.发送消息的消息体
         */
        channel.basicPublish("", SimpleConstants.QUEUE_NAME, null, message.getBytes());
    }
}
