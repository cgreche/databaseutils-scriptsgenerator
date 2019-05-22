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
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.Font;

@SuppressWarnings("serial")
public class ProjectSettingsDialog extends Dialog<Project> {
	
	private JLabel lblProjectName;
	private JTextField tfProjectName;
	private JLabel lblDefaultHeaderMessage;
	private JTextArea taDefaultHeaderMessage;
	private JLabel labelGenerationTargetPath;
	private JPanel panelSearchTargetPath;
		private JTextField tfGenerationTargetPath;
		private JButton buttonSearchGenTargetPath;

	private JButton btnCreateProject;
	
	//
	boolean editMode;
	private Project project;
	private JPanel panel_2;
	private JSeparator separator;
	private JScrollPane scrollPane;
	private Component rigidArea;
	private Component rigidArea_1;
	private Component rigidArea_2;
	private Component rigidArea_4;
	private Component rigidArea_5;
	private JLabel label;
	private JPanel panel_1;
	private JCheckBox cbProfileOracleDB;
	private JCheckBox cbProfileMySQL;
	private JSeparator separator_1;
	private Component rigidArea_6;
	
	public ProjectSettingsDialog(JFrame parent) {
		super(parent);
		setTitle("Propriedades do projeto");
		
		lblProjectName = new JLabel("Nome do projeto");
		lblProjectName.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfProjectName = new JTextField();
		tfProjectName.setMaximumSize(new Dimension(2147483647, 20));
		tfProjectName.setAlignmentX(Component.LEFT_ALIGNMENT);
		tfProjectName.setMinimumSize(new Dimension(4, 20));
		
		lblDefaultHeaderMessage = new JLabel("Mensagem de header padrão para scripts");
		lblDefaultHeaderMessage.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(lblProjectName);
		panel.add(tfProjectName);
		
		rigidArea = Box.createRigidArea(new Dimension(5, 5));
		panel.add(rigidArea);
		labelGenerationTargetPath = new JLabel("Caminho para salvar os scripts gerados");
		labelGenerationTargetPath.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(labelGenerationTargetPath);
		
		panelSearchTargetPath = new JPanel();
		panelSearchTargetPath.setMaximumSize(new Dimension(32767, 20));
		panel.add(panelSearchTargetPath);
		panelSearchTargetPath.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelSearchTargetPath.setLayout(new BoxLayout(panelSearchTargetPath,BoxLayout.X_AXIS));
		tfGenerationTargetPath = new JTextField();
		tfGenerationTargetPath.setAlignmentX(Component.LEFT_ALIGNMENT);
		tfGenerationTargetPath.setMinimumSize(new Dimension(4, 20));
		
		buttonSearchGenTargetPath = new JButton("Buscar");
		buttonSearchGenTargetPath.setPreferredSize(new Dimension(65, 20));
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
		
		rigidArea_1 = Box.createRigidArea(new Dimension(5, 5));
		panel.add(rigidArea_1);
		
		label = new JLabel("Perfis de Gera\u00E7\u00E3o");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(label);
		
		panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(0);
		panel_1.setAlignmentX(0.0f);
		panel.add(panel_1);
		
		cbProfileOracleDB = new JCheckBox("Oracle DB");
		panel_1.add(cbProfileOracleDB);
		
		cbProfileMySQL = new JCheckBox("MySQL");
		panel_1.add(cbProfileMySQL);
		
		rigidArea_5 = Box.createRigidArea(new Dimension(5, 5));
		panel.add(rigidArea_5);
		
		separator_1 = new JSeparator();
		panel.add(separator_1);
		
		rigidArea_6 = Box.createRigidArea(new Dimension(5, 5));
		panel.add(rigidArea_6);
		panel.add(lblDefaultHeaderMessage);
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(2, 32767));
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(scrollPane);
		taDefaultHeaderMessage = new JTextArea();
		scrollPane.setViewportView(taDefaultHeaderMessage);
		taDefaultHeaderMessage.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		rigidArea_2 = Box.createRigidArea(new Dimension(5, 5));
		panel.add(rigidArea_2);
		
		separator = new JSeparator();
		panel.add(separator);
		
		rigidArea_4 = Box.createRigidArea(new Dimension(5, 5));
		panel.add(rigidArea_4);
		
		panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setVgap(0);
		panel_2.setAlignmentX(0.0f);
		panel.add(panel_2);
		
		btnCreateProject = new JButton("Criar");
		btnCreateProject.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnCreateProject);
		btnCreateProject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String projectName = tfProjectName.getText();
				String generationTargetPath = tfGenerationTargetPath.getText();
				String defaultHeaderMessage = taDefaultHeaderMessage.getText();
				long generationProfiles = 0;
				if(cbProfileOracleDB.isSelected())
					generationProfiles |= Project.PROFILE_ORACLE_DB;
				if(cbProfileMySQL.isSelected())
					generationProfiles |= Project.PROFILE_MYSQL;
				
				project.setName(projectName);
				project.setScriptsGenerationBasePath(generationTargetPath);
				project.setDefaultScriptsHeaderMessage(defaultHeaderMessage);
				project.setGenerationProfiles(generationProfiles);
				
				fieldsToObject(project);
				if(validateProject(project)) {
					ProjectSettingsDialog.this.setResult(true, project);
					ProjectSettingsDialog.this.dispose();
				}
			}
			
		});
		this.setContentPane(panel);
		this.setSize(new Dimension(400,360));
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
	
	private void fieldsToObject(Project project) {
		project.setName(tfProjectName.getText().trim());
		project.setScriptsGenerationBasePath(tfGenerationTargetPath.getText().trim());
		long generationProfiles = 0;
		if(cbProfileOracleDB.isSelected())
			generationProfiles |= Project.PROFILE_ORACLE_DB;
		if(cbProfileMySQL.isSelected())
			generationProfiles |= Project.PROFILE_MYSQL;
		project.setGenerationProfiles(generationProfiles);
		project.setDefaultScriptsHeaderMessage(taDefaultHeaderMessage.getText());
	}
	
	private boolean validateProject(Project project) {
		String name = project.getName();
		String scriptsGenerationPath = project.getScriptsGenerationBasePath();
		long generationProfiles = project.getGenerationProfiles();

		if(name == null || "".equals(name)) {
			JOptionPane.showMessageDialog(this, "O nome do projeto não foi informado.");
			return false;
		}
		
		if(scriptsGenerationPath == null || "".equals(scriptsGenerationPath)) {
			JOptionPane.showMessageDialog(this, "O caminho de geração dos scripts não foi informado.");
			return false;
		}
		
		if(generationProfiles == 0) {
			JOptionPane.showMessageDialog(this, "Nenhum perfil de geração selecionado.");
			return false;
		}
		
		return true;
	}
	
}
