package ui.frames;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * 
 * Generic class for dialogs
 * @author cesar.reche@techne.com.br
 * @since 16/04/2019
 *
 */
@SuppressWarnings("serial")
public class Dialog<T> extends JDialog {
	
	protected JFrame parent;

	protected boolean result;
	protected T resultData;
	
	public Dialog(JFrame parent) {
		super(parent,true);
		this.parent = parent;
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Dialog.this.onClose();
			}
		});
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
	
	@Override
	public void setVisible(boolean visible) {
		setLocationRelativeTo(getParent());
		super.setVisible(visible);
	}
	
	public void onClose() {
		setResult(false,null);
	}
}
