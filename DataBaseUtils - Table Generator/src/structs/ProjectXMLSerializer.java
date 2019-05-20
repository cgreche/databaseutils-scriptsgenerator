package structs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.w3c.dom.Node;

//06/05/2019
public class ProjectXMLSerializer implements ProjectSerializer {
	
	String isoDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	DateFormat isoDateFormatter;
	
	private String date2String(Date date) {
		try {
			return isoDateFormatter.format(date);
		} catch(Exception e) {
			return null;
		}
	}

	private Date string2Date(String str) {
		try {
			return isoDateFormatter.parse(str);
		} catch(Exception e) {
			return null;
		}
	}

	public ProjectXMLSerializer() {
		isoDateFormatter = new SimpleDateFormat(isoDateFormat);
	}

	private Element tableFieldToElement(Document document, TableField field, String elementName) {
		Element elemField = document.createElement(elementName);
		Attr attrFieldName = document.createAttribute("name");
		attrFieldName.setValue(field.getName());
		elemField.setAttributeNode(attrFieldName);
		
		Element elem = document.createElement("type");
		elem.setTextContent(field.getType().getId());
		elemField.appendChild(elem);
		
		if(field.getSize() != null) {
			elem = document.createElement("size");
			elem.setTextContent(field.getSize());
			elemField.appendChild(elem);
		}
		
		if(field.getConstraints() != 0) {
			elem = document.createElement("constraints");
			elem.setTextContent(String.valueOf(field.getConstraints()));
			elemField.appendChild(elem);
			
			if((field.getConstraints() & Constraints.FK) != 0) {
				elem = document.createElement("refTable");
				elem.setTextContent(String.valueOf(field.getReferencedTable()));
				elemField.appendChild(elem);
			
				elem = document.createElement("refColumn");
				elem.setTextContent(String.valueOf(field.getReferencedColumn()));
				elemField.appendChild(elem);
			}
		}
		
		return elemField;
	}

