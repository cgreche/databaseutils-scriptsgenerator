package ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import structs.Project;
import structs.ProjectHandler;
import structs.ProjectHandler.ProjectState;
import ui.components.StatusBar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	public JMenuItem itemAbout;
	
	//
	public static ProjectSettingsDialog projectSettingsDialog;
	//
	public static CreateTableCommandDialog createTableCommandDialog;
	public static AddFieldCommandDialog addFieldCommandDialog;
	public static MofidyFieldCommandDialog modifyFieldCommandDialog;
	public static DropFieldCommandDialog dropFieldCommandDialog;
	//
	public static AboutDialog aboutDialog;
	
	private ProjectPanel panelProject;
	
	private ActionListener actionOpenProject = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String filePath = getFilePath(1);
			if(filePath != null) {
				projectHandler = ProjectHandler.loadProject(filePath);
				panelProject.setProject(projectHandler.getProject());
				panelProject.setVisible(true);
				viewProject(projectHandler.getProject());
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
				String filePath = getFilePath(0);
				if(filePath != null) {
					projectHandler.setSavePath(filePath);
				}
				else {
					return;
				}
			}
			
			if(projectHandler.save()) {
				updateStatusText();
			}
		}
	};
	
	private ActionListener actionSaveProjectAs = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(projectHandler == null)
				return;

			String filePath = getFilePath(0);
			if(filePath != null) {
				projectHandler.setSavePath(filePath);
				if(projectHandler.save()) {
					updateStatusText();
				}
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
				JOptionPane.showMessageDialog(MainWindow.this, "Não há projeto aberto");
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
				if(projectHandler.getProjectState() != ProjectHandler.ProjectState.SAVED) {
					String message = "As últimas alterações do projeto não foram salvas.\nDeseja salvar o projeto antes de sair?";
					int result = JOptionPane.showConfirmDialog(MainWindow.this, message, null, JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION){
						String filePath = getFilePath(0);
						if(filePath != null) {
							projectHandler.save(filePath);
						}
					}
				}
			}
			
			System.exit(0);
		}
	};
	
	public MainWindow() {
		super("eSocial Techne Database Utils - Scripts generator");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				actionExit.actionPerformed(null);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/resources/Icone eSocial_45x45.png")));
		setTitle(application.Properties.APPLICATION_NAME);

		menuBar = new JMenuBar();
		menuProject = new JMenu("Projeto");
			itemProjectNew = new JMenuItem("Novo");
			itemProjectNew.addActionListener(actionNewProject);
			
			itemProjectOpen = new JMenuItem("Abrir");
			itemProjectOpen.addActionListener(actionOpenProject);
			
			itemProjectSave = new JMenuItem("Salvar");
			itemProjectSave.setEnabled(false);
			itemProjectSave.addActionListener(actionSaveProject);
			
			itemProjectSaveAs = new JMenuItem("Salvar como...");
			itemProjectSaveAs.setEnabled(false);
			itemProjectSaveAs.addActionListener(actionSaveProjectAs);
			
			itemProperties = new JMenuItem("Propriedades");
			itemProperties.setEnabled(false);
			itemProperties.addActionListener(actionProjectProperties);
			
			itemProjectClose = new JMenuItem("Sair");
			itemProjectClose.addActionListener(actionExit);
			
		itemAbout = new JMenuItem("Sobre");
		itemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutDialog.setVisible(true);
			}
		});
		
		menuProject.add(itemProjectNew);
		menuProject.add(itemProjectOpen);
		menuProject.add(itemProjectSave);
		menuProject.add(itemProjectSaveAs);
		menuProject.add(new JSeparator());
		menuProject.add(itemProperties);
		menuProject.add(new JSeparator());
		menuProject.add(itemProjectClose);
			
		menuBar.add(menuProject);
		menuBar.add(itemAbout);
		setJMenuBar(menuBar);
		
		statusBar = new StatusBar();
		//
		
		//Creating the dialogs
		projectSettingsDialog = new ProjectSettingsDialog(this);
		createTableCommandDialog = new CreateTableCommandDialog(this);
		addFieldCommandDialog = new AddFieldCommandDialog(this);
		modifyFieldCommandDialog = new MofidyFieldCommandDialog(this);
		dropFieldCommandDialog = new DropFieldCommandDialog(this);
		aboutDialog = new AboutDialog(this);
		
		//
		panelProject = new ProjectPanel();
		getContentPane().add(panelProject,BorderLayout.CENTER);
		getContentPane().add(statusBar,BorderLayout.PAGE_END);
		this.setSize(1024, 768);
		
		panelProject.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelProject.setVisible(false);
	}
	
	public void viewProject(Project project) {
		itemProperties.setEnabled(project != null);
		itemProjectSave.setEnabled(project != null);
		itemProjectSaveAs.setEnabled(project != null);
		panelProject.setProject(project);
		panelProject.setVisible(true);
		setTitle(application.Properties.APPLICATION_NAME + (project != null ? " - " + project.getName() : ""));
		updateStatusText();
	}
	
	public void setStatuText(String text) {
		statusBar.setText(text);
	}
	
	public void updateStatusText() {
		if(projectHandler == null) {
			statusBar.setText("");
			return;
		}
		Project project = projectHandler.getProject();
		ProjectState projectState = projectHandler.getProjectState();
		String stateStr;
		if(projectState == ProjectState.NEW) {
			stateStr = "não salvo"; 
		}
		else if(projectState == ProjectState.LOADED_UNMODIFIED) {
			stateStr = "carregado";
		}
		else if(projectState == ProjectState.LOADED_MODIFIED) {
			stateStr = "modificado, não salvo";
		}
		else if(projectState == ProjectState.SAVED) {
			stateStr = "salvo";
		}
		else {
			statusBar.setText("");
			return;
		}
		
		setStatuText("Projeto " + stateStr + ". " + "Número de scripts: " + project.getScripts().size());
	}
	
	public void notifyProjectChanged() {
		if(projectHandler == null) {
			return;
		}
		projectHandler.notifyProjectChanged();
		updateStatusText();
	}
	
	public String getFilePath(int mode) {
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Project Files", "dbup");
		fc.setFileFilter(fileFilter);
		fc.setAcceptAllFileFilterUsed(true);
		int returnVal;
		if(mode == 0)
			returnVal = fc.showSaveDialog(MainWindow.this);
		else
			returnVal = fc.showOpenDialog(MainWindow.this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String filePath = file.getPath();
			if(mode == 0) {
				FileFilter ff = fc.getFileFilter();
				if(ff instanceof FileNameExtensionFilter && !file.getPath().toLowerCase().endsWith(((FileNameExtensionFilter) ff).getExtensions()[0])) {
					filePath += "." + ((FileNameExtensionFilter) ff).getExtensions()[0];
				}
			}
			return filePath;
		}
		
		return null;
	}
}
