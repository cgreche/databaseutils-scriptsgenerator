package structs;

import java.util.ArrayList;
import java.util.List;

//10/04/2019
public class Table {
	private String name;
	private List<TableField> fields;
	
	public Table() {
		fields = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TableField> getFields() {
		return fields;
	}
	public void setFields(List<TableField> fields) {
		this.fields = fields;
	}
	
	public List<TableField> getPK() {
		ArrayList<TableField> pkFields = new ArrayList<>();
		if(fields != null) {
			for(TableField field : fields)
				if(field.isPK())
					pkFields.add(field);
		}
		return pkFields;
	}
	
	public List<TableField> getFKs() {
		ArrayList<TableField> pkFields = new ArrayList<>();
		if(fields != null) {
			for(TableField field : fields)
				if(field.isFK())
					pkFields.add(field);
		}
		return pkFields;
	}
	
}
