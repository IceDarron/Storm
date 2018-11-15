package wordcount_kafka.spout;

import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import util.PropertiesManager;
import wordcount_kafka.scheme.SchemeWordCount;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SpoutWordCount extends KafkaSpout {

    static SpoutConfig spoutConf;

    static {
        Properties properties = PropertiesManager.LOAD_PROPERTIES("ep_xfk/config_SN/storm-nimbus-config/xfk/topology.properties");
        String zkServer = properties.getProperty("base.zkServers");
        int zkPort = Integer.parseInt(properties.getProperty("base.zkPort"));
        String zkRoot = properties.getProperty("base.zkRoot");
        String topic = properties.getProperty("topic");
        BrokerHosts hosts = new ZkHosts(zkServer);
        spoutConf = new SpoutConfig(hosts, topic, zkRoot, "spoutKafka");

        List<String> zkServices = new ArrayList<String>();
        for (String str : zkServer.split(",")) {
            zkServices.add(str.split(":")[0]);
        }
        spoutConf.zkServers = zkServices;
        spoutConf.zkPort = zkPort;
        spoutConf.scheme = new SchemeAsMultiScheme(new SchemeWordCount());
    }

    public SpoutWordCount() {
        super(spoutConf);
    }
}
