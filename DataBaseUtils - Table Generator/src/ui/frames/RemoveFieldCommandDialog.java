package ui.frames;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import structs.AlterTableCommand;
import structs.Script;
import structs.Table;
import structs.TableField;
import ui.frames.dropcolumn.PanelDropColumn;

//16-04-2019

@SuppressWarnings("serial")
public class RemoveFieldCommandDialog extends JDialog {
	
	public static PanelDropColumn panelRemoveField;
	
	private Table parentTable;
	private boolean editMode;
	private AlterTableCommand result;

	public RemoveFieldCommandDialog(JFrame parent) {
		super(parent, true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!editMode) {
					result = new AlterTableCommand(null, parentTable);
					result.dropColumn((TableField)panelRemoveField.fieldList.getSelectedItem());
				}
			}
		});
		
		panelRemoveField = new PanelDropColumn();
		this.add(panelRemoveField);
		this.pack();
	}
	
	public void insertNew(Table parentTable) {
		editMode = false;
		this.parentTable = parentTable;
		panelRemoveField.update(parentTable);
		this.setVisible(true);
	}
	
	public void edit(AlterTableCommand command) {
		editMode = true;
		if(command != null) {
			result = command;
			parentTable = command.getTable();
			panelRemoveField.update(parentTable);
		}
		
		this.setVisible(true);
	}
	
	public AlterTableCommand getResult() {
		return result;
	}
}
