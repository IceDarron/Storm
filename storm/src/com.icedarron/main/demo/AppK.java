package demo;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.Arrays;

public class AppK {

    /**
     * @param args
     * @throws InvalidTopologyException
     * @throws AlreadyAliveException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException, AuthorizationException {

        TopologyBuilder builder = new TopologyBuilder();
        String zkRoot = "/topo_leo";
        BrokerHosts brokerHosts = new ZkHosts("10.4.59.209:2182,10.4.59.141:2182,10.4.59.142:2182");
        SpoutConfig spoutConf = new SpoutConfig(brokerHosts, "TOPIC_LEO", zkRoot, "TOPIC_LEO");
        spoutConf.scheme = new SchemeAsMultiScheme(new CommonScheme());
        spoutConf.zkServers = Arrays.asList("10.4.59.209,10.4.59.141,10.4.59.142".split(","));
        spoutConf.zkPort = 2182;
//        spoutConf.forceFromStart = false;

        builder.setSpout("TOPIC_LEO", new KafkaSpout(spoutConf));
        builder.setBolt("divideBolt", new DivideBolt()).shuffleGrouping("TOPIC_LEO");
        builder.setBolt("calcBolt", new CalcBolt(), 1).fieldsGrouping("divideBolt", new Fields("KEY"));

        Config stormConf = new Config();

        if (args != null && args.length > 0) {
            //集群提交TOPO
            stormConf.setDebug(false);
            StormSubmitter.submitTopology(args[0], stormConf, builder.createTopology());
        } else {
            //本机启动TOPO
            stormConf.setDebug(false);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("leo_topology", stormConf, builder.createTopology());
            System.out.println("启动topo");
        }

        Thread.sleep(1000);
    }

}
