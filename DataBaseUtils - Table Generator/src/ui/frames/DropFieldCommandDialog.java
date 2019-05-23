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
import java.awt.Component;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.Box;
import java.awt.Dimension;

//16-04-2019

@SuppressWarnings("serial")
public class DropFieldCommandDialog extends Dialog<DropFieldCommand> {
	private JLabel lblField;
	private JComboBox<TableField> ddField;
	private JButton btnSave;
	
	//
	boolean editMode;
	private DropFieldCommand currentCommand;

	private Script parentScript;
	private Table currentTable;
	private JPanel panelActions;
	private JPanel panel;
	private JSeparator separator;
	private Component rigidArea;
	private Component rigidArea_1;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	
	public DropFieldCommandDialog(JFrame parent) {
		super(parent);
		setTitle("Comando DropField");
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!editMode) {
				}
			}
		});
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		lblField = new JLabel("Campo");
		lblField.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblField);
		ddField = new JComboBox<TableField>();
		ddField.setMaximumSize(new Dimension(32767, 20));
		panel.add(ddField);
		ddField.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setPreferredSize(new Dimension(5, 5));
		rigidArea.setMinimumSize(new Dimension(5, 5));
		rigidArea.setMaximumSize(new Dimension(5, 5));
		panel.add(rigidArea);
		
		separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 2));
		panel.add(separator);
		
		rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_1.setPreferredSize(new Dimension(5, 5));
		rigidArea_1.setMinimumSize(new Dimension(5, 5));
		rigidArea_1.setMaximumSize(new Dimension(5, 5));
		panel.add(rigidArea_1);
		
		panelActions = new JPanel();
		panel.add(panelActions);
		panelActions.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelActions.setLayout(new BoxLayout(panelActions, BoxLayout.X_AXIS));
		
		horizontalGlue = Box.createHorizontalGlue();
		panelActions.add(horizontalGlue);
		
		btnSave = new JButton("Salvar");
		panelActions.add(btnSave);
		
		horizontalGlue_1 = Box.createHorizontalGlue();
		panelActions.add(horizontalGlue_1);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentCommand.setField((TableField)ddField.getSelectedItem());
				DropFieldCommandDialog.this.setResult(true,currentCommand);
				DropFieldCommandDialog.this.dispose();
			}
		});
		
		this.setContentPane(panel);
		this.setSize(new Dimension(350, 134));
	}
	
	public void updateControls() {
		ddField.removeAllItems();
		List<TableField> fields = currentTable.getFields();
		for(TableField field : fields) {
			ddField.addItem(field);
		}
	}
	
	public void insertNew(Script parentScript) {
		currentTable = parentScript.getResultingTable();
		currentCommand = new DropFieldCommand(parentScript, currentTable, null);

		updateControls();
		editMode = false;
		this.setVisible(true);
	}
	
	public void edit(DropFieldCommand command) {
		if(command != null) {
			currentCommand = command.clone();
			currentTable = command.getRefTable();
		}
		
		updateControls();
		editMode = true;
		this.setVisible(true);
	}
	
}
