package Service;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataBase.DoDataBase;
import DataBase.DoSQL;

public class ServeRegist {
	//用户名或密码是否为空
	public static boolean isEmpty(String nameorpsw) {
		
		if(nameorpsw.trim().isEmpty() || nameorpsw == null) {
			return false;
		}
		return true;
	}
	//验证密码与重复密码是否一致
	public static boolean psw_equ_repsw(String password, String repassword) {
		if(password.equals(repassword))
			return true;
		return false;
	}
	
	public static boolean isexeUser(String username, String password) throws SQLException {
		java.sql.Connection connection = DoDataBase.getConnection();
		java.sql.Statement statement =  connection.createStatement();
		//执行sql语句
		String sqlString = "select * from USERS where USERNAME = '" +username+ "'";
		ResultSet resultSet = DoSQL.Query(statement, sqlString) ;
		//已存在用户名 返回false
		boolean flag = !resultSet.next();
		if(flag) {
			sqlString = "insert into USERS values('" +username+ "','" +password+ "' ,0,0,0,'%0' ,0,0,0,'%0')";
			DoSQL.Update(statement, sqlString);
			
			DoDataBase.releaseConnection(connection, statement, resultSet);
		}
		return flag;
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}
