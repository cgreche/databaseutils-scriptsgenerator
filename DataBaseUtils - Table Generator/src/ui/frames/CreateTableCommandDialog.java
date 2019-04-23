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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import structs.CreateTableCommand;
import structs.Table;
import structs.TableField;
import ui.frames.createtable.TableCreateTableFields;

//16-04-2019

@SuppressWarnings("serial")
public class CreateTableCommandDialog extends JDialog {
	
	public JButton buttonNew;
	public TableCreateTableFields tableFields;
	public JPanel panelMain;
	
	public CreateTableCommand result;

	public CreateTableCommandDialog(JFrame parent) {
		super(parent, true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				//
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				result.getTable().setFields(tableFields.getData());
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
		});
		
		buttonNew = new JButton();
		buttonNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableFields.addEmptyRow();
			}
		});
		tableFields = new TableCreateTableFields();
		panelMain = new JPanel();
		panelMain.setLayout(new BoxLayout(panelMain,BoxLayout.Y_AXIS));
		panelMain.add(buttonNew);
		panelMain.add(new JScrollPane(tableFields));
		this.add(panelMain);
		this.pack();
	}
	
	public void open(CreateTableCommand command) {
		if(command != null) {
			result = command;
			
			Table table = command.getTable();
			List<TableField> fields = table.getFields();
			tableFields.setData(fields);
		}
		
		result = new CreateTableCommand(null, new Table());
		this.setVisible(true);
	}
	
	public CreateTableCommand getResult() {
		return result;
	}
}
