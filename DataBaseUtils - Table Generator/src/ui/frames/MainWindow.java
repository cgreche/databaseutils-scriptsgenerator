package ui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.CellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import generators.Generator;
import generators.MySQLGenerator;
import generators.OracleGenerator;
import structs.AlterTableCommand;
import structs.Command;
import structs.Constraints;
import structs.CreateTableCommand;
import structs.GenericTypes;
import structs.Script;
import structs.Table;
import structs.TableField;
import ui.ScriptListRenderer;
import ui.frames.dropcolumn.PanelDropColumn;
import ui.resultingtable.TableFieldTable;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	public JMenuBar menuBar;
	
	public JMenu menuNew;
		public JMenuItem itemNewProject;
	public JMenuItem itemAuthor;
	
	public NewProjectDialog newProjectDialog;
	
	static CreateTableCommandDialog createTableCommandDialog;
	static AddFieldCommandDialog addFieldCommandDialog;
	static EditFieldCommandDialog editFieldCommandDialog;
	static RemoveFieldCommandDialog removeFieldCommandDialog;

	JList<Command> listCommands;
	PanelDropColumn panelRemoveField;
	
	JPanel panelProject;
	JPanel panelScripts;
		JLabel lblScriptName;
		JTextField tfScriptName;
		JButton btnNewScript;
		
	static JPanel panelCommands;
	static JPanel panelHeader;
	static JPanel panelData;
	static JPanel panelResult;
		JCheckBox cbShowRemovedFields;
	static JPanel panelFooter;
	
	JLabel lblProjectTitle = new JLabel("PROJETO DE TESTE 1");
	static TableFieldTable tableResultingTable;
	
	List<Script> scriptList = new ArrayList<>();
	Script currentSelectedScript = null;


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
	
	private void updateCommandList() {
		if(currentSelectedScript == null)
			return;
		List<Command> commands = currentSelectedScript.getCommands();
		((DefaultListModel<Command>) listCommands.getModel()).clear();
		for(Command command : commands) {
			((DefaultListModel<Command>) listCommands.getModel()).addElement(command);
		}
	}
	
	private void updateColumnTable() {
		tableResultingTable.setData(currentSelectedScript);
	}
	
	public MainWindow() {
		super("eSocial Techne Database Utils - Scripts generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Script script1 = createScript1();
		Script script2 = createScript2();
		
		menuBar = new JMenuBar();
		menuNew = new JMenu();
			itemNewProject = new JMenuItem();
			
		itemAuthor = new JMenuItem();
		
		newProjectDialog = new NewProjectDialog(this);
		
		menuNew.setText("Novo");
		menuNew.add(itemNewProject);
			itemNewProject.setText("Novo projeto");
			itemNewProject.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					System.out.println("cliqued");
					newProjectDialog.setVisible(true);
					boolean result = newProjectDialog.getResult();
					if(result)
						System.out.println(newProjectDialog.getProjectName());
				}
			});
		itemAuthor.setText("Author");
		
		menuBar.add(menuNew);
		menuBar.add(itemAuthor);
		
		
		
		createTableCommandDialog = new CreateTableCommandDialog(this);
		addFieldCommandDialog = new AddFieldCommandDialog(this);
		editFieldCommandDialog = new EditFieldCommandDialog(this);
		removeFieldCommandDialog = new RemoveFieldCommandDialog(this);
		
		tableResultingTable = new TableFieldTable();
		tableResultingTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableResultingTable.setFillsViewportHeight(true);
		tableResultingTable.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}
			
			// this function successfully provides cell editing stop
			// on cell losts focus (but another cell doesn't gain focus)
			public void focusLost(FocusEvent e) {
				CellEditor cellEditor = tableResultingTable.getCellEditor();
				if (cellEditor != null) {
					if (cellEditor.getCellEditorValue() != null)
						cellEditor.stopCellEditing();
					else
						cellEditor.cancelCellEditing();
				}
				
				//currentSelectedTable.setFields(table.getData());
			}
		});
		
		
		JTextField textFieldBasePath = new JTextField();
		JTextField textFieldTableName = new JTextField();
		
		DefaultListModel<Script> scriptsListModel = new DefaultListModel<>();
		
		lblScriptName = new JLabel("Nome ");
		tfScriptName = new JTextField();
		btnNewScript = new JButton("Novo script");
		btnNewScript.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Script script = new Script();
				script.setObjectName(tfScriptName.getName());
				scriptList.add(script);
				
				scriptsListModel.addElement(script);
			}
		});
		
		
		scriptsListModel.addElement(script1);
		scriptsListModel.addElement(script2);
		JList<Script> listScripts = new JList<>(scriptsListModel);
		listScripts.setSize(new Dimension(500, 70));
		listScripts.setCellRenderer(new ScriptListRenderer());
		listScripts.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(!arg0.getValueIsAdjusting()) {
					currentSelectedScript = listScripts.getSelectedValue();
					updateCommandList();
					updateColumnTable();
				}
			}
		});
		
		panelScripts = new JPanel();
		panelScripts.setLayout(new BoxLayout(panelScripts, BoxLayout.Y_AXIS));
		panelScripts.add(btnNewScript);
		panelScripts.add(listScripts);

		JButton buttonNewCommand = new JButton("Novo comando");
		DefaultListModel<Command> listModelCommands = new DefaultListModel<>();
		listCommands = new JList<>(listModelCommands);
		listCommands.setSize(new Dimension(500, 70));
		listCommands.setCellRenderer(new DefaultListCellRenderer());
		listCommands.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(!arg0.getValueIsAdjusting()) {
					/*
					Command command = listCommands.getSelectedValue();
					if(command instanceof CreateTableCommand) {
						createTableWindow.open((CreateTableCommand)command);
					}
					else if(command instanceof AlterTableCommand) {
						AlterTableCommand c = (AlterTableCommand)command;
						AlterTableCommand.SubType subType = c.getSubType();
						if(subType == AlterTableCommand.SubType.MODIFY_COLUMN)
							panelEditField.update(currentSelectedScript);
						else if(subType == AlterTableCommand.SubType.DROP_COLUMN)
							panelRemoveField.update(currentSelectedScript);
					}
					*/
				}
			}
		});
		
		listCommands.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<Command> list = (JList<Command>)evt.getSource();
				if (evt.getClickCount() == 2) {
					// Double-click detected
					int index = list.locationToIndex(evt.getPoint());
					Command command = list.getModel().getElementAt(index);
					if(command instanceof CreateTableCommand) {
						createTableCommandDialog.edit((CreateTableCommand)command);
						CreateTableCommand resultCommand = createTableCommandDialog.getResult();
						updateColumnTable();
					}
					else if(command instanceof AlterTableCommand) {
						AlterTableCommand c = (AlterTableCommand)command;
						AlterTableCommand.SubType subType = c.getSubType();
						if(subType == AlterTableCommand.SubType.MODIFY_COLUMN)
							editFieldCommandDialog.open(c);
						else if(subType == AlterTableCommand.SubType.DROP_COLUMN)
							removeFieldCommandDialog.open(c);
					}
				}
			}
		});

		panelCommands = new JPanel();
		panelCommands.setLayout(new BoxLayout(panelCommands, BoxLayout.Y_AXIS));
		panelCommands.add(buttonNewCommand);
		panelCommands.add(listCommands);
		
		//
		JButton buttonGenerate = new JButton("Generate");
		buttonGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String basePath = textFieldBasePath.getText();
				
				List<Script> scriptList = new ArrayList<Script>();
				int size = listScripts.getModel().getSize(); // 4
				for (int i = 0; i < size; i++) {
					Script script = listScripts.getModel().getElementAt(i);
					scriptList.add(script);
				}
				
				for(Script script : scriptList) {
					script.setBasePath(basePath + "/OracleDB");
					script.setHeaderMessage("test");
					Generator generator = new OracleGenerator();
					generator.generate(script);
					
					script.setBasePath(basePath + "/MySQL");
					generator = new MySQLGenerator();
					generator.generate(script);
				}
			}
		});
		
		panelHeader = new JPanel();
		panelHeader.add(lblProjectTitle);
		
		panelData = new JPanel();
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.X_AXIS));
		panelData.add(panelScripts);
		panelData.add(panelCommands);
		
		cbShowRemovedFields = new JCheckBox("Show removed");
		cbShowRemovedFields.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JCheckBox cbShowRemoved = (JCheckBox) e.getSource();
		        tableResultingTable.showRemoved(cbShowRemoved.isSelected());
		    }
		});
		
		panelResult = new JPanel();
		panelResult.add(cbShowRemovedFields);
		panelResult.add(new JScrollPane(tableResultingTable));
		
		panelFooter = new JPanel();
		panelFooter.add(buttonGenerate);
		
		panelProject = new JPanel();
		panelProject.setLayout(new BoxLayout(panelProject, BoxLayout.Y_AXIS));
		panelProject.add(panelHeader);
		panelProject.add(panelData);
		panelProject.add(panelResult);
		panelProject.add(panelFooter);
		
		this.add(panelProject);
		this.pack();
		
		setJMenuBar(menuBar);
	}
}
