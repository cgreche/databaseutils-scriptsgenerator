package ui.frames;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NewProjectDialog extends JDialog {
	
	JLabel lblProjectName;
	JTextField tfProjectName;
	JButton btnCreateProject;
	
	boolean result;
	
	public NewProjectDialog(JFrame parent) {
		super(parent, true);
		
		lblProjectName = new JLabel("Nome do projeto");
		tfProjectName = new JTextField();
		
		btnCreateProject = new JButton("Criar");
		btnCreateProject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				result = true;
				NewProjectDialog.this.dispose();
			}
			
		});
		
		Container pane = this.getContentPane();
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		pane.add(lblProjectName);
		pane.add(tfProjectName);
		pane.add(btnCreateProject);
		this.pack();
	}
	
	@Override
	public void setVisible(boolean visible) {
		result = false;
		super.setVisible(visible);
	}
	
	public boolean getResult() {
		return result;
	}
	
	public String getProjectName() {
		return tfProjectName.getText();
	}
}
