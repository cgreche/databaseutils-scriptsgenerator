package ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import structs.FieldType;

//11/04/2019

@SuppressWarnings("serial")
public class FieldTypeComboBoxRenderer extends JLabel implements ListCellRenderer<FieldType> {

	@Override
	public Component getListCellRendererComponent(JList<? extends FieldType> list, FieldType value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if(value == null)
			setText("Selecione o tipo");
		else
			setText(((FieldType)value).getName());
		return this;
	}
	
}
