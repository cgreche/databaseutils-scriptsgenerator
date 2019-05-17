package ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

//16/05/2018
public class StatusBar extends JPanel {
	
	private JLabel lblText;
	
	public StatusBar() {
		this.setBackground(new Color(0xC3C3C3));
		lblText = new JLabel("fdsdsdsfsd");
		this.add(lblText);
	}
	
	public void setText(String text) {
		lblText.setText(text);
	}
	
	public String getText() {
		return lblText.getText();
	}
	
}
