package wordcount_kafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

public class Topology
{

    /**
     * @param args
     */
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();

//        builder.setSpout("KafkaSpout", new SpoutFromKafka());
//        builder.setBolt("fieldsExecutor", new FieldsExecutor(), 1).shuffleGrouping("KafkaSpout");
//        builder.setBolt("mainExecutor", new MainExecutor(), 1).setNumTasks(1).shuffleGrouping("fieldsExecutor");
//        //	builder.setBolt("SN1030", new SN1030(),1).shuffleGrouping("mainExecutor", TYPE.SN1030.toString());
//        builder.setBolt("SN1010", new SN1010(), 1).shuffleGrouping("mainExecutor", TYPE.SN1010.toString());
//        builder.setBolt("SN2010", new SN2010(), 10).shuffleGrouping("mainExecutor", TYPE.SN2010.toString());
//        builder.setBolt("SN2011", new SN2011(), 1).shuffleGrouping("mainExecutor", TYPE.SN2011.toString());
//        builder.setBolt("SN2012", new SN2012(), 1).shuffleGrouping("mainExecutor", TYPE.SN2012.toString());
//        builder.setBolt("SN3010", new SN3010(), 1).shuffleGrouping("mainExecutor", TYPE.SN3010.toString());
//        builder.setBolt("SN4010", new SN4010(), 1).shuffleGrouping("mainExecutor", TYPE.SN4010.toString());
//        builder.setBolt("SN4011", new SN4011(), 1).shuffleGrouping("mainExecutor", TYPE.SN4011.toString());
//        builder.setBolt("SN7511", new SN7511(), 1).shuffleGrouping("mainExecutor", TYPE.SN7511.toString());
//        builder.setBolt("SN8010", new SN8010(), 1).shuffleGrouping("mainExecutor", TYPE.SN8010.toString());
//        builder.setBolt("SN9010", new SN9010(), 10).shuffleGrouping("mainExecutor", TYPE.SN9010.toString());
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
            cluster.submitTopology("xfk", config, builder.createTopology());
        }
    }
}
