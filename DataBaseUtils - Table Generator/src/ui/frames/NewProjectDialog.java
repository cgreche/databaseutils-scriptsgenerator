package ui.frames;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import structs.AlterTableCommand;
import structs.Constraints;
import structs.CreateTableCommand;
import structs.GenericTypes;
import structs.Project;
import structs.Script;
import structs.Table;
import structs.TableField;

@SuppressWarnings("serial")
public class NewProjectDialog extends JDialog {
	
	JLabel lblProjectName;
	JTextField tfProjectName;
	JButton btnCreateProject;
	
	Project project;
	
	private static Table createTable1() {
		TableField field1 = new TableField();
		field1.setName("GUID");
		field1.setConstraints(Constraints.PK);
		field1.setArgs("255");
		field1.setType(GenericTypes.TEXT);
		
		TableField field2 = new TableField();
		field2.setName("IDEVENTO");
		field2.setArgs("36");
		field2.setType(GenericTypes.TEXT);
		field2.setConstraints(Constraints.NOT_NULL|Constraints.FK);
		field2.setReferencedTable("WOOOOW");
		
		TableField field3 = new TableField();
		field3.setName("TENANTID");
		field3.setArgs("10,0");
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
		field3.setArgs("100,2");
		
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
		script.setObjectName(table1.getName());
		script.addCommand(new CreateTableCommand(script, table1));
		TableField field3 = new TableField();
		field3.setName("ERICLES");
		field3.setConstraints(Constraints.PK);
		field3.setType(GenericTypes.DATE);
		script.addCommand(new AlterTableCommand(script,table1).addColumn(field3));
		return script;
	}
	
	private static Script createScript2() {
		Table table2 = createTable2();
		Script script = new Script();
		script.setObjectName(table2.getName());
		script.addCommand(new CreateTableCommand(script, table2));
		
		TableField field3 = new TableField();
		field3.setName("GUILHERME");
		field3.setConstraints(Constraints.PK);
		field3.setType(GenericTypes.TIMESTAMP);
		script.addCommand(new AlterTableCommand(script, table2).addColumn(field3));
		script.addCommand(new AlterTableCommand(script,table2).dropColumn(field3));
		
		TableField fieldID2 = table2.getFieldByName("ID2");
		fieldID2.setType(GenericTypes.TEXT);
		fieldID2.setArgs("100");
		script.addCommand(new AlterTableCommand(script,table2).modifyColumn(fieldID2));
		return script;
	}
	
	
	public NewProjectDialog(JFrame parent) {
		super(parent, true);
		
		lblProjectName = new JLabel("Nome do projeto");
		tfProjectName = new JTextField();
		
		btnCreateProject = new JButton("Criar");
		btnCreateProject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				project = new Project();
				project.setName(tfProjectName.getText());
				Script script1 = createScript1();
				Script script2 = createScript2();
				
				project.addScript(script1);
				project.addScript(script2);

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
		super.setVisible(visible);
	}
	
	public Project getResult() {
		return project;
	}
}
