package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JButton;

//16/05/2018
public class StatusBar extends JPanel {
	
	private Color backgroundColor;
	private Color textColor;
	private JLabel lblText;
	
	public StatusBar() {
		setBorder(new EmptyBorder(0, 10, 0, 0));
		lblText = new JLabel("");
		setBackgroundColor(new Color(0xC3C3C3));
		setTextColor(new Color(0x000000));
		setLayout(new FlowLayout(FlowLayout.LEFT));
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
