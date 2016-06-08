package OOXXGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import OOXXServer.DBstoreResult;
import OOXXServer.powerChess;

public class RJChessBoard extends ChessBoard  {

	public static final int ROWS = 9;// 棋盘行数
	public static final int COLS = 9;// 棋盘列数
	public static final int TARGETSCORE = 30;//目标分数
//	public  int blackscore = 0, whitescore = 0;/////
	
	
	private boolean rjflag = false;  //判断电脑是否行动
	
	private RJMainJFrame rjMainJFrame;
	protected DBstoreResult dBstoreResult;
	
	public RJChessBoard() {
		super();
		dBstoreResult = new DBstoreResult();
	}
	
	public RJChessBoard(RJMainJFrame rjMainJFrame) {
		this();
		this.rjMainJFrame = rjMainJFrame;
		powerChess = new powerChess(this);//创建处理类
	}
	
	//1代表电脑胜  2代表人胜 0代表平局 -1代表未分胜负
	public void DisplayResult(int result) {
		
		if(result == 1) {
			isOver = true;
			JOptionPane.showMessageDialog(null, "电脑胜", "比赛结果",
					JOptionPane.WARNING_MESSAGE);
			
			//结果存数据库
			try {
				dBstoreResult.RJStoreResult(-1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(result == 2) {
			isOver = true;
			JOptionPane.showMessageDialog(null, "你赢了", "比赛结果",
					JOptionPane.WARNING_MESSAGE);
			//结果存数据库
			try {
				dBstoreResult.RJStoreResult(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(result == 0){
			isOver = true;
			JOptionPane.showMessageDialog(null, "平分秋色", "比赛结果",
					JOptionPane.WARNING_MESSAGE);
			
			try {
				dBstoreResult.RJStoreResult(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	public void WhoWin() {
		isOver = true;
		if(blackscore == whitescore) {
			JOptionPane.showMessageDialog(null, "平分秋色", "比赛结果",
					JOptionPane.WARNING_MESSAGE);
			
			try {
				dBstoreResult.RJStoreResult(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		else if(blackscore > whitescore) {

			JOptionPane.showMessageDialog(null, "你赢了", "比赛结果",
					JOptionPane.WARNING_MESSAGE);
			
			try {
				dBstoreResult.RJStoreResult(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {

			JOptionPane.showMessageDialog(null, "电脑胜", "比赛结果",
					JOptionPane.WARNING_MESSAGE);
			
			try {
				dBstoreResult.RJStoreResult(-1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void MainLogicThread() {
		new Thread(new Runnable() {
			
			int power = 1,t;
			char c;
			@Override
			public void run() {
				
				LOCKED = true; 
				DoFall();
				
//				System.out.println("%%%");
				
				if( (t=powerChess.isFour(x_now, end_y)) > 0) {
					
					c = powerChess.getChar(x_now, end_y);
//					powerChess.Refresh();

					if(c == 'O') {
						whitescore += t;
					} else {

						blackscore += t;
					}
					
					DoFall();
					power <<= 1;
					
//					powerChess.printSituation();
					
					while(powerChess.doAllMap(power, c)) {
						
						power <<= 1;
						DoFall();
						
//						powerChess.printSituation();
					}
					rjMainJFrame.setBscore("" + blackscore);
					rjMainJFrame.setWscore("" + whitescore);
					
					DisplayResult(powerChess.getResult());
				}
				
				isFull = powerChess.isFull();
				if(isFull) {  //满了  得胜负
					WhoWin();
				}
				
				if(rjflag && !isFull && !isOver) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DoRJ();
				}
				else {
					LOCKED = false;
				}
			}
			
		}).start();
	}
	
	public void mousePressed(MouseEvent e) {
		
		if (isOver || isFull) {
			JOptionPane.showMessageDialog(null, "比赛已结束", "比赛结果",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(LOCKED)
			return;
		//现在落子的位置
		x_now = get_X(e.getX());
		y_now = get_Y(e.getY());
		
		 //落在棋盘外不能下
		if (x_now < 0 || y_now < 0)
			return;
		//已有别的棋子 或 结束游戏
		if(powerChess.getisHas(x_now, y_now)) 
			return;
		
		//得到最终落子点
		 end_y = powerChess.End(x_now, y_now);
		 
		 powerChess.setValue(x_now, y_now, '2');//画黑棋
		 tempColor = Color.BLACK;
		 rjflag = true;
		 
		 MainLogicThread();
		 rjMainJFrame.setBlackdo("");
		 rjMainJFrame.setWhitedo("机");
		
	}
	
	public void DoRJ() {
		x_now = powerChess.MyRJ();
		end_y = powerChess.End(x_now, ROWS);
		y_now = ((end_y+3) > ROWS ) ? ROWS : (end_y+3);
		
		powerChess.setValue(x_now, y_now, '1'); //画白棋
		rjflag = false;
		MainLogicThread();
		rjMainJFrame.setWhitedo("");
		rjMainJFrame.setBlackdo("人");
	}
	
	public void Restart() {
//		System.out.println("@@");
		
		powerChess.Restart();
		repaint();
		rjMainJFrame.setBscore("0");
		rjMainJFrame.setWscore("0");
		blackscore = 0;
		whitescore = 0;
		isOver = false;
//		tempColor = Color.WHITE;
		
		rjMainJFrame.setWhitedo("");
		rjMainJFrame.setBlackdo("人");
		
		LOCKED = false;  //以防万一
		rjflag = false;
		isFull = false;
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
//		RJChessBoard rjChessBoard = new RJChessBoard();
	}*/

}
