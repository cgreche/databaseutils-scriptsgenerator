package structs;

public class Command {
	public enum Type {
		CREATE_TABLE,
		ALTER_TABLE
	};
	
	public enum SubType {
		ADD_FK,
		ADD_COLUMN,
	}
	
	private Type type;
	Table table;
	TableField field;
	
}
