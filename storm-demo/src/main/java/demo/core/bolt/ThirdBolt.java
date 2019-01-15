package demo.core.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class ThirdBolt implements IRichBolt {

    private OutputCollector outputCollector;

    String SecondSubOneBolt1;

    String SecondSubOneBolt2;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        SecondSubOneBolt1 = tuple.getString(0);
        SecondSubOneBolt2 = tuple.getString(1);
        System.out.println("ThirdBolt gets data from SecondSubOneBolt1: " + SecondSubOneBolt1);
        System.out.println("ThirdBolt gets data from SecondSubOneBolt2: " + SecondSubOneBolt2);
        outputCollector.ack(tuple);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
