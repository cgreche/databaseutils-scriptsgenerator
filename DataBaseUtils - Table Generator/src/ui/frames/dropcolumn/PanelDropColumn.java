package ui.frames.dropcolumn;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import structs.AlterTableCommand;
import structs.Table;
import structs.TableField;

//16-04-2019

@SuppressWarnings("serial")
public class PanelDropColumn extends JPanel {
	
	JComboBox<TableField> fieldList;
	Table resultingTable;
	
	public PanelDropColumn() {
		fieldList = new JComboBox<TableField>();
		add(fieldList);
	}
	
	public void update(AlterTableCommand command) {
		fieldList.removeAllItems();
		resultingTable = command.getScript().getResultTable();
		List<TableField> fields = resultingTable.getFields();
		for(TableField field : fields) {
			fieldList.addItem(field);
		}
	}
	
}
