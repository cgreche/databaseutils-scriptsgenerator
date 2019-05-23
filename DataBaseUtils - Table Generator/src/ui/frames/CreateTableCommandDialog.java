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
import java.awt.Component;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JSeparator;

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
	private Component rigidArea;
	private JPanel panelActions;
	private JSeparator separator;
	private Component rigidArea_1;
	private Component rigidArea_2;
	
	public CreateTableCommandDialog(JFrame parent) {
		super(parent);
		setTitle("Comando CreateTable");
		
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
		lblTableName.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfTableName = new JTextField();
		tfTableName.setMaximumSize(new Dimension(2147483647, 20));
		tfTableName.setAlignmentX(Component.LEFT_ALIGNMENT);

		buttonNew = new JButton("Novo campo");
		buttonNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableFields.addEmptyRow();
			}
		});
		
		tableFields = new TableCreateTableFields();
		
		panelMain = new JPanel();
		panelMain.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));
		panelMain.add(lblTableName);
		panelMain.add(tfTableName);
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setPreferredSize(new Dimension(5, 5));
		rigidArea.setMaximumSize(new Dimension(5, 5));
		rigidArea.setMinimumSize(new Dimension(5, 5));
		panelMain.add(rigidArea);
		panelMain.add(buttonNew);
		JScrollPane scrollPane = new JScrollPane(tableFields);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelMain.add(scrollPane);
		
		rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_1.setPreferredSize(new Dimension(5, 5));
		rigidArea_1.setMinimumSize(new Dimension(5, 5));
		rigidArea_1.setMaximumSize(new Dimension(5, 5));
		panelMain.add(rigidArea_1);
		
		separator = new JSeparator();
		panelMain.add(separator);
		
		rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_2.setPreferredSize(new Dimension(5, 5));
		rigidArea_2.setMinimumSize(new Dimension(5, 5));
		rigidArea_2.setMaximumSize(new Dimension(5, 5));
		panelMain.add(rigidArea_2);
		
		panelActions = new JPanel();
		panelActions.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelMain.add(panelActions);
		
		buttonSave = new JButton("Salvar");
		panelActions.add(buttonSave);
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
		
		this.setContentPane(panelMain);
		this.setSize(new Dimension(800, 600));
	}
	
	private boolean validateFields() {
		if(currentTable.getName() == null || "".contentEquals(currentTable.getName())) {
			JOptionPane.showMessageDialog(this, "Nome da tabela n�o informado.");
			return false;
		}
		
		Set<String> fieldNames = new HashSet<String>();
		List<TableField> fields = currentTable.getFields();
		for(TableField field : fields) {
			if(field.getName() == null || "".contentEquals(field.getName())) {
				JOptionPane.showMessageDialog(this, "H� campos da tabela com nome n�o informado.");
				return false;
			}
			
			if(field.getType() == null) {
				JOptionPane.showMessageDialog(this, "H� campos da tabela com tipo n�o informado.");
				return false;
			}
			
			if(fieldNames.contains(field.getName())) {
				JOptionPane.showMessageDialog(this, "N�o pode haver campos com nome repetidos na tabela.");
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
		//Usar o nome do script como nome padr�o da tabela
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
