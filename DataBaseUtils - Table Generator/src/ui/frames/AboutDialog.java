package ui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

//24/05/2019
public class AboutDialog extends Dialog {
	public AboutDialog(JFrame parent) {
		super(parent);
		setTitle("Sobre");
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setIcon(new ImageIcon(AboutDialog.class.getResource("/resources/logoEsocial.png")));
		panel.add(lblNewLabel);
		
		Component verticalGlue = Box.createVerticalGlue();
		panel.add(verticalGlue);
		
		JLabel lblAppName = new JLabel();
		lblAppName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblAppName.setText(application.Properties.APPLICATION_NAME);
		lblAppName.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblAppName);
		
		JLabel lblVersion = new JLabel("v" + application.Properties.APPLICATION_VERSION + " (" + application.Properties.APPLICATION_VERSION_YEAR + ")");
		panel.add(lblVersion);
		lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		panel.add(verticalGlue_2);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblAuthor = new JLabel("Techne Engenharia e Sistemas Ltda.");
		lblAuthor.setMaximumSize(new Dimension(32767, 14));
		lblAuthor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAuthor.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(lblAuthor);
		
		setSize(new Dimension(440,260));
	}
}
