package ui.frames;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
public class AddFieldCommandDialog extends Dialog<AlterTableCommand> {
	
	boolean editMode;
	
	public static class PanelAddField extends JPanel {
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

		public PanelAddField() {
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
	
	private PanelAddField panelAddField;
	
	private Script parentScript;
	private Table resultingTable;
	
	public AddFieldCommandDialog(JFrame parent) {
		super(parent);
		
		panelAddField = new PanelAddField();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!editMode) {
				}
			}
		});
		
		this.add(panelAddField);	
		this.pack();
	}
	
	public void insertNew(Script parentScript) {
		resultingTable = parentScript.getResultTable();
		editMode = false;
		this.setVisible(true);
	}
	
	public void edit(AlterTableCommand command) {
		if(command != null) {
			resultData = (AlterTableCommand) command.clone();
			parentScript = command.getScript();
			resultingTable = parentScript.getResultTable();
			panelAddField.update(command);
		}
		editMode = true;
		this.setVisible(true);
	}
	
	public boolean isEditMode() {
		return editMode;
	}
	
}
