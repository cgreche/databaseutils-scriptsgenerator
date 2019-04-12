

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
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
import structs.Constraints;
import structs.GenericTypes;
import structs.Script;
import structs.Table;
import structs.TableField;
import ui.TableFieldTable;

public class Main {
	
	static Table currentSelectedTable = null;
	
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
	
	
	
	private static void updateColumnTable() {
		if(currentSelectedTable != null) {
			table.setData(currentSelectedTable.getFields());
		}
		else {
			table.setData(null);
		}
		
	}
	
	public static void main(String [] args) {
		
		Table table1 = createTable1();
		Table table2 = createTable2();
		
		JFrame frame = new JFrame("Simple GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new TableFieldTable();
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		
		DefaultListModel<Table> listModel = new DefaultListModel<>();
		listModel.addElement(table1);
		listModel.addElement(table2);
		JList<Table> list = new JList<>(listModel);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(!arg0.getValueIsAdjusting()) {
					currentSelectedTable = list.getSelectedValue();
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
				listModel.addElement(table);
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
				List<TableField> data = table.getData();

				Table table = new Table();
				table.setName("TEST_TABLE");
				table.setFields(data);

				String basePath = textFieldBasePath.getText();
				Script script = new Script();
				script.setBasePath(basePath + "/OracleDB");
				script.setHeaderMessage("test");
				List<Table> tableList = new ArrayList<Table>();
			    int size = list.getModel().getSize(); // 4
			    for (int i = 0; i < size; i++) {
			      Table item = list.getModel().getElementAt(i);
			      tableList.add(item);
			    }
			    
				script.setTables(tableList);
				
				Generator generator = new OracleGenerator();
				generator.generate(script);

				script.setBasePath(basePath + "/MySQL");
				generator = new MySQLGenerator();
				generator.generate(script);
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
