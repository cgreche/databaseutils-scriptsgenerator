package generators;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structs.Constraints;
import structs.FieldType;
import structs.GenericTypes;
import structs.Table;
import structs.TableField;

//10/04/2019
public class MySQLGenerator extends Generator {
	
	private static final List<FieldType> MYSQL_TYPES;
	private static final Map<FieldType,FieldType> typeMapper;
	
	static {
		MYSQL_TYPES = new ArrayList<>();
		FieldType numeric = new FieldType("NUMERIC","NUMERIC",true,false);
		FieldType varchar = new FieldType("VARCHAR","VARCHAR",true,false);
		FieldType longtext  = new FieldType("LONGTEXT","LONGTEXT",false,false);
		
		MYSQL_TYPES.add(numeric);
		MYSQL_TYPES.add(varchar);
		MYSQL_TYPES.add(longtext);
		
		typeMapper = new HashMap<FieldType, FieldType>();
		typeMapper.put(GenericTypes.TEXT,varchar);
		typeMapper.put(GenericTypes.NUMERIC,numeric);
		typeMapper.put(GenericTypes.LONGTEXT,longtext);
	}
	
	private String generateField(TableField field) {
		FieldType mappedType = typeMapper.get(field.getType());
		if(mappedType == null)
			mappedType = field.getType();
		String result = field.getName() + " " + mappedType.getId();
		if(mappedType.acceptArgs() && field.getArgs() != null)
			result += "(" + field.getArgs() + ")";
		int constraints = field.getConstraints();
		if((constraints&Constraints.NOT_NULL) != 0) {
			result += " NOT NULL";
		}
		return result;
	}
	
	@Override
	public String generateCreateTable(Table table) {
		String result = "CREATE TABLE ";
		result += table.getName();
		result += "(\n";

		List<TableField> fields = table.getFields();
		if(fields != null) {
			List<TableField> primaryKey = new ArrayList<>();
			boolean isFirst = true;
			for(TableField field : fields) {
				if(field.isPK()) {
					primaryKey.add(field);
				}
				
				if(!isFirst) {
					result += ",\n";
				}
				
				result += "\t";
				result += generateField(field);
				isFirst = false;
			}
			if(!primaryKey.isEmpty()) {
				result += ",\n\t";
				result += "PRIMARY KEY(" + primaryKey.get(0).getName();
				for(int i = 1; i < primaryKey.size(); ++i) {
					result += ", " + primaryKey.get(i).getName();
				}
				result += ")";
			}
		}		
		
		result += "\n);";
		
		return result;
	}
	
	@Override
	public void postCreateTableGeneration(Table table) {
		String resultContent = "";
		
		List<TableField> fkFields = table.getFKs();
		if(fkFields != null) {
			for(TableField field : fkFields) {
				resultContent += "ALTER TABLE "+ table.getName() + " ADD CONSTRAINT FOREIGN KEY (" + field.getName() + ")"
				+ "\n\t REFERENCES " + field.getReferencedTable() + "(GUID) ENABLE\n"
				+ "/\n";
			}
		}
		
		String outputFilename = "c:/temp/" + table.getName() + ".con";
		try {
			FileWriter fileWriter = new FileWriter(outputFilename);
			fileWriter.write(resultContent);
			fileWriter.close();
		} catch(Exception e) {
			
		}
	}
}
