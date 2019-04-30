package ui.frames;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import structs.AlterTableCommand;
import structs.Script;
import structs.Table;
import structs.TableField;

//16-04-2019

@SuppressWarnings("serial")
public class DropFieldCommandDialog extends Dialog<AlterTableCommand> {
	
	boolean editMode;
	
	public static class PanelDropColumn extends JPanel {
		
		public JComboBox<TableField> fieldList;
		
		public PanelDropColumn() {
			fieldList = new JComboBox<TableField>();
			add(fieldList);
		}
		
		public void update(Table table) {
			fieldList.removeAllItems();
			List<TableField> fields = table.getFields();
			for(TableField field : fields) {
				fieldList.addItem(field);
			}
		}
		
	}
	
	private PanelDropColumn panelDropField;
	
	private Table parentTable;

	public DropFieldCommandDialog(JFrame parent) {
		super(parent);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!editMode) {
				}
			}
		});
		
		panelDropField = new PanelDropColumn();
		this.add(panelDropField);
		this.pack();
	}
	
	public void insertNew(Script parentScript) {
		
		panelDropField.update(parentScript.getResultTable());
		
		editMode = false;
		this.setVisible(true);
	}
	
	public void edit(AlterTableCommand command) {
		if(command != null) {
			parentTable = command.getScript().getResultTable();
			panelDropField.update(parentTable);
		}
		
		editMode = true;
		this.setVisible(true);
	}
	
}
