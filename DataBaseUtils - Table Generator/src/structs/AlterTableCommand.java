package structs;

//15/04/2019

public class AlterTableCommand extends Command {

	public enum SubType {
		ADD_FIELD,
		MODIFY_FIELD,
		DROP_FIELD
	}

	private SubType subType;
	
	private Table refTable;
	private TableField field;
	
	public AlterTableCommand(Script script, Table refTable) {
		super(script);
		this.refTable = refTable;
	}
	
	public AlterTableCommand addColumn(TableField field) {
		this.field = field;
		subType = SubType.ADD_FIELD;
		return this;
	}

	public AlterTableCommand modifyColumn(TableField field) {
		this.field = field;
		subType = SubType.MODIFY_FIELD;
		return this;
	}

	public AlterTableCommand dropColumn(TableField field) {
		this.field = field;
		subType = SubType.DROP_FIELD;
		return this;
	}
	
	public SubType getSubType() {
		return subType;
	}
	
	public Table getRefTable() {
		return refTable;
	}
	
	public TableField getField() {
		return field;
	}
	
	@Override
	public String toString() {
		String cmdString = "Alter Table (";
		switch(subType) {
		case ADD_FIELD:
			cmdString += "Add field";
			break;
		case MODIFY_FIELD:
			cmdString += "Modify field";
			break;
		case DROP_FIELD:
			cmdString += "Drop field";
			break;
		}
		cmdString += ")";
		return cmdString;
	}
	
	@Override
	public AlterTableCommand clone() {
		AlterTableCommand ret = (AlterTableCommand)super.clone();
		ret.field = field.clone();
		return ret;
	}
	
}
