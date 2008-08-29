package plugin;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import Interface.PluginInterface;

/**
 * Classe utilizada para extrair os nomes dos objetos de um arquivo texto simples.
 * 
 * @author Fabio Marmitt
 */
public class PluginTexto implements PluginInterface {
	private String DIRBASE = "arquivos/";
	
	/**
	 * M�todo utilizado para buscar os nomes das extens�es que este plugin pode trabalhar.
	 * 
	 * @return a lista com os nomes das extens�es.
	 */
	@Override
	public LinkedList< String > getExtensoes() {
		LinkedList< String > l = new LinkedList< String >();
		l.add( "txt" );
		return l;
	}
	
	/**
	 * M�todo usado para se conhecer o nome dos arquivos que este plugin pode trabalhar.
	 * 
	 * @return o nome.
	 */
	@Override
	public String getNome() {
		return "Arquivo Texto";
	}
	
	/**
	 * Metodo usado para extrair os objetos dos arquivos de texto.
	 * 
	 * @return lista de objetos extra�dos.
	 */
	@Override
	public LinkedList< String > getObjetos( String arquivo ) {
		LinkedList< String > l = new LinkedList< String >();
		Scanner scan = null;
		try{
			scan = new Scanner( new File( DIRBASE + arquivo ) );
			while( scan.hasNext() ){
				String s = scan.nextLine();
				if( s.startsWith( "+" ) ){
					l.add( s.replace( "+", "" ) );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			l = null;
		}
		return l;
	}
}