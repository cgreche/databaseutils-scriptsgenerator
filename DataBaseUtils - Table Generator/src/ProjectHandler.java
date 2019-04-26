import java.util.List;

import generators.Generator;
import generators.MySQLGenerator;
import generators.OracleGenerator;
import structs.Project;
import structs.Script;

public class ProjectHandler {
	
	private Project project;
	
	public ProjectHandler(Project project) {
		this.project = project;
	}
	
	public void save() {
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
}
