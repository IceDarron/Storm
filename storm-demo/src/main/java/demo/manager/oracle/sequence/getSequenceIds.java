package demo.manager.oracle.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import demo.manager.oracle.DruidManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author
 * @time:
 */
public class getSequenceIds {

	private Connection conn = null;

	PreparedStatement statement = null;

	ResultSet resultSet = null;

	private Logger logger = LoggerFactory.getLogger(getSequenceIds.class);

	int i = 0;

	public List<String> getSeq(String seq, int i) {
		List<String> list = new ArrayList<>();// Vector发生数组越界异常 ORA-08004: sequence SEQ_A_RCA_EXCP_LIST_ID.NEXTVAL
												// exceeds MAXVALUE and cannot be instantiated

		conn = DruidManager.getCoreConnection();
		try {
			statement = conn.prepareStatement(
					"select " + seq + ".nextval job_id from (select 1 from all_objects where rownum <= ?)");
			statement.setInt(1, i);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				list.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			logger.error("get sequence error:" + e.getMessage(), e);
		} finally {
			DruidManager.release(conn, statement);
		}
		return list;
	}
}
