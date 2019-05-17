package ui.frames;

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
import javax.swing.filechooser.FileNameExtensionFilter;

import structs.Project;
import structs.ProjectHandler;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public static ProjectHandler projectHandler;
	
	public JMenuBar menuBar;
	
	public JMenu menuProject;
		public JMenuItem itemProjectNew;
		public JMenuItem itemProjectOpen;
		public JMenuItem itemProjectSave;
		public JMenuItem itemProjectSaveAs;
		public JMenuItem itemProperties;
		public JMenuItem itemProjectClose;
	public JMenuItem itemAuthor;
	
	public ProjectSettingsDialog projectSettingsDialog;
	
	static CreateTableCommandDialog createTableCommandDialog;
	static AddFieldCommandDialog addFieldCommandDialog;
	static MofidyFieldCommandDialog modifyFieldCommandDialog;
	static DropFieldCommandDialog dropFieldCommandDialog;
	
	ProjectPanel panelProject;
	
	public MainWindow() {
		super("eSocial Techne Database Utils - Scripts generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		menuProject = new JMenu("Projeto");
			itemProjectNew = new JMenuItem("Novo");
			itemProjectNew.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					projectSettingsDialog.insertNew();
					if(projectSettingsDialog.getResult()) {
						Project project = projectSettingsDialog.getResultData();
						if(project != null) {
							loadProject(project);
							projectHandler.setProjectState(ProjectHandler.ProjectState.NEW); //TODO: melhorar
						}
					}
				}
			});
			
			itemProjectOpen = new JMenuItem("Abrir");
			itemProjectOpen.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					final JFileChooser fc = new JFileChooser();
					FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Project Files", "dbup");
					fc.setFileFilter(fileFilter);
					fc.setAcceptAllFileFilterUsed(true);
					int returnVal = fc.showOpenDialog(MainWindow.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						projectHandler = new ProjectHandler(null);
						projectHandler = ProjectHandler.loadProject(file.getAbsolutePath());
						panelProject.setProject(projectHandler.getProject());
						panelProject.setVisible(true);
					} else {
						return;
					}
					
				}
			});
			
			itemProjectSave = new JMenuItem("Salvar");
			itemProjectSave.addActionListener(new ActionListener() {
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
					
					projectHandler.save();
				}
			});
			
			itemProjectSaveAs = new JMenuItem("Salvar como...");
			itemProjectSaveAs.addActionListener(new ActionListener() {
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
			});
			
			itemProperties = new JMenuItem("Propriedades");
			itemProperties.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(projectHandler == null)
						return;
					
					Project project = projectHandler.getProject();
					if(project == null) {
						//TODO: mensagem de nenhum projeto aberto
						return;
					}
					
					projectSettingsDialog.edit(project);
					Project editedProject = projectSettingsDialog.getResultData();
					if(editedProject != null) {
						loadProject(editedProject);
					}
				}
			});
			
			itemProjectClose = new JMenuItem("Sair");
			itemProjectClose.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(projectHandler != null) {
						if(projectHandler.getProjectState() != ProjectHandler.ProjectState.MODIFIED_SAVED) {
							String message = "As últimas alterações do projeto não foram salvas.\nDeseja salvar o projeto antes de sair?";
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
			});
			
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
		
		//
		createTableCommandDialog = new CreateTableCommandDialog(this);
		addFieldCommandDialog = new AddFieldCommandDialog(this);
		modifyFieldCommandDialog = new MofidyFieldCommandDialog(this);
		dropFieldCommandDialog = new DropFieldCommandDialog(this);
		
		panelProject = new ProjectPanel();
		this.add(panelProject);
		this.pack();
		
		panelProject.setVisible(false);
	}
	
	
	public void loadProject(Project project) {
		projectHandler = new ProjectHandler(project);
		panelProject.setProject(project);
		panelProject.setVisible(true);
	}
}
