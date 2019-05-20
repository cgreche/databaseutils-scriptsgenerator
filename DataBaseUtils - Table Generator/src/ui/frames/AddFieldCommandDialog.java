package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	
	private JLabel lblName;
	private JTextField tfName;

	private JLabel lblType;
	private JComboBox<FieldType> ddType;
	
	private JLabel lblSize;
	private JTextField tfSize;
	
	private JLabel lblPk;
	private JCheckBox cbPk;

	private JLabel lblFk;
	private JCheckBox cbFk;
	
	private JLabel lblNotNull;
	private JCheckBox cbNotNull;
	
	private JLabel lblReferencedTable;
	private JTextField tfReferencedTable;
	
	private JLabel lblReferencedColumn;
	private JTextField tfReferencedColumn;
	
	private JButton buttonSave;
	
	private boolean editMode;
	private AddFieldCommand currentCommand;
	private Script parentScript;
	private Table currentTable;
	
	public AddFieldCommandDialog(JFrame parent) {
		super(parent);
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		lblName = new JLabel("Nome");
		tfName = new JTextField();

		lblType = new JLabel("Tipo");
		ddType = new JComboBox<FieldType>();
		ddType.addItem(GenericTypes.TEXT);
		ddType.addItem(GenericTypes.NUMERIC);
		ddType.addItem(GenericTypes.DATE);
		ddType.addItem(GenericTypes.TIMESTAMP);
		ddType.addItem(GenericTypes.BLOB);
		ddType.addItem(GenericTypes.LONGTEXT);
		
		lblSize = new JLabel("Tamanho");
		tfSize = new JTextField();

		lblPk = new JLabel("PK");
		cbPk = new JCheckBox();

		lblFk= new JLabel("FK");
		cbFk = new JCheckBox();

		lblNotNull = new JLabel("Not Null");
		cbNotNull = new JCheckBox();

		lblReferencedTable = new JLabel("Tabela referenciada");
		tfReferencedTable = new JTextField();

		lblReferencedColumn = new JLabel("Campo referenciado");
		tfReferencedColumn = new JTextField();
		
		buttonSave = new JButton("Salvar");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TableField field = new TableField();
				field.setName(tfName.getText());
				field.setType((FieldType)ddType.getSelectedItem());
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
				field.setReferencedColumn(tfReferencedColumn.getText());
				currentCommand.setField(field);
				
				if(!validateFields())
					return;

				AddFieldCommandDialog.this.setResult(true,currentCommand);
				AddFieldCommandDialog.this.dispose();
			}
			
		});
		
		add(lblName);
		add(tfName);
		add(lblType);
		add(ddType);
		add(lblSize);
		add(tfSize);
		add(lblPk);
		add(cbPk);
		add(lblFk);
		add(cbFk);
		add(lblNotNull);
		add(cbNotNull);
		add(lblReferencedTable);
		add(tfReferencedTable);
		add(lblReferencedColumn);
		add(tfReferencedColumn);
		add(buttonSave);
		
		this.pack();
	}
	
	private boolean validateFields() {
		Table table = currentTable;
		TableField field = currentCommand.getField();

		if(field.getName() == null || "".contentEquals(field.getName())) {
			JOptionPane.showMessageDialog(this, "Há campos da tabela com nome não informado.");
			return false;
		}
		
		if(field.getType() == null) {
			JOptionPane.showMessageDialog(this, "Há campos da tabela com tipo não informado.");
			return false;
		}
		
		for(TableField f : table.getFields()) {
			if(f.getName().equals(field.getName())) {
				JOptionPane.showMessageDialog(this, "O nome escolhido já existe na tabela destino.");
				return false;
			}
		}
		
		return true;
	}
	
	public void updateControls() {
		TableField field = currentCommand.getField();
		tfName.setText(field.getName());
		ddType.setSelectedItem(field.getType());
		tfSize.setText(field.getSize());
		cbPk.setSelected((field.getConstraints() & Constraints.PK) != 0);
		cbFk.setSelected((field.getConstraints() & Constraints.FK) != 0);
		cbNotNull.setSelected((field.getConstraints() & Constraints.NOT_NULL) != 0);
		tfReferencedTable.setText(field.getReferencedTable());
		tfReferencedColumn.setText(field.getReferencedColumn());
	}
	
	public void insertNew(Script parentScript) {
		this.parentScript = parentScript;
		currentTable = parentScript.getResultingTable();
		editMode = false;
		currentCommand = new AddFieldCommand(parentScript, currentTable, null);
		updateControls();
		this.setVisible(true);
	}
	
	public void edit(AddFieldCommand command) {
		if(command != null) {
			parentScript = command.getScript();
			resultData = (AddFieldCommand) command.clone();
			currentCommand = command.clone();
			currentTable = command.getRefTable();
		}
		
		editMode = true;
		updateControls();
		this.setVisible(true);
	}
	
	public boolean isEditMode() {
		return editMode;
	}
	
}
