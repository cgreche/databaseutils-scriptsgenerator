package ui.components.resultingtable;

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
public class ResultingTable extends JTable {
	
	private ResultingTableModel model;
	DefaultTableCellRenderer tableCellRenderer;
	
	private boolean showRemoved;
	
	public ResultingTable() {
		model = new ResultingTableModel();
		setModel(model);
		
		showRemoved = false;
		
		RowFilter<ResultingTableModel, Integer> filter = new RowFilter<ResultingTableModel, Integer>() {
			public boolean include(Entry<? extends ResultingTableModel, ? extends Integer> entry) {
				ResultingTableItem item = entry.getModel().getData().get(entry.getIdentifier());
				return (item.getFlags() != ResultingTableItemFlags.DROPPED) || showRemoved;
			}
		};
		
		TableRowSorter<ResultingTableModel> sorter = new TableRowSorter<ResultingTableModel>(model);
		sorter.setRowFilter(filter);
		this.setRowSorter(sorter);
		
		tableCellRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				List<ResultingTableItem> data = model.getData();
				int index = 0;
				int i = 0;
				ResultingTableItem rti;
				for(ResultingTableItem rti2 : data) {
					if((rti2.getFlags() != ResultingTableItemFlags.DROPPED) || showRemoved) {
						if(i == row)
							break;
						else
							++i;
					}
					++index;
				}
				rti = model.getData().get(index);
				Color addedLaterColor = new Color(0,0,255);
				Color modifiedColor = new Color(240,180,0);
				Color droppedColor = new Color(255,0,0);
				Color defaultColor = new Color(0,0,0);
				Color currentColor = defaultColor;
				if(rti.getFlags() == ResultingTableItemFlags.ADDED_LATER)
					currentColor = addedLaterColor;
				else if(rti.getFlags() == ResultingTableItemFlags.MODIFIED)
					currentColor = modifiedColor;
				else if(rti.getFlags() == ResultingTableItemFlags.DROPPED)
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
		model.addRow(new ResultingTableItem(field, original ? ResultingTableItemFlags.ORIGINAL : ResultingTableItemFlags.ADDED_LATER));
	}
	
	public void modifyField(TableField currentField, TableField newField) {
		List<ResultingTableItem> tfiList = model.getData();
		for(int i = 0; i < tfiList.size(); ++i) {
			if(currentField.getName().equals(tfiList.get(i).getField().getName())) {
				tfiList.get(i).setField(newField);
				tfiList.get(i).setFlags(ResultingTableItemFlags.MODIFIED);
				model.fireTableRowsUpdated(i, i);
				model.fireTableDataChanged(); //this calls the filter again
				return;
			}
		}
	}
	
	public void dropField(TableField field) {
		List<ResultingTableItem> tfiList = model.getData();
		for(int i = 0; i < tfiList.size(); ++i) {
			if(field.getName().equals(tfiList.get(i).getField().getName())) {
				tfiList.get(i).setFlags(ResultingTableItemFlags.DROPPED);
				model.fireTableRowsUpdated(i, i);
				model.fireTableDataChanged(); //this calls the filter again
				return;
			}
		}
	}
	
	public void removeField(TableField field) {
		List<ResultingTableItem> tfiList = model.getData();
		for(int i = 0; i < tfiList.size(); ++i) {
			if(field.getName().equals(tfiList.get(i).getField().getName())) {
				tfiList.remove(i);
				model.fireTableRowsDeleted(i, i);
				return;
			}
		}
	}
	
	public void addRow(TableField tf) {
		model.addRow(new ResultingTableItem(tf,ResultingTableItemFlags.ORIGINAL));
	}
	
	public void addEmptyRow() {
		model.addRow(new ResultingTableItem(new TableField(),0));
	}
	
	public List<TableField> getData() {
		List<TableField> fields = new ArrayList<>();
		for(ResultingTableItem tfi : model.getData()) {
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
