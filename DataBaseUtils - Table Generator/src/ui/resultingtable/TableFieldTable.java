package ui.resultingtable;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import structs.AddFieldCommand;
import structs.Command;
import structs.CreateTableCommand;
import structs.DropFieldCommand;
import structs.FieldType;
import structs.GenericTypes;
import structs.ModifyFieldCommand;
import structs.Script;
import structs.Table;
import structs.TableField;

//11/04/2019

@SuppressWarnings("serial")
public class TableFieldTable extends JTable {
	
	private TableFieldsTableModel model;
	DefaultTableCellRenderer tableCellRenderer;
	
	private boolean showRemoved;
	
	public TableFieldTable() {
		model = new TableFieldsTableModel();
		setModel(model);
		
		showRemoved = false;
		
		RowFilter<TableFieldsTableModel, Integer> filter = new RowFilter<TableFieldsTableModel, Integer>() {
			public boolean include(Entry<? extends TableFieldsTableModel, ? extends Integer> entry) {
				TableFieldTableItem item = entry.getModel().getData().get(entry.getIdentifier());
				return ((item.getFlags() & TableFieldFlags.DROPPED) == 0) || showRemoved;
			}
		};
		
		TableRowSorter<TableFieldsTableModel> sorter = new TableRowSorter<TableFieldsTableModel>(model);
		sorter.setRowFilter(filter);
		this.setRowSorter(sorter);
		
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
	
	public void showRemoved(boolean show) {
		showRemoved = show;
		model.fireTableDataChanged();
	}
	
	public void setData(Script script) {
		clearData();
		if(script == null)
			return;
		List<Command> commands = script.getCommands();
		if(commands == null || commands.isEmpty())
			return;
		
		for(Command command : commands) {
			if(command instanceof CreateTableCommand) {
				Table originalTable = ((CreateTableCommand) command).getTable().clone();
				this.setData(originalTable.getFields());
			} else if(command instanceof AddFieldCommand) {
				this.addField(((AddFieldCommand) command).getField(),false);
			} else if(command instanceof ModifyFieldCommand) {
				this.modifyField(((ModifyFieldCommand) command).getOldField(),((ModifyFieldCommand) command).getNewField());
			} else if(command instanceof DropFieldCommand) {
				this.dropField(((DropFieldCommand) command).getField());
			}
		}
	}
	
	public void setData(List<TableField> data) {
		clearData();
		for(TableField field : data) {
			addRow(field);
		}
	}
	
	public void addField(TableField field, boolean original) {
		model.addRow(new TableFieldTableItem(field, original ? TableFieldFlags.ORIGINAL : TableFieldFlags.ADDED_LATER));
	}
	
	public void modifyField(TableField currentField, TableField newField) {
		List<TableFieldTableItem> tfiList = model.getData();
		for(int i = 0; i < tfiList.size(); ++i) {
			if(currentField.getName().equals(tfiList.get(i).getField().getName())) {
				tfiList.get(i).setField(newField);
				tfiList.get(i).setFlags(TableFieldFlags.MODIFIED);
				model.fireTableRowsUpdated(i, i);
				model.fireTableDataChanged(); //this calls the filter again
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
				model.fireTableDataChanged(); //this calls the filter again
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
	
	public void refresh() {
		model.fireTableDataChanged();
	}
}
