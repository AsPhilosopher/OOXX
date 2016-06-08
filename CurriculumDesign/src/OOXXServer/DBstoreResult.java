
package OOXXServer;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import DataBase.DoDataBase;
import DataBase.DoSQL;
import Main.Player;
import Service.MyUnits;

public class DBstoreResult {
	private java.sql.Connection con;
	private java.sql.Statement statement;
	private java.sql.ResultSet resultSet;
	private MyUnits myUnits;
	
	private int match_sum, lose_sum, win_sum;
	
	public void RJStoreResult(int flag) throws SQLException {
		
		if(Player.getName() == null)
			return;
		
		con	= DoDataBase.getConnection();
		statement = con.createStatement();
		String username = Player.getName();
		String rate;
		String sqlString = 
				"select RJ_MATCHESNUM, RJ_LOSSNUM, RJ_WINNUM from USERS where USERNAME = '" +username+"'";
		
		resultSet = DoSQL.Query(statement, sqlString);
		resultSet.next();
		match_sum = resultSet.getInt("RJ_MATCHESNUM");
		lose_sum = resultSet.getInt("RJ_LOSSNUM");
		win_sum = resultSet.getInt("RJ_WINNUM");
		
		++match_sum;
		
		myUnits = new MyUnits();
		
		if(flag == 1) { //赢了
			++win_sum;
			rate = myUnits.getPercentage(win_sum*1.0 / match_sum);
			
			sqlString = 
					"update USERS set  RJ_WINNUM = '" +win_sum+ "' ,RJ_WINRATE = '" +rate+"' ,RJ_MATCHESNUM = '"+match_sum
							+ "' where USERNAME = '" +username+ "'";
			
			DoSQL.Update(statement, sqlString);
			
		}else if(flag == -1) {
			
			++lose_sum;
			rate = myUnits.getPercentage(win_sum*1.0 / match_sum);
			
			sqlString = 
					"update USERS set  RJ_LOSSNUM = '" +lose_sum+ "' ,RJ_WINRATE = '" +rate+"' ,RJ_MATCHESNUM = '"+match_sum
					+ "' where USERNAME = '" +username+ "'";
			
			DoSQL.Update(statement, sqlString);
			
		}else if(flag == 0) {
			
			rate = myUnits.getPercentage(win_sum*1.0 / match_sum);
			
			sqlString = 
					"update USERS set RJ_WINRATE = '" +rate+"' ,RJ_MATCHESNUM = '"+match_sum
					+ "' where USERNAME = '" +username+ "'";
			
			DoSQL.Update(statement, sqlString);
			
		}
		
		DoDataBase.releaseConnection(con, statement, resultSet);
	}
	
	public void LJStoreResult(int flag) throws SQLException {
		
		if(Player.getName() == null)
			return;
		
		con	= DoDataBase.getConnection();
		statement = con.createStatement();
		String username = Player.getName();
		String rate;
		String sqlString = 
				"select LJ_MATCHESNUM, LJ_LOSSNUM, LJ_WINNUM from USERS where USERNAME = '" +username+"'";
		
		resultSet = DoSQL.Query(statement, sqlString);
		
		resultSet.next();
		match_sum = resultSet.getInt("LJ_MATCHESNUM");
		lose_sum = resultSet.getInt("LJ_LOSSNUM");
		win_sum = resultSet.getInt("LJ_WINNUM");
		
		++match_sum;
		
		myUnits = new MyUnits();
		
		if(flag == 1) { //赢了
			++win_sum;
			rate = myUnits.getPercentage(win_sum*1.0 / match_sum);
			
			sqlString = 
					"update USERS set  LJ_WINNUM = '" +win_sum+ "' ,LJ_WINRATE = '" +rate+"' ,LJ_MATCHESNUM = '"+match_sum
							+ "' where USERNAME = '" +username+ "'";
			
			DoSQL.Update(statement, sqlString);
			
		}else if(flag == -1) {
			
			++lose_sum;
			rate = myUnits.getPercentage(win_sum*1.0 / match_sum);
			
			sqlString = 
					"update USERS set  LJ_LOSSNUM = '" +lose_sum+ "' ,LJ_WINRATE = '" +rate+"' ,LJ_MATCHESNUM = '"+match_sum
					+ "' where USERNAME = '" +username+ "'";
			
			DoSQL.Update(statement, sqlString);
			
		}else if(flag == 0) {
			
			rate = myUnits.getPercentage(win_sum*1.0 / match_sum);
			
			sqlString = 
					"update USERS set LJ_WINRATE = '" +rate+"' ,LJ_MATCHESNUM = '"+match_sum
					+ "' where USERNAME = '" +username+ "'";
			
			DoSQL.Update(statement, sqlString);
			
		}
		
		DoDataBase.releaseConnection(con, statement, resultSet);
	}
	
	/*public static void main(String[] args) throws SQLException {
		Player.setName("t2");
		DBstoreResult dBstoreResult = new DBstoreResult();
		
		dBstoreResult.RJStoreResult(-1);
		dBstoreResult.RJStoreResult(0);
		dBstoreResult.RJStoreResult(1);
		
		dBstoreResult.LJStoreResult(-1);
		dBstoreResult.LJStoreResult(0);
		dBstoreResult.LJStoreResult(1);
	}*/
}
