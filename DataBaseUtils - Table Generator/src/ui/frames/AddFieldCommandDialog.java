package ui.frames;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

import structs.AlterTableCommand;
import structs.Table;
import ui.frames.addcolumn.PanelAddColumn;

//16-04-2019
@SuppressWarnings("serial")
public class AddFieldCommandDialog extends JDialog {
	
	public static PanelAddColumn panelAddField;
	public static AlterTableCommand input;
	public static AlterTableCommand output;
	
	private boolean editMode;
	private Table parentTable;
	private AlterTableCommand result;

	public AddFieldCommandDialog(JFrame parent) {
		super(parent, true);
		
		panelAddField = new PanelAddColumn();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!editMode) {
					result = new AlterTableCommand(null, parentTable);
				}
			}
		});
		this.add(panelAddField);	
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
			parentTable = command.getTable();
			panelAddField.update(command);
		}
		
		this.setVisible(true);
	}
	
	public AlterTableCommand getResult() {
		return result;
	}
}
