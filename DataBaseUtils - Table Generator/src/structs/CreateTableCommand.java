package structs;

//15/04/2019

public class CreateTableCommand extends Command implements Cloneable {
	private Table table;
	
	public CreateTableCommand(Script script, Table table) {
		super(script);
		this.table = table;
	}
	
	public Table getTable() {
		return table;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	
	@Override
	public String toString() {
		return "Create Table";
	}
	
	@Override
	public CreateTableCommand clone() {
		CreateTableCommand command = (CreateTableCommand)super.clone();
		if(table != null)
			command.table = table.clone();
		return command;
	}
	
}
