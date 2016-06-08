package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoSQL {
	
	public static ResultSet Query(java.sql.Statement stmt, String sqlString) throws SQLException {
		return stmt.executeQuery(sqlString);
	}
	
	public static void Update(java.sql.Statement stmt, String sqlString) throws SQLException {
		stmt.executeUpdate(sqlString);
	}
	
	
	
	/*public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		java.sql.Connection connection = DoDataBase.getConnection();
		java.sql.Statement statement =  connection.createStatement();
		ResultSet resultSet = null;
		
		String sqlString = "insert into USERS values('Lincon','110',10,5,5,0.5)";
//		DoSQL.Update(statement, sqlString);
		
		sqlString = "select * from USERS";
		resultSet = DoSQL.Query(statement, sqlString);
		while(resultSet.next()) {
			System.out.println(resultSet.getString("USERNAME") + " " + resultSet.getString("PASSWORDS"));
		}
		
		DoDataBase.releaseConnection(connection, statement, resultSet);
//		System.out.println("##");
		
	}*/

}
