package ui.resultingtable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import structs.Constraints;
import structs.FieldType;
import structs.TableField;

//11/04/2019

public class TableFieldsTableModel extends AbstractTableModel {
		
	private static final long serialVersionUID = -1L;
	
	List<TableFieldTableItem> data = new ArrayList<>();
	String[] columnNames = {"Nome", "Tipo", "Tamanho", "PK", "FK", "Not null", "Tabela referenciada", "Coluna refenciada"};
	
	@Override
	public Object getValueAt(int row, int col) {
		TableFieldTableItem fieldItem = data.get(row);
		TableField field = fieldItem.getField();
		if(col == 0) return field.getName();
		if(col == 1) return field.getType() != null ? field.getType().getName() : null;
		if(col == 2) return field.getSize();
		if(col == 3) return field.isPK();
		if(col == 4) return field.isFK();
		if(col == 5) return field.isNotNull();
		if(col == 6) return field.getReferencedTable();
		if(col == 7) return field.getReferencedColumn();
		return null;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		TableFieldTableItem fieldItem = data.get(row);
		TableField field = fieldItem.getField();
		if(col == 0) field.setName((String)value);
		if(col == 1) field.setType((FieldType)value);
		if(col == 2) field.setSize((String)value);
		if(col == 3) {
			if((Boolean)value)
				field.setConstraints(field.getConstraints()|Constraints.PK);
			else
				field.setConstraints(field.getConstraints()&~Constraints.PK);
		}
		if(col == 4) {
			if((Boolean)value)
				field.setConstraints(field.getConstraints()|Constraints.FK);
			else
				field.setConstraints(field.getConstraints()&~Constraints.FK);
		}
		if(col == 5) {
			if((Boolean)value)
				field.setConstraints(field.getConstraints()|Constraints.NOT_NULL);
			else
				field.setConstraints(field.getConstraints()&~Constraints.NOT_NULL);
		}
		if(col == 6) field.setReferencedTable((String)value);
		if(col == 7) field.setReferencedColumn((String)value);
		fireTableCellUpdated(row, col);
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		if(col == 0) return String.class;
		if(col == 1) return FieldType.class;
		if(col == 2) return String.class;
		if(col == 3) return Boolean.class;
		if(col == 4) return Boolean.class;
		if(col == 5) return Boolean.class;
		if(col == 6) return String.class;
		if(col == 7) return String.class;
		return super.getColumnClass(col);
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return data != null ? data.size() : 0;
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}
	
	public void addRow(TableFieldTableItem field) {
		data.add(field);
		fireTableRowsInserted(data.size()-1, data.size()-1);
	}
	
	public void addEmptyRow() {
		addRow(new TableFieldTableItem(new TableField(),0));
	}
	
	public List<TableFieldTableItem> getData() {
		return data;
	}
	
	public void clearData() {
		if(data == null)
			return;
		
		int deleteRowsCount = data.size();
		if(deleteRowsCount > 0) {
			data.clear();
			fireTableRowsDeleted(0, deleteRowsCount-1);
		}
	}
}
