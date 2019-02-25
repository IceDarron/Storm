package demo.core.bolt;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

public class FieldStrategyBolt implements IBasicBolt {

    String[] array = {"1", "4", "7", "10", "13", "16", "19", "22", "25", "27", "28", "16"};

    int count = 0;

    @Override
    public void prepare(Map map, TopologyContext topologyContext) {

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        if (count >= array.length) {
            Utils.sleep(10000);
            count = 0;
        }
        basicOutputCollector.emit(new Values(array[count], "a"));
        count++;
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("field", "other"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
