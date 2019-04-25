package ui.frames;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import structs.AlterTableCommand;
import structs.Table;
import ui.modifycolumn.PanelModifyField;

//16-04-2019

@SuppressWarnings("serial")
public class MofidyFieldCommandDialog extends JDialog {
	
	public static PanelModifyField panelEditField;
	
	private boolean editMode;
	private AlterTableCommand result;
	private Table parentTable;

	public MofidyFieldCommandDialog(JFrame parent) {
		super(parent, true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!editMode) {
					result = new AlterTableCommand(null,parentTable);
					result.addColumn(panelEditField.currentField);
				}
			}
		});
		
		panelEditField = new PanelModifyField();
		this.add(panelEditField);
		this.pack();
	}
	
	public void insertNew(Table parentTable) {
		editMode = false;
		this.parentTable = parentTable;
		this.setVisible(true);
	}
	
	public void edit(AlterTableCommand command) {
		editMode = true;
		if(command != null) {
			result = command;
			panelEditField.update(result.getTable());
		}
		
		this.setVisible(true);
	}
	
	public AlterTableCommand getResult() {
		return result;
	}
}
