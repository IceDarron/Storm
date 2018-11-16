package wordcount_kafka.bolt;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCountBolt implements IBasicBolt {

    private Map<String, Long> counts = null;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        this.counts = new HashMap<String, Long>();
    }

    @Override
    public void cleanup() {
        //拓扑结束执行
        FileWriter writer = null;
        try {
            writer = new FileWriter("E:\\IDEA\\workspace\\StormRoot\\storm-demo\\src\\main\\java\\result.txt");
            for (String key : counts.keySet()) {
                writer.write(key + " : " + this.counts.get(key));
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
//        String word = input.getStringByField("word");
        String word = input.getString(0);
        Long count = this.counts.get(word);
        if (count == null) {
            count = 0L;
        }
        count++;
        this.counts.put(word, count);
    }


}
