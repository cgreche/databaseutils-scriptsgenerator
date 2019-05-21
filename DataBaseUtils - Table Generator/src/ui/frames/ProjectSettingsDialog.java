package ui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import structs.Project;
import tests.Test;

@SuppressWarnings("serial")
public class ProjectSettingsDialog extends Dialog<Project> {
	
	private JPanel panelProjectName; //todo: use
		private JLabel lblProjectName;
		private JTextField tfProjectName;
		private JLabel lblDefaultHeaderMessage;
		private JTextArea taDefaultHeaderMessage;
	
	private JPanel panelGenerationTargetPath;
	private JLabel labelGenerationTargetPath;
	private JPanel panelSearchTargetPath;
		private JTextField tfGenerationTargetPath;
		private JButton buttonSearchGenTargetPath;
	private JLabel lblGenerationProfiles;
		private JCheckBox cbProfileOracle;
		private JCheckBox cbProfileMySQL;

	private JButton btnCreateProject;
	
	private static int TEXT_FIELD_DEFAULT_HEIGHT = 26;

	//
	boolean editMode;
	private Project project;
	
	public ProjectSettingsDialog(JFrame parent) {
		super(parent);
		
		lblProjectName = new JLabel("Nome do projeto");
		tfProjectName = new JTextField();
		tfProjectName.setMinimumSize(new Dimension(4, TEXT_FIELD_DEFAULT_HEIGHT));
		tfProjectName.setPreferredSize(new Dimension(4, TEXT_FIELD_DEFAULT_HEIGHT));
		
		lblDefaultHeaderMessage = new JLabel("Mensagem de header padrão para scripts");
		taDefaultHeaderMessage = new JTextArea();
		
		panelGenerationTargetPath = new JPanel();
		panelGenerationTargetPath.setLayout(new BoxLayout(panelGenerationTargetPath,BoxLayout.Y_AXIS));
		labelGenerationTargetPath = new JLabel("Caminho para salvar os scripts gerados");
		
		panelSearchTargetPath = new JPanel();
		panelSearchTargetPath.setLayout(new BoxLayout(panelSearchTargetPath,BoxLayout.X_AXIS));
		tfGenerationTargetPath = new JTextField();
		tfGenerationTargetPath.setMinimumSize(new Dimension(4, TEXT_FIELD_DEFAULT_HEIGHT));
		tfGenerationTargetPath.setPreferredSize(new Dimension(4, TEXT_FIELD_DEFAULT_HEIGHT));
		
		buttonSearchGenTargetPath = new JButton("Buscar");
		buttonSearchGenTargetPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(ProjectSettingsDialog.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					tfGenerationTargetPath.setText(file.getAbsolutePath());
				} else {
				}
			}
		});
		
		panelSearchTargetPath.add(tfGenerationTargetPath);
		panelSearchTargetPath.add(buttonSearchGenTargetPath);
		
		panelGenerationTargetPath.add(labelGenerationTargetPath);
		panelGenerationTargetPath.add(panelSearchTargetPath);
		
		lblGenerationProfiles = new JLabel("Perfis de Geração");
		cbProfileOracle = new JCheckBox("Oracle DB");
		cbProfileMySQL = new JCheckBox("MySQL");
		
		btnCreateProject = new JButton("Criar");
		btnCreateProject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String projectName = tfProjectName.getText();
				String generationTargetPath = tfGenerationTargetPath.getText();
				String defaultHeaderMessage = taDefaultHeaderMessage.getText();
				long generationProfiles = 0;
				if(cbProfileOracle.isSelected())
					generationProfiles |= Project.PROFILE_ORACLE_DB;
				if(cbProfileMySQL.isSelected())
					generationProfiles |= Project.PROFILE_MYSQL;
				
				project.setName(projectName);
				project.setScriptsGenerationBasePath(generationTargetPath);
				project.setDefaultScriptsHeaderMessage(defaultHeaderMessage);
				project.setGenerationProfiles(generationProfiles);
				
				if(checkFieldsIntegrity()) {
					ProjectSettingsDialog.this.setResult(true, project);
					ProjectSettingsDialog.this.dispose();
				}
			}
			
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(lblProjectName);
		panel.add(tfProjectName);
		panel.add(panelGenerationTargetPath);
		panel.add(lblDefaultHeaderMessage);
		panel.add(taDefaultHeaderMessage);
		panel.add(lblGenerationProfiles);
		panel.add(cbProfileOracle);
		panel.add(cbProfileMySQL);
		panel.add(btnCreateProject);
		this.setContentPane(panel);
		this.pack();
	}
	
	public void insertNew() {
		editMode = false;
		//TODO
		this.project = Test.newProject();
		//
		this.setResult(false, null);
		this.setVisible(true);
	}
	
	public void edit(Project project) {
		editMode = true;
		this.project = project.clone();
		this.setResult(false, null);
		this.setVisible(true);
	}
	
	public boolean isEditMode() {
		return editMode;
	}
	
	public boolean checkFieldsIntegrity() {
		if(tfProjectName.getText() == null || "".equals(tfProjectName.getText().trim())) {
			JOptionPane.showMessageDialog(this, "O nome do projeto não foi informado.");
			return false;
		}
		
		if(tfGenerationTargetPath.getText() == null || "".equals(tfGenerationTargetPath.getText().trim())) {
			JOptionPane.showMessageDialog(this, "O caminho de geração dos scripts não foi informado.");
			return false;
		}
		
		//TODO: normalizar controle ou field da classe?
		if(project.getGenerationProfiles() == 0) {
			JOptionPane.showMessageDialog(this, "Nenhum perfil de geração selecionado.");
			return false;
		}
		
		return true;
	}
	
}
