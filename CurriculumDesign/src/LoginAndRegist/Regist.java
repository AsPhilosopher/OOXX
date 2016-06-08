package LoginAndRegist;

import java.awt.Font;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Service.ServeRegist;
import Service.getPromptBox;

public class Regist extends JFrame {
	
	private JButton button_reg = null;
	private JTextField jtextuser = null;
	private JPasswordField jtextpsw = null;
	private JPasswordField rejtextpsw = null;
	private JLabel jlabeluser = null;
	private JLabel jlabelpsw = null;
	private JLabel rejlabelpsw = null;
	
	public Regist() {
		this.InitComponent();
		setLayout(null); //绝对定位
		setResizable(false); //窗口不能变大小
		add(jlabeluser);
		add(jtextuser);
		
		add(jlabelpsw);
		add(jtextpsw);
		add(rejtextpsw);
		
		add(rejlabelpsw);
		add(jtextpsw);
		add(rejtextpsw);
		
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
					jtextuser.setBounds(130, 50, 200, 25);  //宽，高
					jtextuser.setFont(font2);
					
					jtextpsw = new JPasswordField(20);
					jtextpsw.setBounds(130, 100, 200, 25); //宽，高
					
					rejtextpsw = new JPasswordField(20);
					rejtextpsw.setBounds(130, 150, 200, 25); //宽，高
					//设置标签
					jlabeluser = new JLabel("用户名");
					jlabeluser.setBounds(40, 50, 80, 25);
					jlabeluser.setFont(font);
					
					jlabelpsw = new JLabel("密  码");
					jlabelpsw.setBounds(40, 100, 80, 25);
					jlabelpsw.setFont(font);
					
					rejlabelpsw = new JLabel("重复密码");
					rejlabelpsw.setBounds(40, 150, 120, 25);
					rejlabelpsw.setFont(font);
					//设置按钮
					button_reg = new JButton("注册");
					button_reg.setFont(font1);
					button_reg.setBounds(150, 210, 80, 40);
			
		}
		
		//设置窗口
		public void InitWindows() {
			this.setTitle("OOXX注册(Regist)");
			this.setSize(400, 300); //宽，高
			this.setLocationRelativeTo(null); //居中显示
//			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
			
			button_reg.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//用户名为空
					String username = getText(jtextuser);
//					System.out.println(username);////////////
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
					//密码与密码重复不一致
					String repassword = getText(rejtextpsw);
					if(!ServeRegist.psw_equ_repsw(password, repassword)) {
						getPromptBox.getBox("密码输入不一致", "Warning");
						return;
					}
					//用户名已存在
					try {
						if(!ServeRegist.isexeUser(username, password)) {
							getPromptBox.getBox("用户名已存在", "Warning");
							return;
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					//注册成功
//					setVisible(false);
					dispose(); //释放资源
					Login login = new Login();
					login.InitWindows();
					getPromptBox.getBox("注册成功", "Tip");
				}
				
			});
			
			jtextuser.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int code = e.getKeyCode();
					if(code == KeyEvent.VK_ENTER ) {
						button_reg.doClick();
//						e.consume();//控制非法输入
					}
				}
			});
			
			jtextpsw.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int code = e.getKeyCode();
					if(code == KeyEvent.VK_ENTER ) {
						button_reg.doClick();
//						e.consume();//控制非法输入
					}
				}
			});
			
			rejtextpsw.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int code = e.getKeyCode();
					if(code == KeyEvent.VK_ENTER ) {
						button_reg.doClick();
//						e.consume();//控制非法输入
					}
				}
			});
		}
		
		/*public static void main(String[] args) {
			// TODO Auto-generated method stub
			Regist regist = new Regist();
			regist.InitWindows();
			
//			System.out.println("@@");
		}*/

}
