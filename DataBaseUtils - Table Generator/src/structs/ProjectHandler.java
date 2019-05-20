package structs;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import generators.Generator;
import generators.MySQLGenerator;
import generators.OracleGenerator;

//26/04/2019
public class ProjectHandler {
	
	public static enum ProjectState {
		NEW,
		LOADED_UNMODIFIED,
		LOADED_MODIFIED,
		MODIFIED_SAVED
	}
	
	private Project project;
	private ProjectState projectState;
	private String currentSavePath;
	
	public ProjectHandler(Project project) {
		this.project = project;
		projectState = ProjectState.NEW;
	}
	
	public boolean save(String savePath) {
		File file = new File(savePath);
		Path path = Paths.get(file.getParent());
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ProjectSerializer serializer = new ProjectXMLSerializer();
		byte [] fileData = serializer.serialize(project);
		try(FileOutputStream fout = new FileOutputStream(file)) {
			fout.write(fileData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public boolean save() {
		if(currentSavePath == null)
			return false;
		if(projectState == ProjectState.LOADED_MODIFIED)
			setProjectState(ProjectState.MODIFIED_SAVED);
		return save(currentSavePath);
	}
	
	public static ProjectHandler loadProject(String path) {
		if(path == null)
			return null;
		
		byte[] fileData;
		try {
			fileData = Files.readAllBytes(Paths.get(path));
			ProjectSerializer serializer = new ProjectXMLSerializer();
			Project project = serializer.deserialize(fileData);
			ProjectHandler projectHandler = new ProjectHandler(project);
			projectHandler.setProjectState(ProjectState.LOADED_UNMODIFIED);
			projectHandler.setSavePath(path);
			return projectHandler;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void notifyProjectChanged() {
		if(projectState != ProjectState.NEW)
			projectState = ProjectState.LOADED_MODIFIED;
	}
	
	public void generateScripts() {
		String basePath = project.getScriptsGenerationBasePath();
		
		long genProfiles = project.getGenerationProfiles();
		Generator generator;
		if((genProfiles & Project.PROFILE_ORACLE_DB) != 0) {
			generator = new OracleGenerator(project);
			generator.generate(basePath + "/OracleDB");
		}
		
		if((genProfiles & Project.PROFILE_MYSQL) != 0) {
			generator = new MySQLGenerator(project);
			generator.generate(basePath + "/MySQL");

		}
		
	}
	
	public void setSavePath(String savePath) {
		currentSavePath = savePath;
	}
	
	public String getSavePath() {
		return this.currentSavePath;
	}
	
	public void setProjectState(ProjectState state) {
		this.projectState = state;
	}
	
	public ProjectState getProjectState() {
		return projectState;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public Project getProject() {
		return project;
	}
	
	public long validate() {
		return project.validate();
	}
	
}
