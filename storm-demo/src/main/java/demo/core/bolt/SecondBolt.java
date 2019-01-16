package demo.core.bolt;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

public class SecondBolt implements IBasicBolt {

    List<Object> values;

    String msg;

    JSONObject json;

    String boltCode;

    @Override
    public void prepare(Map map, TopologyContext topologyContext) {
        values = new Values();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        msg = tuple.getString(0);
        System.out.println("SecondBolt gets data: " + msg);

        try {
            json = JSONObject.parseObject(msg);
            boltCode = json.getString("BOLT_CODE");
        } catch (Exception e) {
            System.out.println("SecondBolt data is not a basic json." + msg);
            return;
        }

        values.add(msg);

        if ("SlidingWindowByCountBolt".equals(boltCode)) {
            basicOutputCollector.emit("SlidingWindowByCountBolt", values);
        } else if ("SlidingWindowByTimeBolt".equals(boltCode)) {
            basicOutputCollector.emit("SlidingWindowByTimeBolt", values);
        } else if ("TickBolt".equals(boltCode)) {
            basicOutputCollector.emit("TickBolt", values);
        } else {
            return;
        }

        values = new Values();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // 注意使用的方法declareStream，与topology是绑定的
        outputFieldsDeclarer.declareStream("SlidingWindowByTimeBolt", new Fields("SlidingWindowByTimeBolt"));
        outputFieldsDeclarer.declareStream("SlidingWindowByCountBolt", new Fields("SlidingWindowByCountBolt"));
        outputFieldsDeclarer.declareStream("TickBolt", new Fields("TickBolt"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}