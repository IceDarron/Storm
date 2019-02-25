package demo.core.bolt;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class FieldStrategyResultBolt implements IBasicBolt {
    @Override
    public void prepare(Map map, TopologyContext topologyContext) {

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        // 可以很好地指定消费的数据
        System.out.println("tuple_field->" + tuple.getStringByField("field") + "  " + Thread.currentThread().getName());
        System.out.println("tuple_other->" + tuple.getStringByField("other") + "  " + Thread.currentThread().getName());
        // 上一个组件发射两条数据，虽然在topology指定了，但是在tuple都传递过来了
//        System.out.println("tuple0->" + tuple.getString(0) + "  " + Thread.currentThread().getName());
        // test result
        // storm 和jstorm中fieldsGrouping分配策略的实质是根据指定的字段的值，进行hash取模，根据模进行分配。相同的值会被同一个bolt处理。
//        tuple0->1  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->4  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->7  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->10  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->13  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->16  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->19  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->22  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->25  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->27  Thread-31-FieldStrategyResultBolt-executor[5 5]
//        tuple0->28  Thread-25-FieldStrategyResultBolt-executor[6 6]
//        tuple0->16  Thread-25-FieldStrategyResultBolt-executor[6 6]

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