package structs;

public class CreateTableCommand extends Command implements Cloneable {
	private Table table;
	
	public CreateTableCommand(Table table) {
		this.table = table;
	}
	
	public Table getTable() {
		return table;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}

}
