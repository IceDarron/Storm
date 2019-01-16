package demo.core.bolt;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

import static org.apache.storm.utils.Utils.tuple;

public class SecondSubOneBolt implements IRichBolt {

    private OutputCollector outputCollector;

    String msg;

    JSONObject json;

    String SecondSubOneBolt1;

    String SecondSubOneBolt2;

    List<Object> values;


    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
        this.values = new Values();
    }

    @Override
    public void execute(Tuple tuple) {
        msg = tuple.getString(0);
        System.out.println(" SecondSubOneBolt: " + msg);

        try {
            json = JSONObject.parseObject(msg);
            SecondSubOneBolt1 = (String) json.get("SecondSubOneBolt1");
            SecondSubOneBolt2 = (String) json.get("SecondSubOneBolt2");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        outputCollector.emit(tuple(SecondSubOneBolt1, SecondSubOneBolt2));
        outputCollector.ack(tuple);

//        values.add(SecondSubOneBolt1);
//        values.add(SecondSubOneBolt2);
//        outputCollector.emit(tuple(SecondSubOneBolt1, SecondSubOneBolt2));
//        outputCollector.ack(tuple);
//        values = new Values();
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("SecondSubOneBolt1", "SecondSubOneBolt2"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
