package demo.manager.redis;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import demo.manager.properties.PropertiesManager;
import demo.util.BasicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

public class JedisPoolManage {

	private final static Logger LOGGER = LoggerFactory.getLogger(JedisPoolManage.class);

	private static Pool<Jedis> sentinelPool;

	public static Properties getJedisProperties() {
		return PropertiesManager.LOAD_PROPERTIES("config/storm-supervisor/redis.properties");
	}

	private static void createJedisPool() {
		// 利用双端检测方式来保证只有一个jedispool
		if (sentinelPool != null)
			return;
		// 建立连接池配置参数
		JedisPoolConfig config = new JedisPoolConfig();
		Properties prop = getJedisProperties();
		boolean isSingle = BasicUtils.getBoolean(prop, "isSingle", true);
		// 设置最大连接数
		config.setMaxTotal(BasicUtils.getInt(prop, "maxTotal", 20));
		// 设置超时时间
		int timeout = BasicUtils.getInt(prop, "timeout", 5000);
		// 最大空闲连接数
		if (prop.containsKey("maxIdle")) {
			int maxIdle = BasicUtils.getInt(prop, "maxIdle", 20);
			config.setMaxIdle(maxIdle);
		}

		// 最小空闲连接数
		if (prop.containsKey("minIdle")) {
			int minIdle = BasicUtils.getInt(prop, "minIdle", 5);
			config.setMinIdle(minIdle);
		}

		// 当调用borrow Object方法时，是否进行有效性检查
		if (prop.containsKey("testOnBorrow")) {
			boolean borrow = BasicUtils.getBoolean(prop, "testOnBorrow", false);
			config.setTestOnBorrow(borrow);
		}

		// 当调用return Object方法时，是否进行有效性检
		if (prop.containsKey("testOnReturn")) {
			boolean testOnReturn = BasicUtils.getBoolean(prop, "testOnReturn", false);
			config.setTestOnReturn(testOnReturn);
		}
		// 设置最大阻塞时间，记住是毫秒数milliseconds
		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
		if (prop.containsKey("maxWaitMillis")) {
			long maxWaitMillis = BasicUtils.getLong(prop, "maxWaitMillis", 5000L);
			config.setMaxWaitMillis(maxWaitMillis);
		}

		// 在空闲时检查有效性
		if (prop.containsKey("testWhileIdle")) {
			boolean testWhileIdle = BasicUtils.getBoolean(prop, "testWhileIdle", true);
			config.setTestOnReturn(testWhileIdle);
		}

		String host = prop.getProperty("host");
		int port = BasicUtils.getInt(prop, "port", 6379);

		String masterName = "mymaster";
		if (prop.containsKey("masterName")) {
			masterName = prop.getProperty("masterName");
		}

		Set<String> sentinelSet = new HashSet<String>();
		if (prop.containsKey("sentinels")) {
			String sentinels = prop.getProperty("sentinels");
			String[] hostList = sentinels.split(";");
			for (String h : hostList) {
				sentinelSet.add(h);
			}
		}

		String password = "";
		if (prop.containsKey("password")) {
			password = prop.getProperty("password");
		}
		if (isSingle) {
			if (BasicUtils.isNULL(host)) {
				LOGGER.error("未设置集群节点");
			}
			if (BasicUtils.isNULL(password)) {
				// 单机无密码
				sentinelPool = new JedisPool(config, host, port, timeout);
			} else {
				// 单机有密码
				sentinelPool = new JedisPool(config, host, port, timeout, password);
			}
		} else {
			if (sentinelSet.size() == 0) {
				LOGGER.error("未设置集群节点");
			}
			if (BasicUtils.isNULL(password)) {
				// 集群无密码
				sentinelPool = new JedisSentinelPool(masterName, sentinelSet, config, timeout);
			} else {
				// 集群有密码
				sentinelPool = new JedisSentinelPool(masterName, sentinelSet, config, timeout, password);
			}
		}

	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (sentinelPool == null)
			createJedisPool();
	}

	/**
	 * 获取一个jedis 对象
	 *
	 * @return
	 */
	public static Jedis getJedis() {
		if (sentinelPool == null)
			poolInit();
		return sentinelPool.getResource();
	}

	/**
	 * 关闭一个连接
	 *
	 * @param jedis
	 */
	public static void close(Jedis jedis) {
		jedis.close();
	}

	/**
	 * 销毁一个连接
	 *
	 * @param jedis
	 */
	public static void destroy(Jedis jedis) {
		sentinelPool.destroy();
	}
}
