package OOXXGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import OOXXServer.*;

public class ChessBoard extends JPanel implements MouseListener {

	protected final int MARGIN = 30;// 与框架边的距离
	protected final int GRID_SPAN = 60;// 网格边长
	
	public static final int ROWS = 9;// 棋盘行数
	public static final int COLS = 9;// 棋盘列数
	public final int DIAMETER = 60;//棋子直径
	
	protected final int Yo = MARGIN + ROWS*GRID_SPAN; //最下边那条边的距离
	protected final int Xo = MARGIN; //最左边那条边的距离
	protected final int XX = MARGIN + GRID_SPAN*COLS; //最右边那条边的距离
	protected final int YY = Yo;
	
	public static final int TARGETSCORE = 30;//目标分数
	
	protected boolean isOver = false; //标记游戏是否结束
	protected int x_now, y_now;// 当前刚下棋子的索引
	protected int end_y; //棋子最终落在哪 防止刷新出错
	
	protected Color tempColor = Color.WHITE; //刚落的子的颜色 默认黑棋先行
	private Lock lock = new ReentrantLock();//下落时用来同步的锁

	protected int blackscore = 0, whitescore = 0;
	protected boolean LOCKED = false; //来标记是否已落子 防止同步出错
	protected boolean isFull = false; //来标记是否已满
	
	protected powerChess powerChess;
	private MainJFrame mainJFrame;
	
