package OOXXGUI;

import Service.MyUnits;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Main.Player;
import OOXXServer.DBstoreResult;
import OOXXServer.powerChess;

public class LJChessBoard extends ChessBoard {
	
	private LJMainJFrame ljMainJFrame;
	
	public static final int ROWS = 9;// 棋盘行数
	public static final int COLS = 9;// 棋盘列数
	public static final int TARGETSCORE = 30;//目标分数
	
	private final String IP = "localhost";
	private final int TCPport = 10004;
	private final int UDPport1 = 10000;
	private final int UDPport2 = 9999;
	
//	private Lock lock = new ReentrantLock();
	private Lock lockchat = new ReentrantLock();
	private Lock lockchat2 = new ReentrantLock();
	
	private Socket socket;
	private ServerSocket serverSocket;
	private OutputStream out;
	private InputStream in;
	
	private boolean YOURTURN;
	
	private byte[] buf;
	private int len;
	private String getString;
	
	private DatagramSocket datagramsocket_get, datagramsocket_send;
	private byte[] chatbuf = new byte[1024];
	private String chatMessage;
	
	private JTextArea chat_get, chat_send;
	private MyUnits myUnits = new MyUnits();
	protected DBstoreResult dBstoreResult; 
	
	public LJChessBoard() {
		super();
		dBstoreResult = new DBstoreResult();
	}

	public LJChessBoard(LJMainJFrame ljMainJFrame) {
		this();
		this.ljMainJFrame = ljMainJFrame;
		powerChess = new powerChess(this);
	}
	
