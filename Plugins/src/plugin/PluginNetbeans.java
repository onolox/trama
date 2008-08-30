package plugin;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Interface.PluginInterface;

/**
 * Classe utilizada para extrair os nomes dos objetos dos arquivos gerados pelo plugin UML do Netbeans 6.1. <br>
 * Aviso importante: <b>É necessário o arquivo de projeto do Netbeans (extensão .etd) junto com os de diagrama (extensão .etlp).</b>
 * 
 * @author Fabio Marmitt
 */
public class PluginNetbeans implements PluginInterface {
	private String DIRBASE = "arquivos/";
	
	/**
	 * Método utilizado para buscar os nomes das extensões que este plugin pode trabalhar utilizadas pelo Netbeans.
	 * 
	 * @return a lista com os nomes das extensões.
	 */
	@Override
	public LinkedList< String > getExtensoes() {
		LinkedList< String > l = new LinkedList< String >();
		l.add( "etlp" );
		return l;
	}
	
	/**
	 * Método usado para se conhecer o nome dos arquivos que este plugin pode trabalhar.
	 * 
	 * @return o nome.
	 */
	@Override
	public String getNome() {
		return "Arquivos de projeto UML do Netbeans";
	}
	
	/**
	 * Metodo usado para extrair os objetos dos arquivos do Netbeans.
	 * 
	 * @return lista de objetos extraídos.
	 */
	@Override
	public LinkedList< String > getObjetos( String arquivo ) {
		LinkedList< String > l = new LinkedList< String >();
		Pattern pat = Pattern.compile( "<UML:.*name=.*<UML:Element.presentation>" );
		Scanner scan;
		File file = new File( DIRBASE );
		StringBuilder sb2 = new StringBuilder( 500 );
		
		try{
			scan = new Scanner( new FileReader( DIRBASE + arquivo ) );
			while( scan.hasNext() )
				sb2.append( scan.nextLine() + "\n" );
			
			for( File fi : file.listFiles() ){
				if( fi.toString().endsWith( "etd" ) ){
					scan = new Scanner( new FileReader( fi.toString() ) );
					StringBuilder sb = new StringBuilder( 500 );
					while( scan.hasNext() )
						sb.append( scan.nextLine() + "\n" );
					
					Matcher mat = pat.matcher( sb.toString() );
					while( mat.find() ){
						String s = mat.group();
						
						Matcher mat2 = Pattern.compile( "(?<=name=\")\\p{Alnum}+(?=\")" ).matcher( s );
						if( mat2.find() ){
							s = mat2.group();
							if( sb2.toString().contains( s ) ) l.add( s );
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
		return l;
	}
}
