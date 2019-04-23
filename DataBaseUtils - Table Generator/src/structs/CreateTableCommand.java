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

}
