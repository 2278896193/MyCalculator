import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * ʵ�ּ������Ĳ���
 */
class CalculatorLayout extends JFrame {
	
	JPanel jpl_2=new JPanel();//����BorderLayout���֣�����ı���jpl_3
	JPanel jpl_3=new JPanel();//����GirdLayout���֣����button
	JTextField jtf=new JTextField(20);//�ı����ʼ��
	String[] c= {"ʱ��","ɾ��","����","7","8","9","%","+","4","5","6","-","1","2","3","x","0",".","=","/"};//�ַ�����
	Button[] b=new Button[20];//��������
	
	public CalculatorLayout() {
		//jpl3--5��4��
		jpl_3.setLayout(new GridLayout(5,4));
		
		for(int i=0;i<20;i++) {
			Button btn=new Button(c[i]);
			b[i]=btn;
			jpl_3.add(b[i]);
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

}