package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import structs.Constraints;
import structs.FieldType;
import structs.GenericTypes;
import structs.ModifyFieldCommand;
import structs.Script;
import structs.Table;
import structs.TableField;

//16-04-2019

@SuppressWarnings("serial")
public class MofidyFieldCommandDialog extends Dialog<ModifyFieldCommand> {
	
	private JLabel lblField;
	private JComboBox<TableField> ddField;
	
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
	private JTextField tfRetferencedColumn;

	private JButton buttonSave;
	
	boolean editMode;
	
	private Script parentScript;
	private Table currentTable;
	private ModifyFieldCommand currentCommand;
	private TableField currentNewField;
	
	public MofidyFieldCommandDialog(JFrame parent) {
		super(parent);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			}
		});
		
		
		lblField = new JLabel("Campo");
		ddField = new JComboBox<TableField>();
		ddField.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				TableField item = (TableField)ddField.getSelectedItem();
				ddType.setSelectedItem(item.getType());
				tfSize.setText(item.getSize());
				cbPk.setSelected(item.isPK());
				cbFk.setSelected(item.isFK());
				cbNotNull.setSelected(item.isNotNull());
				tfReferencedTable.setText(item.getReferencedTable());
				tfRetferencedColumn.setText(item.getReferencedColumn());
			}
		});
		
		
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
		
		lblFk = new JLabel("FK");
		cbFk = new JCheckBox();
		
		lblNotNull = new JLabel("Not Null");
		cbNotNull = new JCheckBox();
		
		lblReferencedTable = new JLabel("Tabela referenciada");
		tfReferencedTable = new JTextField();
		
		lblReferencedColumn = new JLabel("Campo referenciado");
		tfRetferencedColumn = new JTextField();
			
		buttonSave = new JButton("Salvar");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TableField oldField = (TableField)ddField.getSelectedItem();
				
				currentNewField.setType((FieldType)ddType.getSelectedItem());
				currentNewField.setSize(tfSize.getText());
				int constraints = 0;
				if(cbPk.isSelected())
					constraints |= Constraints.PK;
				if(cbFk.isSelected())
					constraints |= Constraints.FK;
				if(cbNotNull.isSelected())
					constraints |= Constraints.NOT_NULL;
				currentNewField.setConstraints(constraints);
				currentNewField.setReferencedTable(tfReferencedTable.getText());
				currentNewField.setReferencedColumn(tfRetferencedColumn.getText());
				
				currentCommand.setOldField(oldField);
				currentCommand.setNewField(currentNewField);
				MofidyFieldCommandDialog.this.setResult(true, currentCommand);
				MofidyFieldCommandDialog.this.dispose();
			}
		});
		
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		add(lblName);
		add(tfName);
		add(lblField);
		add(ddField);
		add(lblType);
		add(ddType);
		add(lblPk);
		add(cbPk);
		add(lblFk);
		add(cbFk);
		add(lblNotNull);
		add(cbNotNull);
		add(lblReferencedTable);
		add(tfReferencedTable);
		add(lblReferencedColumn);
		add(tfRetferencedColumn);
		add(buttonSave);
		
		this.pack();
	}
		
	public void updateControls() {
		ddField.removeAllItems();
		List<TableField> fields = currentTable.getFields();
		for(TableField field : fields)
			ddField.addItem(field);
	}
	
	public void insertNew(Script parentScript) {
		Table resultingTable = parentScript.getResultTable();
		
		this.parentScript = parentScript;
		currentTable = resultingTable;
		currentCommand = new ModifyFieldCommand(parentScript,resultingTable, null, null);
		currentNewField = new TableField();
		
		editMode = false;
		updateControls();
		this.setVisible(true);
	}
	
	public void edit(ModifyFieldCommand command) {
		parentScript = command.getScript();
		currentCommand = command.clone();
		currentTable = command.getRefTable();

		editMode = true;
		updateControls();
		this.setVisible(true);
	}
	
}
