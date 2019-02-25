package demo.core.bolt;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class BlockBolt implements IBasicBolt {

    @Override
    public void prepare(Map stormConf, TopologyContext context) {

    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        System.out.println(input);
        System.out.println(input.getMessageId());
        System.out.println(input.getSourceComponent());
        System.out.println(input.getSourceGlobalStreamId());
        System.out.println(input.getSourceStreamId());
        System.out.println(input.getSourceTask());
        System.out.println("*********");
        System.out.println(input.getValue(0));
        System.out.println(input.getValue(0).toString());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
