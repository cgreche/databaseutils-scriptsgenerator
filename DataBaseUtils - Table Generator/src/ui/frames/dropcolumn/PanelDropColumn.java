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
	
	public JComboBox<TableField> fieldList;
	
	public PanelDropColumn() {
		fieldList = new JComboBox<TableField>();
		add(fieldList);
	}
	
	public void update(Table table) {
		fieldList.removeAllItems();
		List<TableField> fields = table.getFields();
		for(TableField field : fields) {
			fieldList.addItem(field);
		}
	}
	
}
