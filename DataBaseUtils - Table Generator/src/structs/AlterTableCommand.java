package structs;

//15/04/2019

public class AlterTableCommand extends Command {

	public enum SubType {
		ADD_COLUMN,
		MODIFY_COLUMN,
		DROP_COLUMN
	}

	private SubType subType;
	
	private Table table;
	private TableField field;
	
	public AlterTableCommand(Script script, Table table) {
		super(script);
		this.table = table;
	}
	
	public AlterTableCommand addColumn(TableField field) {
		this.field = field;
		subType = SubType.ADD_COLUMN;
		return this;
	}

	public AlterTableCommand modifyColumn(TableField field) {
		this.field = field;
		subType = SubType.MODIFY_COLUMN;
		return this;
	}

	public AlterTableCommand dropColumn(TableField field) {
		this.field = field;
		subType = SubType.DROP_COLUMN;
		return this;
	}
	
	public SubType getSubType() {
		return subType;
	}
	
	public Table getTable() {
		return table;
	}
	
	public TableField getField() {
		return field;
	}
	
}
