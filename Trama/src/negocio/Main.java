package negocio;

import java.awt.EventQueue;

import javax.swing.UIManager;

import visao.Tela;

/**
 * @author Fabio
 * @version 1.0
 * @created 22-mai-2008 19:52:54
 */
public class Main {
	public Tela m_Tela;
	
	public Main() {
		
	}
	
	public static void main( String args[] ) {
		try{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
			System.out.print( " " );
		} catch( Exception ex ){
			ex.printStackTrace();
		}
		EventQueue.invokeLater( new Runnable() {
			public void run() {
				new Tela().setVisible( true );
			}
		} );
	}
	
}