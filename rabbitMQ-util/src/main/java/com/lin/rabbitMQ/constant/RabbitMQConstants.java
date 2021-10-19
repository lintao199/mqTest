package com.lin.rabbitMQ.constant;

/**
 * @Author: LinTao
 * @DateTime: 2021/10/17 21:42
 */
public abstract class RabbitMQConstants {
    public static final String QUEUE_NAME = "hello";

    public static final boolean DURABLE=false;

    public static final boolean EXCLUSIVE=false;

    public static final boolean AUTO_DELETE=false;

    public static final boolean AUTO_ACK=true;
}
