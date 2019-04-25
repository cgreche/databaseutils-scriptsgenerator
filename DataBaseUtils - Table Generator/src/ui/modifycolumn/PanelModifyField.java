package ui.modifycolumn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
public class PanelModifyField extends JPanel {
	JComboBox<TableField> ddField;
	JComboBox<FieldType> ddFieldType;
	JTextField tfSize;
	JCheckBox cbPk;
	JCheckBox cbFk;
	JCheckBox cbNotNull;
	JTextField tfReferencedTable;
	JTextField tfRetferencedColumn;
	
	Script currentScript;
	public TableField currentField;

	public PanelModifyField() {
		ddField = new JComboBox<TableField>();
		ddField.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				TableField item = (TableField)ddField.getSelectedItem();
				ddFieldType.setSelectedItem(item.getType());
				tfSize.setText(item.getArgs());
				cbPk.setSelected(item.isPK());
				cbFk.setSelected(item.isFK());
				cbNotNull.setSelected(item.isNotNull());
				tfReferencedTable.setText(item.getReferencedTable());
				tfRetferencedColumn.setText(item.getReferencedColumn());
				
				currentField = item;
			}
		});
		
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
		add(ddField);
		add(ddFieldType);
		add(cbPk);
		add(cbFk);
		add(cbNotNull);
		add(tfReferencedTable);
		add(tfRetferencedColumn);
	}
	
	public void update(Table table) {
		ddField.removeAllItems();
		List<TableField> fields = table.getFields();
		for(TableField field : fields) {
			ddField.addItem(field);
		}
	}
}