	@Override
	public byte [] serialize(Project project) {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			
			List<Script> scripts = project.getScripts();
			List<Table> tables = null; //project.getTables();
			
			tables = new ArrayList<Table>();
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
			
			if(tables != null && !tables.isEmpty()) {
				Element elemTables = document.createElement("tables");
				for(Table table : tables) {
					Element elemTable = document.createElement("table");
						Attr attrTableName = document.createAttribute("name");
						attrTableName.setValue(table.getName());
							elemTable.setAttributeNode(attrTableName);
					
					Element elemFields = document.createElement("fields");
					
					List<TableField> fields = table.getFields();
					for(TableField field : fields) {
						Element elemField = tableFieldToElement(document,field,"field");
						elemFields.appendChild(elemField);
					}
					
					elemTable.appendChild(elemFields);
					elemTables.appendChild(elemTable);
				}
				
				root.appendChild(elemTables);
			}
			
			if(scripts != null && !scripts.isEmpty()) {
				Element elemScripts = document.createElement("scripts");
				for(Script script : scripts) {
					Element elemScript = document.createElement("script");
					Attr attrScriptName = document.createAttribute("name");
					attrScriptName.setValue(script.getName());
					elemScript.setAttributeNode(attrScriptName);
					
					Element elem = document.createElement("creationDate");
					elem.setTextContent(this.date2String(script.getCreationDate()));
					elemScript.appendChild(elem);
					
					elem = document.createElement("lastModifiedDate");
					elem.setTextContent(this.date2String(script.getLastModifiedDate()));
					elemScript.appendChild(elem);
					
					if(script.getBasePath() != null) {
						elem = document.createElement("basePath");
						elem.setTextContent(script.getBasePath());
						elemScript.appendChild(elem);
					}
					
					if(script.getHeaderMessage() != null) {
						elem = document.createElement("headerMessage");
						elem.setTextContent(script.getHeaderMessage());
						elemScript.appendChild(elem);
					}
					
					if(script.getCommands() != null && !script.getCommands().isEmpty()) {
						Element elemCommands = document.createElement("commands");
						for(Command command : script.getCommands()) {
							Element elemCommand = document.createElement("command");
							Attr attrCommandType = document.createAttribute("type");
							attrCommandType.setValue(command.getClass().getSimpleName());
							elemCommand.setAttributeNode(attrCommandType);
								
							if(command instanceof CreateTableCommand) {
								CreateTableCommand c = (CreateTableCommand)command;
								Table table = c.getTable();
								elem = document.createElement("table");
								elem.setTextContent(table.getName());
								elemCommand.appendChild(elem);
							}
							else if(command instanceof AddFieldCommand) {
								AddFieldCommand c = (AddFieldCommand)command;
								elem = tableFieldToElement(document, c.getField(), "field");
								elemCommand.appendChild(elem);
							}
							else if(command instanceof ModifyFieldCommand) {
								ModifyFieldCommand c = (ModifyFieldCommand)command;
								elem = document.createElement("oldField");
								elem.setTextContent(c.getOldField().getName());
								elemCommand.appendChild(elem);
								
								elem = tableFieldToElement(document,c.getNewField(),"newField");
								elemCommand.appendChild(elem);
							}
							else if(command instanceof DropFieldCommand) {
								DropFieldCommand c = (DropFieldCommand)command;
								elem = document.createElement("field");
								elem.setTextContent(c.getField().getName());
								elemCommand.appendChild(elem);
							}
							
							elemCommands.appendChild(elemCommand);
						}
						elemScript.appendChild(elemCommands);
					}
					
					elemScripts.appendChild(elemScript);
				}
				
				root.appendChild(elemScripts);
			}
			
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
			e.printStackTrace();
		} catch(Exception e) {
		}
		return null;
	}
	
	private Element getChildElement(Node node) {
		Node child = node.getFirstChild();
		while(child != null && child.getNodeType() != Node.ELEMENT_NODE) {
			child = child.getNextSibling();
		} 
		return (Element)child;
	}
	
	private Element getNextElement(Node node) {
		do {
			node = node.getNextSibling();
		} while(node != null && node.getNodeType() != Node.ELEMENT_NODE);
		return (Element)node;
	}
	
	private CreateTableCommand parseCreateTableCommand(Element elem, Script parentScript, Project project) {
		Table table = null;
		
		elem = getChildElement(elem);
		while(elem != null) {
			String strValue = elem.getTextContent();
			if("table".equals(elem.getNodeName())) {
				table = project.getTable(strValue);
			}
			
			elem = getNextElement(elem);
		}
		
		return new CreateTableCommand(parentScript,table);
	}

	private AddFieldCommand parseAddFieldCommand(Element elem, Script parentScript, Project project) {
		
		TableField field = null;
		
		elem = getChildElement(elem);
		while(elem != null) {
			String nodeName = elem.getNodeName();
			if("field".equals(nodeName)) {
				field = parseTableField(elem);
			}
			
			elem = getNextElement(elem);
		}
		
		return new AddFieldCommand(parentScript,parentScript.getResultingTable(),field);
	}
	
	private ModifyFieldCommand parseModifyFieldCommand(Element elem, Script parentScript, Project project) {
		TableField oldField = null;
		TableField newField = null;
		
		elem = getChildElement(elem);
		while(elem != null) {
			String strValue = elem.getTextContent();
			if("oldField".equals(elem.getNodeName())) {
				oldField = parentScript.getField(strValue);
			}
			else if("newField".equals(elem.getNodeName())) {
				newField = parseTableField(elem);
			}
			
			elem = getNextElement(elem);
		}
		
		return new ModifyFieldCommand(parentScript,parentScript.getResultingTable(),oldField,newField);
	}

	private DropFieldCommand parseDropFieldCommand(Element elem, Script parentScript, Project project) {
		TableField field = null;
		
		elem = getChildElement(elem);
		while(elem != null) {
			String strValue = elem.getTextContent();
			if("field".equals(elem.getNodeName())) {
				field = parentScript.getField(strValue);
			}
			
			elem = getNextElement(elem);
		}
		return new DropFieldCommand(parentScript,parentScript.getResultingTable(),field);
	}

	private Command parseCommand(Element elem, Script parentScript, Project project) {
		Command command;
		String typeStr = elem.getAttribute("type");
		if("CreateTableCommand".contentEquals(typeStr))
			command = parseCreateTableCommand(elem,parentScript,project);
		else if("AddFieldCommand".contentEquals(typeStr))
			command = parseAddFieldCommand(elem,parentScript,project);
		else if("ModifyFieldCommand".contentEquals(typeStr))
			command = parseModifyFieldCommand(elem,parentScript,project);
		else if("DropFieldCommand".contentEquals(typeStr))
			command = parseDropFieldCommand(elem,parentScript,project);
		else
			command = null; //TODO
		
		return command;
	}

	private TableField parseTableField(Element elem) {
		TableField field = new TableField();
		
		String fieldName = elem.getAttribute("name");
		field.setName(fieldName);
		
		elem = getChildElement(elem);
		while(elem != null) {
			String strValue = elem.getTextContent();
			if("type".equals(elem.getNodeName())) {
				FieldType type = null;
				
				if("TEXT".contentEquals(strValue))
					type = GenericTypes.TEXT;
				else if("NUMERIC".contentEquals(strValue))
					type = GenericTypes.NUMERIC;
				else if("DATE".contentEquals(strValue))
					type = GenericTypes.DATE;
				else if("TIMESTAMP".contentEquals(strValue))
					type = GenericTypes.TIMESTAMP;
				else if("BLOB".contentEquals(strValue))
					type = GenericTypes.BLOB;
				else if("LONGTEXT".contentEquals(strValue))
					type = GenericTypes.LONGTEXT;
				else
					; //TODO
				
				field.setType(type);
			}
			else if("size".equals(elem.getNodeName())) {
				field.setSize(strValue);
			}
			else if("constraints".equals(elem.getNodeName())) {
				int constraints = Integer.parseUnsignedInt(strValue);
				field.setConstraints(constraints);
			}
			else if("refTable".equals(elem.getNodeName())) {
				field.setReferencedTable(strValue);
			}
			else if("refColumn".equals(elem.getNodeName())) {
				field.setReferencedColumn(strValue);
			}
			
			elem = getNextElement(elem);
		}
		
		return field;
	}

	private Table parseTable(Element elem, Project project) {
		Table table = new Table();
		String tableName = elem.getAttribute("name");
		table.setName(tableName);
		
		elem = getChildElement(elem);
		while(elem != null) {
			if("fields".equals(elem.getNodeName())) {
				elem = getChildElement(elem);
				while(elem != null) {
					if("field".contentEquals(elem.getNodeName())) {
						TableField field = parseTableField(elem);
						table.getFields().add(field);
					}
					elem = getNextElement(elem);
				}
				break;
			}
			
			elem = getNextElement(elem);
		}
		
		return table;
	}
	
	private Script parseScript(Element elem, Project project) {
		Script script = new Script();
		String scriptName = elem.getAttribute("name");
		script.setName(scriptName);
		
		elem = getChildElement(elem);
		while(elem != null) {
			String nodeName = elem.getNodeName();
			String strValue = elem.getTextContent();
			
			if("creationDate".contentEquals(nodeName)) {
				script.setCreationDate(string2Date(strValue));
			}
			else if("lastModifiedDate".contentEquals(nodeName)) {
				script.setLastModifiedDate(string2Date(strValue));
			}
			else if("basePath".contentEquals(nodeName)) {
				script.setBasePath(strValue);
			}
			else if("commands".equals(elem.getNodeName())) {
				elem = getChildElement(elem);
				while(elem != null) {
					if("commands".contentEquals(nodeName)) {
						Command command = parseCommand(elem,script,project);
						script.addCommand(command);
					}
					elem = getNextElement(elem);
				}
				break;
			}
			
			elem = getNextElement(elem);
		}
		
		return script;
	}
	
	private Project parseProject(Node rootNode) {
		Project project;
		if(!"project".contentEquals(rootNode.getNodeName())) {
			return null;
		}

		project = new Project();
		String projectName = rootNode.getAttributes().getNamedItem("name").getTextContent();
		project.setName(projectName);
		
		Element elem = getChildElement(rootNode);
		while(elem != null) {
			String elemName = elem.getNodeName();
			if("tables".contentEquals(elemName)) {
				Element elemTable = getChildElement(elem);
				while(elemTable != null) {
					if("table".contentEquals(elemTable.getNodeName())) {
						Table table = parseTable(elemTable,project);
						project.addTable(table);
					}
					elemTable = getNextElement(elemTable);
				}
			} else if("scripts".contentEquals(elemName)) {
				Element elemScript = getChildElement(elem);
				while(elemScript != null) {
					if("script".contentEquals(elemScript.getNodeName())) {
						Script script = parseScript(elemScript,project);
						project.addScript(script);
					}
					elemScript = getNextElement(elemScript);
				}
			}
			elem = getNextElement(elem);
		}
		
		return project;
	}
	
	@Override
	public Project deserialize(byte [] data) {
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(new ByteArrayInputStream(data));
			Node node = doc.getFirstChild();
			Project project = parseProject(node);
			return project;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch(Exception e) {
			//TODO: error message?
		}
		return null;
	}
	
}
