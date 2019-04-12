package ui;

import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import structs.FieldType;
import structs.GenericTypes;
import structs.TableField;

//11/04/2019

@SuppressWarnings("serial")
public class TableFieldTable extends JTable {
	
	private TableFieldsTableModel model;
	
	public TableFieldTable() {
		model = new TableFieldsTableModel();
		setModel(model);

		JComboBox<FieldType> comboBox = new JComboBox<>();
		comboBox.setRenderer(new FieldTypeComboBoxRenderer());
		comboBox.addItem(null);
		comboBox.addItem(GenericTypes.TEXT);
		comboBox.addItem(GenericTypes.NUMERIC);
		comboBox.addItem(GenericTypes.DATE);
		comboBox.addItem(GenericTypes.TIMESTAMP);
		comboBox.addItem(GenericTypes.BLOB);
		comboBox.addItem(GenericTypes.LONGTEXT);

		TableColumn fieldTypeColumn = this.getColumnModel().getColumn(1);
		fieldTypeColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}
	
	public void setData(List<TableField> data) {
		for(TableField field : data) {
			addRow(field);
		}
	}
	
	public void addRow(TableField tf) {
		model.addRow(new TableFieldTableItem(tf,TableFieldFlags.ADDED_LATER));
	}
	
	public void addEmptyRow() {
		addRow(new TableField());
	}
	
	public List<TableField> getData() {
		List<TableField> fields = new ArrayList<>();
		for(TableField tf : ) {
			fields.add(tf);
		}
		return model.getData();
	}
	
	public void clearData() {
		model.clearData();
	}
}
