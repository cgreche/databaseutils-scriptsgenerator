package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

//16/05/2018
public class StatusBar extends JPanel {
	
	private JLabel lblText;
	
	public StatusBar() {
		lblText = new JLabel();
		this.add(lblText);
	}
	
	public void setText(String text) {
		lblText.setText(text);
	}
	
	public String getText() {
		return lblText.getText();
	}
	
}
