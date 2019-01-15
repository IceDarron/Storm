package wordcount.wordcount_kafka.bolt;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class SplitSentenceBolt implements IBasicBolt {

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //定义了传到下一个bolt的字段描述
        declarer.declare(new Fields("word"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    public void prepare(Map map, TopologyContext topologyContext) {
        System.out.println(map.keySet());
    }

    public void cleanup() {

    }

    public void execute(Tuple input, BasicOutputCollector collector) {
//        String sentence = input.getStringByField("SpoutWordCount");
        String sentence = input.getString(0);
        String[] words = sentence.split(" ");
        for (String word : words) {
            //发送单词
            collector.emit(new Values(word));
        }
    }
}
