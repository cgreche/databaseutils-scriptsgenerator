package ui.frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import structs.Project;
import structs.ProjectHandler;
import ui.StatusBar;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public static ProjectHandler projectHandler;
	
	public JMenuBar menuBar;
	public StatusBar statusBar;
	
	public JMenu menuProject;
		public JMenuItem itemProjectNew;
		public JMenuItem itemProjectOpen;
		public JMenuItem itemProjectSave;
		public JMenuItem itemProjectSaveAs;
		public JMenuItem itemProperties;
		public JMenuItem itemProjectClose;
	public JMenuItem itemAuthor;
	
	public ProjectSettingsDialog projectSettingsDialog;
	
	public static CreateTableCommandDialog createTableCommandDialog;
	public static AddFieldCommandDialog addFieldCommandDialog;
	public static MofidyFieldCommandDialog modifyFieldCommandDialog;
	public static DropFieldCommandDialog dropFieldCommandDialog;
	
	private ProjectPanel panelProject;
	
	private ActionListener actionOpenProject = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Project Files", "dbup");
			fc.setFileFilter(fileFilter);
			fc.setAcceptAllFileFilterUsed(true);
			int returnVal = fc.showOpenDialog(MainWindow.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				projectHandler = ProjectHandler.loadProject(file.getAbsolutePath());
				panelProject.setProject(projectHandler.getProject());
				panelProject.setVisible(true);
				viewProject(projectHandler.getProject());
			} else {
				return;
			}
			
		}
	};
	
	private ActionListener actionNewProject = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ev) {
			projectSettingsDialog.insertNew();
			if(projectSettingsDialog.getResult()) {
				Project project = projectSettingsDialog.getResultData();
				if(project != null) {
					projectHandler = new ProjectHandler(project);
					viewProject(project);
				}
			}
		}
	};
	
	private ActionListener actionSaveProject = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(projectHandler == null)
				return;
			
			if(projectHandler.getSavePath() == null) {
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Project Files", "dbup");
				fc.setFileFilter(fileFilter);
				fc.setAcceptAllFileFilterUsed(true);
				int returnVal = fc.showSaveDialog(MainWindow.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					projectHandler.setSavePath(file.getPath());
				} else {
					return;
				}
			}
			
			if(projectHandler.save()) {
				setStatuText("Projeto salvo com sucesso.");
			}
		}
	};
	
	private ActionListener actionSaveProjectAs = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(projectHandler == null)
				return;
			
			final JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Project Files", "dbup");
			fc.setFileFilter(fileFilter);
			fc.setAcceptAllFileFilterUsed(true);
			int returnVal = fc.showSaveDialog(MainWindow.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				projectHandler.save(file.getPath());
			} else {
				return;
			}
		}
	};
	
	private ActionListener actionProjectProperties = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(projectHandler == null)
				return;
			
			Project project = projectHandler.getProject();
			if(project == null) {
				JOptionPane.showMessageDialog(MainWindow.this, "N�o h� projeto aberto");
				return;
			}
			
			projectSettingsDialog.edit(project);
			if(projectSettingsDialog.getResult()) {
				Project editedProject = projectSettingsDialog.getResultData();
				projectHandler.setProject(editedProject);
				projectHandler.notifyProjectChanged();
				viewProject(editedProject);
			}
		}
	};
	
	private ActionListener actionExit = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(projectHandler != null) {
				if(projectHandler.getProjectState() != ProjectHandler.ProjectState.MODIFIED_SAVED) {
					String message = "As �ltimas altera��es do projeto n�o foram salvas.\nDeseja salvar o projeto antes de sair?";
					int result = JOptionPane.showConfirmDialog(MainWindow.this, message, null, JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION){
						final JFileChooser fc = new JFileChooser();
						FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Project Files", "dbup");
						fc.setFileFilter(fileFilter);
						fc.setAcceptAllFileFilterUsed(true);
						int returnVal = fc.showSaveDialog(MainWindow.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							projectHandler.save(file.getPath());
						} else {
							return;
						}
					}
				}
			}
			
			System.exit(0);
		}
	};
	
	public MainWindow() {
		super("eSocial Techne Database Utils - Scripts generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		menuProject = new JMenu("Projeto");
			itemProjectNew = new JMenuItem("Novo");
			itemProjectNew.addActionListener(actionNewProject);
			
			itemProjectOpen = new JMenuItem("Abrir");
			itemProjectOpen.addActionListener(actionOpenProject);
			
			itemProjectSave = new JMenuItem("Salvar");
			itemProjectSave.addActionListener(actionSaveProject);
			
			itemProjectSaveAs = new JMenuItem("Salvar como...");
			itemProjectSaveAs.addActionListener(actionSaveProjectAs);
			
			itemProperties = new JMenuItem("Propriedades");
			itemProperties.addActionListener(actionProjectProperties);
			
			itemProjectClose = new JMenuItem("Sair");
			itemProjectClose.addActionListener(actionExit);
			
		itemAuthor = new JMenuItem("Autor");
		
		projectSettingsDialog = new ProjectSettingsDialog(this);
		menuProject.add(itemProjectNew);
		menuProject.add(itemProjectOpen);
		menuProject.add(itemProjectSave);
		menuProject.add(itemProjectSaveAs);
		menuProject.add(new JSeparator());
		menuProject.add(itemProperties);
		menuProject.add(new JSeparator());
		menuProject.add(itemProjectClose);
			
		menuBar.add(menuProject);
		menuBar.add(itemAuthor);
		setJMenuBar(menuBar);
		
		statusBar = new StatusBar();
		//
		createTableCommandDialog = new CreateTableCommandDialog(this);
		addFieldCommandDialog = new AddFieldCommandDialog(this);
		modifyFieldCommandDialog = new MofidyFieldCommandDialog(this);
		dropFieldCommandDialog = new DropFieldCommandDialog(this);
		
		panelProject = new ProjectPanel();
		this.add(panelProject,BorderLayout.CENTER);
		this.add(statusBar,BorderLayout.PAGE_END);
		this.setSize(1024, 768);
		
		panelProject.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelProject.setVisible(false);
	}
	
	public void viewProject(Project project) {
		panelProject.setProject(project);
		panelProject.setVisible(true);
		setStatuText("Projeto carregado." + " N�mero de scripts: " + project.getScripts().size());
	}
	
	public void setStatuText(String text) {
		statusBar.setText(text);
	}
}
