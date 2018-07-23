package demo;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class App {

    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException, AuthorizationException {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("commonSpout", new CommonSpout());
        builder.setBolt("divideBolt", new DivideBolt()).shuffleGrouping("commonSpout");
        builder.setBolt("calcBolt", new CalcBolt(), 1).fieldsGrouping("divideBolt", new Fields("KEY"));

        Config stormConf = new Config();

        if (args != null && args.length > 0) {
            //集群提交topo
            stormConf.setDebug(false);
            StormSubmitter.submitTopology(args[0], stormConf, builder.createTopology());

        } else {
            //本机启动topo
            stormConf.setDebug(false);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("leo_topology", stormConf, builder.createTopology());
            System.out.println("启动topo");
        }

        Thread.sleep(1000);
    }
}
