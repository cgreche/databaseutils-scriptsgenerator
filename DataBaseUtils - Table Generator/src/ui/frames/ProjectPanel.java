package ui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.CellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import structs.AddFieldCommand;
import structs.Command;
import structs.CreateTableCommand;
import structs.DropFieldCommand;
import structs.ModifyFieldCommand;
import structs.Project;
import structs.ProjectHandler;
import structs.Script;
import ui.TableCommands;
import ui.TableScripts;
import ui.resultingtable.TableFieldTable;

@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {
	
	private JPanel panelScriptActions;
		private JButton btnNewScript;
		//
		private JButton btnCreateTableCommand;
		private JButton btnAddFieldCommand;
		private JButton btnModifyFieldCommand;
		private JButton btnDropFieldCommand;

	private JTextField tfScriptHeader;
	private JPanel panelScripts;
	private TableScripts tableScripts;
	private TableCommands tableCommands;
	
	private JPanel panelCommands;
	
	private JPanel panelHeader;
	private JPanel panelData;
	private JPanel panelResult;
		JCheckBox cbShowRemovedFields;
	private JPanel panelFooter;
	
	JLabel lblProjectTitle = new JLabel();
	private TableFieldTable tableResultingTable;
	
	Project project;
	List<Script> scriptList;
	Script currentSelectedScript = null;

	private void updateCommandList() {
		if(currentSelectedScript == null)
			return;
		List<Command> commands = currentSelectedScript.getCommands();
		if(commands == null)
			return;
		tableCommands.setData(commands);
	}
	
	private void updateColumnTable() {
		tableResultingTable.setData(currentSelectedScript);
	}
	

	
	public ProjectPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		cbShowRemovedFields = new JCheckBox("Show removed");
		cbShowRemovedFields.setAlignmentX(RIGHT_ALIGNMENT);
		cbShowRemovedFields.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JCheckBox cbShowRemoved = (JCheckBox) e.getSource();
		        tableResultingTable.showRemoved(cbShowRemoved.isSelected());
		    }
		});
		
		tableResultingTable = new TableFieldTable();
		tableResultingTable.setAlignmentX(RIGHT_ALIGNMENT);
		tableResultingTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tableResultingTable.setFillsViewportHeight(true);
		tableResultingTable.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}
			
			// this function successfully provides cell editing stop
			// on cell losts focus (but another cell doesn't gain focus)
			public void focusLost(FocusEvent e) {
				CellEditor cellEditor = tableResultingTable.getCellEditor();
				if (cellEditor != null) {
					if (cellEditor.getCellEditorValue() != null)
						cellEditor.stopCellEditing();
					else
						cellEditor.cancelCellEditing();
				}
				
				//currentSelectedTable.setFields(table.getData());
			}
		});
		
		tfScriptHeader = new JTextField();
		btnNewScript = new JButton("Novo script");
		btnNewScript.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Script script = new Script();
				scriptList.add(script);
				//
				tableScripts.updateUI();
			}
		});
		
		
		tableScripts = new TableScripts();
		tableScripts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					int selectionIndex = ((DefaultListSelectionModel)e.getSource()).getAnchorSelectionIndex();
					currentSelectedScript = selectionIndex >= 0 ? scriptList.get(selectionIndex) : null;
					updateCommandList();
					updateColumnTable();
				}
			}
		});
		
		//Commands Panel
		btnCreateTableCommand = new JButton("Create Table");
		btnCreateTableCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.createTableCommandDialog.insertNew(currentSelectedScript);
				if(MainWindow.createTableCommandDialog.getResult()) {
					CreateTableCommand command = MainWindow.createTableCommandDialog.getResultData();
					if(command != null) {
						currentSelectedScript.addCommand(command);
						
						//
						tableCommands.updateUI();
					}
				}
			}
		});
		
		btnAddFieldCommand = new JButton("Add field");
		btnAddFieldCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.addFieldCommandDialog.insertNew(currentSelectedScript);
				if(MainWindow.addFieldCommandDialog.getResult()) {
					AddFieldCommand command = MainWindow.addFieldCommandDialog.getResultData();
					currentSelectedScript.addCommand(command);
					
					//
					tableCommands.updateUI();
				}
			}
		});
		
		btnModifyFieldCommand = new JButton("Modify field");
		btnModifyFieldCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.modifyFieldCommandDialog.insertNew(currentSelectedScript);
				if(MainWindow.modifyFieldCommandDialog.getResult()) {
					ModifyFieldCommand command = MainWindow.modifyFieldCommandDialog.getResultData();
					if(command != null) {
						currentSelectedScript.addCommand(command);
						
						//
						tableCommands.updateUI();
					}
				}
			}
		});
		
		btnDropFieldCommand = new JButton("Remove field");
		btnDropFieldCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.dropFieldCommandDialog.insertNew(currentSelectedScript);
				if(MainWindow.dropFieldCommandDialog.getResult()) {
					DropFieldCommand command = MainWindow.dropFieldCommandDialog.getResultData();
					if(command != null) {
						currentSelectedScript.addCommand(command);
						
						//
						tableCommands.updateUI();
					}
				}
			}
		});
		
		tableCommands = new TableCommands();
		tableCommands.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent evt) {
				TableCommands table = (TableCommands)evt.getSource();
				if(evt.getClickCount() == 2) {
					// Double-click detected
					int index = table.rowAtPoint(evt.getPoint());
					Command command = table.getData().get(index);
					
					boolean result = false;
					Dialog<?> dialog = null;
					
					if(command instanceof CreateTableCommand) {
						MainWindow.createTableCommandDialog.edit((CreateTableCommand)command);
						dialog = MainWindow.createTableCommandDialog;
					}
					else if(command instanceof AddFieldCommand) {
						MainWindow.addFieldCommandDialog.edit((AddFieldCommand)command);
						dialog = MainWindow.addFieldCommandDialog;
					}
					else if(command instanceof ModifyFieldCommand) {
						MainWindow.modifyFieldCommandDialog.edit((ModifyFieldCommand)command);
						dialog = MainWindow.modifyFieldCommandDialog;
					}
					else if(command instanceof DropFieldCommand) {
						MainWindow.dropFieldCommandDialog.edit((DropFieldCommand)command);
						dialog = MainWindow.dropFieldCommandDialog;
					}
					else {
						return;
					}
					
					result = dialog.getResult();
					if(result) {
						Command resultCommand = (Command)dialog.getResultData();
						currentSelectedScript.getCommands().set(index, resultCommand);
						
						//update UI
						tableCommands.updateUI();
						updateColumnTable();
					}
				}
			}

		});

		panelCommands = new JPanel();
		panelCommands.setLayout(new BoxLayout(panelCommands, BoxLayout.Y_AXIS));
		panelCommands.add(btnCreateTableCommand);
		panelCommands.add(btnAddFieldCommand);
		panelCommands.add(btnModifyFieldCommand);
		panelCommands.add(btnDropFieldCommand);
		
		//
		JButton buttonGenerate = new JButton("Gerar scripts");
		buttonGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Project project = MainWindow.projectHandler.getProject();
				
				if(project.getScriptsGenerationBasePath() == null) {
					//todo(cesar.reche): error message
					return;
				}
				
				ProjectHandler handler = new ProjectHandler(project);
				handler.generateScripts();
			}
		});
		
		panelHeader = new JPanel();
		panelHeader.add(lblProjectTitle);
		
		panelScripts = new JPanel();
		panelScripts.setLayout(new BoxLayout(panelScripts, BoxLayout.X_AXIS));
		panelScripts.add(new JScrollPane(tableScripts));
		panelScripts.add(new JScrollPane(tableCommands));
		
		panelData = new JPanel();
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.Y_AXIS));
		panelData.add(panelScripts);
		
		panelResult = new JPanel();
		panelResult.setLayout(new BoxLayout(panelResult, BoxLayout.Y_AXIS));
		panelResult.add(cbShowRemovedFields);
		panelResult.add(new JScrollPane(tableResultingTable));
		
		panelFooter = new JPanel();
		panelFooter.add(buttonGenerate);
		
		add(panelHeader);
		add(panelData);
		add(panelResult);
		add(panelFooter);
	}
	
	private void updateControls() {
		lblProjectTitle.setText(MainWindow.projectHandler.getProject().getName());
		tableScripts.setData(MainWindow.projectHandler.getProject().getScripts());
		tableCommands.setData(currentSelectedScript != null ? currentSelectedScript.getCommands() : null);
	}
	
	public void setProject(Project project) {
		this.project = project;
		this.scriptList = project.getScripts();
	}
	
	@Override
	public void setVisible(boolean visible) {
		if(visible) {
			updateControls();
		}
		
		super.setVisible(visible);
	}
}
