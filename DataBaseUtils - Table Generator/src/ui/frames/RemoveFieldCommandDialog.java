package ui.frames;

import javax.swing.JDialog;
import javax.swing.JFrame;

import structs.AlterTableCommand;
import structs.Table;
import ui.frames.dropcolumn.PanelDropColumn;

//16-04-2019

@SuppressWarnings("serial")
public class RemoveFieldCommandDialog extends JDialog {
	
	public static PanelDropColumn panelRemoveField;

	public RemoveFieldCommandDialog(JFrame parent) {
		super(parent, true);
		
		panelRemoveField = new PanelDropColumn();
		this.add(panelRemoveField);
		this.pack();
	}
	
	public void open(AlterTableCommand command) {
		if(command != null) {
			Table table = command.getTable();
			panelRemoveField.update(command);
		}
		
		this.setVisible(true);
	}
}
