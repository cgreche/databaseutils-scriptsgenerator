package ui.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
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

import structs.Constraints;
import structs.FieldType;
import structs.ModifyFieldCommand;
import structs.Script;
import structs.Table;
import structs.TableField;
import ui.components.fieldtypecombobox.FieldTypeComboBox;

//16-04-2019

@SuppressWarnings("serial")
public class MofidyFieldCommandDialog extends Dialog<ModifyFieldCommand> {
	
	private JLabel lblField;
	private JComboBox<TableField> ddField;
	
	private JLabel lblType;
	private JLabel lblPk;
	private JLabel lblFk;
	private JLabel lblNotNull;
	private JLabel lblReferencedTable;
	private JLabel lblReferencedColumn;
	
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JSeparator separator;
	private JLabel lblSize;
	private FieldTypeComboBox ddType;
	private JTextField tfSize;
	private JCheckBox cbPk;
	private JCheckBox cbFk;
	private JCheckBox cbNotNull;
	private JTextField tfReferencedTable;
	private JTextField tfReferencedField;
	private Component rigidArea;
	private Component rigidArea_1;
	private Component rigidArea_2;
	private JPanel panel_3;
	private JSeparator separator_1;
	private Component rigidArea_3;
	
	private JButton btnSave;
	
	private Script parentScript;
	private Table currentTable;
	private ModifyFieldCommand currentCommand;
	private TableField currentNewField;

	boolean editMode;
	
	public MofidyFieldCommandDialog(JFrame parent) {
		super(parent);
		setTitle("Comando AddField");
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		lblSize = new JLabel("Tamanho");
		tfSize = new JTextField();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel_1 = new JPanel();
		panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		lblField = new JLabel("Campo");
		lblField.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblField);
		
