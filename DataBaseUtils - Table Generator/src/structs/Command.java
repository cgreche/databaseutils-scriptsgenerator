package structs;

//10/04/2019
public class Command {
	public enum Type {
		CREATE_TABLE,
		ALTER_TABLE
	};
	
	protected Type type;
	protected Script script;
	
	public Command(Script script) {
		this.script = script;
	}
	
	public Script getScript() {
		return script;
	}
}
