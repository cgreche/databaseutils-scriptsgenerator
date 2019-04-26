package ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import structs.Project;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	public static Project currentProject;
	
	public JMenuBar menuBar;
	
	public JMenu menuNew;
		public JMenuItem itemNewProject;
	public JMenuItem itemAuthor;
	
	public NewProjectDialog newProjectDialog;
	
	static CreateTableCommandDialog createTableCommandDialog;
	static AddFieldCommandDialog addFieldCommandDialog;
	static MofidyFieldCommandDialog editFieldCommandDialog;
	static RemoveFieldCommandDialog removeFieldCommandDialog;
	
	JPanel panelProject;
	
	public void openProject(Project project) {
		currentProject = project;
		panelProject.setVisible(true);
	}
	
	public MainWindow() {
		super("eSocial Techne Database Utils - Scripts generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		menuNew = new JMenu();
			itemNewProject = new JMenuItem();
			
		itemAuthor = new JMenuItem();
		
		newProjectDialog = new NewProjectDialog(this);
		
		menuNew.setText("Novo");
		menuNew.add(itemNewProject);
			itemNewProject.setText("Novo projeto");
			itemNewProject.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					System.out.println("cliqued");
					newProjectDialog.setVisible(true);
					Project project = newProjectDialog.getResult();
					if(project != null) {
						currentProject = project;
						System.out.println(project.getName());
						panelProject.setVisible(true);
					}
				}
			});
		itemAuthor.setText("Author");
		
		menuBar.add(menuNew);
		menuBar.add(itemAuthor);
		setJMenuBar(menuBar);
		
		//
		createTableCommandDialog = new CreateTableCommandDialog(this);
		addFieldCommandDialog = new AddFieldCommandDialog(this);
		editFieldCommandDialog = new MofidyFieldCommandDialog(this);
		removeFieldCommandDialog = new RemoveFieldCommandDialog(this);
		
		panelProject = new ProjectPanel();
		this.add(panelProject);
		this.pack();
		
		panelProject.setVisible(false);

	}
}
