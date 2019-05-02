package structs;
import java.io.File;
import java.io.FileWriter;
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
	
	private Project project;
	private String currentSavePath;
	
	public ProjectHandler(Project project) {
		this.project = project;
	}
	
	public void save() {
		if(currentSavePath == null)
			return;
		
		File file = new File(currentSavePath);
		Path path = Paths.get(file.getPath());
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.append("");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void load() {
		
	}
	
	public void generateScripts() {
		String basePath = project.getScriptsGenerationBasePath();
		
		List<Script> scriptList = project.getScripts();
		for(Script script : scriptList) {
			script.setBasePath(basePath + "/OracleDB");
			script.setHeaderMessage("test");
			Generator generator = new OracleGenerator();
			generator.generate(script);
			
			script.setBasePath(basePath + "/MySQL");
			generator = new MySQLGenerator();
			generator.generate(script);
		}
	}
	
	public void setSavePath(String savePath) {
		currentSavePath = savePath;
	}
	
	public String getSavePath() {
		return this.currentSavePath;
	}
	
	public Project getProject() {
		return project;
	}
}
