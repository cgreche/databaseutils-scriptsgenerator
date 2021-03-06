package generators;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structs.AddFieldCommand;
import structs.Constraints;
import structs.CreateTableCommand;
import structs.DropFieldCommand;
import structs.FieldType;
import structs.GenericTypes;
import structs.ModifyFieldCommand;
import structs.Project;
import structs.Table;
import structs.TableField;

/**
 * 
 * Script Generator for OracleDB.
 * @author cesar.reche@techne.com.br
 * @since 10/04/2019
 *
 */
public class OracleGenerator extends Generator {
	
	private static final List<FieldType> oracleTypes;
	private static final Map<FieldType,FieldType> typeMapper;
	
	static {
		oracleTypes = new ArrayList<>();
		FieldType number = new FieldType("NUMBER","NUMBER",true,false);
		FieldType varchar2 = new FieldType("VARCHAR2","VARCHAR2",true,false);
		FieldType clob = new FieldType("CLOB","CLOB",false,false);
		oracleTypes.add(number);
		oracleTypes.add(varchar2);
		oracleTypes.add(clob);
		
		typeMapper = new HashMap<FieldType, FieldType>();
		typeMapper.put(GenericTypes.TEXT,varchar2);
		typeMapper.put(GenericTypes.NUMERIC,number);
		typeMapper.put(GenericTypes.LONGTEXT,clob);
	}
	
	public OracleGenerator(Project project) {
		super(project);
	}
	
	private String generateField(TableField field) {
		FieldType mappedType = typeMapper.get(field.getType());
		if(mappedType == null)
			mappedType = field.getType();
		String result = field.getName() + " " + mappedType.getId();
		if(mappedType.acceptArgs() && field.getSize() != null)
			result += "(" + field.getSize() + ")";
		int constraints = field.getConstraints();
		if((constraints&Constraints.NOT_NULL) != 0) {
			result += " NOT NULL";
		}
		return result;
	}
	
	@Override
	public String generateCreateTableCommand(CreateTableCommand command) {
		Table table = command.getTable();
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
		
		result += "\n)\n/\n";
		
		return result;
	}
	
	@Override
	public void postCreateTableCommand(CreateTableCommand command) {
		Table table = command.getTable();
		String resultContent = "";
		
		List<TableField> fkFields = table.getFKs();
		if(fkFields == null)
			return;
		
		for(TableField field : fkFields) {
			resultContent += "ALTER TABLE "+ table.getName() + " ADD CONSTRAINT FOREIGN KEY (" + field.getName() + ")"
			+ "\n\t REFERENCES " + field.getReferencedTable() + "(" + field.getReferencedColumn() + ") ENABLE\n"
			+ "/\n";
		}

		String outputFilename = basePath + "/" + command.getScript().getName() + ".con";
		try {
			FileWriter fileWriter = new FileWriter(outputFilename);
			fileWriter.write(resultContent);
			fileWriter.close();
		} catch(Exception e) {
		}
	}
	
	@Override
	protected String generateAddFieldCommand(AddFieldCommand command) {
		String result = "";
		AddFieldCommand afc = (AddFieldCommand)command;
		Table refTable = afc.getRefTable();
		TableField field = afc.getField();
		result += "ALTER TABLE " + refTable.getName() + " ADD ";
		result += generateField(field);
		result += "\n/\n";
		return result;
	}
	
	@Override
	protected String generateModifyFieldCommand(ModifyFieldCommand command) {
		String result = "";
		ModifyFieldCommand mfc = (ModifyFieldCommand)command;
		Table refTable = mfc.getRefTable();
		TableField oldField = mfc.getOldField();
		TableField newField = mfc.getNewField();
		result += "ALTER TABLE " + refTable.getName() + " MODIFY ";
		result += generateField(newField);
		result += "\n/\n";
		return result;
	}
	
	@Override
	protected String generateDropFieldCommand(DropFieldCommand command) {
		String result = "";
		DropFieldCommand dfc = (DropFieldCommand)command;
		Table refTable = dfc.getRefTable();
		TableField field = dfc.getField();
		result += "ALTER TABLE " + refTable.getName() + " DROP ";
		result += field.getName();
		result += "\n/\n";
		return result;
	}
}
