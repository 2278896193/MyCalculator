import java.awt.*;
import java.awt.event.*;
class ButtonListener {
	public ButtonListener(Button btn,Listener lis) {
		btn.addActionListener(lis);
	}
}
	
	
class Listener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		String s=e.getActionCommand();
		
	}
}