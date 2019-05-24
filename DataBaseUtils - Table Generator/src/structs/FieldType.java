package structs;

/**
 * 
 * Class that represents the type of a table field.
 * @author cesar.reche@techne.com.br
 * @since 10/04/2019
 *
 */

public class FieldType {
	
	private String id;
	private String name;
	private boolean acceptArgs;
	private boolean generic;
	
	public FieldType(String id, String name, boolean acceptArgs, boolean generic) {
		this.id = id;
		this.name = name;
		this.acceptArgs = acceptArgs;
		this.generic = generic;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.name = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean acceptArgs() {
		return acceptArgs;
	}
	public void setAcceptArgs(boolean acceptArgs) {
		this.acceptArgs = acceptArgs;
	}
	
	public boolean isGeneric() {
		return generic;
	}
	public void setGeneric(boolean generic) {
		this.generic = generic;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
