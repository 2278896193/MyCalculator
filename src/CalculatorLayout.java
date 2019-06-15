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

import java.util.regex.Pattern;//������ʽ�ı����ʾ
import java.util.Stack;

@SuppressWarnings("serial")
/*
 * ʵ�ּ������Ĳ���
 */
class CalculatorLayout extends JFrame {
	
	JPanel jpl_2=new JPanel();//����BorderLayout���֣�����ı���jpl_3
	JPanel jpl_3=new JPanel();//����GirdLayout���֣����button
	JTextField jtf=new JTextField("0",20);//�ı����ʼ��
	String[] c= {"ʱ��","ɾ��","����","7","8","9","%","+","4","5","6","-","1","2","3","*","0",".","=","/"};//�ַ�����
	Button[] b=new Button[20];//��������
	Listener l=new Listener();//������
	boolean point=true;//true��ʾ���֣�false��ʾ����

	
	StringBuffer display=new StringBuffer();

	public CalculatorLayout() {
		//jpl3--5��4��
		jpl_3.setLayout(new GridLayout(5,4));
		
		for(int i=0;i<20;i++) {
			Button btn=new Button(c[i]);
			b[i]=btn;
			jpl_3.add(b[i]);
			addButtonListener(b[i],l);
		}

		
		//jpl_2
		jpl_2.setLayout(new BorderLayout());
		
		//������
		jpl_2.add(jtf,"North");
		jpl_2.add(jpl_3,"Center");
		
		//�������
		this.add(jpl_2);
		this.setTitle("������");//����
		this.setSize(400,320);//��С
		this.setVisible(true);//������ʾ
		this.setLocationRelativeTo(null);//����λ��
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//�ر�
	}
	

	class Listener implements ActionListener{

		String s;
		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
			s=e.getActionCommand();	
			switch(s) {
			case "ʱ��":{
				jtf.setText(new SimpleDateFormat().format(new Date()));
				Timer t=new Timer();
				t.schedule(new TimerTask() {

					@Override
					public void run() {
							
						jtf.setText(display.toString());//�ӳ��ض�ʱ���ִ�и����{public void run()�Ļ�����������}
					}
						
				}, 1000);
				break;
			}	
			case "ɾ��":{
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
			case "����":{
				display.delete(0, display.length());//delete(int start,int end)����start,������end
				jtf.setText("0");
				point=true;
				break;
			}
			case "=":{
				String exp=display.toString();
				double result=countResult(exp);
				//�����������ʾ
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
	//��Ӽ���
	public void addButtonListener(Button btn,Listener lis) {
		btn.addActionListener(lis);
	}
	//�ܼ�����򣬷ֱ����spilt(),infixTo
	public double countResult(String s) {
		String[] strSplit=split(s);
		String[] strSuffix=infixToSuffix(strSplit);
		double re=getResult(strSuffix);
		return re;
	}
	//ʵ����׺ת��׺
	public String[] infixToSuffix(String[] exp) {
		/*
		 * ʵ����׺���ʽת��׺���ʽ
		 * (1).����������ֱ��������������У�
		 * (2).�����������tʱ��
		 * 		a.��ջ���������ȼ����ڻ����t�����������
		 * 		b.t��ջ
		 * (3)��׺���ʽȫ�����꣬��ջ������������������͵���������С�
		 */
		//������׺���ʽ�ִ�
		ArrayList<String> suffix=new ArrayList<String>();//��Ŷ���
		//������������ջ
		Stack<String> s =new Stack<String>();
		//�������׺���ʽ�ĳ���
		int length=exp.length;
		for(int i=0;i<length;i++) {
			String temp;//��ʱ�ַ��������Ƚ����ȼ�
			//��ȡ����׺���ʽ��ÿһ���ַ��������ж�
			String ch=exp[i];
			switch(ch) {
				//����"+","-",��ջ�е����������ȫ������ȥ,�����������ȥ
				case "+":
				case "-":
					while(s.size()!=0) {
						temp=s.pop();
						suffix.add(temp);
					}
					s.push(ch);
					break;
				//����"*","/"�Ƚ�ջ���������"*","/"����
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
	//ʵ�ֺ�׺����
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
	//ʵ���ַ����ָ�
	public String[] split(String expression) {
		ArrayList<String> strList=new ArrayList<String>();
		int splitIndex=0;
		
		for(int i=0;i<expression.length();i++) {
			if(expression.charAt(i)=='+'||expression.charAt(i)=='-'
					||expression.charAt(i)=='*'||expression.charAt(i)=='/') 
			{
				strList.add(expression.substring(splitIndex, i));//������ʼ��������������������
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