package com.lin.rabbitMQ.constant;

/**
 * @Author: LinTao
 * @DateTime: 2021/10/17 21:44
 */
public class WorkConstants extends RabbitMQConstants {
    public static final String QUEUE_NAME = "worker";

    public static final boolean AUTO_ACK = false;
}
