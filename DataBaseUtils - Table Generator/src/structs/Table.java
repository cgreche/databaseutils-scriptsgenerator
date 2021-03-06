package structs;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class for representing a Table.
 * @author cesar.reche@techne.com.br
 * @since 10/04/2019
 *
 */
public class Table implements Cloneable {
	private String name;
	private List<TableField> fields;
	
	public Table() {
		fields = new ArrayList<>();
	}
	
	public TableField getFieldByName(String fieldName) {
		for(TableField field : fields) {
			if(fieldName.contentEquals(field.getName()))
				return field;
		}
		return null;
	}
	
	public int getFieldIndex(TableField field) {
		for(int i = 0; i < fields.size(); ++i) {
			if(fields.get(i).getName().equals(field.getName()))
				return i;
		}
		return -1;
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
	
	@Override
	public Table clone() {
		Table ret = new Table();
		ret.setName(this.name);
		for(TableField field : fields) {
			ret.fields.add((TableField)field.clone());
		}
		return ret;
	}
}
