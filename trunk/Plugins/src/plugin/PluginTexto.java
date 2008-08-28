package plugin;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import Interface.PluginInterface;

public class PluginTexto implements PluginInterface {
	private String DIRBASE = "arquivos/";
	
	@Override
	public LinkedList< String > getExtensoes() {
		LinkedList< String > l = new LinkedList< String >();
		l.add( "txt" );
		return l;
	}
	
	@Override
	public String getNome() {
		return "Arquivo Texto";
	}
	
	@Override
	public LinkedList< String > getObjetos( String arquivo ) {
		LinkedList< String > l = new LinkedList< String >();
		Scanner scan = null;
		try{
			scan = new Scanner( new File( DIRBASE + arquivo ) );
			while( scan.hasNext() ){
				String s = scan.nextLine();
				if( s.startsWith( "+" ) ){
					l.add( s );
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			l = null;
		}
		return l;
	}
	
}
