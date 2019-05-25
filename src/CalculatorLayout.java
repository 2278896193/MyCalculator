import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/*
 * 实现计算器的布局
 */
class CalculatorLayout extends JFrame {
	
	JPanel jpl_2=new JPanel();//采用BorderLayout布局，添加文本框，jpl_3
	JPanel jpl_3=new JPanel();//采用GirdLayout布局，添加button
	JTextField jtf=new JTextField(20);//文本框初始化
	String[] c= {"时间","删除","归零","7","8","9","%","+","4","5","6","-","1","2","3","x","0",".","=","/"};//字符数组
	Button[] b=new Button[20];//键盘数组
	Listener l=new Listener();//监听器
	
	String display;
	
	public CalculatorLayout() {
		//jpl3--5行4列
		jpl_3.setLayout(new GridLayout(5,4));
		
		for(int i=0;i<20;i++) {
			Button btn=new Button(c[i]);
			b[i]=btn;
			jpl_3.add(b[i]);
			addButtonListener(b[i],l);
		}

		
		//jpl_2
		jpl_2.setLayout(new BorderLayout());
		
		//添加组件
		jpl_2.add(jtf,"North");
		jpl_2.add(jpl_3,"Center");
		
		//窗口添加
		this.add(jpl_2);
		this.setTitle("计算器");//标题
		this.setSize(400,320);//大小
		this.setVisible(true);//窗口显示
		this.setLocationRelativeTo(null);//窗口位置
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//关闭
	}

		public void addButtonListener(Button btn,Listener lis) {
			btn.addActionListener(lis);
		}
		
		class Listener implements ActionListener{

			String s=null;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				s=e.getActionCommand();	
				display=jtf.getText()+s;
				jtf.setText(display);
			}
		
		}
}