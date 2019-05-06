package structs;

//15/04/2019

public class AddFieldCommand extends Command {

	private Table refTable;
	private TableField field;
	
	public AddFieldCommand(Script script, Table refTable, TableField field) {
		super(script);
		this.refTable = refTable;
		this.field = field;
	}
	
	public AddFieldCommand addColumn(TableField field) {
		this.field = field;
		return this;
	}

	public Table getRefTable() {
		return refTable;
	}
	
	public TableField getField() {
		return field;
	}
	
	@Override
	public String toString() {
		return "Add field (" + field.getName() + ")";
	}
	
	@Override
	public AddFieldCommand clone() {
		return (AddFieldCommand)super.clone();
	}
}