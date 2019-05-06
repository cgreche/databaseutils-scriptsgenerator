package ui;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import structs.Command;

//06/05/2019

@SuppressWarnings("serial")
public class TableCommands extends JTable {
	
	public static class TableCommandsModel extends AbstractTableModel {
		
		private static final long serialVersionUID = -1L;
		
		String[] columnNames = {"Nome", "Remover"};
		private List<Command> data;
		
		@Override
		public Object getValueAt(int row, int col) {
			Command commands = data.get(row);
			if(col == 0) return commands.toString();
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
			return false;
		}
		
		public void setData(List<Command> data) {
			this.data = data;
		}
		
		public List<Command> getData() {
			return data;
		}
		
	}
	
	private TableCommandsModel model;
	private List<Command> data;
	
	public TableCommands() {
		super();
		model = new TableCommandsModel();
		this.setModel(model);
	}
	
	public void setData(List<Command> commandList) {
		this.data = commandList;
		model.setData(commandList);
		model.fireTableDataChanged();
	}
	
	public List<Command> getData() {
		return data;
	}
	
	public void refresh() {
		model.fireTableDataChanged();
	}
}
