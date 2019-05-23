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
		// TODO 自动生成的方法存根
		String s=e.getActionCommand();
		
	}
}