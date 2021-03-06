package ui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import structs.Script;

/**
 * 
 * Table for script list (view).
 * @author cesar.reche@techne.com.br
 * @since 06/05/2019
 *
 */
@SuppressWarnings("serial")
public class TableScripts extends JTable {
	
	public static class TableScriptsModel extends AbstractTableModel {
		
		private static final long serialVersionUID = -1L;
		
		String[] columnNames = {"Nome", "Remover"};
		private List<Script> data;
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			if(data == null || row >= data.size())
				return;
			Script script = data.get(row);
			if(col == 0) {
				if(!value.equals(script.getName())) {
					script.setName((String)value);
					script.notifyScriptChanged();
					application.Main.mainWindow.notifyProjectChanged();
				}
			}
		}
		
		@Override
		public Object getValueAt(int row, int col) {
			if(data == null || row >= data.size())
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
		private ActionListener action;
		
		private Object editorValue;
		public DeleteButton() {
		}
		
		@Override
		public Object getCellEditorValue() {
			return editorValue;
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			return new JButton("Remover");
		}
		
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			JButton button = new JButton("Remover");
			button.addActionListener(action);
			return button;
		}
		
		public void setDeleteAction(ActionListener action) {
			this.action = action;
		}
	}
	
	private TableScriptsModel model;
	private List<Script> data;
	
	private DeleteButton deleteButton;
	
	public TableScripts() {
		super();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		putClientProperty("terminateEditOnFocusLost", true);
		model = new TableScriptsModel();
		this.setModel(model);
		deleteButton = new DeleteButton();
		this.getColumnModel().getColumn(1).setCellRenderer(deleteButton);
		this.getColumnModel().getColumn(1).setCellEditor(deleteButton);
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
	}
	
	public void setDeleteAction(ActionListener action) {
		deleteButton.setDeleteAction(action);
	}
}
