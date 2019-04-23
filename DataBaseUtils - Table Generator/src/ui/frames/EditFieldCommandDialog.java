package ui.frames;

import javax.swing.JDialog;
import javax.swing.JFrame;

import structs.AlterTableCommand;
import structs.Table;
import ui.modifycolumn.PanelModifyColumn;

//16-04-2019

@SuppressWarnings("serial")
public class EditFieldCommandDialog extends JDialog {
	
	public static PanelModifyColumn panelEditField;

	public EditFieldCommandDialog(JFrame parent) {
		super(parent, true);
		
		panelEditField = new PanelModifyColumn();
		this.add(panelEditField);
		this.pack();
	}
	
	public void open(AlterTableCommand command) {
		if(command != null) {
			Table table = command.getTable();
			panelEditField.update(command);
		}
		
		this.setVisible(true);
	}
}
