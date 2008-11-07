package plugin;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import Interface.PluginInterface;

/**
 * Classe utilizada para extrair os nomes dos objetos de um arquivo texto simples.<br>
 * Um arquivo válido é um arquivo que possua em cada linha um nome de objeto. E Na frente de cada linha à ser lida deverá haver um sinal de + (mais). Outras linhas serão ignoradas.
 * 
 * @author Fabio Marmitt
 */
public class PluginTexto implements PluginInterface {
	
	/**
	 * Método utilizado para buscar os nomes das extensões que este plugin pode trabalhar.
	 * 
	 * @return a lista com os nomes das extensões.
	 */
	@Override
	public LinkedList< String > getExtensoes() {
		LinkedList< String > l = new LinkedList< String >();
		l.add( "txt" );
		return l;
	}
	
	/**
	 * Método usado para se conhecer o nome dos arquivos que este plugin pode trabalhar.
	 * 
	 * @return o nome.
	 */
	@Override
	public String getNome() {
		return "Arquivo Texto";
	}
	
	/**
	 * Método usado para extrair os nomes de objetos de um arquivo de texto.
	 * 
	 * @param arquivo arquivo caminho e nome do arquivo à ser aberto
	 * @return lista de objetos extraídos.
	 */
	@Override
	public LinkedList< String > getObjetos( String arquivo ) {
		LinkedList< String > l = new LinkedList< String >();
		Scanner scan = null;
		try{
			scan = new Scanner( new File( arquivo ) );
			while( scan.hasNext() ){
				String s = scan.nextLine();
				if( s.startsWith( "+" ) ){
					l.add( s.replace( "+", "" ) );
				}
			}
			scan.close();
		} catch( Exception e ){
			e.printStackTrace();
			l = null;
		}
		return l;
	}
}