	public ChessBoard() {
		
//		System.out.println("father");
		
		setBackground(Color.orange); //设置背景色为橘黄色
		addMouseListener(this);
		
		addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseDragged(MouseEvent e) {

			}
		
		public void mouseMoved(MouseEvent e) {  //变手型
		
		int x1 = get_X(e.getX());
		int y1 = get_Y(e.getY());
		
		if (x1 < 0 || y1<0 || isOver
				|| powerChess.getisHas(x1, y1))
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  //默认类型
		// 设置成默认状态
		else
			setCursor(new Cursor(Cursor.HAND_CURSOR));  //手型
		}
		});
		
		powerChess = new powerChess(this);//创建处理类
		
	}
	
	public ChessBoard(MainJFrame mainJFrame) {
		this();
		this.mainJFrame = mainJFrame;
	}
	
	

		public int getBlackscore() {
		return blackscore;
	}

	public void setBlackscore(int blackscore) {
		this.blackscore = blackscore;
	}

	public int getWhitescore() {
		return whitescore;
	}

	public void setWhitescore(int whitescore) {
		this.whitescore = whitescore;
	}

		//根据鼠标的坐标得出在棋盘里的位置	
		public int get_X(int x) {
			if(x < MARGIN || x > XX)
				return -1;
			return (x - Xo) / GRID_SPAN + 1;
		}
		public int get_Y(int y) {
			if(y < MARGIN || y > YY)
				return -1;
			return (Yo - y) / GRID_SPAN + 1;
		}

	// 绘制
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);// 画棋盘

			for (int i = 0; i <= ROWS; i++) {// 画横线
				g.drawLine(MARGIN, MARGIN + i * GRID_SPAN,     MARGIN + COLS
						* GRID_SPAN,     MARGIN + i * GRID_SPAN);
			}
			for (int i = 0; i <= COLS; i++) {// 画竖线
				g.drawLine(MARGIN + i * GRID_SPAN,    MARGIN,      MARGIN + i * GRID_SPAN,
						MARGIN + ROWS * GRID_SPAN);
			}

			// 画棋子
			RadialGradientPaint paint = null;
			Color tColor = null;

			for (int i = 1; i <= ROWS; i++) {
				for(int j=1; j <= COLS; j++) {
					
					//关键
					int yPos = (ROWS-i) * GRID_SPAN + MARGIN;     //定义圆位置
					int xPos = j * GRID_SPAN + MARGIN;
					
					/**
					 * public RadialGradientPaint(
					   	   float cx,
                           float cy,
                           float radius,
                           float[] fractions,
                           Color[] colors)
					    cx - 定义渐变的圆的中心点在用户空间中的 X 坐标。渐变的最后一种颜色被映射到圆周上。
						cy - 定义渐变的圆的中心点在用户空间中的 Y 坐标。渐变的最后一种颜色被映射到圆周上。
						radius - 定义颜色渐变范围的圆的半径
						fractions - 范围从 0.0 到 1.0 之间的数字，用于指定沿渐变的颜色分布
						colors - 在渐变中使用的颜色数组。第一种颜色用于焦点处，最后一种颜色环绕在圆周上。
					 */
					
//					tColor = powerChess.getColor(i, j);
					char color = powerChess.getChar(j, i);
//					System.out.println(color);
					if(color == '1')
						tColor = Color.WHITE;
					else if(color == '2')
						tColor = Color.BLACK;
					else 
						tColor = null;
					
					if (tColor == Color.BLACK) {

						paint = new RadialGradientPaint(
								xPos - DIAMETER + 25, yPos + 10, 
								
								20, new float[] { 0f, 1f }, new Color[] {
								Color.WHITE, Color.BLACK });
						

					} else if (tColor == Color.white) {

						paint = new RadialGradientPaint(
								xPos -DIAMETER + 25,  yPos + 10, 
								
								130, new float[] { 0f, 1f }, new Color[] {   //这里是130 颜色渐变半径 为白棋
								Color.WHITE, Color.BLACK });
					}
					
					if(tColor != null) {
						((Graphics2D) g).setPaint(paint);
						((Graphics2D) g).setRenderingHint(
								RenderingHints.KEY_ANTIALIASING,
								RenderingHints.VALUE_ANTIALIAS_ON);
						((Graphics2D) g).setRenderingHint(
								RenderingHints.KEY_ALPHA_INTERPOLATION,
								RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

						Ellipse2D e = new Ellipse2D.Float(
								xPos - DIAMETER,   yPos,
								DIAMETER - 1,   DIAMETER);   //后两个定义椭圆的 长短轴
						((Graphics2D) g).fill(e);
					}
					
				}
					
			}
			
		}
		
		public void DoFall() {
			// TODO Auto-generated method stub
			lock.lock(); //锁上
			try {
				do {
					repaint();// 通知系统重新绘制
					
					try {
						Thread.sleep(150);
					} catch (InterruptedException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
				} while (powerChess.PointsFall());
				
			} finally {
				lock.unlock();//解锁
			}
			
		}
		
		//1代表白胜  2代表黑胜 0代表平局 -1代表未分胜负
			public void DisplayResult(int result) {
				
				if(result == 1) {
					isOver = true;
					JOptionPane.showMessageDialog(null, "白棋胜", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
				}else if(result == 2) {
					isOver = true;
					JOptionPane.showMessageDialog(null, "黑棋胜", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
				}else if(result == 0){
					isOver = true;
					JOptionPane.showMessageDialog(null, "平分秋色", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
				}
			}
				
			public void WhoWin() {
				isOver = true;
				if(blackscore == whitescore)
					JOptionPane.showMessageDialog(null, "平分秋色", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
				else if(blackscore > whitescore) {
					JOptionPane.showMessageDialog(null, "黑棋赢", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "白棋赢", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
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
						
						if( (t=powerChess.isFour(x_now, end_y)) > 0) {
							
							c = powerChess.getChar(x_now, end_y);
//							powerChess.Refresh();

							if(c == 'O') {
								whitescore += t;
							} else {

								blackscore += t;
							}
							
							DoFall();
							power <<= 1;
							
//							powerChess.printSituation();
							
							while(powerChess.doAllMap(power, c)) {
								
								power <<= 1;
								DoFall();
								
//								powerChess.printSituation();
							}
							
							mainJFrame.setBscore("" + blackscore);
							mainJFrame.setWscore("" + whitescore);
							
							DisplayResult(powerChess.getResult());
						}
						
						isFull = powerChess.isFull();
						if(isFull) {  //满了  得胜负
							WhoWin();
						}
						//加标识
						if(tempColor == Color.WHITE) {
							
							mainJFrame.setWhitedo("");
							mainJFrame.setBlackdo("黑");
						} else if(tempColor == Color.BLACK) {
							mainJFrame.setBlackdo("");
							mainJFrame.setWhitedo("白");
						}
						
						LOCKED = false;
					}
					
				}).start();
			}
		
		@Override
		public void mousePressed(MouseEvent e) {// 鼠标在组件上按下时调用
			
//			System.out.println("888");////
			
			// 游戏结束时，不再能下
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
			
			if(tempColor == Color.BLACK) {
				powerChess.setValue(x_now, y_now, '1'); //画白棋
				tempColor = Color.WHITE;
			}
				
			else {
				powerChess.setValue(x_now, y_now, '2');//画黑棋
				tempColor = Color.BLACK;
			}

//			powerChess.Refresh();
			
			//开线程
			MainLogicThread();
			
	}

	
	public void Restart() {
		powerChess.Restart();
		repaint();
		mainJFrame.setBscore("0");
		mainJFrame.setWscore("0");

		blackscore = 0;
		whitescore = 0;
		isOver = false;
		tempColor = Color.WHITE;
		
		mainJFrame.setWhitedo("");
		mainJFrame.setBlackdo("黑");
		
		LOCKED = false;  //以防万一
		isFull = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
