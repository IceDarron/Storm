package demo.core.topology;

import demo.core.bolt.*;
import demo.core.spout.StormDemoSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt.Count;
import org.apache.storm.topology.base.BaseWindowedBolt.Duration;
import org.apache.storm.tuple.Fields;

import java.util.concurrent.TimeUnit;

/**
 * 处理的报文样例：
 */
public class Topology {

    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("KafkaSpout", new StormDemoSpout());
        builder.setBolt("FirstBolt", new FirstBolt(), 1).shuffleGrouping("KafkaSpout");
        builder.setBolt("SecondBolt", new SecondBolt(), 1).shuffleGrouping("FirstBolt");
        // 分支-测试IRichBolt及多Fields
        builder.setBolt("SecondSubOneBolt", new SecondSubOneBolt(), 1).shuffleGrouping("FirstBolt");
        builder.setBolt("ThirdBolt", new ThirdBolt(), 1).shuffleGrouping("SecondSubOneBolt");
        // 定时器
        builder.setBolt("TickBolt", new TickBolt(), 1)
                .fieldsGrouping("SecondBolt", "TickBolt", new Fields("TickBolt"));
        // 滑窗窗口长度：tuple数, 滑动间隔: tuple数 每2秒统计最近6秒的数据，所以一条数据可以被重复统计6/2=3次。
        builder.setBolt("SlidingWindowByTimeBolt", new SlidingWindowByTimeBolt()
                .withWindow(new Duration(6, TimeUnit.SECONDS), new Duration(2, TimeUnit.SECONDS)), 1)
                .fieldsGrouping("SecondBolt", "SlidingWindowByTimeBolt", new Fields("SlidingWindowByTimeBolt"));
        // 滑窗窗口长度：tuple数, 滑动间隔: tuple数 每收到2条数据统计当前6条数据的总和。所以一条数据可以被重复统计6/2=3次。
        builder.setBolt("SlidingWindowByCountBolt", new SlidingWindowByCountBolt()
                .withWindow(new Count(6), new Count(2)), 1)
                .fieldsGrouping("SecondBolt", "SlidingWindowByCountBolt", new Fields("SlidingWindowByCountBolt"));
        //


        Config config = new Config();
        config.setDebug(false);

        if (args != null && args.length > 0) {
            config.setNumWorkers(8);
            config.setNumAckers(0);
            StormSubmitter.submitTopology(args[0], config, builder.createTopology());
        } else {
            config.setMaxTaskParallelism(10);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("storm_demo", config, builder.createTopology());
        }
    }
}