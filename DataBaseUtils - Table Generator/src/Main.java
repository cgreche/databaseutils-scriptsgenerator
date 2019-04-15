

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.CellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
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
import ui.TableFieldTable;
import ui.TableListRenderer;

public class Main {
	
	static Script currentSelectedScript = null;
	
	static TableFieldTable table;
	
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
		table.setFields(Arrays.asList(new TableField[] {field1,field2,field3}));
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
		table.setFields(Arrays.asList(new TableField[] {field1,field2,field3}));
		return table;
	}
	
	private static Script createScript1() {
		Table table1 = createTable1();
		Script script = new Script();
		script.setObjectName(table1.getName());
		script.addCommand(new CreateTableCommand(table1));
		TableField field3 = new TableField();
		field3.setName("ERICLES");
		field3.setConstraints(Constraints.PK);
		field3.setType(GenericTypes.DATE);
		script.addCommand(new AlterTableCommand(table1).addColumn(field3));
		return script;
	}
	
	private static Script createScript2() {
		Table table2 = createTable2();
		Script script = new Script();
		script.setObjectName(table2.getName());
		script.addCommand(new CreateTableCommand(table2));
		
		TableField field3 = new TableField();
		field3.setName("GUILHERME");
		field3.setConstraints(Constraints.PK);
		field3.setType(GenericTypes.TIMESTAMP);
		script.addCommand(new AlterTableCommand(table2).addColumn(field3));
		script.addCommand(new AlterTableCommand(table2).dropColumn(field3));
		
		TableField fieldID2 = table2.getFieldByName("ID2");
		fieldID2.setType(GenericTypes.TEXT);
		fieldID2.setArgs("100");
		script.addCommand(new AlterTableCommand(table2).modifyColumn(fieldID2));
		return script;
	}
	
	
	
	private static void updateColumnTable() {
		if(currentSelectedScript != null) {
			List<Command> commands = currentSelectedScript.getCommands();
			if(commands == null) {
				table.setData(null);
				return;
			}
			

			Table resultingTable = new Table();
			for(Command command : commands) {
				if(command instanceof CreateTableCommand) {
					resultingTable = ((CreateTableCommand) command).getTable().clone();
					table.setData(resultingTable.getFields());
				}
				else if(command instanceof AlterTableCommand) {
					TableField field = ((AlterTableCommand) command).getField();
					AlterTableCommand.SubType subType = ((AlterTableCommand) command).getSubType();
					if(subType == AlterTableCommand.SubType.MODIFY_COLUMN) {
						table.modifyField(field);
					}
					else if(subType == AlterTableCommand.SubType.DROP_COLUMN) {
						table.dropField(((AlterTableCommand) command).getField());
					}
					else if(subType == AlterTableCommand.SubType.ADD_COLUMN) {
						table.addField(((AlterTableCommand) command).getField(),false);
					}
				}
			}
				
		}
		else {
			table.setData(null);
		}
	}
	
	public static void main(String [] args) {
		
		Table table1 = createTable1();
		Table table2 = createTable2();
		Script script1 = createScript1();
		Script script2 = createScript2();
		
		JFrame frame = new JFrame("Simple GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new TableFieldTable();
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            // this function successfully provides cell editing stop
            // on cell losts focus (but another cell doesn't gain focus)
            public void focusLost(FocusEvent e) {
                CellEditor cellEditor = table.getCellEditor();
                if (cellEditor != null) {
                    if (cellEditor.getCellEditorValue() != null)
                        cellEditor.stopCellEditing();
                    else
                        cellEditor.cancelCellEditing();
                }
                
                //currentSelectedTable.setFields(table.getData());
            }
        });
		
		DefaultListModel<Script> listModel = new DefaultListModel<>();
		listModel.addElement(script1);
		listModel.addElement(script2);
		JList<Script> list = new JList<>(listModel);
		list.setCellRenderer(new TableListRenderer());
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(!arg0.getValueIsAdjusting()) {
					currentSelectedScript = list.getSelectedValue();
					updateColumnTable();
				}
			}
		});
		
		JTextField textFieldBasePath = new JTextField();
		JTextField textFieldTableName = new JTextField();
		JPanel panel = new JPanel();
		JButton buttonAddTable = new JButton("Add table");
		buttonAddTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Table table = new Table();
				table.setName(textFieldTableName.getName());
				//listModel.addElement(table);
			}
		});
		JButton buttonAddColumn = new JButton("Add");
		JButton buttonGenerate = new JButton("Generate");
		buttonAddColumn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.addEmptyRow();
			}
		});
		
		buttonGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String basePath = textFieldBasePath.getText();
				
				List<Script> scriptList = new ArrayList<Script>();
				int size = list.getModel().getSize(); // 4
				for (int i = 0; i < size; i++) {
					Script script = list.getModel().getElementAt(i);
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
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(textFieldBasePath);
		panel.add(textFieldTableName);
		panel.add(buttonAddTable);
		panel.add(list);
		panel.add(buttonAddColumn);
		panel.add(buttonGenerate);
		panel.add(new JScrollPane(table));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
}
