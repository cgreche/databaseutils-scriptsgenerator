package structs;

//10/04/2019
public class Command {
	public enum Type {
		CREATE_TABLE,
		ALTER_TABLE
	};
	
	private Type type;
}