		ddField = new JComboBox<TableField>();
		ddField.setAlignmentX(Component.LEFT_ALIGNMENT);
		ddField.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateControlsWithOldFieldInfo();
			}
		});
		panel_1.add(ddField);
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setMinimumSize(new Dimension(5, 5));
		rigidArea.setMaximumSize(new Dimension(5, 5));
		rigidArea.setPreferredSize(new Dimension(5, 5));
		panel.add(rigidArea);
		
		separator_1 = new JSeparator();
		panel.add(separator_1);
		
		rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_3.setPreferredSize(new Dimension(5, 5));
		rigidArea_3.setMinimumSize(new Dimension(5, 5));
		rigidArea_3.setMaximumSize(new Dimension(5, 5));
		panel.add(rigidArea_3);
		
		panel_2 = new JPanel();
		panel_2.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{20, 44, 12, 12, 37, 96, 97, 0};
		gbl_panel_2.rowHeights = new int[]{14, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		
		lblType = new JLabel("Tipo");
		lblType.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblType.insets = new Insets(0, 0, 0, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 0;
		panel_2.add(lblType, gbc_lblType);
		
		lblSize = new JLabel("Tamanho");
		lblSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblSize = new GridBagConstraints();
		gbc_lblSize.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSize.insets = new Insets(0, 0, 0, 5);
		gbc_lblSize.gridx = 1;
		gbc_lblSize.gridy = 0;
		panel_2.add(lblSize, gbc_lblSize);
		
		lblPk = new JLabel("PK");
		lblPk.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblPk = new GridBagConstraints();
		gbc_lblPk.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPk.insets = new Insets(0, 0, 0, 5);
		gbc_lblPk.gridx = 2;
		gbc_lblPk.gridy = 0;
		panel_2.add(lblPk, gbc_lblPk);
		
		lblFk = new JLabel("FK");
		lblFk.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblFk = new GridBagConstraints();
		gbc_lblFk.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblFk.insets = new Insets(0, 0, 0, 5);
		gbc_lblFk.gridx = 3;
		gbc_lblFk.gridy = 0;
		panel_2.add(lblFk, gbc_lblFk);
		
		lblNotNull = new JLabel("Not Null");
		lblNotNull.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblNotNull = new GridBagConstraints();
		gbc_lblNotNull.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNotNull.insets = new Insets(0, 0, 0, 5);
		gbc_lblNotNull.gridx = 4;
		gbc_lblNotNull.gridy = 0;
		panel_2.add(lblNotNull, gbc_lblNotNull);
		
		lblReferencedTable = new JLabel("Tabela referenciada");
		lblReferencedTable.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblReferencedTable = new GridBagConstraints();
		gbc_lblReferencedTable.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblReferencedTable.insets = new Insets(0, 0, 0, 5);
		gbc_lblReferencedTable.gridx = 5;
		gbc_lblReferencedTable.gridy = 0;
		panel_2.add(lblReferencedTable, gbc_lblReferencedTable);
		
		lblReferencedColumn = new JLabel("Campo referenciado");
		lblReferencedColumn.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblReferencedColumn = new GridBagConstraints();
		gbc_lblReferencedColumn.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblReferencedColumn.gridx = 6;
		gbc_lblReferencedColumn.gridy = 0;
		panel_2.add(lblReferencedColumn, gbc_lblReferencedColumn);
		
		ddType = new FieldTypeComboBox();
		ddType.setMinimumSize(new Dimension(100, 20));
		ddType.setAlignmentX(0.0f);
		GridBagConstraints gbc_ddType = new GridBagConstraints();
		gbc_ddType.anchor = GridBagConstraints.LINE_START;
		gbc_ddType.weightx = 1.0;
		gbc_ddType.insets = new Insets(0, 0, 0, 5);
		gbc_ddType.fill = GridBagConstraints.HORIZONTAL;
		gbc_ddType.gridx = 0;
		gbc_ddType.gridy = 1;
		panel_2.add(ddType, gbc_ddType);
		
		tfSize = new JTextField();
		GridBagConstraints gbc_tfSize = new GridBagConstraints();
		gbc_tfSize.insets = new Insets(0, 0, 0, 5);
		gbc_tfSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSize.gridx = 1;
		gbc_tfSize.gridy = 1;
		panel_2.add(tfSize, gbc_tfSize);
		tfSize.setColumns(10);
		
		cbPk = new JCheckBox();
		GridBagConstraints gbc_cbPk = new GridBagConstraints();
		gbc_cbPk.anchor = GridBagConstraints.LINE_START;
		gbc_cbPk.insets = new Insets(0, 0, 0, 5);
		gbc_cbPk.gridx = 2;
		gbc_cbPk.gridy = 1;
		panel_2.add(cbPk, gbc_cbPk);
		
		cbFk = new JCheckBox();
		GridBagConstraints gbc_cbFk = new GridBagConstraints();
		gbc_cbFk.anchor = GridBagConstraints.LINE_START;
		gbc_cbFk.insets = new Insets(0, 0, 0, 5);
		gbc_cbFk.gridx = 3;
		gbc_cbFk.gridy = 1;
		panel_2.add(cbFk, gbc_cbFk);
		
		cbNotNull = new JCheckBox();
		GridBagConstraints gbc_cbNotNull = new GridBagConstraints();
		gbc_cbNotNull.anchor = GridBagConstraints.LINE_START;
		gbc_cbNotNull.insets = new Insets(0, 0, 0, 5);
		gbc_cbNotNull.gridx = 4;
		gbc_cbNotNull.gridy = 1;
		panel_2.add(cbNotNull, gbc_cbNotNull);
		
		tfReferencedTable = new JTextField();
		tfReferencedTable.setAlignmentX(0.0f);
		GridBagConstraints gbc_tfReferencedTable = new GridBagConstraints();
		gbc_tfReferencedTable.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfReferencedTable.insets = new Insets(0, 0, 0, 5);
		gbc_tfReferencedTable.gridx = 5;
		gbc_tfReferencedTable.gridy = 1;
		panel_2.add(tfReferencedTable, gbc_tfReferencedTable);
		
		tfReferencedField = new JTextField();
		tfReferencedField.setAlignmentX(0.0f);
		GridBagConstraints gbc_tfReferencedField = new GridBagConstraints();
		gbc_tfReferencedField.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfReferencedField.gridx = 6;
		gbc_tfReferencedField.gridy = 1;
		panel_2.add(tfReferencedField, gbc_tfReferencedField);
		
		rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_1.setPreferredSize(new Dimension(5, 5));
		rigidArea_1.setMinimumSize(new Dimension(5, 5));
		rigidArea_1.setMaximumSize(new Dimension(5, 5));
		panel.add(rigidArea_1);
		
		separator = new JSeparator();
		panel.add(separator);
		
		rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_2.setPreferredSize(new Dimension(5, 5));
		rigidArea_2.setMinimumSize(new Dimension(5, 5));
		rigidArea_2.setMaximumSize(new Dimension(5, 5));
		panel.add(rigidArea_2);
		
		panel_3 = new JPanel();
		panel_3.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(panel_3);
		
		btnSave = new JButton("Salvar");
		panel_3.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogControlsToObject(currentCommand);
				if(validateModifyFieldCommand(currentCommand)) {
					MofidyFieldCommandDialog.this.setResult(true, currentCommand);
					MofidyFieldCommandDialog.this.dispose();
				}
			}
		});
		
		this.setContentPane(panel);
		this.setSize(new Dimension(620, 180));
	}
		
	public void updateControls() {
		ddField.removeAllItems();
		List<TableField> fields = currentTable.getFields();
		for(TableField field : fields)
			ddField.addItem(field);
		ddField.setSelectedItem(currentCommand.getOldField());
		
		//
		
		ddType.setSelectedItem(currentCommand.getOldField());
		tfSize.setText(currentNewField.getSize());
		int constraints = currentNewField.getConstraints();
		cbPk.setSelected((constraints & Constraints.PK) != 0);
		cbFk.setSelected((constraints & Constraints.FK) != 0);
		cbNotNull.setSelected((constraints & Constraints.NOT_NULL) != 0);
		tfReferencedTable.setText(currentNewField.getReferencedTable());
		tfReferencedField.setText(currentNewField.getReferencedColumn());
	}
	
	private void updateControlsWithOldFieldInfo() {
		TableField field = (TableField)ddField.getSelectedItem();
		if(field != null) {
			ddType.setSelectedItem(field.getType());
			tfSize.setText(field.getSize());
			cbPk.setSelected(field.isPK());
			cbFk.setSelected(field.isFK());
			cbNotNull.setSelected(field.isNotNull());
			tfReferencedTable.setText(field.getReferencedTable());
			tfReferencedField.setText(field.getReferencedColumn());
		}
		else {
			ddType.setSelectedItem(null);
			tfSize.setText(null);
			cbPk.setSelected(false);
			cbFk.setSelected(false);
			cbNotNull.setSelected(false);
			tfReferencedTable.setText(null);
			tfReferencedField.setText(null);
		}
	}
	
	private void dialogControlsToObject(ModifyFieldCommand command) {
		TableField oldField = (TableField)ddField.getSelectedItem();
		TableField newField = command.getNewField();
		newField.setName(oldField != null ? oldField.getName() : null);
		newField.setType((FieldType)ddType.getSelectedItem());
		newField.setSize(tfSize.getText().trim());
		int constraints = 0;
		if(cbPk.isSelected())
			constraints |= Constraints.PK;
		if(cbFk.isSelected())
			constraints |= Constraints.FK;
		if(cbNotNull.isSelected())
			constraints |= Constraints.NOT_NULL;
		newField.setConstraints(constraints);
		newField.setReferencedTable(tfReferencedTable.getText());
		newField.setReferencedColumn(tfReferencedField.getText());
		command.setOldField(oldField);
	}
	
	private boolean validateModifyFieldCommand(ModifyFieldCommand command) {
		TableField oldField = command.getOldField();
		TableField newField = command.getNewField();
		if(oldField == null) {
			JOptionPane.showMessageDialog(this, "O campo a ser modificado não foi informado.");
			return false;
		}
		
		if(newField.getType() == null) {
			JOptionPane.showMessageDialog(this, "O tipo não foi informado.");
			return false;
		}
		
		return true;
	}
	
	public void insertNew(Script parentScript) {
		Table resultingTable = parentScript.getResultingTable();
		
		this.parentScript = parentScript;
		currentTable = resultingTable;
		currentNewField = new TableField();
		currentCommand = new ModifyFieldCommand(parentScript,resultingTable, null, currentNewField);
		
		editMode = false;
		updateControls();
		this.setVisible(true);
	}
	
	public void edit(ModifyFieldCommand command) {
		parentScript = command.getScript();
		currentCommand = command.clone();
		currentNewField = command.getNewField().clone();
		currentTable = command.getRefTable();

		editMode = true;
		updateControls();
		this.setVisible(true);
	}
	
}
