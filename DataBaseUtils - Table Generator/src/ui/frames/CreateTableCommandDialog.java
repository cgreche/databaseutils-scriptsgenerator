package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import structs.CreateTableCommand;
import structs.Table;
import structs.TableField;
import ui.frames.createtable.TableCreateTableFields;

//16-04-2019

@SuppressWarnings("serial")
public class CreateTableCommandDialog extends JDialog {
	
	public JButton buttonNew;
	public JPanel panelMain;
	public JLabel lblTableName;
	public JTextField tfTableName;
	public TableCreateTableFields tableFields;
	public JButton buttonSave;
	
	public CreateTableCommand result;
	
	boolean editMode;
	
	private CreateTableCommand currentCommand;
	private Table currentTable;
	private List<TableField> currentFields;

	public CreateTableCommandDialog(JFrame parent) {
		super(parent, true);
		
		editMode = false;
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				//
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				String tableName = tfTableName.getText();
				
				//currentCommand references currentTable
				currentTable.setName(tableName);
				result = currentCommand;
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
		
		buttonSave = new JButton("Salvar tabela");
		
		panelMain = new JPanel();
		panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));
		panelMain.add(lblTableName);
		panelMain.add(tfTableName);
		panelMain.add(buttonNew);
		panelMain.add(new JScrollPane(tableFields));
		this.add(panelMain);
		this.pack();
	}
	
	private void updateControls() {
		tfTableName.setText(currentTable.getName());
		tableFields.setData(currentFields);
	}
	
	public void insertNew() {
		editMode = false;
		this.setVisible(true);
	}
	
	public void edit(CreateTableCommand command) {
		editMode = true;
		
		if(command != null) {
			currentCommand = command;
			currentTable = command.getTable().clone();
			currentFields = new ArrayList<>(command.getTable().getFields());
		}
		else {
			currentTable = new Table();
			currentFields = currentTable.getFields();
			currentCommand = new CreateTableCommand(null,currentTable);
		}
		
		updateControls();
		this.setVisible(true);
	}
	
	public CreateTableCommand getResult() {
		return result;
	}
}
