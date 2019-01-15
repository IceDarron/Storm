package demo.core.bolt;

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.Config;
import org.apache.storm.shade.com.google.common.base.Strings;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class FirstBolt implements IBasicBolt {

    String msg;

    JSONObject json;

    @Override
    public void prepare(Map map, TopologyContext topologyContext) {
        // 用于bolt加载前执行
        System.out.println("FirstBolt prepare");
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        // 用于bolt的数据处理，业务代码全在此处
        System.out.println("FirstBolt execute");
        msg = tuple.getString(0);
        if (Strings.isNullOrEmpty(msg)) {
            System.out.println("FirstBolt data is null");
            return;
        }

        try {
            json = JSONObject.parseObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        basicOutputCollector.emit(new Values(msg));
    }

    @Override
    public void cleanup() {
        // 用于bolt在topo停止时执行
        System.out.println("FirstBolt cleanup");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        // 用于bolt声明下一个流传数据的名称
        System.out.println("FirstBolt declareOutputFields");
        outputFieldsDeclarer.declare(new Fields("First_msg"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        // 用于bolt声明独立的参数配置
        System.out.println("FirstBolt getComponentConfiguration");
        Config conf = new Config();
//        return conf;
        return null;
    }
}