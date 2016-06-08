package Service;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import DataBase.DoDataBase;
import DataBase.DoSQL;
import Main.Choose;
import Main.Player;

public class DoRank extends JFrame {
	
	private JButton lastpage, nextpage;
	private JLabel ranking, player, sum, win, lose, dogfall, winrate;
	private final int per_page = 8;  //每页8条记录
	private int now_page = 1; //当前页 初始为1
	private int total_page; //总页数
	private final int linespa = 30; //行间距
	
	private final int start_y = 35;
	
	private int rank = 1;//排名
	
	private MyRank[] myRank = new MyRank[per_page];//记录处理对象
	private int choosedo;  //选择查询哪个
	
	private ResultSet resultSet;
	
//	private JLabel[] test = new JLabel[14];
	
	public DoRank(int choosedo) throws SQLException {
		InitJLabel();
		InitButton();
		this.choosedo = choosedo;
		
		doDB();
		displayRank();
		
		makeNodo();
		MyEvent();
		InitWindow();
	}
	
	
	public void InitWindow() {
		setTitle("排行榜");
		setSize(510, 390);
		setLayout(null); //绝对定位
		setResizable(false); //窗口不能变大小
		setLocationRelativeTo(null); //居中显示
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void InitButton() {
		Font font = new Font("楷体", Font.BOLD, 13);
		
		lastpage = new JButton("上一页");
		lastpage.setBounds(30, 300, 80, 40);
		lastpage.setFont(font);
		add(lastpage);
		
		nextpage = new JButton("下一页");
		nextpage.setBounds(395, 300, 80, 40);
		nextpage.setFont(font);
		add(nextpage);
	}
	
	/*//测试方法
	public void Test() {
		Font font = new Font("楷体", Font.BOLD, 15);
		test[0] = new JLabel("1");
		test[0].setBounds(10, 35, 30, 30);//////
		test[0].setFont(font);
		add(test[0]);
		
		test[1] = new JLabel("haha");
		test[1].setBounds(60, 35, 80, 30);//////
		test[1].setFont(font);
//		test[1].setForeground(Color.RED);
		add(test[1]);
		
		test[2] = new JLabel("2");
		test[2].setBounds(10, 65, 30, 30);
		test[2].setFont(font);
		add(test[2]);
		
		test[3] = new JLabel("%100");
		test[3].setBounds(450, 65, 50, 30);
		test[3].setFont(font);
		add(test[3]);
		
		test[4] = new JLabel("90");
		test[4].setBounds(10, 245, 30, 30);
		test[4].setFont(font);
		add(test[4]);
	}*/
	
	public void InitJLabel() {
		Font font = new Font("楷体", Font.BOLD, 20);
		ranking = new JLabel("名次");
		ranking.setBounds(5, 5, 60, 30);
		ranking.setFont(font);
		add(ranking);
		
		player = new JLabel("用户名");
		player.setBounds(60, 5, 80, 30);
		player.setFont(font);
		add(player);
		
		sum = new JLabel("总局数");
		sum.setBounds(160, 5, 80, 30);
		sum.setFont(font);
		add(sum);
		
		win = new JLabel("胜局");
		win.setBounds(255, 5, 60, 30);
		win.setFont(font);
		add(win);
		
		lose = new JLabel("败局");
		lose.setBounds(320, 5, 60, 30);
		lose.setFont(font);
		add(lose);
		
		dogfall = new JLabel("平局");
		dogfall.setBounds(385, 5, 60, 30);
		dogfall.setFont(font);
		add(dogfall);
		
		winrate = new JLabel("胜率");
		winrate.setBounds(450, 5, 60, 30);
		winrate.setFont(font);
		add(winrate);
		
	}
	
	public void doDB() throws SQLException {
		String sqlString = "";
		if(choosedo == 1) {//人机
			sqlString = 
					"select USERNAME, RJ_MATCHESNUM, RJ_LOSSNUM, RJ_WINNUM, RJ_WINRATE from USERS order by RJ_WINNUM DESC";
		}else if(choosedo == 2) {//联机
			sqlString = 
					"select USERNAME, LJ_MATCHESNUM, LJ_LOSSNUM, LJ_WINNUM, LJ_WINRATE from USERS order by LJ_WINNUM DESC";
		}
		java.sql.Connection connection = DoDataBase.getConnection();
		java.sql.Statement statement = connection.createStatement();
		
		resultSet = DoSQL.Query(statement, sqlString);
		resultSet.last();
		System.out.println(resultSet.getRow() + "@@");
		
		total_page = resultSet.getRow()/per_page; //获得总页数
		
		if(resultSet.getRow()%per_page != 0)
			++total_page;
		
		System.out.println(total_page + "$$");
	}
	
	public void displayRank() throws SQLException {
		int temprank, falldog_val;
		int winnum, sumnum, losenum;
		String player, winrate;
//		JLabel[] tempLabel = new JLabel[7];
		int temp_y;
		
		temprank = (now_page-1)*per_page + 1;
		System.out.println(temprank + "((");///////
		resultSet.absolute(temprank);//指针移动到指定位置
		
		for(int i=0; i<per_page; i++) {
			
			player = resultSet.getString(1);
			sumnum = resultSet.getInt(2);
			losenum = resultSet.getInt(3);
			winnum = resultSet.getInt(4);
			falldog_val = sumnum - losenum - winnum;
			winrate = resultSet.getString(5);
			
			if(myRank[i] == null)
				myRank[i] = new MyRank();
			
			temp_y = start_y + i*linespa;
				
			myRank[i].setRanking(temp_y, temprank+"");
			myRank[i].setPlayer(temp_y, player);
			myRank[i].setSum(temp_y, sumnum+"");
			myRank[i].setWin(temp_y, winnum+"");
			myRank[i].setLose(temp_y, losenum+"");
			myRank[i].setDogfall(temp_y, falldog_val+"");
			myRank[i].setWinrate(temp_y, winrate);
			
			
			if(temprank == 1) {
				myRank[i].makeChange(temprank);
			}else if(temprank == 2) {
				myRank[i].makeChange(temprank);
			}else if(temprank == 3) {
				myRank[i].makeChange(temprank);
			}
			
			if(player.equals(Player.getName())) {
				myRank[i].makeChange(0);
			}
			
			add(myRank[i].getRanking());
			add(myRank[i].getPlayer());
			add(myRank[i].getSum());
			add(myRank[i].getWin());
			add(myRank[i].getLose());
			add(myRank[i].getDogfall());
			add(myRank[i].getWinrate());
			
			temprank++;
			
			if(!resultSet.next())
				break;
		}
		
	}
	
	public void makeNodo() {
		lastpage.setEnabled(true);
		nextpage.setEnabled(true);
		
		if(now_page == 1) {
			lastpage.setEnabled(false);
		}
		if(now_page == total_page) {
			nextpage.setEnabled(false);
		}
	}
	
	public void ClearContext() {
		for(int i=0; i<per_page; i++) {
			if(myRank[i] != null) {
				myRank[i].clearLabel();
			}
		}
	}
	
	public void MyEvent() {
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//				setVisible(false);
				dispose();
			}
		});
		
		nextpage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				++now_page;
				makeNodo();
				ClearContext();
				try {
					System.out.println("***");
					displayRank();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		lastpage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				--now_page;
				makeNodo();
				ClearContext();
				try {
					System.out.println("***");
					displayRank();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		new DoRank(1);
	}

}
