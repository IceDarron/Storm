package demo.manager.properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import demo.manager.properties.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesManager {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesManager.class);

	public static final Properties LOAD_PROPERTIES(String filename) {
		Properties prop = new Properties();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(Path.getTopoResource(filename), "utf-8"));
			prop.load(reader);
		} catch (IOException e) {
			logger.error("配置文件加载失败");
			e.printStackTrace();
		}

		return prop;
	}

	public static String getString(Properties properties, String string) {
		return properties.getProperty(string);
	}

	public static void main(String[] args) {
		Properties properties = PropertiesManager
				.LOAD_PROPERTIES("config/storm-nimbus/topology.properties");
		System.out.println(properties.keySet());
	}
}
