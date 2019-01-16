package demo.core.bolt;

import demo.constant.Const;
import org.apache.storm.Config;
import org.apache.storm.metric.api.CountMetric;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.utils.TupleUtils;

import java.lang.management.ManagementFactory;
import java.util.Map;

public class MetricBolt extends CountMetric implements IBasicBolt {

    String msg;

    @Override
    public void prepare(Map map, TopologyContext topologyContext) {

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        if (TupleUtils.isTick(tuple)) {
            Long statistics = (Long) getValueAndReset();
            if (statistics == 0) {
                return;
            }
            System.out.println("MetricBolt`s count by time: " + statistics);
            System.out.println("Pid is: " + ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
            System.out.println("Thread is: " + Thread.currentThread().getName());
        } else {
            msg = tuple.getString(0);
            incr();
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config conf = new Config();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, Const.Tick_Time.TICK_TIME_10);
        return conf;
    }
}
