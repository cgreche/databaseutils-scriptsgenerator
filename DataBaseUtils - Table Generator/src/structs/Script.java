package structs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//10/04/2019
public class Script {
	private String name;
	private Date creationDate;
	private Date lastModifiedDate;
	private List<Command> commands;
	private String basePath;
	
	private String headerMessage;
	
	public Script() {
		this.lastModifiedDate = this.creationDate = new Date();
		commands = new ArrayList<>();
	}
	
	public void addCommand(Command command) {
		commands.add(command);
	}
	
	public Table getResultingTable() {
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
	
	public Table getResultingTableUntil(Command commandToStop) {
		Table resultingTable = new Table();
		for(Command command : commands) {
			if(command == commandToStop)
				return resultingTable;
			
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
	
	public boolean validate() {
		Table resultingTable = null;
		for(Command command : commands) {
			if(command instanceof CreateTableCommand) {
				resultingTable = ((CreateTableCommand) command).getTable().clone();
			}
			else if(command instanceof AddFieldCommand) {
				AddFieldCommand afc = (AddFieldCommand)command;
				if(resultingTable.getFieldByName(afc.getField().getName()) != null) {
					//Field already exists
					return false;
				}
				resultingTable.getFields().add(afc.getField());
			}
			else if(command instanceof ModifyFieldCommand) {
				ModifyFieldCommand mfc = (ModifyFieldCommand)command;
				int fieldIndex = resultingTable.getFieldIndex(mfc.getOldField());
				if(fieldIndex == -1) {
					//Field doesn't exist
					return false;
				}
				if(fieldIndex != -1)
					resultingTable.getFields().set(fieldIndex, mfc.getNewField());
			}
			else if(command instanceof DropFieldCommand) {
				DropFieldCommand dfc = (DropFieldCommand)command;
				int fieldIndex = resultingTable.getFieldIndex(dfc.getField());
				if(fieldIndex == -1) {
					//Field doesn't exist
					return false;
				}
				if(fieldIndex != -1)
					resultingTable.getFields().remove(fieldIndex);
			}
		}
		return true;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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
	
	public TableField getField(String fieldName) {
		return getResultingTable().getFieldByName(fieldName);
	}
	
	public void notifyScriptChanged() {
		lastModifiedDate = new Date();
	}
	
}
