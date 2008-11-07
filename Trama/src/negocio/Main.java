package negocio;

import javax.swing.UIManager;

import visao.Tela;

/**
 * Classe que dá partida no aplicativo, começando pela interface gráfica, {@link Tela}.
 * 
 * @author Fabio
 * @version 1.0
 */
public class Main {
	
	/**
	 * Método que da partida no sistema.
	 * 
	 * @param args argumentos
	 */
	public static void main( String args[] ) {
		try{
			UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
			UIManager.put( "OptionPane.cancelButtonText", "Cancelar" );
			UIManager.put( "OptionPane.noButtonText", "Não" );
			UIManager.put( "OptionPane.yesButtonText", "Sim" );
			UIManager.put( "FileChooser.saveButtonText", "Salvar" );
			UIManager.put( "FileChooser.cancelButtonText", "Cancelar" );
			UIManager.put( "FileChooser.openButtonText", "Abrir" );
			// System.out.print( " " );
		} catch( Exception ex ){
			ex.printStackTrace();
		}
		new Tela().setVisible( true );
	}
}