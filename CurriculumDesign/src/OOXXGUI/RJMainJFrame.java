package OOXXGUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;

import Main.AudioPlayer;
import Main.Choose;
import Service.DoRank;

public class RJMainJFrame extends MainJFrame {

	private RJChessBoard rjChessBoard;

	protected JButton Rank;
//	protected DoRank doRank;

	public RJMainJFrame() {
//		super();
//		System.out.println("@@@");
		rjChessBoard = new RJChessBoard(this);
		
		InitButton();
		add(StartOrStopMusic);
		InitJLable();
		
		InitWindow();
		MyEvent();
		
	}
	
	public void InitButton() {
		Exit = new JButton("退出");
		Exit.setBounds(20, 620, 65, 30);
		add(Exit);
		
		Restart = new JButton("重新开始");
		Restart.setBounds(120, 620, 100, 30);
		add(Restart);
		
		Rank = new JButton("排行榜");
		Rank.setBounds(255, 620, 80, 30);
		add(Rank);
		
		StartOrStopMusic = new JButton("音乐开");
		StartOrStopMusic.setBounds(375, 620, 75, 30);
		
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
		//		System.exit(0);
				new Choose();
				AudioPlayer.setisOver(true);
				dispose();
			}
		});
		Rank.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					/*if(doRank != null) {
						doRank.setVisible(true);
					}else {
						doRank = new DoRank(1);
					}*/
					new DoRank(1);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		Restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rjChessBoard.Restart();
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
		
		this.Blackdo = new JLabel("人");
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
		
		rjChessBoard.setBounds(0, 0, 600, 600);
		add(rjChessBoard);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RJMainJFrame rjMainJFrame = new RJMainJFrame();
		rjMainJFrame.addChessBoard();
	}

}