	//1代表白胜  2代表黑胜 0代表平局 -1代表未分胜负
		public void DisplayResult(int result) {
			
			if(result == 1) {
				isOver = true;
				if(tempColor == Color.WHITE) {
					JOptionPane.showMessageDialog(null, "你赢了", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
					
					try {
						dBstoreResult.LJStoreResult(1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				else {
					JOptionPane.showMessageDialog(null, "你输了", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
					
					try {
						dBstoreResult.LJStoreResult(-1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if(result == 2) {
				isOver = true;
				if(tempColor == Color.BLACK) {
					JOptionPane.showMessageDialog(null, "你赢了", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
					
					try {
						dBstoreResult.LJStoreResult(1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
					else {
						JOptionPane.showMessageDialog(null, "你输了", "比赛结果",
								JOptionPane.WARNING_MESSAGE);
						
						try {
							dBstoreResult.LJStoreResult(-1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}else if(result == 0){
				isOver = true;
				JOptionPane.showMessageDialog(null, "平分秋色", "比赛结果",
						JOptionPane.WARNING_MESSAGE);
				
				try {
					dBstoreResult.LJStoreResult(0);
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
					dBstoreResult.LJStoreResult(0);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			else if(blackscore > whitescore) {
				
				if(tempColor == Color.BLACK) {
					JOptionPane.showMessageDialog(null, "你赢了", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
					
					try {
						dBstoreResult.LJStoreResult(1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
				else {
					JOptionPane.showMessageDialog(null, "你输了", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
					
					try {
						dBstoreResult.LJStoreResult(-1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}else {

				if(tempColor == Color.WHITE) {
					
					JOptionPane.showMessageDialog(null, "你赢了", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
					try {
						dBstoreResult.LJStoreResult(1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
					else {
						JOptionPane.showMessageDialog(null, "你输了", "比赛结果",
								JOptionPane.WARNING_MESSAGE);
						try {
							dBstoreResult.LJStoreResult(-1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				
			}
		}
		
		public void MainLogicThread() {
			new Thread(new Runnable() {
				int power = 1,t;
				char c;
				@Override
				public void run() {
//					LOCKED = true;
					DoFall();
					
					if( (t=powerChess.isFour(x_now, end_y)) > 0) {
//						System.out.println(t + "@@");
						
						c = powerChess.getChar(x_now, end_y);
						
//						System.out.println((t + "@@"));
						if(c == 'O') {
							whitescore += t;
						} else {
//							System.out.println(power + "##" + t);
							blackscore += t;
						}
						
						DoFall();
						power <<= 1;
						
//						powerChess.printSituation();
						
						while(powerChess.doAllMap(power, c)) {
//							System.out.println("@@");
							
							power <<= 1;
							DoFall();
							
//							powerChess.printSituation();
						}
						
						ljMainJFrame.setBscore("" + blackscore);
						ljMainJFrame.setWscore("" + whitescore);
						
						DisplayResult(powerChess.getResult());  //得到结果
					}
					
//					powerChess.printSituation();///////
					
					isFull = powerChess.isFull();
					if(isFull) {
						WhoWin();
					}
					
//					LOCKED = false;
				}
				
			}).start();
		}
		
		public void mousePressed(MouseEvent e) {// 鼠标在组件上按下时调用
			
//			System.out.println("@@%%");
			
			// 游戏结束时，不再能下
				if (isOver || isFull) {
					JOptionPane.showMessageDialog(null, "比赛已结束", "比赛结果",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!YOURTURN)
					return;
				
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
				 
				 try {
						out.write((x_now + "@" + y_now).getBytes());  //发送消息
						
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				 
				 YOURTURN = false;
				
				
				 if (tempColor == Color.BLACK) {
					powerChess.setValue(x_now, y_now, '2'); //画黑棋
				} else {
					powerChess.setValue(x_now, y_now, '1');//画白棋
				}
				 
//				 System.out.println(this + "敌");///////////
				 if(tempColor == Color.BLACK) {   
//					 System.out.println("白敌");///////
					 ljMainJFrame.setBlackdo("");
					 ljMainJFrame.setWhitedo("敌");
				 }else {
//					 ljMainJFrame.setBlackdo("敌");
//					 System.out.println("黑敌");//////
					 ljMainJFrame.setWhitedo("");
					 ljMainJFrame.setBlackdo("敌");
				 }
						 
				MainLogicThread();
		}
		
		public void Create(JTextArea chat_get, JTextArea chat_send) throws IOException {
			
			tempColor = Color.BLACK;
			YOURTURN = true;
			ljMainJFrame.setBlackdo("我");
			
			serverSocket = new ServerSocket(TCPport);
			
			try {
				socket = serverSocket.accept();
				out = socket.getOutputStream();
				in = socket.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.chat_get = chat_get;
			this.chat_send = chat_send;
			
			datagramsocket_get = new DatagramSocket(UDPport2);//接收来自这个端口的信息
//			System.out.println(datagramsocket_get.getPort() + "Serve");/////
			
			datagramsocket_send = new DatagramSocket();
			
			getChatMessage();
			DogetMes();  //处理收到的消息
			
//			System.out.println("OK");
		}
		
		public void Connect(JTextArea chat_get, JTextArea chat_send)
				throws UnknownHostException, IOException {
			tempColor = Color.WHITE;
			YOURTURN = false;
			ljMainJFrame.setBlackdo("敌");
			
			socket = new Socket(IP, TCPport);
			out = socket.getOutputStream();
			in = socket.getInputStream();
			
			this.chat_get = chat_get;
			this.chat_send = chat_send;
			
			datagramsocket_get = new DatagramSocket(UDPport1);//接收来自这个端口的信息
			
//			System.out.println(datagramsocket_get.getPort() + "Connect");/////////
			datagramsocket_send = new DatagramSocket();
			
			getChatMessage();
			DogetMes();
		}
		
		public void DogetMes() {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					while(true) {
						buf = new byte[100];
						try {
							len = in.read(buf);
							getString = new String(buf,0,len);
							getNowXY(getString);
							
							//得到最终落子点
							 end_y = powerChess.End(x_now, y_now);
							
							if(tempColor == Color.BLACK) { //自己是黑的
								powerChess.setValue(x_now, y_now, '1'); //画对方的棋 白棋
							}
								
							else {
								powerChess.setValue(x_now, y_now, '2');//画黑棋
							}
							
//							powerChess.Refresh();
							MainLogicThread();
							
							YOURTURN = true;   
//							System.out.println(this + "我");/////
							if(tempColor == Color.BLACK) {
								ljMainJFrame.setWhitedo("");
								ljMainJFrame.setBlackdo("我");
//								 System.out.println("黑我");
//								ljMainJFrame.setWhitedo("我");
							 }else {
//								 System.out.println("白我");
								 ljMainJFrame.setBlackdo("");
								 ljMainJFrame.setWhitedo("我");
							 }
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}).start();
		}

		public void getNowXY(String getmes) {
			int index = getmes.indexOf("@");
			x_now = Integer.parseInt(getmes.substring(0, index));
			y_now = Integer.parseInt(getmes.substring(index+1, getmes.length()));
		}
		
		
		public void getChatMessage() {
			
			new Thread(new Runnable(
					) {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					while(true) {
						
						
//						byte[] buf22 = new byte[1024];
						chatbuf = new byte[1024];

						DatagramPacket dp = new DatagramPacket(chatbuf, chatbuf.length);
						
							try {
//								System.out.println(datagramsocket_get);///
								datagramsocket_get.receive(dp);
								chatMessage =  new String(dp.getData(), 0, dp.getLength());
								
								System.out.println(chatMessage);////
								
								lockchat.lock();
									chat_get.append(chatMessage);
								lockchat.unlock();
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
					}
				}
			}).start();
			
		}
		
		public void sendChatMessage(String mes) throws IOException {
			String message = (Player.getName() + "  ");
			message += myUnits.getNowTime() + "\n";
			message += (mes + "\n\n");
			byte[] tbuf = message.getBytes();
			
			int send_port;
			if(tempColor == Color.BLACK)
				send_port = UDPport1;
			else {
				send_port = UDPport2;
			}
			
//			System.out.println(send_port);/////
			DatagramPacket dp = new DatagramPacket(
					tbuf, tbuf.length, InetAddress.getByName(IP), send_port);
			
			datagramsocket_send.send(dp);
			
			lockchat2.lock();
				chat_send.setText("");
				chat_get.append(message);
			lockchat2.unlock();
		}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}
