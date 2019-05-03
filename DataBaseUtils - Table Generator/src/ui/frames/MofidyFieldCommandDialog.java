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
import javax.swing.JTextField;

import structs.AlterTableCommand;
import structs.Constraints;
import structs.FieldType;
import structs.GenericTypes;
import structs.Script;
import structs.Table;
import structs.TableField;

//16-04-2019

@SuppressWarnings("serial")
public class MofidyFieldCommandDialog extends Dialog<AlterTableCommand> {
	
	private JComboBox<TableField> ddField;
	private JComboBox<FieldType> ddFieldType;
	private JTextField tfSize;
	private JCheckBox cbPk;
	private JCheckBox cbFk;
	private JCheckBox cbNotNull;
	private JTextField tfReferencedTable;
	private JTextField tfRetferencedColumn;
	private JButton buttonAdd;
	
	boolean editMode;
	
	private Script parentScript;
	private AlterTableCommand currentCommand;
	private TableField currentNewField;
	
	public MofidyFieldCommandDialog(JFrame parent) {
		super(parent);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			}
		});
		
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
			
		buttonAdd = new JButton("Adicionar");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TableField oldField = (TableField)ddField.getSelectedItem();
				
				currentNewField.setType((FieldType)ddFieldType.getSelectedItem());
				currentNewField.setArgs(tfSize.getText());
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
				
				currentCommand.modifyColumn(oldField, currentNewField);
				MofidyFieldCommandDialog.this.setResult(true, currentCommand);
				MofidyFieldCommandDialog.this.dispose();
			}
		});
		
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		add(ddField);
		add(ddFieldType);
		add(cbPk);
		add(cbFk);
		add(cbNotNull);
		add(tfReferencedTable);
		add(tfRetferencedColumn);
		add(buttonAdd);
		
		this.pack();
	}
		
	public void update(Table table) {
		ddField.removeAllItems();
		List<TableField> fields = table.getFields();
		for(TableField field : fields)
			ddField.addItem(field);
	}
	
	public void insertNew(Script parentScript) {
		Table resultingTable = parentScript.getResultTable();
		
		this.parentScript = parentScript;
		currentCommand = new AlterTableCommand(parentScript,resultingTable);
		currentNewField = new TableField();
		
		this.update(resultingTable);
		
		editMode = false;
		this.setVisible(true);
	}
	
	public void edit(AlterTableCommand command) {
		editMode = true;
		this.setVisible(true);
	}
	
}
