package OOXXGUI;
import java.awt.Color;
//import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Main.AudioPlayer;
import Main.Choose;
import Main.Player;

public class MainJFrame extends JFrame {
	private ChessBoard chessBoard;
//	private NETChessBoard netChessBoard;
	
	protected JLabel BLACK; //黑子分数
	protected JLabel WHITE; //白子分数
	protected JLabel targetScore;//目标分数
	
	protected JLabel Bscore;//黑棋分数标签
	protected JLabel Wscore;//白棋分数标签
	protected JLabel Blackdo;//标记黑棋落子
	protected JLabel Whitedo;//标记白棋落子
	
	protected JButton Restart;
	protected JButton Exit;
	protected JButton StartOrStopMusic;
	
	protected final String URL = 
			"C:/Users/10033/MyApp/Android/AndroidProjects/CurriculumDesign/mus/sen.wav"; 
	
	
//	protected boolean flagofmusic = false;
	
	protected Color color = new Color(119, 136, 153);

	public MainJFrame() {
		
		chessBoard = new ChessBoard(this);
		
		InitButton();
		InitJLable();
		
		InitWindow();
		MyEvent();
	}
	

	public void setBscore(String text) {
		Bscore.setText(text);
	}

	public void setWscore(String text) {
		Wscore.setText(text);
	}

	public void setBlackdo(String text) {
		Blackdo.setText(text);;
	}

	public void setWhitedo(String text) {
		Whitedo.setText(text);;
	}
	
	public void InitButton() {
		Exit = new JButton("退出");
		Exit.setBounds(20, 620, 65, 30);
		add(Exit);
		
		Restart = new JButton("重新开始");
		Restart.setBounds(120, 620, 100, 30);
		add(Restart);
		
		StartOrStopMusic = new JButton("音乐开");
		StartOrStopMusic.setBounds(250, 620, 75, 30);
		add(StartOrStopMusic);
	}
	
	public void InitJLable() {
		Font fontWB = new Font("楷体", Font.BOLD, 40);
		Font fontTS = new Font("楷体", Font.BOLD, 60);
		
		targetScore = new JLabel("" + ChessBoard.TARGETSCORE);
		
		targetScore.setFont(fontTS);
		targetScore.setForeground(Color.RED);
		targetScore.setBounds(660, 100, 100, 120);
		add(targetScore);
		
		BLACK = new JLabel("黑:");
		BLACK.setFont(fontWB);
		BLACK.setForeground(Color.BLACK);
		BLACK.setBounds(620, 210, 80, 80);
		add(BLACK);
		
		WHITE = new JLabel("白:");
		WHITE.setFont(fontWB);
//		WHITE.setForeground(Color.WHITE);
		WHITE.setForeground(color);
		WHITE.setBounds(620, 270, 80, 80);
		add(WHITE);
	}
	
	public void InitWindow() {
		setTitle("OOXX  欢迎您：" + Player.getName());
		setLayout(null); //绝对定位
		setResizable(false); //窗口不能变大小
		this.setSize(800, 700); //宽，高
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); //居中显示
//		pack();// 自适应大小
		setVisible(true);
	}
	
	/*public void MakeNoDo(JButton jButton) {
		jButton.setEnabled(false);
	}*/
	
	public void MyEvent() {
		
		//关闭窗口
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
//						System.exit(0);
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
		
		Restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chessBoard.Restart();
//				MakeNoDo(Restart);
//				Restart.setEnabled(false);
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
		
		this.Blackdo = new JLabel("黑");
//		this.Blackdo = new JLabel("");
		this.Blackdo.setFont(fontwhodo);
		this.Blackdo.setForeground(Color.BLACK);
		this.Blackdo.setBounds(680, 500, 60, 30);
		add(Blackdo);
		
		this.Whitedo = new JLabel("");
		this.Whitedo.setFont(fontwhodo);
//		this.Whitedo.setForeground(Color.WHITE);
		this.Whitedo.setForeground(color);
		this.Whitedo.setBounds(680, 500, 60, 30);
		add(Whitedo);
	}
	
	public void addChessBoard() {
		InitCanChangeLable();
		
		chessBoard.setBounds(0, 0, 600, 600);
		add(chessBoard);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainJFrame mf = new MainJFrame();
		mf.addChessBoard();
	}

}
