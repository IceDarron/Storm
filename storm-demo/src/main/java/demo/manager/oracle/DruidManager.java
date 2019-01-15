package demo.manager.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import demo.manager.properties.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * @author wu_jch
 * @time:2018-6-14 上午10:47:50
 */
public class DruidManager {

	// 定义一个变量 用来记录数据库连接池对象
	private static volatile DataSource ds_core;

	private static final Logger LOGGER = LoggerFactory.getLogger(DruidManager.class);

	private static void createDruidPool() {
		if (ds_core != null)
			return;
		Properties pp_core = new Properties();
		try {
			// 加载properties配置文件
			pp_core = PropertiesManager.LOAD_PROPERTIES("config/storm-supervisor/druid.properties");
			// 数据库连接池配置 使用DruidDataSourceFactory工厂来创建Druid连接池
			ds_core = DruidDataSourceFactory.createDataSource(pp_core);
			LOGGER.info("ds_core: " + ds_core);
		} catch (Exception e) {
			LOGGER.error("DruidUtils 连接池初始化失败：" + e);
		}
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (ds_core == null)
			createDruidPool();
	}

	/**
	 * 获取数据库连接Connection
	 * 
	 * @return
	 */
	public static Connection getCoreConnection() {
		if (ds_core == null)
			poolInit();
		try {
			return ds_core.getConnection();
		} catch (SQLException e) {
			LOGGER.error("DruidUtils 获取连接失败：" + e);
		}
		return null;
	}

	// 释放资源
	public static void release(Connection conn, Statement stat, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void release(Connection conn, Statement stat) {
		try {
			if (stat != null) {
				stat.close();
				stat = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
