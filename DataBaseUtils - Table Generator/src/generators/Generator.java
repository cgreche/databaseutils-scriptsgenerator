package generators;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import structs.AddFieldCommand;
import structs.Command;
import structs.CreateTableCommand;
import structs.DropFieldCommand;
import structs.ModifyFieldCommand;
import structs.Project;
import structs.Script;

/**
 * 
 * Base class for Script Generator implementations.
 * @author cesar.reche@techne.com.br
 * @since 10/04/2019
 *
 */
public abstract class Generator {
	
	protected Project project;
	protected String defaultHeaderMessage;
	protected String basePath;
	
	public void setDefaultHeaderMessage(String defaultHeaderMessage) {
		this.defaultHeaderMessage = defaultHeaderMessage;
	}
	
	public String getDefaultHeaderMessage() {
		return defaultHeaderMessage;
	}
	
	public Generator(Project project) {
		this.project = project;
		setDefaultHeaderMessage(project.getDefaultScriptsHeaderMessage());
	}
	
	public void generate(String basePath) {
		this.basePath = basePath;
		
		List<Script> scriptList = project.getScripts();
		if(scriptList.isEmpty())
			return;
		
		for(Script script : scriptList) {
			generateScript(script);
		}
	}
	
	private void generateScript(Script script) {
 		List<Command> commands = script.getCommands();
		if(commands == null || commands.isEmpty())
			return;
		
		String scriptBasePath = basePath;
		if(script.getBasePath() != null)
			scriptBasePath += "/" + script.getBasePath();
		
		try {
			Files.createDirectories(Paths.get(scriptBasePath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String scriptName = script.getName();
		String projectName = project.getName();

		String headerMessage = script.getHeaderMessage();
		if(headerMessage == null)
			headerMessage = defaultHeaderMessage;
		if(headerMessage != null) {
			headerMessage = headerMessage.replace("%projectName%", projectName);
			headerMessage = headerMessage.replace("%scriptName%", scriptName);
			headerMessage = headerMessage.replace("%applicationName%", application.Properties.APPLICATION_NAME);
			headerMessage = headerMessage.replace("%applicationVersion%", application.Properties.APPLICATION_VERSION);
		}
		
		String outputFileNameTab = scriptBasePath + "/" + scriptName + ".tab";
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(outputFileNameTab);
			if(headerMessage != null) {
				fileWriter.write(headerMessage);
				fileWriter.append("\n");
			}
			for(Command command : commands) {
				String resultContent = "";
				if(command instanceof CreateTableCommand) {
					preCreateTableCommand((CreateTableCommand)command);
					resultContent = generateCreateTableCommand((CreateTableCommand)command);
					postCreateTableCommand((CreateTableCommand)command);
				}
				else if(command instanceof AddFieldCommand) {
					preAddFieldCommand((AddFieldCommand)command);
					resultContent = generateAddFieldCommand((AddFieldCommand)command);
					postAddFieldCommand((AddFieldCommand)command);
				}
				else if(command instanceof ModifyFieldCommand) {
					preModifyFieldCommand((ModifyFieldCommand)command);
					resultContent = generateModifyFieldCommand((ModifyFieldCommand)command);
					postModifyFieldCommand((ModifyFieldCommand)command);
				}
				else if(command instanceof DropFieldCommand) {
					preDropFieldCommand((DropFieldCommand)command);
					resultContent = generateDropFieldCommand((DropFieldCommand)command);
					postDropFieldCommand((DropFieldCommand)command);
				}
				fileWriter.write(resultContent);
			}
			
			fileWriter.close();
		} catch(Exception e) {
			
		}
	}
	
	protected void preCreateTableCommand(CreateTableCommand command) { }
	protected abstract String generateCreateTableCommand(CreateTableCommand command);
	protected void postCreateTableCommand(CreateTableCommand command) { }
	
	protected void preAddFieldCommand(AddFieldCommand command) { }
	protected abstract String generateAddFieldCommand(AddFieldCommand command);
	protected void postAddFieldCommand(AddFieldCommand command) { }
	
	protected void preModifyFieldCommand(ModifyFieldCommand command) { }
	protected abstract String generateModifyFieldCommand(ModifyFieldCommand command);
	protected void postModifyFieldCommand(ModifyFieldCommand command) { }
	
	protected void preDropFieldCommand(DropFieldCommand command) { }
	protected abstract String generateDropFieldCommand(DropFieldCommand command);
	protected void postDropFieldCommand(DropFieldCommand command) { }
	
}
