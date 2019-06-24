package demo.core.spout;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class LocalSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    private JSONObject json;

    //    private String msg = "{\"SecondSubOneBolt1\":\"1\",\"SecondSubOneBolt2\":\"2\"}";
//    private String msg = "test-message";
    private String msg = "{\"BOLT_CODE\":\"SlidingWindowByTimeBolt\",\"INDEX\":\"1\"}";

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //定义输出字段描述
        declarer.declare(new Fields("msg"));
    }

    public void open(Map config, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void nextTuple() {
        if (StringUtils.isEmpty(msg)) {
            System.out.println("msg is null");
            return;
        }
        this.collector.emit(new Values(msg));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
