package demo.core.bolt;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.windowing.TupleWindow;

import java.util.Map;

public class SlidingWindowByTimeBolt extends BaseWindowedBolt {

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
            msg = (String) tuple.getValueByField("SlidingWindowByTimeBolt");
            int index = 0;
            try {
                json = JSONObject.parseObject(msg);
                index = Integer.parseInt((String) json.get("INDEX"));
            } catch (RuntimeException e) {
                e.printStackTrace();
                return;
            }
            System.out.println(" INDEX: " + index);
            sum += index;
            // 需要显示的ack，
            // 注意由于是时间类滑动窗口，如果不显示提交在统计上是发现不出问题的，
            // 但当系统长时间运行时，由于一直没有提交会出现内存溢出
            collector.ack(tuple);
        }
        System.out.println("****** sum = " + sum);
    }
}
