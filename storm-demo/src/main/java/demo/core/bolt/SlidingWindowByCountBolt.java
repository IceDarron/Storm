package demo.core.bolt;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.windowing.TupleWindow;

import java.util.Map;

public class SlidingWindowByCountBolt extends BaseWindowedBolt {

    private OutputCollector collector;

    private String msg;

    private JSONObject json;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(TupleWindow tupleWindow) {
        int sum = 0;
        System.out.println("一个窗口内的数据");
        for (Tuple tuple : tupleWindow.get()) {
            msg = (String) tuple.getValueByField("SlidingWindowByCountBolt");
            int index = 0;
            try {
                json = JSONObject.parseObject(msg);
                index = Integer.parseInt((String) json.get("INDEX"));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            System.out.println(" INDEX: " + index);
            sum += index;
            // 需要显示的ack
            collector.ack(tuple);
        }
        System.out.println("****** sum = " + sum);
    }
}
