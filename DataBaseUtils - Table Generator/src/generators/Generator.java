package generators;

import java.io.FileWriter;
import java.util.List;

import structs.Script;
import structs.Table;

//10/04/2019
public abstract class Generator {
	public void generate(Script script) {
		List<Table> tables = script.getTables();
		
		for(Table table : tables) {
			String tableName = table.getName();
			String outputFileNameTab = script.getBasePath() + "/" + tableName + ".tab";
			
			String resultContent = generateCreateTable(table);
			try {
				FileWriter fileWriter = new FileWriter(outputFileNameTab);
				fileWriter.write(resultContent);
				fileWriter.close();
			} catch(Exception e) {
				
			}
			
			postCreateTableGeneration(table);
		}
	}
	
	public void preCreateTableGeneration() {
		
	}
	
	public String generateCreateTable(Table table) {
		return null;
	}
	
	public void postCreateTableGeneration(Table table) {
	}
}
