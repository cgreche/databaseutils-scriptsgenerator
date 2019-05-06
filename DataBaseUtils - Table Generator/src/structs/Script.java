package structs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//10/04/2019
public class Script {
	private String objectName;
	private Date creationDate;
	private Date modifyDate;
	private List<Command> commands;
	private String basePath;
	
	private String headerMessage;
	
	public void addCommand(Command command) {
		if(commands == null)
			commands = new ArrayList<>();
		commands.add(command);
	}
	
	public Table getResultTable() {
		if(commands == null)
			return null;
		Table resultingTable = new Table();
		for(Command command : commands) {
			if(command instanceof CreateTableCommand) {
				resultingTable = ((CreateTableCommand) command).getTable().clone();
			}
			else if(command instanceof AddFieldCommand) {
				AddFieldCommand afc = (AddFieldCommand)command;
				resultingTable.getFields().add(afc.getField());
			}
			else if(command instanceof ModifyFieldCommand) {
				ModifyFieldCommand mfc = (ModifyFieldCommand)command;
				int fieldIndex = resultingTable.getFieldIndex(mfc.getOldField());
				if(fieldIndex != -1)
					resultingTable.getFields().set(fieldIndex, mfc.getNewField());
			}
			else if(command instanceof DropFieldCommand) {
				DropFieldCommand dfc = (DropFieldCommand)command;
				int fieldIndex = resultingTable.getFieldIndex(dfc.getField());
				if(fieldIndex != -1)
					resultingTable.getFields().remove(fieldIndex);
			}
		}
		return resultingTable;
	}
	
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public String getHeaderMessage() {
		return headerMessage;
	}

	public void setHeaderMessage(String headerMessage) {
		this.headerMessage = headerMessage;
	}
	
	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
}
