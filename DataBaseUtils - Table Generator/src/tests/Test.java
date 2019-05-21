package tests;
import java.util.ArrayList;
import java.util.Arrays;

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

public class Test {
	
	void test() {
		/*
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
	    
		script.setTables(Arrays.asList(new Table[] {table}));
		
		Generator generator = new OracleGenerator();
		generator.generate(script);
	
		script.setBasePath(basePath + "/MySQL");
		generator = new MySQLGenerator();
		generator.generate(script);
		*/
	}
	
	public static Table createTable1() {
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
	
	public static Table createTable2() {
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
	
	public static Script createScript1() {
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
	
	public static Script createScript2() {
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
		fieldID2.setSize("100");
		script.addCommand(new ModifyFieldCommand(script,table2,fieldID2Old,fieldID2));
		return script;
	}
	
	public static Project newProject() {
		Script script1 = createScript1();
		Script script2 = createScript2();
		Project project = new Project();
		project.addScript(script1);
		project.addScript(script2);
		return project;
	}
}
