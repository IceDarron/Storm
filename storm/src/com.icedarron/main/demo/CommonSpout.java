package demo;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class CommonSpout extends BaseRichSpout {
    
    private SpoutOutputCollector collector;
    private long lastTimeSubmmit = System.currentTimeMillis();

    @SuppressWarnings("rawtypes")
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        System.out.println("初始化CommonSpout");
    }

    public void nextTuple() {
        if (System.currentTimeMillis() - lastTimeSubmmit > 2000) {
            String key = "key";
            String message = System.currentTimeMillis() + "";
            collector.emit(new Values(key, message));
            System.out.println("Spout发射数据" + message);
            lastTimeSubmmit = System.currentTimeMillis();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("KEY", "MESSAGE"));
    }

}
