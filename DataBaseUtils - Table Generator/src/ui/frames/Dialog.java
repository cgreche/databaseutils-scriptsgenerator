package ui.frames;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Dialog<T> extends JDialog {
	
	protected JFrame parent;

	protected boolean result;
	protected T resultData;
	
	public Dialog(JFrame parent) {
		super(parent,true);
		this.parent = parent;
	}
	
	public boolean getResult() {
		return result;
	}
	
	public T getResultData() {
		return resultData;
	}
	
	public JFrame getParent() {
		return parent;
	}
	
	public void setResult(boolean result, T resultData) {
		this.result = result;
		this.resultData = resultData;
	}
}
