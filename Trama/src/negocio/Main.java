package negocio;

import javax.swing.UIManager;

import visao.Tela;

/**
 * Classe que dá partida no aplicativo.
 * 
 * @author Fabio
 * @version 1.0
 */
public class Main {
	public Tela m_Tela;
	
	/**
	 * Método que da partida na interface gráfica.
	 * 
	 * @param args argumentos
	 */
	public static void main( String args[] ) {
		try{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
			UIManager.put( "OptionPane.cancelButtonText", "Cancelar" );
			UIManager.put( "OptionPane.noButtonText", "Não" );
			UIManager.put( "OptionPane.yesButtonText", "Sim" );
			UIManager.put( "FileChooser.saveButtonText", "Abrir" );
			UIManager.put( "FileChooser.cancelButtonText", "Cancelar" );
			System.out.print( " " );
		} catch( Exception ex ){
			ex.printStackTrace();
		}
		new Tela().setVisible( true );
	}
}