滑动窗口
===

strom的TickTuple机制
===


样例代码
===
+ 关于tick的参数配置，有topology层面，有BoltDeclarer层面，也有bolt的getComponentConfiguration层面，三种方式，BoltDeclarer优先级最高，然后是bolt的getComponentConfiguration，最后是全局的topology层面的配置

+ 对于tickTuple，采用的是StormTimer进行调度，调度的时候，往bolt的JCQueue的publish方法，具体是是调用recvQueue.offer(obj)；而executor的asycLoop调用Executor.call().call()方法，对于BoltExecutor.call则是调用JCQueue.consume方法，该方法调用的是recvQueue.poll()

+ 因此可以看到timer只负责往队列发送tickTuple，至于触发的时间精度，不一定百分百精确，具体要看recvQueue队列的长度以及executor的消费能力

```java
// 共三种
// 第一种
// bolt的getComponentConfiguration改法
public class SN1010 implements IBasicBolt {
    
    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config conf = new Config();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 10);
        return conf;
    }
    
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        if (TupleUtils.isTick(tuple)) {
            
        } else {
            
        }
    }
}

// 第二种
// 除了重写getComponentConfiguration方法配置Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS参数外，还可以在TopologyBuilder.setBolt之后调用addConfiguration方法在配置，这个配置会覆盖getComponentConfiguration方法的配置

// 第三种
// 另外除了在bolt上配置，还可以在StormSubmitter.submitTopology时，对传入的conf配置Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS参数，不过这个配置是全局的，作用于整个topology的所有bolt；当出现既有全局配置，又有bolt自己的配置时，作用范围小的优先。

```


参考文献
===
https://www.jianshu.com/p/b7a196d98a5d

http://mdsa.51cto.com/art/201507/486973_1.htm

http://www.michael-noll.com/blog/2013/01/18/implementing-real-time-trending-topics-in-storm/

http://blog.jobbole.com/88475/