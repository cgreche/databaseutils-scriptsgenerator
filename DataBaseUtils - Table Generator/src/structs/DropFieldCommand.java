package structs;

//03/05/2019
public class DropFieldCommand extends Command {
	private Table refTable;
	private TableField field;
	
	public DropFieldCommand(Script parentScript, Table refTable, TableField field) {
		super(parentScript);
		this.refTable = refTable;
		this.field = field;
	}
	
	public Table getRefTable() {
		return refTable;
	}

	public void setRefTable(Table refTable) {
		this.refTable = refTable;
	}

	public TableField getField() {
		return field;
	}

	public void setField(TableField field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return "Drop Field (" + field.getName() + ")";
	}
	
	@Override
	public DropFieldCommand clone() {
		return (DropFieldCommand)super.clone();
	}
}
