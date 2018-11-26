package wordcount_kafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import wordcount_kafka.bolt.SplitSentenceBolt;
import wordcount_kafka.bolt.WordCountBolt;
import wordcount_kafka.spout.SpoutWordCount;

public class Topology
{

    /**
     * @param args
     */
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("SpoutWordCount", new SpoutWordCount());

        builder.setBolt("split", new SplitSentenceBolt()).shuffleGrouping("SpoutWordCount");
        builder.setBolt("count", new WordCountBolt(), 1).fieldsGrouping("split", new Fields("word"));

        Config config = new Config();
        config.setDebug(false);

        if (args != null && args.length > 0)
        {
            config.setNumWorkers(8);
            config.setNumAckers(0);
            StormSubmitter.submitTopology(args[0], config, builder.createTopology());
        }
        else
        {
            config.setMaxTaskParallelism(10);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("wordcount", config, builder.createTopology());
        }
    }
}
