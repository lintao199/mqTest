package com.lin.rabbit.subscribe;

import com.lin.rabbitMQ.constant.SubscribeConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author LinTao
 * @version v1.0
 * @date 2021/10/15 15:03
 * @since v1.0
 */
public class PublicSubscribeConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitUtil.getChannel();
        /**
         * 声明一个不带额外参数的交换机
         * 类型 fanout:发送到fanout类型的交换机时，他会把消息投递到所有注册在此交换器的队列
         */
        channel.exchangeDeclare(SubscribeConstants.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);


        /**
         * 创建一个具有生成名称的、非持久的、独占的、自动删除队列
         */
        String queueName = channel.queueDeclare().getQueue();

        /**
         * 将一个queueName队列绑定到logs交换机上
         */
        channel.queueBind(queueName, SubscribeConstants.EXCHANGE_NAME, "");

        System.out.println("等待接收数据");

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println("收到" + msg);
            }
        };
        CancelCallback cancel = consumerTag -> {

        };
        channel.basicConsume(queueName, true, deliverCallback, cancel);

    }
}