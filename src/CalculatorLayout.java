import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.regex.Pattern;//正则表达式的编译表示
import java.util.Stack;

@SuppressWarnings("serial")
/*
 * 实现计算器的布局
 */
class CalculatorLayout extends JFrame {
	
	JPanel jpl_2=new JPanel();//采用BorderLayout布局，添加文本框，jpl_3
	JPanel jpl_3=new JPanel();//采用GirdLayout布局，添加button
	JTextField jtf=new JTextField("0",20);//文本框初始化
	String[] c= {"时间","删除","归零","7","8","9","%","+","4","5","6","-","1","2","3","*","0",".","=","/"};//字符数组
	Button[] b=new Button[20];//键盘数组
	Listener l=new Listener();//监听器
	boolean point=true;//true表示数字，false表示符号

	
	StringBuffer display=new StringBuffer();

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
	

	class Listener implements ActionListener{

		String s;
		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
			s=e.getActionCommand();	
			switch(s) {
			case "时间":{
				jtf.setText(new SimpleDateFormat().format(new Date()));
				Timer t=new Timer();
				t.schedule(new TimerTask() {

					@Override
					public void run() {
							
						jtf.setText(display.toString());//延迟特定时间后执行改语句{public void run()的花括号里的语句}
					}
						
				}, 1000);
				break;
			}	
			case "删除":{
				if(display.length()!=0){
					display.deleteCharAt(display.length()-1);
					jtf.setText(display.toString());
					try {
						char ch=display.charAt(display.length()-1);
						switch(ch) {
						case '+':
						case '-':
						case '*':
						case '/':
						case '.':
							point=false;
							break;
						default:
							point=true;
						}
					}catch(StringIndexOutOfBoundsException e1) {
						System.out.println(e1.getMessage());
					}
				}
				break;
			}
			case "归零":{
				display.delete(0, display.length());//delete(int start,int end)包括start,不包括end
				jtf.setText("0");
				point=true;
				break;
			}
			case "=":{
				String exp=display.toString();
				double result=countResult(exp);
				//将结果返回显示
				display.delete(0, display.length());
				display.append(result);
				jtf.setText(display.toString());
				
				break;
			}
			case "+":
			case "-":
			case "*":
			case "/":
			case ".":
				if(point) {
					display.append(s);
					jtf.setText(display.toString());
					point=false;
				}
				break;
			default:
				display.append(s);
				jtf.setText(display.toString());
				point=true;
				break;
			
			
			}
		}
	}
	//添加监听
	public void addButtonListener(Button btn,Listener lis) {
		btn.addActionListener(lis);
	}
	//总计算程序，分别调用spilt(),infixTo
	public double countResult(String s) {
		String[] strSplit=split(s);
		String[] strSuffix=infixToSuffix(strSplit);
		double re=getResult(strSuffix);
		return re;
	}
	//实现中缀转后缀
	public String[] infixToSuffix(String[] exp) {
		/*
		 * 实现中缀表达式转后缀表达式
		 * (1).当读到数字直接送至输出队列中；
		 * (2).当读到运算符t时：
		 * 		a.将栈中所有优先级高于或等于t的运算符弹出
		 * 		b.t进栈
		 * (3)中缀表达式全部读完，若栈中仍有运算符，将其送到输出队列中。
		 */
		//创建后缀表达式字串
		ArrayList<String> suffix=new ArrayList<String>();//存放队列
		//创建操作符堆栈
		Stack<String> s =new Stack<String>();
		//输入的中缀表达式的长度
		int length=exp.length;
		for(int i=0;i<length;i++) {
			String temp;//临时字符量用来比较优先级
			//获取该中缀表达式的每一个字符并进行判断
			String ch=exp[i];
			switch(ch) {
				//遇到"+","-",将栈中的所有运算符全部弹出去,输入带队列中去
				case "+":
				case "-":
					while(s.size()!=0) {
						temp=s.pop();
						suffix.add(temp);
					}
					s.push(ch);
					break;
				//遇到"*","/"比较栈中运算符，"*","/"弹出
				case "*":
				case "/":
					while(s.size()!=0) {
						temp=s.pop();
						
						if(temp.equals("*")||temp.equals("/")) {
							suffix.add(temp);
							s.push(ch);
							break;
						}else {
							s.push(temp);
							s.push(ch);
							break;
						}
					}
					if(s.empty())
						s.push(ch);
					break;
				default:
					suffix.add(ch);
					break;
			}
		}
		while(!s.isEmpty()){
			suffix.add(s.pop());
		}
		return suffix.toArray(new String[0]);
	}
	//实现后缀运算
	public double getResult(String[] expression) {
		double result=0;
		double num1,num2;
		Stack<Double> strStack=new Stack<Double>();
		
		for(int i=0;i<expression.length;i++) {
			if(expression[i].equals("+")||expression[i].equals("-")
					||expression[i].equals("*")||expression[i].equals("/")) {
				String str=expression[i];
				switch(str) {
					case "+":{
						num1=strStack.pop();
						num2=strStack.pop();
						result=num2+num1;
						strStack.push(result);
						break;
					}
					case "-":{
						num1=strStack.pop();
						num2=strStack.pop();
						result=num2-num1;
						strStack.push(result);
						break;
					}
					case "*":{
						num1=strStack.pop();
						num2=strStack.pop();
						result=num2*num1;
						strStack.push(result);
						break;
					}
					case "/":{
						num1=strStack.pop();
						num2=strStack.pop();
						result=num2/num1;
						strStack.push(result);
						break;
					}
				}
			}else {
				strStack.push(Double.valueOf(expression[i]));
			}
			
		}
		result=0;
		while(!strStack.isEmpty()) {
			result+=strStack.pop();
		}
		return result;
		
	}
	//实现字符串分割
	public String[] split(String expression) {
		ArrayList<String> strList=new ArrayList<String>();
		int splitIndex=0;
		
		for(int i=0;i<expression.length();i++) {
			if(expression.charAt(i)=='+'||expression.charAt(i)=='-'
					||expression.charAt(i)=='*'||expression.charAt(i)=='/') 
			{
				strList.add(expression.substring(splitIndex, i));//包含起始索引，不包含结束索引
				splitIndex=i;
				strList.add(expression.substring(splitIndex,splitIndex+1));
				splitIndex++;
			}
			if((i==expression.length()-1)&&(splitIndex<=i)) {
				strList.add(expression.substring(splitIndex));
			}
		}
		return  strList.toArray(new String[0]);//?????????????????????
	}
}