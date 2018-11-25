package wordcount_demo;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

public class WordCountTopology {

    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new SentenceSpout());
        builder.setBolt("split", new SplitSentenceBolt()).shuffleGrouping("spout");
        builder.setBolt("count", new WordCountBolt(), 1).fieldsGrouping("split", new Fields("word"));
        Config conf = new Config();
        conf.setDebug(false);
        if (args != null && args.length > 0) {
            // 集群模式
            conf.setNumWorkers(2);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            // 本地模式
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
            System.out.print("开启Topology");
            Utils.sleep(60000); // 跑的时间不够就关闭拓扑会造成单机拓扑运行不出结果
            cluster.killTopology("word-count");
            cluster.shutdown();
        }
    }
}
