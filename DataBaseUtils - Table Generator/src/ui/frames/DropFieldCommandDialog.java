package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import structs.DropFieldCommand;
import structs.Script;
import structs.Table;
import structs.TableField;

//16-04-2019

@SuppressWarnings("serial")
public class DropFieldCommandDialog extends Dialog<DropFieldCommand> {
	private JLabel lblField;
	private JComboBox<TableField> ddField;
	private JButton buttonSave;
	
	//
	boolean editMode;
	private DropFieldCommand currentCommand;

	private Script parentScript;
	private Table currentTable;
	
	public DropFieldCommandDialog(JFrame parent) {
		super(parent);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!editMode) {
				}
			}
		});
		
		lblField = new JLabel("Campo");
		ddField = new JComboBox<TableField>();
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		buttonSave = new JButton("Salvar");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentCommand.setField((TableField)ddField.getSelectedItem());
				DropFieldCommandDialog.this.setResult(true,currentCommand);
				DropFieldCommandDialog.this.dispose();
			}
		});
		
		add(lblField);
		add(ddField);
		add(buttonSave);
		this.pack();
	}
	
	public void updateControls() {
		ddField.removeAllItems();
		List<TableField> fields = currentTable.getFields();
		for(TableField field : fields) {
			ddField.addItem(field);
		}
	}
	
	public void insertNew(Script parentScript) {
		currentTable = parentScript.getResultTable();
		currentCommand = new DropFieldCommand(parentScript, currentTable, null);
		updateControls();
		
		editMode = false;
		this.setVisible(true);
	}
	
	public void edit(DropFieldCommand command) {
		if(command != null) {
			currentCommand = command.clone();
			currentTable = command.getScript().getResultTable();
		}
		
		updateControls();
		editMode = true;
		this.setVisible(true);
	}
	
}
