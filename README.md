# mqTest
[资料地址](https://my.oschina.net/u/4115134/blog/3235922)

simple:简单模式
work:工作模式-> 多消费者争抢资源
subscribe:发布订阅模式-> 多个观察者订阅一个管道更新，一对多的关系;生产者向交换机发送消息，交换机给订阅的消费者发送更新通知
route:路由模式，可以根据对routeKey进行消息类型的精准消费
topic:主题交换机，可以通配单个或多个单词
