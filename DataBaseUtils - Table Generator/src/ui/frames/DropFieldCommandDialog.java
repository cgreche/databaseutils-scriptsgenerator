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

import structs.AddFieldCommand;
import structs.DropFieldCommand;
import structs.Script;
import structs.Table;
import structs.TableField;

//16-04-2019

@SuppressWarnings("serial")
public class DropFieldCommandDialog extends Dialog<DropFieldCommand> {
	private JComboBox<TableField> fieldList;
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
		
		
		fieldList = new JComboBox<TableField>();
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		buttonSave = new JButton("Salvar");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentCommand.setField((TableField)fieldList.getSelectedItem());
				DropFieldCommandDialog.this.setResult(true,currentCommand);
				DropFieldCommandDialog.this.dispose();
			}
		});
		add(fieldList);
		add(buttonSave);
		this.pack();
	}
	
	public void updateControls() {
		fieldList.removeAllItems();
		List<TableField> fields = currentTable.getFields();
		for(TableField field : fields) {
			fieldList.addItem(field);
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
