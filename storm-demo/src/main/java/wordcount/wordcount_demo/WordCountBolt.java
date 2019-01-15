package wordcount.wordcount_demo;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCountBolt extends BaseBasicBolt {

    private Map<String, Long> counts = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        this.counts = new HashMap<String, Long>();
    }

    @Override
    public void cleanup() {
        //拓扑结束执行
        try {
            FileWriter writer = new FileWriter("D:\\rongxn\\private\\workspace\\Storm\\storm-demo\\src\\main\\java\\result.txt");
            for (String key : counts.keySet()) {
                writer.write(key + " : " + this.counts.get(key));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(Tuple input, BasicOutputCollector collector) {
        String word = input.getStringByField("word");
        Long count = this.counts.get(word);
        if (count == null) {
            count = 0L;
        }
        count++;
        this.counts.put(word, count);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}
