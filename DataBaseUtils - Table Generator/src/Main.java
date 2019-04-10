

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import generators.Generator;
import generators.OracleGenerator;
import structs.Constraints;
import structs.FieldType;
import structs.GenericTypes;
import structs.Script;
import structs.Table;
import structs.TableField;

public class Main {
	
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
	
	public static void main(String [] args) {
		/*
		Table table1 = createTable1();
		Table table2 = createTable2();
		
		Script script = new Script();
		script.setHeaderMessage("test");
		script.setTables(Arrays.asList(new Table[] {table1, table2}));
		
		Generator generator = new OracleGenerator();
		generator.generate(script);
		
		*/
		JFrame frame = new JFrame("Simple GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		String[] columnNames = {"Nome","Tipo", "PK", "FK", "Not null", "Tabela referenciada"};
		DefaultTableModel model = new DefaultTableModel(columnNames,0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if(columnIndex == 1)
					return FieldType.class;
				return super.getColumnClass(columnIndex);
			}
		};
		
		model.addRow(new Object[]{"GUID", GenericTypes.TEXT, true, false, false, null});
		model.addRow(new Object[]{"TENANTID", GenericTypes.NUMERIC, false, false, false, null});
		
		final JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		model.addRow(new Object[]{"TEST", GenericTypes.TEXT, false, false, false, null});
		
		JPanel panel = new JPanel();
		
		JButton buttonAddColumn = new JButton("Add");
		JButton buttonGenerate = new JButton("Generate");
		buttonAddColumn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addRow(new Object[] {});
			}
		});
		
		buttonGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<TableField> fields = new ArrayList<>();
		        Vector data = model.getDataVector();
		        for(int i = 0; i < data.size(); ++i) {
		        	Vector o = (Vector)data.get(i);
		        	String name = (String)o.get(0);
		        	FieldType type = (FieldType)o.get(1);
		        	boolean pk = (boolean)o.get(2);
		        	boolean fk = (boolean)o.get(3);
		        	boolean notNull = (boolean)o.get(4);
		        	String referencedTable = (String)o.get(5);
		        	
		        	int constraints = 0;
		        	if(pk) constraints |= Constraints.PK;
		        	if(fk) constraints |= Constraints.FK;
		        	if(notNull) constraints |= Constraints.NOT_NULL;
		        	
		        	TableField field = new TableField();
		        	field.setName(name);
		        	field.setType(type);
		        	field.setConstraints(constraints);
		        	field.setReferencedTable(referencedTable);
		        	fields.add(field);
		        }
		        
		        Table table = new Table();
		        table.setName("TEST_TABLE");
		        table.setFields(fields);
		        
				Script script = new Script();
				script.setHeaderMessage("test");
				script.setTables(Arrays.asList(new Table[] {table}));
				
				Generator generator = new OracleGenerator();
				generator.generate(script);
			}
		});
		
		panel.add(buttonAddColumn);
		panel.add(buttonGenerate);
		panel.add(new JScrollPane(table));
		frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        
		
	}
}
