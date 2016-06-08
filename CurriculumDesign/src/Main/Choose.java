package Main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import OOXXGUI.LJMainJFrame;
import OOXXGUI.MainJFrame;
import OOXXGUI.RJMainJFrame;

public class Choose extends JFrame {
	
	private JButton RJ, DJ, LJ;
	
	public Choose() {
		InitWindow();
		InitButton();
		MyEvent();
	}
	
	public void InitWindow() {
		this.setTitle("选择模式");
		this.setSize(350, 150);
		this.setLocationRelativeTo(null); //居中显示
		this.setResizable(false); //窗口不能变大小
		this.setLayout(null); //绝对定位
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void InitButton() {
		Font font = new Font("楷体", Font.BOLD, 15);//按钮字体
		RJ = new JButton("人机");
		RJ.setFont(font);
		RJ.setBounds(30, 40, 80, 40);
		add(RJ);
		
		DJ = new JButton("单机");
		DJ.setFont(font);
		DJ.setBounds(130, 40, 80, 40);
		add(DJ);
		
		LJ = new JButton("联机");
		LJ.setFont(font);
		LJ.setBounds(230, 40, 80, 40);
		add(LJ);
	}
	
	public void MyEvent() {
		
		//关闭窗口
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		RJ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new RJMainJFrame().addChessBoard();
				dispose();
			}
			
		});
		
		DJ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new MainJFrame().addChessBoard();;
				dispose();
			}
			
		});
		
		LJ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new LJMainJFrame();
				dispose();
			}
			
		});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Choose choose = new Choose();
	}

}
