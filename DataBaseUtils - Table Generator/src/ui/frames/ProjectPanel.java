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
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import structs.AlterTableCommand;
import structs.Command;
import structs.CreateTableCommand;
import structs.Project;
import structs.ProjectHandler;
import structs.Script;
import ui.ScriptListRenderer;
import ui.resultingtable.TableFieldTable;

@SuppressWarnings("serial")
public class ProjectPanel extends JPanel {
	
	JList<Command> listCommands;
	
	JPanel panelScripts;
	JPanel panelScriptNameField;
		JLabel lblScriptName;
		JTextField tfScriptName;
	JButton btnNewScript;
	JList<Script> listScripts;
	
	static JPanel panelCommands;
		JButton btnCreateTableCommand;
		JButton btnAddFieldCommand;
		JButton btnModifyFieldCommand;
		JButton btnDropFieldCommand;
	
	static JPanel panelHeader;
	static JPanel panelData;
	static JPanel panelResult;
		JCheckBox cbShowRemovedFields;
	static JPanel panelFooter;
	
	JLabel lblProjectTitle = new JLabel();
	static TableFieldTable tableResultingTable;
	
	Project project;
	List<Script> scriptList;
	Script currentSelectedScript = null;

	private void updateCommandList() {
		((DefaultListModel<Command>) listCommands.getModel()).clear();
		if(currentSelectedScript == null)
			return;
		List<Command> commands = currentSelectedScript.getCommands();
		if(commands == null)
			return;
		for(Command command : commands) {
			((DefaultListModel<Command>) listCommands.getModel()).addElement(command);
		}
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
		
		
		lblScriptName = new JLabel("Nome ", JLabel.LEFT);
		lblScriptName.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tfScriptName = new JTextField();
		tfScriptName.setMaximumSize(new Dimension(tfScriptName.getMaximumSize().width,tfScriptName.getPreferredSize().height));
		btnNewScript = new JButton("Novo script");
		btnNewScript.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Script script = new Script();
				script.setObjectName(tfScriptName.getText());
				scriptList.add(script);
				
				((DefaultListModel<Script>)listScripts.getModel()).addElement(script);
			}
		});
		
		
		DefaultListModel<Script> scriptsListModel = new DefaultListModel<>();
		listScripts = new JList<>(scriptsListModel);
		//listScripts.setSize(new Dimension(500, 70));
		listScripts.setCellRenderer(new ScriptListRenderer());
		listScripts.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(!arg0.getValueIsAdjusting()) {
					currentSelectedScript = listScripts.getSelectedValue();
					updateCommandList();
					updateColumnTable();
				}
			}
		});
		
		panelScripts = new JPanel();
		panelScripts.setLayout(new BoxLayout(panelScripts, BoxLayout.Y_AXIS));
		panelScripts.add(lblScriptName);
		panelScripts.add(tfScriptName);
		panelScripts.add(btnNewScript);
		panelScripts.add(listScripts);
		
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
						((DefaultListModel<Command>)listCommands.getModel()).addElement(command);
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
					AlterTableCommand command = MainWindow.addFieldCommandDialog.getResultData();
					currentSelectedScript.addCommand(command);
					
					((DefaultListModel<Command>)listCommands.getModel()).addElement(command);
				}
			}
		});
		
		btnModifyFieldCommand = new JButton("Modify field");
		btnModifyFieldCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.editFieldCommandDialog.insertNew(currentSelectedScript);
				AlterTableCommand command = MainWindow.editFieldCommandDialog.getResultData();
				if(command != null) {
					currentSelectedScript.addCommand(command);
					((DefaultListModel<Command>)listCommands.getModel()).addElement(command);
				}
			}
		});
		
		btnDropFieldCommand = new JButton("Remove field");
		btnDropFieldCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.dropFieldCommandDialog.insertNew(currentSelectedScript);
				AlterTableCommand command = MainWindow.dropFieldCommandDialog.getResultData();
				if(command != null) {
					currentSelectedScript.addCommand(command);
					((DefaultListModel<Command>)listCommands.getModel()).addElement(command);
				}
			}
		});
		
		DefaultListModel<Command> listModelCommands = new DefaultListModel<>();
		listCommands = new JList<>(listModelCommands);
		//listCommands.setSize(new Dimension(500, 70));
		listCommands.setCellRenderer(new DefaultListCellRenderer());
		listCommands.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(!arg0.getValueIsAdjusting()) {
				}
			}
		});
		
		listCommands.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<Command> list = (JList<Command>)evt.getSource();
				if (evt.getClickCount() == 2) {
					// Double-click detected
					int index = list.locationToIndex(evt.getPoint());
					Command command = list.getModel().getElementAt(index);
					boolean result = false;
					if(command instanceof CreateTableCommand) {
						MainWindow.createTableCommandDialog.edit((CreateTableCommand)command);
						if(MainWindow.createTableCommandDialog.getResult()) {
							CreateTableCommand resultCommand = MainWindow.createTableCommandDialog.getResultData();
							currentSelectedScript.getCommands().set(index, resultCommand);
						}
					}
					else if(command instanceof AlterTableCommand) {
						AlterTableCommand c = (AlterTableCommand)command;
						AlterTableCommand.SubType subType = c.getSubType();
						if(subType == AlterTableCommand.SubType.ADD_FIELD) {
							MainWindow.addFieldCommandDialog.edit(c);
							result = MainWindow.addFieldCommandDialog.getResult();
						} else if(subType == AlterTableCommand.SubType.MODIFY_FIELD) {
							MainWindow.editFieldCommandDialog.edit(c);
							result = MainWindow.editFieldCommandDialog.getResult();
						} else if(subType == AlterTableCommand.SubType.DROP_FIELD) {
							MainWindow.dropFieldCommandDialog.edit(c);
							result = MainWindow.dropFieldCommandDialog.getResult();
						}
					}
					
					if(result) {
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
		panelCommands.add(listCommands);
		
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
		
		panelData = new JPanel();
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.X_AXIS));
		panelData.add(panelScripts);
		panelData.add(panelCommands);
		
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
	
	private void clearControls() {
		lblProjectTitle.setText(null);
		((DefaultListModel<Script>)listScripts.getModel()).clear();
		((DefaultListModel<Command>)listCommands.getModel()).clear();
	}
	
	private void updateControls() {
		lblProjectTitle.setText(MainWindow.projectHandler.getProject().getName());
		List<Script> scripts = MainWindow.projectHandler.getProject().getScripts();
		for(Script script : scripts) {
			((DefaultListModel<Script>)listScripts.getModel()).addElement(script);
		}
	}
	
	public void setProject(Project project) {
		this.project = project;
		this.scriptList = project.getScripts();
	}
	
	@Override
	public void setVisible(boolean visible) {
		clearControls();
		
		if(visible) {
			updateControls();
		}
		
		super.setVisible(visible);
	}
}
