package structs;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//06/05/2019
public class ProjectXMLSerializer implements ProjectSerializer {
	
	@Override
	public byte [] serialize(Project project) {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			
			List<Script> scripts = project.getScripts();
			List<Table> tables = null; //project.getTables();
			List<TableField> extraFields = null; //project.getExtraFields();
			
			tables = new ArrayList<Table>();
			extraFields = new ArrayList<TableField>();
			for(Script script : scripts) {
				for(Command command : script.getCommands()) {
					if(command instanceof CreateTableCommand) {
						tables.add(((CreateTableCommand)command).getTable());
					}
				}
			}
			
			
			documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			// root element
			Element root = document.createElement("project");
			Attr attr = document.createAttribute("name");
			attr.setValue(project.getName());
			root.setAttributeNode(attr);
			
			Element elemTables = document.createElement("tables");
			for(Table table : tables) {
				Element elemTable = document.createElement("table");
					Attr attrTableName = document.createAttribute("name");
					attrTableName.setValue(table.getName());
						elemTable.setAttributeNode(attrTableName);
				
				Element elemFields = document.createElement("fields");
				
				List<TableField> fields = table.getFields();
				for(TableField field : fields) {
					Element elemField = document.createElement("field");
					Attr attrFieldName = document.createAttribute("name");
					attrFieldName.setValue(field.getName());
					elemField.setAttributeNode(attrFieldName);
					
					Element elem = document.createElement("type");
					elem.setTextContent(field.getType().toString());
					elemField.appendChild(elem);
					
					elem = document.createElement("size");
					elem.setTextContent(field.getArgs());
					elemField.appendChild(elem);
					
					elem = document.createElement("constraints");
					elem.setTextContent(String.valueOf(field.getConstraints()));
					elemField.appendChild(elem);
					
					elem = document.createElement("referencedTable");
					elem.setTextContent(String.valueOf(field.getReferencedTable()));
					elemField.appendChild(elem);
					
					elem = document.createElement("referencedColumn");
					elem.setTextContent(String.valueOf(field.getReferencedColumn()));
					elemField.appendChild(elem);
					
					//
					elemFields.appendChild(elemField);
				}
				
				elemTable.appendChild(elemFields);
				elemTables.appendChild(elemTable);
			}
			
			
			Element elemScripts = document.createElement("scripts");
			for(Script script : scripts) {
				Element elemScript = document.createElement("script");
				Element elemCommands = document.createElement("commands");
				for(Command command : script.getCommands()) {
					Element elemCommand = document.createElement("command");
					if(command instanceof CreateTableCommand) {
						CreateTableCommand c = (CreateTableCommand)command;
					}
					else if(command instanceof AddFieldCommand) {
						AddFieldCommand c = (AddFieldCommand)command;
					}
					else if(command instanceof ModifyFieldCommand) {
						ModifyFieldCommand c = (ModifyFieldCommand)command;
					}
					else if(command instanceof DropFieldCommand) {
						DropFieldCommand c = (DropFieldCommand)command;
					}
					
					elemCommands.appendChild(elemCommand);
				}
				
				Attr attrScriptType = document.createAttribute("type");
				attr.setValue(script.toString());
					elemScript.setAttributeNode(attrScriptType);
				elemScript.appendChild(elemCommands);
				elemScripts.appendChild(elemScript);
			}
			
			root.appendChild(elemTables);
			root.appendChild(elemScripts);
			document.appendChild(root);
 
 
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource domSource = new DOMSource(document);
			
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			transformer.transform(domSource, new StreamResult(bos));
			return bos.toByteArray();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
		}
		return null;
	}
	
	@Override
	public Project deserialize(byte [] data) {
		return new Project();
	}
	
}
