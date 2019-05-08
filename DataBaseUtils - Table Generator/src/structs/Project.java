package structs;

import java.util.ArrayList;
import java.util.List;

public class Project {
	private String name;
	private List<Script> scripts;
	private String scriptsGenerationBasePath;
	
	private List<Table> tableList;
	
	public Project() {
		tableList = new ArrayList<>();
		scripts = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScripts(List<Script> scripts) {
		this.scripts = scripts;
	}
	
	public List<Table> getTables() {
		return tableList;
	}
	
	public void addTable(Table table) {
		tableList.add(table);
	}
	
	public List<Script> getScripts() {
		return scripts;
	}

	public void addScript(Script script) {
		scripts.add(script);
	}

	public String getScriptsGenerationBasePath() {
		return scriptsGenerationBasePath;
	}

	public void setScriptsGenerationBasePath(String scriptsGenerationBasePath) {
		this.scriptsGenerationBasePath = scriptsGenerationBasePath;
	}
	
}
