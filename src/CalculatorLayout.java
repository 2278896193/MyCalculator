import java.awt.Button;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * ʵ�ּ������Ĳ���
 */
class CalculatorLayout extends JFrame {
	public CalculatorLayout() {
		
		JPanel jpl_1=new JPanel();
		JPanel jpl_2=new JPanel();
		JPanel jpl_3=new JPanel();//����GirdLayou���֣����button
		
		//jpl3--4��5��
		jpl_3.setLayout(new GridLayout(4,5));
		
		//���̳�ʼ��
		Button btn_0=new Button("0");
		Button btn_1=new Button("1");
		Button btn_2=new Button("2");
		Button btn_3=new Button("3");
		Button btn_4=new Button("4");
		Button btn_5=new Button("5");
		Button btn_6=new Button("6");
		Button btn_7=new Button("7");
		Button btn_8=new Button("8");
		Button btn_9=new Button("9");
		Button btn_t=new Button("ʱ��");
		Button btn_de=new Button("ɾ��");
		Button btn_mz=new Button("����");
		Button btn_r=new Button("%");
		Button btn_pl=new Button("+");
		Button btn_mi=new Button("-");
		Button btn_mu=new Button("*");
		Button btn_di=new Button("/");
		Button btn_po=new Button(".");
		Button btn_e=new Button("=");
		
		//���̼���jpl_3
		jpl_3.add(btn_t);
		jpl_3.add(btn_de);
		jpl_3.add(btn_mz);
		jpl_3.add(btn_r);
		
		jpl_3.add(btn_7);
		jpl_3.add(btn_8);
		jpl_3.add(btn_9);
		jpl_3.add(btn_pl);
		
		jpl_3.add(btn_4);
		jpl_3.add(btn_5);
		jpl_3.add(btn_6);
		jpl_3.add(btn_mi);
		
		jpl_3.add(btn_1);
		jpl_3.add(btn_2);
		jpl_3.add(btn_3);
		jpl_3.add(btn_mu);
		
		jpl_3.add(btn_0);
		jpl_3.add(btn_po);
		jpl_3.add(btn_e);
		jpl_3.add(btn_di);
		
	}
	
	public void go() {//ʵ�ּ������Ĵ�����ʾ
		this.setTitle("������");//����
		this.setSize(500,500);//��С
		this.setVisible(true);//������ʾ
		this.setLocation(400, 300);//����λ��
	}
}
