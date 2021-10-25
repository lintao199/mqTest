package com.lin.rabbit.client;

import com.lin.rabbitMQ.constant.RPCConstants;
import com.lin.rabbitMQ.util.RabbitUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @author LinTao
 * @version v1.0
 * @date 2021/10/22 16:01
 * @since v1.0
 */
public class RPCClient {
    Connection con;
    Channel channel;

    public RPCClient() throws IOException, TimeoutException {
        this.channel = RabbitUtil.getChannel();
    }


    public String call(String msg) throws IOException, InterruptedException {
        //自动生成队列名,非持久,独占,自动删除
        String replyQueueName = channel.queueDeclare().getQueue();
        //关联ID
        String corrId = UUID.randomUUID().toString();

        //设置两个参数:
        //1. 请求和响应的关联id
        //2. 传递响应数据的queue
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

        //向rpc_queue 队列发送请求数据,请求第n个斐波那契数列
        channel.basicPublish("", RPCConstants.QUEUE_NAME, props, msg.getBytes());

        //用来保存结果的阻塞集合,取数据时，没有数据会暂停等待
        BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("收到消息");
                if (message.getProperties().getCorrelationId().contentEquals(corrId)) {
                    //把收到的响应数据，放入阻塞集合
                    response.offer(new String(message.getBody()));
                }
            }
        };
        CancelCallback cancelCallback = consumerTag -> {
        };
        channel.basicConsume(replyQueueName, true, deliverCallback, cancelCallback);

        return response.take();

    }

    public static void main(String[] args) throws Exception {
        RPCClient client = new RPCClient();
        while (true) {
            System.out.print("求第几个斐波那契数:");
            int n = new Scanner(System.in).nextInt();
            String r = client.call("" + n);
            System.out.println(r);
        }
    }
}
