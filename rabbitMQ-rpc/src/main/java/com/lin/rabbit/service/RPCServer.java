package com.lin.rabbit.service;

import com.lin.rabbitMQ.constant.RPCConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author LinTao
 * @version v1.0
 * @date 2021/10/22 16:01
 * @since v1.0
 */
public class RPCServer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitUtil.getChannel();

        /**
         * 定义队列 rpc_queue, 将从它接收请求信息
         * 参数:
         * 1. queue, 对列名
         * 2. durable, 持久化
         * 3. exclusive, 排他
         * 4. autoDelete, 自动删除
         * 5. arguments, 其他参数属性
         */
        channel.queueDeclare(RPCConstants.QUEUE_NAME,RPCConstants.DURABLE,RPCConstants.EXCLUSIVE,RPCConstants.AUTO_DELETE,null);

        //清除队列中的内容
        channel.queuePurge(RPCConstants.QUEUE_NAME);

        //一次只接收1条消息
        channel.basicQos(1);

        DeliverCallback deliverCallback=new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg=new String(message.getBody());

                int n=Integer.parseInt(msg);

                //求出第n个
            }
        }

    }
}
