package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import structs.CreateTableCommand;
import structs.Script;
import structs.Table;
import structs.TableField;
import ui.frames.createtable.TableCreateTableFields;

//16-04-2019

@SuppressWarnings("serial")
public class CreateTableCommandDialog extends Dialog<CreateTableCommand> {
	
	private boolean editMode;
	
	
	public JButton buttonNew;
	public JPanel panelMain;
	public JLabel lblTableName;
	public JTextField tfTableName;
	public TableCreateTableFields tableFields;
	public JButton buttonSave;
	
	//
	private Script parentScript;
	private CreateTableCommand currentCommand;
	private Table currentTable;
	private List<TableField> currentFields;
	
	public CreateTableCommandDialog(JFrame parent) {
		super(parent);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				//
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				setResult(false,null);
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
		});

		lblTableName = new JLabel("Nome da tabela");
		tfTableName = new JTextField();

		buttonNew = new JButton("Novo campo");
		buttonNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableFields.addEmptyRow();
			}
		});
		
		tableFields = new TableCreateTableFields();
		
		buttonSave = new JButton("Salvar");
		buttonSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String tableName = tfTableName.getText();
				
				//currentCommand references currentTable
				currentTable.setName(tableName);

				if(validateFields()) {
					setResult(true,currentCommand);
					CreateTableCommandDialog.this.dispose();
				}
			}
			
		});
		
		panelMain = new JPanel();
		panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));
		panelMain.add(lblTableName);
		panelMain.add(tfTableName);
		panelMain.add(buttonNew);
		panelMain.add(new JScrollPane(tableFields));
		panelMain.add(buttonSave);
		this.add(panelMain);
		this.pack();
	}
	
	private boolean validateFields() {
		if(currentTable.getName() == null || "".contentEquals(currentTable.getName())) {
			JOptionPane.showMessageDialog(this, "Nome da tabela não informado.");
			return false;
		}
		
		Set<String> fieldNames = new HashSet<String>();
		List<TableField> fields = currentTable.getFields();
		for(TableField field : fields) {
			if(field.getName() == null || "".contentEquals(field.getName())) {
				JOptionPane.showMessageDialog(this, "Há campos da tabela com nome não informado.");
				return false;
			}
			
			if(field.getType() == null) {
				JOptionPane.showMessageDialog(this, "Há campos da tabela com tipo não informado.");
				return false;
			}
			
			if(fieldNames.contains(field.getName())) {
				JOptionPane.showMessageDialog(this, "Não pode haver campos com nome repetidos na tabela.");
				return false;
			}
			
			fieldNames.add(field.getName());
		}
		
		return true;
	}
	
	private void updateControls() {
		tfTableName.setText(currentTable.getName());
		tableFields.setData(currentFields);
	}
	
	public void insertNew(Script parentScript) {
		this.parentScript = parentScript;
		currentTable = new Table();
		//Usar o nome do script como nome padrão da tabela
		currentTable.setName(parentScript.getName());
		currentFields = currentTable.getFields();
		currentCommand = new CreateTableCommand(parentScript,currentTable);
		updateControls();

		editMode = false;
		this.setVisible(true);
	}
	
	public void edit(CreateTableCommand command) {
		if(command != null) {
			parentScript = command.getScript();
			currentCommand = (CreateTableCommand)command.clone();
			currentTable = currentCommand.getTable();
			currentFields = currentCommand.getTable().getFields();
		}
		
		updateControls();
		
		editMode = true;
		this.setVisible(true);
	}
	
	public boolean isEditMode() {
		return editMode;
	}
	
}
