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

	public AddFieldCommandDialog(JFrame parent) {
		super(parent, true);
		
		panelAddField = new PanelAddColumn();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(input != null)
					output = input;
			}
		});
		this.add(panelAddField);	
		this.pack();
	}
	
	public void open(AlterTableCommand command) {
		if(command != null) {
			input = command;
			Table table = command.getTable();
			panelAddField.update(command);
		}
		
		this.setVisible(true);
	}
}
