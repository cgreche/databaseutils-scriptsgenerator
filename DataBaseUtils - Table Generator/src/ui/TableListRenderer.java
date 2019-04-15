package ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import structs.Script;

@SuppressWarnings("serial")
public class TableListRenderer extends JLabel implements ListCellRenderer<Script> {
	
	public TableListRenderer() {
		setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Script> list, Script value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if(value == null)
			return null;
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		setText(value.getObjectName());
		return this;
	}
	
}

