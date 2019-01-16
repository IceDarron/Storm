环境配置
===
需要zookeeper(zookeeper-3.4.6)，kafka(kafka_2.11-0.9.0.0)环境。

### 测试中，zookeeper，kafka相关指令
```text
启动zookeeper：./zookeeper-server-start.sh config/zookeeper.properties &

启动kafka：./kafka-server-start.sh config/server.properties &

停止kafka：./kafka-server-stop.sh

停止zookeeper：./zookeeper-server-stop.sh

查看所有topic列表：./kafka-topics.sh --zookeeper 10.4.59.209:2181 --list

创建主题：./kafka-topics.sh --zookeeper 10.4.59.209:2181 --create --topic STORM_DEMO --partitions 1  --replication-factor 2

查看指定topic信息：./kafka-topics.sh --describe --zookeeper 10.4.59.209:2181 -topic STORM_DEMO

查看topic某分区偏移量最大（小）值(time为-1时表示最大值，time为-2时表示最小值):
./kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list 10.4.59.209:9092,10.4.59.141:9092 --topic STORM_DEMO -time -1

生成模式：
./kafka-console-producer.sh --broker-list 10.4.59.209:9092,10.4.59.141:9092 --topic STORM_DEMO

消费模式：
./kafka-console-consumer.sh --zookeeper 10.4.59.209:2181 --from-beginning --topic STORM_DEMO
```


storm相关概念及延伸
===
基本术语：
+ https://www.cnblogs.com/dreamforwang/p/6526421.html

生命周期：
+ https://blog.csdn.net/u010003835/article/details/52168829
+ https://blog.csdn.net/sanamaz/article/details/51153987

declareOutputFields：
+ https://blog.csdn.net/qq_23660243/article/details/53929525
+ https://blog.csdn.net/scl1991/article/details/51822840

Worker、Executor、Task关系 + 并发度详解:
+ https://www.cnblogs.com/xymqx/p/4374909.html

IBasicBolt与IRichBolt的区别：
着重看一下BasicOutputCollector和OutputCollector
+ https://blog.csdn.net/ch717828/article/details/52561904

bolt的配置：
```text
优先级从低到高。
1.在topology中配置Config对象。
2.在topology中配置每个bolt时调用addConfiguration。
3.在bolt中的getComponentConfiguration方法中配置。最高。
```

滑动窗口：
每一个bolt的并行节点只能统计自己一个窗口接收到数据的总和，无法统计出一个窗口内全局数据的总和。
+ https://www.cnblogs.com/intsmaze/p/6481588.html

Metrics机制:
+ https://blog.csdn.net/opensure/article/details/50824446
+ https://brandnewuser.iteye.com/blog/2311481

Ack机制：
+ https://www.cnblogs.com/intsmaze/p/5918087.html


其他参考资料
===
kafka常用命令:
+ https://www.cnblogs.com/flymercurial/p/7913160.html

kafka 消费者offset记录位置和方式
+ https://blog.csdn.net/u013063153/article/details/78122088

