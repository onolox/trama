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
 * Aviso importante: <b>É necessário o arquivo de projeto do Netbeans (extensão .etd) junto com os de diagrama (extensão .etlp).</b><br>
 * Somente os caracteres +-_: são aceitos em nomes de objetos.
 * 
 * @author Fabio Marmitt
 */
public class PluginNetbeans implements PluginInterface {
	private String DIRBASE = "arquivos/";
	
	/**
	 * Método utilizado para buscar os nomes das extens�es que este plugin pode trabalhar utilizadas pelo Netbeans.
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
	 * Método usado para extrair os objetos dos arquivos do Netbeans.
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
					sb = new StringBuilder( 50 );
					while( mat.find() ){
						sb.append( mat.group() + "\n" );
					}
					// System.out.println( sb.toString() );
					
					Matcher mat3 = Pattern.compile( "(?<=name=\"2\" MEID=\")[\\p{Alnum}\\p{Punct}_+-: ]+(?=\">)" ).matcher( sb2.toString() );
					sb2 = new StringBuilder( 50 );
					while( mat3.find() ){
						sb2.append( mat3.group() + "\n" );
					}
				// System.out.println( sb2.toString() );
					Matcher mat2 = Pattern.compile( "(?<=value=\")[\\p{Alnum}_+-:]+(?=\")" ).matcher( sb2.toString() );
					
					if( mat2.find() && sb.toString().contains( mat2.group() ) ){
						if( !mat2.group().equals( "Attributes" ) && !mat2.group().equals( "Operations" ) ) l.add( mat2.group() );
						while( mat2.find() ){
							String s2 = mat2.group();
							for( String str : l ){
								if( str.equalsIgnoreCase( s2 ) ) s2 = "Attributes";
							}
							if( !s2.equals( "Attributes" ) && !s2.equals( "Operations" ) ) l.add( s2 );
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
