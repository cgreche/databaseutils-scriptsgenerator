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
			else if(command instanceof AlterTableCommand) {
				TableField field = ((AlterTableCommand) command).getField();
				AlterTableCommand.SubType subType = ((AlterTableCommand) command).getSubType();
				int fieldIndex = resultingTable.getFieldIndex(field);
				if(fieldIndex != -1) {
					if(subType == AlterTableCommand.SubType.MODIFY_COLUMN) {
						resultingTable.getFields().set(fieldIndex, field);
					}
					else if(subType == AlterTableCommand.SubType.DROP_COLUMN) {
						resultingTable.getFields().remove(fieldIndex);
					}
				}
				else {
					if(subType == AlterTableCommand.SubType.ADD_COLUMN) {
						resultingTable.getFields().add(((AlterTableCommand) command).getField());
					}
				}
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
