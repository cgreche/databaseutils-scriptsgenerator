package generators;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import structs.AddFieldCommand;
import structs.Command;
import structs.CreateTableCommand;
import structs.Script;
import structs.Table;

//10/04/2019
public abstract class Generator {
	public void generate(Script script) {
		String objectName = script.getName();
		List<Command> commands = script.getCommands();
		if(commands == null)
			return;
		
		try {
			Files.createDirectories(Paths.get(script.getBasePath()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String outputFileNameTab = script.getBasePath() + "/" + objectName + ".tab";
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(outputFileNameTab);
			for(Command command : commands) {
				String resultContent = "";
				if(command instanceof CreateTableCommand) {
					resultContent = generateCreateTable((CreateTableCommand)command);
					postCreateTableGeneration((CreateTableCommand)command);
				}
				else if(command instanceof AddFieldCommand) {
					resultContent = generateAlterTable((AddFieldCommand)command);
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
	
	public String generateAlterTable(Command command) {
		return null;
	}
}
