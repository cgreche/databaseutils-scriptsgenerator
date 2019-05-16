package ui.frames;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import structs.AddFieldCommand;
import structs.Constraints;
import structs.CreateTableCommand;
import structs.DropFieldCommand;
import structs.GenericTypes;
import structs.ModifyFieldCommand;
import structs.Project;
import structs.Script;
import structs.Table;
import structs.TableField;

@SuppressWarnings("serial")
public class ProjectSettingsDialog extends Dialog<Project> {
	
	private JPanel panelProjectName; //todo: use
		private JLabel lblProjectName;
		private JTextField tfProjectName;
	
	private JPanel panelGenerationTargetPath;
	private JLabel labelGenerationTargetPath;
	private JPanel panelSearchTargetPath;
		private JTextField tfGenerationTargetPath;
		private JButton buttonSearchGenTargetPath;

	private JButton btnCreateProject;

	//
	boolean editMode;
	private boolean result;
	private Project project;
	
	private static Table createTable1() {
		TableField field1 = new TableField();
		field1.setName("GUID");
		field1.setConstraints(Constraints.PK);
		field1.setSize("255");
		field1.setType(GenericTypes.TEXT);
		
		TableField field2 = new TableField();
		field2.setName("IDEVENTO");
		field2.setSize("36");
		field2.setType(GenericTypes.TEXT);
		field2.setConstraints(Constraints.NOT_NULL|Constraints.FK);
		field2.setReferencedTable("WOOOOW");
		
		TableField field3 = new TableField();
		field3.setName("TENANTID");
		field3.setSize("10,0");
		field3.setType(GenericTypes.NUMERIC);
		
		Table table = new Table();
		table.setName("S5001_EVT_BASES_TRAB");
		table.setFields(new ArrayList<>(Arrays.asList(new TableField[] {field1,field2,field3})));
		return table;
	}
	
	private static Table createTable2() {
		TableField field1 = new TableField();
		field1.setName("GUID");
		field1.setConstraints(Constraints.PK);
		field1.setType(GenericTypes.TEXT);
		
		TableField field3 = new TableField();
		field3.setName("ID2");
		field3.setConstraints(Constraints.PK);
		field3.setType(GenericTypes.NUMERIC);
		field3.setSize("99,2");
		
		TableField field2 = new TableField();
		field2.setName("TENANTID");
		field2.setType(GenericTypes.NUMERIC);
		
		Table table = new Table();
		table.setName("TEST_TABLE");
		table.setFields(new ArrayList<>(Arrays.asList(new TableField[] {field1,field2,field3})));
		return table;
	}
	
	private static Script createScript1() {
		Table table1 = createTable1();
		Script script = new Script();
		script.setName(table1.getName());
		script.addCommand(new CreateTableCommand(script, table1));
		TableField field3 = new TableField();
		field3.setName("ERICLES");
		field3.setConstraints(Constraints.PK);
		field3.setType(GenericTypes.DATE);
		script.addCommand(new AddFieldCommand(script,table1,field3));
		return script;
	}
	
	private static Script createScript2() {
		Table table2 = createTable2();
		Script script = new Script();
		script.setName(table2.getName());
		script.addCommand(new CreateTableCommand(script, table2));
		
		TableField field3 = new TableField();
		field3.setName("GUILHERME");
		field3.setConstraints(Constraints.PK);
		field3.setType(GenericTypes.TIMESTAMP);
		script.addCommand(new AddFieldCommand(script,table2,field3));
		script.addCommand(new DropFieldCommand(script,table2,field3));
		
		TableField fieldID2Old = table2.getFieldByName("ID2");
		TableField fieldID2 = fieldID2Old.clone();
		fieldID2.setType(GenericTypes.TEXT);
		fieldID2.setName("ID2_MODIFICADO");
		fieldID2.setSize("100");
		script.addCommand(new ModifyFieldCommand(script,table2,fieldID2Old,fieldID2));
		return script;
	}
	
	
	public ProjectSettingsDialog(JFrame parent) {
		super(parent);
		
		lblProjectName = new JLabel("Nome do projeto");
		tfProjectName = new JTextField();
		
		panelGenerationTargetPath = new JPanel();
		panelGenerationTargetPath.setLayout(new BoxLayout(panelGenerationTargetPath,BoxLayout.Y_AXIS));
		labelGenerationTargetPath = new JLabel("Caminho para salvar os scripts gerados");
		
		panelSearchTargetPath = new JPanel();
		panelSearchTargetPath.setLayout(new BoxLayout(panelSearchTargetPath,BoxLayout.X_AXIS));
		tfGenerationTargetPath = new JTextField();
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
		
		btnCreateProject = new JButton("Criar");
		btnCreateProject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String projectName = tfProjectName.getText();
				String generationTargetPath = tfGenerationTargetPath.getText();
				
				project.setName(projectName);
				project.setScriptsGenerationBasePath(generationTargetPath);
				
				if(checkFieldsIntegrity()) {
					ProjectSettingsDialog.this.setResult(true, project);
					ProjectSettingsDialog.this.dispose();
				}
			}
			
		});
		
		Container pane = this.getContentPane();
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		pane.add(lblProjectName);
		pane.add(tfProjectName);
		pane.add(panelGenerationTargetPath);
		pane.add(btnCreateProject);
		this.pack();
	}
	
	public void insertNew() {
		editMode = false;
		this.project = new Project();
		//
		Script script1 = createScript1();
		Script script2 = createScript2();
		project.addScript(script1);
		project.addScript(script2);
		
		this.setVisible(true);
	}
	
	public void edit(Project project) {
		editMode = true;
		this.project = project.clone();
		this.setVisible(true);
	}
	
	public boolean isEditMode() {
		return editMode;
	}
	
	public boolean getResult() {
		return result;
	}
	
	public Project getResultData() {
		return project;
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
		
		return true;
	}
	
}
