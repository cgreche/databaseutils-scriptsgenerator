package ui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
	
	private JButton btnSave;
	
	private boolean editMode;
	private AddFieldCommand currentCommand;
	private Script parentScript;
	private Table currentTable;
	private JPanel panelFieldData;
	private JSeparator separator;
	private JPanel panel;
	
	public AddFieldCommandDialog(JFrame parent) {
		super(parent);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panelFieldData = new JPanel();
		panel.add(panelFieldData);
		GridBagLayout gbl_panelFieldData = new GridBagLayout();
		gbl_panelFieldData.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelFieldData.rowHeights = new int[] {0, 0};
		gbl_panelFieldData.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0};
		gbl_panelFieldData.rowWeights = new double[]{0.0, 0.0};
		panelFieldData.setLayout(gbl_panelFieldData);
		
		lblName = new JLabel("Nome");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 0, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panelFieldData.add(lblName, gbc_lblName);
		tfName = new JTextField();
		GridBagConstraints gbc_tfName = new GridBagConstraints();
		gbc_tfName.weightx = 2.0;
		gbc_tfName.anchor = GridBagConstraints.LINE_START;
		gbc_tfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfName.insets = new Insets(0, 0, 0, 5);
		gbc_tfName.gridx = 0;
		gbc_tfName.gridy = 1;
		panelFieldData.add(tfName, gbc_tfName);
		
		lblType = new JLabel("Tipo");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.WEST;
		gbc_lblType.insets = new Insets(0, 0, 0, 5);
		gbc_lblType.gridx = 1;
		gbc_lblType.gridy = 0;
		panelFieldData.add(lblType, gbc_lblType);
		ddType = new JComboBox<FieldType>();
		GridBagConstraints gbc_ddType = new GridBagConstraints();
		gbc_ddType.anchor = GridBagConstraints.LINE_START;
		gbc_ddType.weightx = 1.0;
		gbc_ddType.fill = GridBagConstraints.HORIZONTAL;
		gbc_ddType.insets = new Insets(0, 0, 0, 5);
		gbc_ddType.gridx = 1;
		gbc_ddType.gridy = 1;
		panelFieldData.add(ddType, gbc_ddType);
		ddType.addItem(GenericTypes.TEXT);
		ddType.addItem(GenericTypes.NUMERIC);
		ddType.addItem(GenericTypes.DATE);
		ddType.addItem(GenericTypes.TIMESTAMP);
		ddType.addItem(GenericTypes.BLOB);
		ddType.addItem(GenericTypes.LONGTEXT);
				
		lblSize = new JLabel("Tamanho");
		GridBagConstraints gbc_lblSize = new GridBagConstraints();
		gbc_lblSize.anchor = GridBagConstraints.WEST;
		gbc_lblSize.insets = new Insets(0, 0, 0, 5);
		gbc_lblSize.gridx = 2;
		gbc_lblSize.gridy = 0;
		panelFieldData.add(lblSize, gbc_lblSize);
		tfReferencedColumn = new JTextField();
		GridBagConstraints gbc_tfReferencedColumn = new GridBagConstraints();
		gbc_tfReferencedColumn.weightx = 1.0;
		gbc_tfReferencedColumn.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfReferencedColumn.gridx = 7;
		gbc_tfReferencedColumn.gridy = 1;
		panelFieldData.add(tfReferencedColumn, gbc_tfReferencedColumn);
		
		lblPk = new JLabel("PK");
		GridBagConstraints gbc_lblPk = new GridBagConstraints();
		gbc_lblPk.anchor = GridBagConstraints.WEST;
		gbc_lblPk.insets = new Insets(0, 0, 0, 5);
		gbc_lblPk.gridx = 3;
		gbc_lblPk.gridy = 0;
		panelFieldData.add(lblPk, gbc_lblPk);
		cbPk = new JCheckBox();
		GridBagConstraints gbc_cbPk = new GridBagConstraints();
		gbc_cbPk.anchor = GridBagConstraints.LINE_START;
		gbc_cbPk.insets = new Insets(0, 0, 0, 5);
		gbc_cbPk.gridx = 3;
		gbc_cbPk.gridy = 1;
		panelFieldData.add(cbPk, gbc_cbPk);
						
		lblFk= new JLabel("FK");
		GridBagConstraints gbc_lblFk = new GridBagConstraints();
		gbc_lblFk.anchor = GridBagConstraints.WEST;
		gbc_lblFk.insets = new Insets(0, 0, 0, 5);
		gbc_lblFk.gridx = 4;
		gbc_lblFk.gridy = 0;
		panelFieldData.add(lblFk, gbc_lblFk);
		cbFk = new JCheckBox();
		GridBagConstraints gbc_cbFk = new GridBagConstraints();
		gbc_cbFk.anchor = GridBagConstraints.WEST;
		gbc_cbFk.insets = new Insets(0, 0, 0, 5);
		gbc_cbFk.gridx = 4;
		gbc_cbFk.gridy = 1;
		panelFieldData.add(cbFk, gbc_cbFk);
								
		lblNotNull = new JLabel("Not Null");
		GridBagConstraints gbc_lblNotNull = new GridBagConstraints();
		gbc_lblNotNull.anchor = GridBagConstraints.WEST;
		gbc_lblNotNull.insets = new Insets(0, 0, 0, 5);
		gbc_lblNotNull.gridx = 5;
		gbc_lblNotNull.gridy = 0;
		panelFieldData.add(lblNotNull, gbc_lblNotNull);
		cbNotNull = new JCheckBox();
		GridBagConstraints gbc_cbNotNull = new GridBagConstraints();
		gbc_cbNotNull.anchor = GridBagConstraints.WEST;
		gbc_cbNotNull.insets = new Insets(0, 0, 0, 5);
		gbc_cbNotNull.gridx = 5;
		gbc_cbNotNull.gridy = 1;
		panelFieldData.add(cbNotNull, gbc_cbNotNull);
										
		lblReferencedTable = new JLabel("Tabela referenciada");
		GridBagConstraints gbc_lblReferencedTable = new GridBagConstraints();
		gbc_lblReferencedTable.anchor = GridBagConstraints.WEST;
		gbc_lblReferencedTable.insets = new Insets(0, 0, 0, 5);
		gbc_lblReferencedTable.gridx = 6;
		gbc_lblReferencedTable.gridy = 0;
		panelFieldData.add(lblReferencedTable, gbc_lblReferencedTable);
		tfReferencedTable = new JTextField();
		GridBagConstraints gbc_tfReferencedTable = new GridBagConstraints();
		gbc_tfReferencedTable.weightx = 1.0;
		gbc_tfReferencedTable.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfReferencedTable.insets = new Insets(0, 0, 0, 5);
		gbc_tfReferencedTable.gridx = 6;
		gbc_tfReferencedTable.gridy = 1;
		panelFieldData.add(tfReferencedTable, gbc_tfReferencedTable);
												
		lblReferencedColumn = new JLabel("Campo referenciado");
		GridBagConstraints gbc_lblReferencedColumn = new GridBagConstraints();
		gbc_lblReferencedColumn.anchor = GridBagConstraints.WEST;
		gbc_lblReferencedColumn.insets = new Insets(0, 0, 0, 5);
		gbc_lblReferencedColumn.gridx = 7;
		gbc_lblReferencedColumn.gridy = 0;
		panelFieldData.add(lblReferencedColumn, gbc_lblReferencedColumn);
		tfSize = new JTextField();
		GridBagConstraints gbc_tfSize = new GridBagConstraints();
		gbc_tfSize.insets = new Insets(0, 0, 0, 5);
		gbc_tfSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSize.gridx = 2;
		gbc_tfSize.gridy = 1;
		panelFieldData.add(tfSize, gbc_tfSize);
		
		separator = new JSeparator();
		panel.add(separator);
		
		btnSave = new JButton("Salvar");
		panel.add(btnSave);
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSave.addActionListener(new ActionListener() {
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
		panel.add(btnSave);
		
		panel.setSize(new Dimension(620,150));
	}
	
	private boolean validateFields() {
		Table table = currentTable;
		TableField field = currentCommand.getField();

		if(field.getName() == null || "".contentEquals(field.getName())) {
			JOptionPane.showMessageDialog(this, "H� campos da tabela com nome n�o informado.");
			return false;
		}
		
		if(field.getType() == null) {
			JOptionPane.showMessageDialog(this, "H� campos da tabela com tipo n�o informado.");
			return false;
		}
		
		for(TableField f : table.getFields()) {
			if(f.getName().equals(field.getName())) {
				JOptionPane.showMessageDialog(this, "O nome escolhido j� existe na tabela destino.");
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
