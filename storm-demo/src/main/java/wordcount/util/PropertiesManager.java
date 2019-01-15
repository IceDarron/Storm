package wordcount.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesManager {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesManager.class);

    public static final Properties LOAD_PROPERTIES(String filename) {
        Properties prop = new Properties();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(PropertiesManager.class.getClassLoader().getResourceAsStream(filename), "utf-8"));
            prop.load(reader);
        } catch (IOException e) {
            logger.error("配置文件加载失败");
            e.printStackTrace();
        }
        return prop;
    }

    public static void main(String[] args) {
        Properties properties = PropertiesManager.LOAD_PROPERTIES("topology.properties");
        System.out.println(properties.keySet());
    }
}
