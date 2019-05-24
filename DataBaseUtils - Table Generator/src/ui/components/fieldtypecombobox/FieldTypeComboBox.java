package ui.components.fieldtypecombobox;

import javax.swing.JComboBox;

import structs.FieldType;
import structs.GenericTypes;

//24 05 2019
public class FieldTypeComboBox extends JComboBox<FieldType> {
	public FieldTypeComboBox() {
		super();
		this.addItem(null);
		this.addItem(GenericTypes.TEXT);
		this.addItem(GenericTypes.NUMERIC);
		this.addItem(GenericTypes.DATE);
		this.addItem(GenericTypes.TIMESTAMP);
		this.addItem(GenericTypes.BLOB);
		this.addItem(GenericTypes.LONGTEXT);
		
		this.setRenderer(new FieldTypeComboBoxRenderer());
	}
}
