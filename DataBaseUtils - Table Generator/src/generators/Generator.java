package generators;

import java.io.FileWriter;
import java.util.List;

import structs.AlterTableCommand;
import structs.Command;
import structs.CreateTableCommand;
import structs.Script;
import structs.Table;

//10/04/2019
public abstract class Generator {
	public void generate(Script script) {
		String objectName = script.getObjectName();
		List<Command> commands = script.getCommands();
		if(commands == null)
			return;
		
		String outputFileNameTab = script.getBasePath() + "/" + ((CreateTableCommand)commands.get(0)).getTable().getName() + ".tab";
		FileWriter fileWriter;
		try {
		 fileWriter = new FileWriter(outputFileNameTab);
		for(Command command : commands) {
			String resultContent = "";
			if(command instanceof CreateTableCommand) {
				resultContent = generateCreateTable((CreateTableCommand)command);
				postCreateTableGeneration((CreateTableCommand)command);
			}
			else if(command instanceof AlterTableCommand) {
				resultContent = generateAlterTable((AlterTableCommand)command);
			}
			fileWriter.write(resultContent);
		}
		
		fileWriter.close();
		} catch(Exception e) {
			
		}
	}
	
	public void preCreateTableGeneration() {
		
	}
	
	public String generateCreateTable(CreateTableCommand command) {
		Table table = command.getTable();
		return null;
	}
	
	public void postCreateTableGeneration(CreateTableCommand command) {
	}
	
	public String generateAlterTable(AlterTableCommand command) {
		return null;
	}
}
