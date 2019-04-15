package ui;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

//15/04/2019

@SuppressWarnings("serial")
public class TableCellDefaultRenderer extends JLabel implements TableCellRenderer {
	
    public TableCellDefaultRenderer() {
        setOpaque(true); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
    	this.setForeground(new Color(0,0,255));
    	/*
    	TableFieldTableItem tfi = (TableFieldTableItem)color;
    	if((tfi.getFlags() & TableFieldFlags.ADDED_LATER) != 0)
    		this.setForeground(new Color(0,0,255));
    		*/
		if(value == null)
			return null;
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
		setText(value.toString());
        return this;
    }
}