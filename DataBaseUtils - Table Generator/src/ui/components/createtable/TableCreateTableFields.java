package ui.components.createtable;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import structs.FieldType;
import structs.TableField;
import ui.components.fieldtypecombobox.FieldTypeComboBox;

/**
 * 
 * A table for adding and removing fields for a CreateTableCommand.
 * @author cesar.reche@techne.com.br
 * @since 11/04/2019
 *
 */
@SuppressWarnings("serial")
public class TableCreateTableFields extends JTable {
	
	public class DeleteButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
		private JTable table;
		
		private JButton deleteButton;
		private Object editorValue;
		public DeleteButton(JTable table, int column) {
			this.table = table;
			
			deleteButton = new JButton();
			deleteButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//System.out.println("WOW");
					
				}
			});
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(column).setCellRenderer(this);
			columnModel.getColumn(column).setCellEditor(this);
		}
		
		@Override
		public Object getCellEditorValue() {
			return editorValue;
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			deleteButton.setText("Remover");
			deleteButton.setIcon(null);
			return deleteButton;
		}
		
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			JButton button = new JButton("Remover");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					model.getData().remove(row);
					model.fireTableDataChanged();
				}
			});
			return button;
		}
	}
	
	private TableCreateTableFieldsModel model;
	private DefaultTableCellRenderer tableCellRenderer;
	
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

		FieldTypeComboBox comboBox = new FieldTypeComboBox();

		TableColumn fieldTypeColumn = this.getColumnModel().getColumn(1);
		fieldTypeColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		new DeleteButton(this,8);
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
