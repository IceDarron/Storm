package demo.core.bolt;

import com.alibaba.fastjson.JSONObject;
import demo.constant.Const;
import org.apache.storm.Config;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.utils.TupleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TickBolt implements IBasicBolt {

    String msg;

    JSONObject json;

    // 用于积攒数据
    List<JSONObject> dataList;


    @Override
    public void prepare(Map map, TopologyContext topologyContext) {
        json = new JSONObject();
        dataList = new ArrayList();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        if (TupleUtils.isTick(tuple)) {
            // tick触发时，校验是否有待处理数据
            if (dataList.size() <= 0) {
                return;
            }
            System.out.println("TickBolt is executing data by Tick");
            dataList = new ArrayList();

        } else {
            msg = tuple.getString(0);
            json = JSONObject.parseObject(msg);
            dataList.add(json);

            if (dataList.size() >= Const.Accumulate_Limit.ACCUMULATE_LIMIT__10) {
                System.out.println("TickBolt is executing data by Accumulation");
                dataList = new ArrayList();
            }
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
