package wordcount;


import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;

import java.util.Arrays;


public class WordCountTopologyByKafka {

    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();
        BrokerHosts brokerHosts = new ZkHosts("10.4.59.209:2182,10.4.59.141:2182,10.4.59.142:2182");
        SpoutConfig spoutConf = new SpoutConfig(brokerHosts, "TOPIC_WORDCOUNT", "TOPIC_WORDCOUNT", "TOPIC_WORDCOUNT");
        spoutConf.scheme = new SchemeAsMultiScheme(new CommonScheme());
        spoutConf.zkServers = Arrays.asList("10.4.59.209,10.4.59.141,10.4.59.142".split(","));
        spoutConf.zkPort = 2182;

        builder.setSpout("TOPIC_LEO", new KafkaSpout(spoutConf));
        builder.setBolt("SPLITBOLT", new SplitSentenceBolt(), 2).shuffleGrouping("TOPIC_WORDCOUNT");
        builder.setBolt("COUNTBOLT", new WordCountBolt(), 2).fieldsGrouping("SPLITBOLT", new Fields("WORD"));

        //集群提交TOPO
        Config stormConf = new Config();
        stormConf.setDebug(false);
        StormSubmitter.submitTopology(args[0], stormConf, builder.createTopology());
        Thread.sleep(1000);
    }
}
