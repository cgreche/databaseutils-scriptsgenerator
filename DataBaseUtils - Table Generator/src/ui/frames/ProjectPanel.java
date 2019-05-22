package ui.frames;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
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

/**
 * Painel de construção/edição do projeto
 * @author cesar.reche
 *
 */
public class ProjectPanel extends JPanel {
	private JLabel lblProjectName;
	private TableScripts tableScripts;
	private TableCommands tableCommands;
	private TableFieldTable tableResultingTable;
	
	private Font defaultTitleFont;

	private Project project;
	private List<Script> scriptList;
	private Script currentSelectedScript = null;

	private ActionListener actionSetShowRemovedFields = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox cbShowRemoved = (JCheckBox) e.getSource();
			tableResultingTable.showRemoved(cbShowRemoved.isSelected());
		}
	};
	
	private ActionListener actionNewScript = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Script script = new Script();
			scriptList.add(script);
			//
			tableScripts.updateUI();
		}
	};
	
	private ActionListener actionCreateTableCommand = new ActionListener() {
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
	};
	
	private ActionListener actionAddFieldCommand = new ActionListener() {
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
	};
	
	private ActionListener actionModifyFieldCommand = new ActionListener() {
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
	};
	
	private ActionListener actionDropFieldCommand = new ActionListener() {
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
	};
	
	private ActionListener actionGenerateScripts = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Project project = MainWindow.projectHandler.getProject();
			
			if(project.getScriptsGenerationBasePath() == null) {
				JOptionPane.showMessageDialog(ProjectPanel.this, "O caminho para geração de scripts não foi definido.\nAbra as propriedades do projeto e defina um caminho.");
				return;
			}
			
			ProjectHandler handler = new ProjectHandler(project);
			long errors = handler.validate();
			if(errors == 0) {
				handler.generateScripts();
			}
			else {
				String message = "Os scripts não puderam ser gerados:\n";
				if((errors & Project.ERROR_UNNAMED_SCRIPT) != 0) {
					message += "-Há scripts sem nome.";
				}
				
				JOptionPane.showMessageDialog(null,message);
			}
		}
	};
	
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
		
		defaultTitleFont = new Font("Tahoma", Font.BOLD, 11);
		
		setAlignmentY(Component.TOP_ALIGNMENT);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panelHeader = new JPanel();
		panelHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(panelHeader);
		
		lblProjectName = new JLabel("PROJECT NAME");
		lblProjectName.setFont(defaultTitleFont);
		panelHeader.add(lblProjectName);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(5, 5));
		add(rigidArea_2);
		
		JSeparator separator_1 = new JSeparator();
		add(separator_1);
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(5, 5));
		add(rigidArea_3);
		
		JPanel panelData = new JPanel();
		add(panelData);
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.Y_AXIS));
		
		JPanel panelScripts = new JPanel();
		panelScripts.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelData.add(panelScripts);
		GridBagLayout gbl_panelScripts = new GridBagLayout();
		gbl_panelScripts.columnWidths = new int[]{0, 0, 0};
		gbl_panelScripts.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelScripts.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelScripts.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelScripts.setLayout(gbl_panelScripts);
		
		JLabel lblCommandsTitle = new JLabel("Comandos");
		lblCommandsTitle.setFont(defaultTitleFont);
		GridBagConstraints gbc_lblCommandsTitle = new GridBagConstraints();
		gbc_lblCommandsTitle.anchor = GridBagConstraints.LINE_START;
		gbc_lblCommandsTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblCommandsTitle.gridx = 1;
		gbc_lblCommandsTitle.gridy = 0;
		panelScripts.add(lblCommandsTitle, gbc_lblCommandsTitle);
		
		JPanel panelScriptActions = new JPanel();
		GridBagConstraints gbc_panelScriptActions = new GridBagConstraints();
		gbc_panelScriptActions.insets = new Insets(0, 0, 5, 5);
		gbc_panelScriptActions.anchor = GridBagConstraints.LINE_START;
		gbc_panelScriptActions.weightx = 1.0;
		gbc_panelScriptActions.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelScriptActions.gridx = 0;
		gbc_panelScriptActions.gridy = 1;
		panelScripts.add(panelScriptActions, gbc_panelScriptActions);
		panelScriptActions.setLayout(new BoxLayout(panelScriptActions, BoxLayout.X_AXIS));
		
		JButton btnNewScript = new JButton("Novo script");
		btnNewScript.addActionListener(actionNewScript);
		panelScriptActions.add(btnNewScript);
		
		JPanel panelCommandActions = new JPanel();
		GridBagConstraints gbc_panelCommandActions = new GridBagConstraints();
		gbc_panelCommandActions.insets = new Insets(0, 0, 5, 0);
		gbc_panelCommandActions.anchor = GridBagConstraints.LINE_START;
		gbc_panelCommandActions.weightx = 1.0;
		gbc_panelCommandActions.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelCommandActions.gridx = 1;
		gbc_panelCommandActions.gridy = 1;
		panelScripts.add(panelCommandActions, gbc_panelCommandActions);
		panelCommandActions.setLayout(new BoxLayout(panelCommandActions, BoxLayout.X_AXIS));
		
		JButton buttonCreateTableCommand = new JButton("Create table");
		buttonCreateTableCommand.addActionListener(actionCreateTableCommand);
		panelCommandActions.add(buttonCreateTableCommand);
		
		JButton buttonAddFieldCommand = new JButton("Add field");
		buttonAddFieldCommand.addActionListener(actionAddFieldCommand);
		panelCommandActions.add(buttonAddFieldCommand);
		
		JButton buttonModifyFieldCommand = new JButton("Modify field");
		buttonAddFieldCommand.addActionListener(actionModifyFieldCommand);
		panelCommandActions.add(buttonModifyFieldCommand);
		
		JButton buttonDropFieldCommand = new JButton("Drop field");
		buttonDropFieldCommand.addActionListener(actionDropFieldCommand);
		panelCommandActions.add(buttonDropFieldCommand);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.weightx = 1.0;
		gbc_scrollPane_1.weighty = 1.0;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 2;
		panelScripts.add(scrollPane_1, gbc_scrollPane_1);
		
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
		tableScripts.setDeleteAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tableScripts.getSelectedRow();
				System.out.println("" + index);
				if(index >= 0 && index < scriptList.size()) {
					scriptList.remove(index);
					tableScripts.refresh();
				}
			}
		});
		scrollPane_1.setViewportView(tableScripts);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.weighty = 1.0;
		gbc_scrollPane_2.weightx = 1.0;
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 2;
		panelScripts.add(scrollPane_2, gbc_scrollPane_2);
		
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
					boolean validCommand;
					do {
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
						
						validCommand = true;
						result = dialog.getResult();
						if(result) {
							Command resultCommand = (Command)dialog.getResultData();
							currentSelectedScript.getCommands().set(index, resultCommand);
							validCommand = currentSelectedScript.validate();
							if(validCommand) {
								tableCommands.updateUI();
								updateColumnTable();
							}
							else {
								JOptionPane.showMessageDialog(ProjectPanel.this, "O comando não pode ser modificado, pois há comandos posteriores dependentes do mesmo.\nVerifique o conteúdo do comando atual ou altere os comandos dependentes para execução deste procedimento.");
							}
							//update UI

						}
					} while(!validCommand);
				}
			}
		});
		scrollPane_2.setViewportView(tableCommands);
		
		JLabel lblScriptsTitle = new JLabel("Scripts");
		lblScriptsTitle.setFont(defaultTitleFont);
		GridBagConstraints gbc_lblScriptsTitle = new GridBagConstraints();
		gbc_lblScriptsTitle.anchor = GridBagConstraints.LINE_START;
		gbc_lblScriptsTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblScriptsTitle.gridx = 0;
		gbc_lblScriptsTitle.gridy = 0;
		panelScripts.add(lblScriptsTitle, gbc_lblScriptsTitle);
		
		Component rigidArea = Box.createRigidArea(new Dimension(5, 5));
		panelData.add(rigidArea);
		
		JSeparator separator = new JSeparator();
		panelData.add(separator);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(5, 5));
		panelData.add(rigidArea_1);
		
		JPanel panelResultingTable = new JPanel();
		panelResultingTable.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelData.add(panelResultingTable);
		panelResultingTable.setLayout(new BoxLayout(panelResultingTable, BoxLayout.Y_AXIS));
		
		JPanel panelResultingTableTitle = new JPanel();
		panelResultingTableTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelResultingTable.add(panelResultingTableTitle);
		panelResultingTableTitle.setLayout(new BoxLayout(panelResultingTableTitle, BoxLayout.X_AXIS));
		
		JLabel lblResultingTableTitle = new JLabel("Tabela resultante");
		panelResultingTableTitle.add(lblResultingTableTitle);
		lblResultingTableTitle.setFont(defaultTitleFont);
		lblResultingTableTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		panelResultingTableTitle.add(horizontalGlue_1);
		
		JCheckBox cbShowRemovedFields = new JCheckBox("Mostrar fields removidos");
		cbShowRemovedFields.addActionListener(actionSetShowRemovedFields);
		panelResultingTableTitle.add(cbShowRemovedFields);
		
		JScrollPane scrollPane = new JScrollPane();
		panelResultingTable.add(scrollPane);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		tableResultingTable = new TableFieldTable();
		scrollPane.setViewportView(tableResultingTable);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(5, 5));
		add(rigidArea_4);
		
		JSeparator separator_2 = new JSeparator();
		add(separator_2);
		
		Component rigidArea_5 = Box.createRigidArea(new Dimension(5, 5));
		add(rigidArea_5);
		
		JPanel panelFooter = new JPanel();
		panelFooter.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(panelFooter);
		panelFooter.setLayout(new BoxLayout(panelFooter, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		panelFooter.add(horizontalGlue);
		
		JButton btnGenerateScripts = new JButton("Gerar scripts");
		btnGenerateScripts.addActionListener(actionGenerateScripts);
		panelFooter.add(btnGenerateScripts);
		
		Component horizontalGlue_2 = Box.createHorizontalGlue();
		panelFooter.add(horizontalGlue_2);
	}

	private void updateControls() {
		lblProjectName.setText(MainWindow.projectHandler.getProject().getName());
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
