package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

//16/05/2018
public class StatusBar extends JPanel {
	
	private Color backgroundColor;
	private Color textColor;
	private JLabel lblText;
	
	public StatusBar() {
		lblText = new JLabel("");
		setBackgroundColor(new Color(0xC3C3C3));
		setTextColor(new Color(0xff0000));
		this.add(lblText);
		this.setPreferredSize(new Dimension(this.getMaximumSize().width,26));
	}
	
	public void setText(String text) {
		lblText.setText(text);
	}
	
	public String getText() {
		return lblText.getText();
	}
	
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
		this.setBackground(color);
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setTextColor(Color color) {
		this.textColor = color;
		lblText.setForeground(color);
	}

	public Color getTextColor() {
		return textColor;
	}
	
}
