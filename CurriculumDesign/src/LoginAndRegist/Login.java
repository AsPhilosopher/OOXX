package LoginAndRegist;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Main.Choose;
import Main.Player;
import Service.ServeLogin;
import Service.ServeRegist;
import Service.getPromptBox;

public class Login extends JFrame {
	private JButton button_log = null;
	private JButton button_reg = null;
	private JTextField jtextuser = null;
	private JPasswordField jtextpsw = null;
	private JLabel jlabeluser = null;
	private JLabel jlabelpsw = null;
	
	//添加组件与事件
	public Login() {
		this.InitComponent();
		setLayout(null); //绝对定位
		setResizable(false); //窗口不能变大小
		add(jlabeluser);
		add(jtextuser);
		
		add(jlabelpsw);
		add(jtextpsw);
		
		add(button_log);
		add(button_reg);
		
		this.MyEvent(); //加载事件
	}
	
	//设置组件
	public void InitComponent() { 
		//设置字体
				Font font = new Font("楷体", Font.BOLD, 20);//标签字体
				Font font1 = new Font("楷体", Font.BOLD, 15);//按钮字体
				Font font2 = new Font("楷体", Font.BOLD, 18);//输入框字体
				//设置输入框
				jtextuser = new JTextField(20);
				jtextuser.setBounds(120, 50, 200, 25);  //宽，高
				jtextuser.setFont(font2);
				
				jtextpsw = new JPasswordField(20);
				jtextpsw.setBounds(120, 140, 200, 25); //宽，高
				//设置标签
				jlabeluser = new JLabel("用户名");
				jlabeluser.setBounds(40, 50, 80, 25);
				jlabeluser.setFont(font);
				
				jlabelpsw = new JLabel("密  码");
				jlabelpsw.setBounds(40, 140, 80, 25);
				jlabelpsw.setFont(font);
				//设置按钮
				button_log = new JButton("登录");
				button_log.setFont(font1);
				button_log.setBounds(60, 190, 80, 40);
				
				button_reg = new JButton("注册");
				button_reg.setFont(font1);
				button_reg.setBounds(190, 190, 80, 40);
	}
	
	//设置窗口相关
	public void InitWindows() {
		this.setTitle("OOXX登录(Login)");
		this.setSize(400, 300); //宽，高
		this.setLocationRelativeTo(null); //居中显示
//		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	//获得输入框里的内容
	public String getText(JTextField  text) {
		return text.getText();
	}
	
	//设置事件
	public void MyEvent() {
		//关闭窗口
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		button_log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//用户名为空
				String username = getText(jtextuser);
//				System.out.println(username);////////////
				if(!ServeRegist.isEmpty(username)) {
					getPromptBox.getBox("用户名不能为空", "Warning");
					return;
				}
				//密码为空
				String password = getText(jtextpsw);
				if(!ServeRegist.isEmpty(password)) {
					getPromptBox.getBox("密码不能为空", "Warning");
					return;
				}
				//检验用户名和密码
				try {
					int flag = ServeLogin.messageisTrue(username, password);
					
					/**
					 * 成功登陆  后期要改
					 */
					if(flag == 1) { 
//						getPromptBox.getBox("登录成功", "Tip");
						Player.setName(username); //给玩家用户名
						new Choose();
						dispose(); //释放资源
						
					} else if (flag == 0){
						getPromptBox.getBox("密码错误", "Warning");
					}else {
						getPromptBox.getBox("用户名不存在", "Warning");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		jtextuser.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if(code == KeyEvent.VK_ENTER ) {
					button_log.doClick();
//					e.consume();//控制非法输入
				}
			}
		});
		
		jtextpsw.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if(code == KeyEvent.VK_ENTER ) {
					button_log.doClick();
//					e.consume();//控制非法输入
				}
			}
		});
		
		button_reg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				setVisible(false);
				dispose(); //释放资源
				Regist regist = new Regist();
				regist.InitWindows();
			}
			
		});
		
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login login = new Login();
		login.InitWindows();
		
		
//		System.out.println("@@");
	}*/

}
