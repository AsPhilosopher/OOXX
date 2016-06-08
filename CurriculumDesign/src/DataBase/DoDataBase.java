package DataBase;
/**
 * 获取连接 归还连接资源
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DoDataBase {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	private static java.sql.Connection connection = null;
	
	public static java.sql.Connection getConnection() throws SQLException {
		if(connection != null) return connection;
		return dataSource.getConnection();
	}
	
	//归还连接
	public static void releaseConnection(java.sql.Connection con, 
			java.sql.Statement statement, ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		resultSet.close();
		statement.close();
		con.close();
	}
	
	
	
	/*public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(DoDataBase.getConnection());
	}*/

}
