import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import generators.Generator;
import generators.MySQLGenerator;
import generators.OracleGenerator;
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
}
