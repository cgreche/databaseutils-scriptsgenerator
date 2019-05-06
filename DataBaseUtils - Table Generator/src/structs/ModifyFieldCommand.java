package structs;

//03/05/2019
public class ModifyFieldCommand extends Command {
	private Table refTable;
	private TableField oldField;
	private TableField newField;
	
	public ModifyFieldCommand(Script parentScript, Table refTable, TableField currentField, TableField newField) {
		super(parentScript);
		this.refTable = refTable;
		this.oldField = currentField;
		this.newField = newField;
	}
	
	public Table getRefTable() {
		return refTable;
	}

	public void setRefTable(Table refTable) {
		this.refTable = refTable;
	}

	public TableField getOldField() {
		return oldField;
	}

	public void setOldField(TableField oldField) {
		this.oldField = oldField;
	}

	public TableField getNewField() {
		return newField;
	}

	public void setNewField(TableField newField) {
		this.newField = newField;
	}

	@Override
	public String toString() {
		return "Modify Field (" + oldField.getName() + " --> " + newField.getName() + ")";
	}
	
	@Override
	public ModifyFieldCommand clone() {
		return (ModifyFieldCommand)super.clone();
	}
}
