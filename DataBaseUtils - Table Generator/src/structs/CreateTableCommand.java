package structs;

/**
 * 
 * @author cesar.reche@techne.com.br
 * @since 15/04/2019
 *
 */

public class CreateTableCommand extends Command {
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
		return "Create Table (" + table.getName() + ")";
	}
	
}
