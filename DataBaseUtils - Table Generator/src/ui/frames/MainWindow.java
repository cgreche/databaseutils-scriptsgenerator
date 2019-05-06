package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		public JMenuItem itemProjectClose;
	public JMenuItem itemAuthor;
	
	public NewProjectDialog newProjectDialog;
	
	static CreateTableCommandDialog createTableCommandDialog;
	static AddFieldCommandDialog addFieldCommandDialog;
	static MofidyFieldCommandDialog modifyFieldCommandDialog;
	static DropFieldCommandDialog dropFieldCommandDialog;
	
	ProjectPanel panelProject;
	
	public MainWindow() {
		super("eSocial Techne Database Utils - Scripts generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		menuProject = new JMenu();
			itemProjectNew = new JMenuItem();
			itemProjectOpen = new JMenuItem();
			itemProjectSave = new JMenuItem();
			itemProjectSaveAs = new JMenuItem();
			itemProjectClose = new JMenuItem();
			
		itemAuthor = new JMenuItem();
		
		newProjectDialog = new NewProjectDialog(this);
		
		menuProject.setText("Projeto");
		menuProject.add(itemProjectNew);
			itemProjectNew.setText("Novo");
			itemProjectNew.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					newProjectDialog.setVisible(true);
					Project project = newProjectDialog.getResultData();
					if(project != null) {
						loadProject(project);
					}
				}
			});
			
		menuProject.add(itemProjectOpen);
			itemProjectOpen.setText("Abrir");
		menuProject.add(itemProjectSave);
			itemProjectSave.setText("Salvar");
			itemProjectSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Project project = projectHandler.getProject();
					if(project == null)
						return;
					
					if(projectHandler.getSavePath() == null) {
						final JFileChooser fc = new JFileChooser();
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
			
		menuProject.add(itemProjectSaveAs);
			itemProjectSaveAs.setText("Salvar como...");
		menuProject.add(itemProjectClose);
			itemProjectClose.setText("Fechar");
			
		itemAuthor.setText("Author");
		
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
