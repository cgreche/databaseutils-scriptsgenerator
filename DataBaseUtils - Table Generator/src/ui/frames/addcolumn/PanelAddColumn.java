package ui.frames.addcolumn;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import structs.AlterTableCommand;
import structs.FieldType;
import structs.GenericTypes;
import structs.Script;
import structs.Table;
import structs.TableField;

//16-04-2019

@SuppressWarnings("serial")
public class PanelAddColumn extends JPanel {
	JTextField tfName;
	JComboBox<FieldType> ddFieldType;
	JTextField tfSize;
	JCheckBox cbPk;
	JCheckBox cbFk;
	JCheckBox cbNotNull;
	JTextField tfReferencedTable;
	JTextField tfRetferencedColumn;
	
	Script currentScript;
	Table resultingTable;
	TableField currentField;

	public PanelAddColumn() {
		tfName = new JTextField();
		ddFieldType = new JComboBox<FieldType>();
		ddFieldType.addItem(GenericTypes.TEXT);
		ddFieldType.addItem(GenericTypes.NUMERIC);
		ddFieldType.addItem(GenericTypes.DATE);
		ddFieldType.addItem(GenericTypes.TIMESTAMP);
		ddFieldType.addItem(GenericTypes.BLOB);
		ddFieldType.addItem(GenericTypes.LONGTEXT);
		
		tfSize = new JTextField();
		cbPk = new JCheckBox();
		cbFk = new JCheckBox();
		cbNotNull = new JCheckBox();
		tfReferencedTable = new JTextField();
		tfRetferencedColumn = new JTextField();
		add(tfName);
		add(ddFieldType);
		add(cbPk);
		add(cbFk);
		add(cbNotNull);
		add(tfReferencedTable);
		add(tfRetferencedColumn);
	}
	
	public void update(AlterTableCommand command) {
	}
}
