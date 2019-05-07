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
	
	private Project project;
	private String currentSavePath;
	
	private ProjectSerializer serializer;
	
	public ProjectHandler(Project project) {
		this.project = project;
		serializer = new ProjectXMLSerializer();
	}
	
	public boolean save() {
		if(currentSavePath == null)
			return false;
		
		File file = new File(currentSavePath);
		Path path = Paths.get(file.getParent());
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte [] fileData = serializer.serialize(project);
		try(FileOutputStream fout = new FileOutputStream(file)) {
			fout.write(fileData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean load() {
		if(currentSavePath == null)
			return false;
		
		byte[] fileData;
		try {
			fileData = Files.readAllBytes(Paths.get(currentSavePath));
			Project project = serializer.deserialize(fileData);
			this.project = project;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return this.project != null;
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
