package ui.components.resultingtable;

import structs.TableField;

/**
 * 
 * @author cesar.reche@techne.com.br
 * @since 11/04/2019
 *
 */
public class ResultingTableItem {
	private TableField field;
	private int flags;
	
	public ResultingTableItem(TableField field, int flags) {
		this.field = field;
		this.flags = flags;
	}

	public TableField getField() {
		return field;
	}

	public void setField(TableField field) {
		this.field = field;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public void setFlags(int flags) {
		this.flags = flags;
	}
}
