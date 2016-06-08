package OOXXGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Main.AudioPlayer;
import Main.Choose;
import Main.Player;
import Service.DoRank;

public class LJMainJFrame extends RJMainJFrame {
//public class LJMainJFrame extends JFrame {
	
	private LJChessBoard ljChessBoard;
	private JButton Create, Connect, Send;
	
	private JTextArea chat_send;
	private JTextArea chat_get;
	
	private JScrollPane jsp_get, jsp_send;
	
//	private boolean myflag;
	
//	private JPanel con_but, cre_but;
	
//	private JButton Rank;
	
	public LJMainJFrame() {
		
//		myflag = false;
		ljChessBoard = new LJChessBoard(this);
		addChessBoard();
		
		InitButton();
		
		InitJLable();
		InitJText();
		
		add(Connect);
		add(Create);
		
		InitWindow();
		StartOrStopMusic.setBounds(460, 620, 75, 30);
		add(StartOrStopMusic);
		addChatTextEvent();
		MyEvent();
	}
	
	public void addChatTextEvent() {
		chat_send.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
//				int code = e.getKeyCode();
				if(e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER ) {
					Send.doClick();
//					e.consume();//控制非法输入
				}
			}
		});
		
	}
	
	public void InitButton() {
		
		StartOrStopMusic = new JButton("音乐开");
		
		Exit = new JButton("退出");
		Exit.setBounds(20, 620, 65, 30);
		add(Exit);
		
		Create = new JButton("创建房间");
		Create.setBounds(120, 620, 100, 30);
//		add(Create);
		
		Connect = new JButton("连接");
		Connect.setBounds(240, 620, 65, 30);
//		add(Connect);
		
		Rank = new JButton("排行榜");
		Rank.setBounds(340, 620, 100, 30);
		add(Rank);
		
		
		Send = new JButton("发送");
		Send.setBounds(1120, 620, 65, 30);
		add(Send);
		
	}
	
	public void InitWindow() {
		setTitle("OOXX 欢迎您：" + Player.getName());
		setLayout(null); //绝对定位
		setResizable(false); //窗口不能变大小
		this.setSize(1200, 700); //宽，高
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); //居中显示
//		pack();// 自适应大小
		setVisible(true);
	}
	
	public void InitJText() {
//		System.out.println("@@");
		
		Font font = new Font("楷体", Font.BOLD, 15);
		
		chat_send = new JTextArea(435, 100);
//		chat_send.setBounds(755, 400, 435, 200); //宽高
		
		chat_send.setFont(font);
		
		chat_get = new JTextArea(435, 390);
//		chat_get.setBounds(755, 0, 435, 390);
		
		chat_get.setFont(font);
		
		chat_send.setEditable(false);
		chat_get.setEditable(false);  //设成只读

		//自动换行
		chat_get.setLineWrap(true);
		chat_send.setLineWrap(true);
		
		jsp_get = new JScrollPane(chat_get);
		
		jsp_get.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		
		jsp_send = new JScrollPane(chat_send);
		
		jsp_send.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		
		jsp_get.setBounds(755, 0, 435, 390);
		jsp_send.setBounds(755, 400, 435, 200);
		
		add(jsp_get);
		add(jsp_send);
//		System.out.println("@@");
	}
	
	public void makeNoDo() {
		Create.setEnabled(false);
		Connect.setEnabled(false);
		/*Create.setBackground(Color.GRAY);
		Connect.setBackground(Color.GRAY);*/
	}
	
	public void MyEvent() {
		
		//关闭窗口
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//				System.exit(0);
				
				new Choose();
				AudioPlayer.setisOver(true);
				dispose();
			}
		});
		
		Exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				System.exit(0);
				new Choose();
				AudioPlayer.setisOver(true);
				dispose();
			}
		});
		
Create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				chessBoard.Restart();
				makeNoDo();
				
				chat_send.setEditable(true);
				
//				System.out.println("Create");////////
				try {
					ljChessBoard.Create(chat_get, chat_send);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				makeNoDo();
				
				chat_send.setEditable(true);
//				System.out.println("Connect");////////
				try {
					ljChessBoard.Connect(chat_get, chat_send);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		//添加发送事件
		Send.addActionListener(new ActionListener(
				) {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String chatMessage = chat_send.getText();
				if(!chatMessage.trim().isEmpty() && chatMessage != null) {
					try {
						ljChessBoard.sendChatMessage(chatMessage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		Rank.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
//				MakeNoDo(Rank);
				try {
					/*if(doRank != null) {
						doRank.setVisible(true);
					}else {
						doRank = new DoRank(2);
					}*/
					new DoRank(2);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		StartOrStopMusic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(StartOrStopMusic.getText().equals("音乐开")) {
					StartOrStopMusic.setText("音乐停");
					AudioPlayer.setisOver(false);
					
					new AudioPlayer(URL).start();
				} else {
					StartOrStopMusic.setText("音乐开");
					AudioPlayer.setisOver(true);
				}
			}
		});
		
	}
	
public void InitCanChangeLable() {
		
		Font fontSC = new Font("楷体", Font.BOLD, 20);
		Font fontwhodo = new Font("楷体", Font.BOLD, 35);
		
		this.Bscore = new JLabel("" + 0);
		this.Bscore.setFont(fontSC);
		this.Bscore.setBounds(680, 240, 60, 30);
		add(Bscore);
		
		this.Wscore = new JLabel("" + 0);
		this.Wscore.setFont(fontSC);
//		this.Wscore.setForeground(Color.WHITE);
		this.Wscore.setForeground(color);
		this.Wscore.setBounds(680, 300, 60, 30);
		add(Wscore);
		
		this.Blackdo = new JLabel("");//////
//		this.Blackdo = new JLabel("");
		this.Blackdo.setFont(fontwhodo);
		this.Blackdo.setForeground(Color.BLACK);
		this.Blackdo.setBounds(640, 500, 60, 30);
		add(Blackdo);
		
		this.Whitedo = new JLabel("");
		this.Whitedo.setFont(fontwhodo);
//		this.Whitedo.setForeground(Color.WHITE);
		this.Whitedo.setForeground(color);
		this.Whitedo.setBounds(640, 500, 60, 30);
		add(Whitedo);
	}

	public void addChessBoard() {
		InitCanChangeLable();
		
		ljChessBoard.setBounds(0, 0, 600, 600);
		add(ljChessBoard);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LJMainJFrame ljMainJFrame = new LJMainJFrame();
	}

}
