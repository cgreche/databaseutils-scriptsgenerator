package ui.frames.createtable;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import structs.FieldType;
import structs.GenericTypes;
import structs.TableField;
import ui.resultingtable.FieldTypeComboBoxRenderer;

//11/04/2019

@SuppressWarnings("serial")
public class TableCreateTableFields extends JTable {
	
	private TableCreateTableFieldsModel model;
	DefaultTableCellRenderer tableCellRenderer;
	
	public TableCreateTableFields() {
		model = new TableCreateTableFieldsModel();
		setModel(model);
		tableCellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				TableField tf = model.getData().get(row);
				Color defaultColor = new Color(0,0,0);
				Color currentColor = defaultColor;
				
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
		model.setData(data);
	}
	
	public void addField(TableField field) {
		model.addRow(field);
	}
	
	public void modifyField(TableField field) {
		List<TableField> tfiList = model.getData();
		for(int i = 0; i < tfiList.size(); ++i) {
			if(field.getName().equals(tfiList.get(i).getName())) {
				tfiList.set(i, field);
				model.fireTableRowsUpdated(i, i);
				return;
			}
		}
	}
	
	public void removeField(TableField field) {
		List<TableField> tfList = model.getData();
		for(int i = 0; i < tfList.size(); ++i) {
			if(field.getName().equals(tfList.get(i).getName())) {
				tfList.remove(i);
				model.fireTableRowsUpdated(i, i);
				return;
			}
		}
	}
	
	public void addEmptyRow() {
		model.addRow(new TableField());
	}
	
	public List<TableField> getData() {
		return model.getData();
	}
	
	public void clearData() {
		model.clearData();
	}
}
