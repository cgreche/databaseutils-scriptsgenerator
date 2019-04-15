package ui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import structs.FieldType;
import structs.GenericTypes;
import structs.TableField;

//11/04/2019

@SuppressWarnings("serial")
public class TableFieldTable extends JTable {
	
	private TableFieldsTableModel model;
	DefaultTableCellRenderer tableCellRenderer;
	
	public TableFieldTable() {
		model = new TableFieldsTableModel();
		setModel(model);
		tableCellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				TableFieldTableItem tfi = model.getData().get(row);
				Color addedLaterColor = new Color(0,0,255);
				Color modifiedColor = new Color(240,180,0);
				Color droppedColor = new Color(255,0,0);
				Color defaultColor = new Color(0,0,0);
				Color currentColor = defaultColor;
				if((tfi.getFlags() & TableFieldFlags.ADDED_LATER) != 0)
					currentColor = addedLaterColor;
				else if((tfi.getFlags() & TableFieldFlags.MODIFIED) != 0)
					currentColor = modifiedColor;
				else if((tfi.getFlags() & TableFieldFlags.DROPPED) != 0)
					currentColor = droppedColor;
				
				setFont(table.getFont());
				setForeground(currentColor);
				
				if (isSelected) {
					setBackground(table.getSelectionBackground());
				} else {
					setBackground(table.getBackground());
				}
				setText(value == null ? "" : value.toString());
				return this;
			}
		};
		this.setDefaultRenderer(String.class, tableCellRenderer);

		JComboBox<FieldType> comboBox = new JComboBox<>();
		comboBox.setRenderer(new FieldTypeComboBoxRenderer());
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
		clearData();
		for(TableField field : data) {
			addRow(field);
		}
	}
	
	private void markAs(int rowIndex, TableFieldFlags flags) {
		
	}
	
	public void addField(TableField field, boolean original) {
		model.addRow(new TableFieldTableItem(field, original ? TableFieldFlags.ORIGINAL : TableFieldFlags.ADDED_LATER));
	}
	
	public void modifyField(TableField field) {
		List<TableFieldTableItem> tfiList = model.getData();
		for(int i = 0; i < tfiList.size(); ++i) {
			if(field.getName().equals(tfiList.get(i).getField().getName())) {
				tfiList.get(i).setField(field);
				tfiList.get(i).setFlags(TableFieldFlags.MODIFIED);
				model.fireTableRowsUpdated(i, i);
				return;
			}
		}
	}
	
	public void dropField(TableField field) {
		List<TableFieldTableItem> tfiList = model.getData();
		for(int i = 0; i < tfiList.size(); ++i) {
			if(field.getName().equals(tfiList.get(i).getField().getName())) {
				tfiList.get(i).setFlags(TableFieldFlags.DROPPED);
				model.fireTableRowsUpdated(i, i);
				return;
			}
		}
	}
	
	public void removeField(TableField field) {
		List<TableFieldTableItem> tfiList = model.getData();
		for(int i = 0; i < tfiList.size(); ++i) {
			if(field.getName().equals(tfiList.get(i).getField().getName())) {
				tfiList.remove(i);
				model.fireTableRowsDeleted(i, i);
				return;
			}
		}
	}
	
	public void addRow(TableField tf) {
		model.addRow(new TableFieldTableItem(tf,TableFieldFlags.ORIGINAL));
	}
	
	public void addEmptyRow() {
		model.addRow(new TableFieldTableItem(new TableField(),0));
	}
	
	public List<TableField> getData() {
		List<TableField> fields = new ArrayList<>();
		for(TableFieldTableItem tfi : model.getData()) {
			fields.add(tfi.getField());
		}
		return fields;
	}
	
	public void clearData() {
		model.clearData();
	}
}
