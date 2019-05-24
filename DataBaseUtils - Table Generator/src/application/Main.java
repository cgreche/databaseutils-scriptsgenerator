package application;

/* TODO:
 * Notify script changed on Script rename
Enviar para github
Dialog Autor
Melhorar validação de scripts antes da geração
Mudar Criar/Salvar para Proriedades de Projeto
 */

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ui.frames.MainWindow;

public class Main {
			
	public static MainWindow mainWindow;
	
	public static void main(String [] args) {
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
	
}
