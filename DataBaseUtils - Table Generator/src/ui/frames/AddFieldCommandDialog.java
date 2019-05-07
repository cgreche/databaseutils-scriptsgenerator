package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import structs.AddFieldCommand;
import structs.Constraints;
import structs.FieldType;
import structs.GenericTypes;
import structs.Script;
import structs.Table;
import structs.TableField;

//16-04-2019
@SuppressWarnings("serial")
public class AddFieldCommandDialog extends Dialog<AddFieldCommand> {
	
	private JTextField tfName;
	private JComboBox<FieldType> ddFieldType;
	private JTextField tfSize;
	private JCheckBox cbPk;
	private JCheckBox cbFk;
	private JCheckBox cbNotNull;
	private JTextField tfReferencedTable;
	private JTextField tfRetferencedColumn;
	private JButton buttonSave;
	
	private boolean editMode;
	private AddFieldCommand currentCommand;
	private Script parentScript;
	private Table resultingTable;
	
	public AddFieldCommandDialog(JFrame parent) {
		super(parent);
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
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
		buttonSave = new JButton("Salvar");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TableField field = new TableField();
				field.setName(tfName.getText());
				field.setType((FieldType)ddFieldType.getSelectedItem());
				field.setSize(tfSize.getText());
				int constraints = 0;
				if(cbPk.isSelected())
					constraints |= Constraints.PK;
				if(cbFk.isSelected())
					constraints |= Constraints.FK;
				if(cbNotNull.isSelected())
					constraints |= Constraints.NOT_NULL;
				field.setConstraints(constraints);
				field.setReferencedTable(tfReferencedTable.getText());
				field.setReferencedColumn(tfRetferencedColumn.getText());
				currentCommand.addColumn(field);
				AddFieldCommandDialog.this.setResult(true,currentCommand);
				AddFieldCommandDialog.this.dispose();
			}
			
		});
		
		add(tfName);
		add(ddFieldType);
		add(tfSize);
		add(cbPk);
		add(cbFk);
		add(cbNotNull);
		add(tfReferencedTable);
		add(tfRetferencedColumn);
		add(buttonSave);
		
		this.pack();
	}
	
	public void updateControls() {
		
	}
	
	public void insertNew(Script parentScript) {
		this.parentScript = parentScript;
		resultingTable = parentScript.getResultTable();
		editMode = false;
		currentCommand = new AddFieldCommand(parentScript,resultingTable, null);
		updateControls();
		this.setVisible(true);
	}
	
	public void edit(AddFieldCommand command) {
		if(command != null) {
			parentScript = command.getScript();
			resultData = (AddFieldCommand) command.clone();
			currentCommand = command.clone();
			resultingTable = parentScript.getResultTable();
		}
		
		editMode = true;
		updateControls();
		this.setVisible(true);
	}
	
	public boolean isEditMode() {
		return editMode;
	}
	
}
