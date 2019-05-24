package structs;

/**
 * 
 * Base class for commands.
 * @author cesar.reche@techne.com.br
 * @since 10/04/2019
 *
 */
public class Command implements Cloneable {
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
	
	@Override
	public Command clone() {
		try {
			return (Command) super.clone();
		} catch(Exception e) {
			return null;
		}
	}
}
