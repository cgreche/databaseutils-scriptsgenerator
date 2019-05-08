package structs;

import java.io.ByteArrayInputStream;
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
import org.w3c.dom.Node;

//06/05/2019
public class ProjectXMLSerializer implements ProjectSerializer {

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
					Element elemField = tableFieldToElement(document,field,"field");
					elemFields.appendChild(elemField);
				}
				
				elemTable.appendChild(elemFields);
				elemTables.appendChild(elemTable);
			}
			
			
			Element elemScripts = document.createElement("scripts");
			for(Script script : scripts) {
				Element elemScript = document.createElement("script");
				Attr attrScriptName = document.createAttribute("name");
				attrScriptName.setValue(script.getObjectName());
				elemScript.setAttributeNode(attrScriptName);
				
				Element elemCommands = document.createElement("commands");
				for(Command command : script.getCommands()) {
					Element elemCommand = document.createElement("command");
					Attr attrCommandType = document.createAttribute("type");
					attrCommandType.setValue(command.getClass().getSimpleName());
					elemCommand.setAttributeNode(attrCommandType);
						
					if(command instanceof CreateTableCommand) {
						CreateTableCommand c = (CreateTableCommand)command;
						Table table = c.getTable();
						Element elem = document.createElement("table");
						elem.setTextContent(table.getName());
						elemCommand.appendChild(elem);
					}
					else if(command instanceof AddFieldCommand) {
						AddFieldCommand c = (AddFieldCommand)command;
						Element elem = document.createElement("refTable");
						elem.setTextContent(c.getRefTable().getName());
						elemCommand.appendChild(elem);
						
						elem = tableFieldToElement(document, c.getField(), "field");
						elemCommand.appendChild(elem);
					}
					else if(command instanceof ModifyFieldCommand) {
						ModifyFieldCommand c = (ModifyFieldCommand)command;
						Element elem = document.createElement("refTable");
						elem.setTextContent(c.getRefTable().getName());
						elemCommand.appendChild(elem);
						
						elem = document.createElement("oldField");
						elem.setTextContent(c.getOldField().getName());
						elemCommand.appendChild(elem);
						
						elem = tableFieldToElement(document,c.getNewField(),"newField");
						elemCommand.appendChild(elem);
					}
					else if(command instanceof DropFieldCommand) {
						DropFieldCommand c = (DropFieldCommand)command;
						Element elem = document.createElement("refTable");
						elem.setTextContent(c.getRefTable().getName());
						elemCommand.appendChild(elem);
						
						elem = document.createElement("field");
						elem.setTextContent(c.getField().getName());
						elemCommand.appendChild(elem);
					}
					
					elemCommands.appendChild(elemCommand);
				}
				

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
	
	private CreateTableCommand parseCreateTableCommand(Element elem, Script parentScript) {
		elem = getChildElement(elem);
		while(elem != null) {
			String strValue = elem.getTextContent();
			if("table".equals(elem.getNodeName())) {
			}
			
			elem = getNextElement(elem);
		}
		return null; //TODO
	}

	private AddFieldCommand parseAddFieldCommand(Element elem, Script parentScript) {
		
		Table refTable = null; //TODO
		TableField field = new TableField();
		elem = getChildElement(elem);
		while(elem != null) {
			String strValue = elem.getTextContent();
			if("refTable".equals(elem.getNodeName())) {
				//TODO
			}
			else if("field".equals(elem.getNodeName())) {
				field = parseTableField(elem);
			}
			
			elem = getNextElement(elem);
		}
		
		return new AddFieldCommand(parentScript,refTable,field);
	}
	
	private ModifyFieldCommand parseModifyFieldCommand(Element elem, Script parentScript) {
		elem = getChildElement(elem);
		while(elem != null) {
			String strValue = elem.getTextContent();
			if("refTable".equals(elem.getNodeName())) {
				//TODO
			}
			else if("oldField".equals(elem.getNodeName())) {
				//TODO
			}
			else if("newField".equals(elem.getNodeName())) {
				parseTableField(elem);
			}
			
			elem = getNextElement(elem);
		}
		
		return null; //TODO
	}

	private DropFieldCommand parseDropFieldCommand(Element elem, Script parentScript) {
		elem = getChildElement(elem);
		while(elem != null) {
			String strValue = elem.getTextContent();
			if("refTable".equals(elem.getNodeName())) {
				//TODO
			}
			else if("field".equals(elem.getNodeName())) {
				//TODO
			}
			
			elem = getNextElement(elem);
		}
		
		return null; //TODO
	}

	private Command parseCommand(Element elem, Script parentScript) {
		Command command;
		String typeStr = elem.getAttribute("type");
		if("CreateTableCommand".contentEquals(typeStr))
			command = parseCreateTableCommand(elem,parentScript);
		else if("AddFieldCommand".contentEquals(typeStr))
			command = parseAddFieldCommand(elem,parentScript);
		else if("ModifyFieldCommand".contentEquals(typeStr))
			command = parseModifyFieldCommand(elem,parentScript);
		else if("DropFieldCommand".contentEquals(typeStr))
			command = parseDropFieldCommand(elem,parentScript);
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
				//TODO
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

	void parseTable(Element elem, Project project) {
		Table table = new Table();
		String tableName = elem.getAttribute("name");
		table.setName(tableName);
		
		elem = getChildElement(elem);
		while(elem != null) {
			if("fields".equals(elem.getNodeName())) {
				elem = getChildElement(elem);
				while(elem != null) {
					if("field".contentEquals(elem.getNodeName())) {
						table.getFields().add(parseTableField(elem));
					}
					elem = getNextElement(elem);
				}
				break;
			}
			
			elem = getNextElement(elem);
		}
	}
	
	private Script parseScript(Element elem, Project project) {
		Script script = new Script();
		String scriptName = elem.getAttribute("name");
		script.setObjectName(scriptName);
		
		elem = getChildElement(elem);
		while(elem != null) {
			if("commands".equals(elem.getNodeName())) {
				elem = getChildElement(elem);
				while(elem != null) {
					if("commands".contentEquals(elem.getNodeName())) {
						Command command = parseCommand(elem,script);
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
					if("table".contentEquals(elemTable.getNodeName()))
						parseTable(elemTable,project);
					elemTable = getNextElement(elemTable);
				}
			} else if("scripts".contentEquals(elemName)) {
				
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e) {
			//todo: error message?
		}
		return null;
	}
	
}
