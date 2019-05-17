package structs;

import java.util.ArrayList;
import java.util.List;

public class Project implements Cloneable {
	
	public static final int PROFILE_ORACLE_DB = 1;
	public static final int PROFILE_MYSQL = 2;
	
	public static final long ERROR_NONE = 0;
	public static final long ERROR_UNNAMED_SCRIPT = 1;
	
	private String name;
	private List<Script> scripts;
	private String scriptsGenerationBasePath;
	private String defaultScriptsHeaderMessage;
	private long generationProfiles;
	
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
	
	public Table getTable(String tableName) {
		for(Table table : tableList) {
			if(table.getName().contentEquals(tableName)) {
				return table;
			}
		}
		return null;
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

	public String getDefaultScriptsHeaderMessage() {
		return defaultScriptsHeaderMessage;
	}

	public void setDefaultScriptsHeaderMessage(String defaultScriptsHeaderMessage) {
		this.defaultScriptsHeaderMessage = defaultScriptsHeaderMessage;
	}
	
	public long validate() {
		long errors = 0;
		if(scripts != null) {
			for(Script script : scripts) {
				if(script.getName() == null || "".equals(script.getName())) {
					errors |= ERROR_UNNAMED_SCRIPT;
				}
			}
		}
		return errors;
	}
	
	public void setGenerationProfiles(long generationProfiles) {
		this.generationProfiles = generationProfiles;
	}
	
	public long getGenerationProfiles() {
		return generationProfiles;
	}
	
	@Override
	public Project clone() {
		try {
			return (Project)super.clone();
		} catch(Exception e) {
			return null;
		}
	}
}
