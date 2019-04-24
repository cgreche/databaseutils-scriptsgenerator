package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
				
				if(editMode) {
					result.getTable().setName(tableName);
				}
				else {
					Table table = new Table();
					table.setName(tableName);
					result = new CreateTableCommand(null,table);
				}
				
				result.getTable().setFields(tableFields.getData());
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
	
	public void insertNew() {
		editMode = false;
		this.setVisible(true);
	}
	
	public void edit(CreateTableCommand command) {
		editMode = true;
		
		if(command != null) {
			result = command;
			Table table = command.getTable();
			List<TableField> fields = table.getFields();
			tableFields.setData(fields);
		}
		
		this.setVisible(true);
	}
	
	public CreateTableCommand getResult() {
		return result;
	}
}
