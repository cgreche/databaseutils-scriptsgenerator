package structs;

//10/04/2019
public class Command {
	public enum Type {
		CREATE_TABLE,
		ALTER_TABLE
	};
	
	public enum SubType {
		ADD_COLUMN,
		MODIFY_COLUMN,
		DROP_COLUMN
	}
	
	private Type type;
	private Table table;
	private TableField field;
}
