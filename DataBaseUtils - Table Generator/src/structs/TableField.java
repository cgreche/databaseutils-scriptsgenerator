package structs;

//10/04/2019
public class TableField {
	private String name;
	private FieldType type;
	private String args;
	private int constraints;
	private String referencedTable;
	private String referencedColumn;
	
	public TableField() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public FieldType getType() {
		return type;
	}
	
	public void setType(FieldType type) {
		this.type = type;
	}
	
	public String getArgs() {
		return args;
	}
	
	public void setArgs(String args) {
		this.args = args;
	}
	
	public int getConstraints() {
		return constraints;
	}
	
	public void setConstraints(int constraints) {
		this.constraints = constraints;
	}
	
	public boolean isPK() {
		return (constraints&Constraints.PK) != 0;
	}
	
	public boolean isFK() {
		return (constraints&Constraints.FK) != 0;
	}
	
	public boolean isNotNull() {
		return (constraints&Constraints.NOT_NULL) != 0;
	}
	
	public String getReferencedTable() {
		return referencedTable;
	}
	
	public void setReferencedTable(String referencedTable) {
		this.referencedTable = referencedTable;
	}
	
	public String getReferencedColumn() {
		return referencedColumn;
	}
	
	public void setReferencedColumn(String referencedColumn) {
		this.referencedColumn = referencedColumn;
	}
	
}
