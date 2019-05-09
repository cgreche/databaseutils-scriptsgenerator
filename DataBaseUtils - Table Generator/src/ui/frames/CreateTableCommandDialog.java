package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
				
				setResult(true,currentCommand);
				CreateTableCommandDialog.this.dispose();
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
