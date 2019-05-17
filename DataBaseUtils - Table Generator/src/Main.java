
import ui.frames.MainWindow;

/*
-Ajustar bug de projeto ser criado ao dar cancelar em Novo Project;
-Adicionar header message padrão no Project Settings;
-Adicionar ActionPanel acima dos scripts/Commands (talvez utilizar TableLayout);
-Adicionar header message para scritpts específicos;
-Adicionar botão remover para comandos;
-Fazer validação de fields para CreateTableCommandDialog;
-Ajustar dialog de AddFieldCommandDialog;
-Ajustar dialog de DropFieldCommandDialog;
-Ajustar dialog de ModifyFieldCommandDialog;
-Abrir dialogs de forma centralizada;
-Adicionar validação de scripts; bloquear geração caso algum esteja inválido;
-Adicionar Dropdown de tabelas em campo tabela referenciada;
-Adicionar Dropdown de fields em campo coluna referenciada;
-Mudar nome de campo "coluna referenciada" para "field referenciado";
-Adicionar texto de título para "Tabela resultante";
-Consertar exception que ocorre ao remover script;
-Programar mecanismo de geração de header com variáveis de %scriptName%/%projectName%;
-Adicionar header à geração de scripts;
-Adicionar função de "Sair" no menu;
-Adicionar dialog e função de menu de Autor;
-Adicionar filtros de extensões em savar/savar como/abrir;
-Adicionar ao Project Settings o perfil de salvamento (Oracle/MySQL);
-Melhorar os dropdowns em geral;
-Trocar o tema de UI para Windows 10;
-Melhorar disposição dos elementos da UI;
-Mudar mensagem de documentação javadoc para adicionar descrição das classes/métodos e author cesar.reche
-Adicionar StatusBar com informação de quantidade de scripts/tabelas.
*/

public class Main {
	
	public static void main(String [] args) {
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
}
