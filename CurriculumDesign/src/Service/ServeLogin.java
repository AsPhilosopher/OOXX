package Service;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataBase.DoDataBase;
import DataBase.DoSQL;

public class ServeLogin {
	public static int messageisTrue(String username, String password) throws SQLException {
		java.sql.Connection connection = DoDataBase.getConnection();
		java.sql.Statement statement =  connection.createStatement();
		int flag=0;
		String sqlString = "select PASSWORDS from USERS where USERNAME = '" +username+ "'";
		ResultSet resultSet = DoSQL.Query(statement, sqlString) ;
		if(resultSet.next()) {
			String psw = resultSet.getString(1);
			if(password.equals(psw))
				flag = 1;  //成功登陆
			else    //密码错误
				flag = 0;
		}
		else flag=-1; //用户名不存在
		
		DoDataBase.releaseConnection(connection, statement, resultSet);
		return flag;
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}
