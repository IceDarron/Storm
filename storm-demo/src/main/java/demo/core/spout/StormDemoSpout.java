package demo.core.spout;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import demo.manager.properties.PropertiesManager;
import demo.util.BasicUtils;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;

/**
 * 初始化配置，并将配置注入KafkaSpout。
 */
public class StormDemoSpout extends KafkaSpout {

	private static final long serialVersionUID = 3070135337182778842L;

	static SpoutConfig spoutConf;

	static {
		Properties properties = PropertiesManager.LOAD_PROPERTIES("config/storm-nimbus/topology.properties");
		String zkServer = properties.getProperty("base.zkServers");
		int zkPort = BasicUtils.getInt(properties, "base.zkPort", 2181);
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
		spoutConf.scheme = new SchemeAsMultiScheme(new StromDemoScheme());
	}

	public StormDemoSpout() {
		super(spoutConf);
	}
}
