package ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import structs.Constraints;
import structs.FieldType;
import structs.Script;
import structs.TableField;
import ui.resultingtable.TableFieldTableItem;

//06/05/2019

@SuppressWarnings("serial")
public class TableScripts extends JTable {
	
	public static class InternalData {
		Script script;
		JButton removeButton;
	}
	
	public static class TableScriptsModel extends AbstractTableModel {
		
		private static final long serialVersionUID = -1L;
		
		String[] columnNames = {"Nome", "Remover"};
		private List<Script> data;
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			if(data == null)
				return;
			Script script = data.get(row);
			if(col == 0)
				script.setName((String)value);
		}
		
		@Override
		public Object getValueAt(int row, int col) {
			if(data == null)
				return null;
			Script script = data.get(row);
			if(col == 0) return script.getName();
			return null;
		}
		
		@Override
		public Class<?> getColumnClass(int col) {
			if(col == 0) return String.class;
			return JButton.class;
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
		
		public void setData(List<Script> data) {
			this.data = data;
		}
		
		public List<Script> getData() {
			return data;
		}
		
	}
	
	public class DeleteButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
		private JTable table;
		private int mnemonic;
		
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
					data.remove(row);
					refresh();
				}
			});
			return button;
		}
	}
	
	private TableScriptsModel model;
	private List<Script> data;
	
	public TableScripts() {
		super();
		model = new TableScriptsModel();
		this.setModel(model);
		
		new DeleteButton(this,1);
	}
	
	public void setData(List<Script> scriptList) {
		this.data = scriptList;
		model.setData(data);
		
		model.fireTableDataChanged();
	}
	
	public List<Script> getData() {
		return data;
	}
	
	public void refresh() {
		model.fireTableDataChanged();
		updateUI();
	}
}
