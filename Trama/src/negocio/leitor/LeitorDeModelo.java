package negocio.leitor;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;

import negocio.leitor.Interface.PluginInterface;

public class LeitorDeModelo {
	private final String DIRBASE = "plugins/";
	private LinkedList< String > lista;
	
	/**
	 * @return
	 */
	public HashMap< String, LinkedList< String >> getNomesExtensoes() {
		HashMap< String, LinkedList< String >> nE = new HashMap< String, LinkedList< String >>();
		lista = getJars();
		for( String li : lista ){
			PluginInterface cla = getClasseJar( li );
			nE.put( cla.getNome(), cla.getExtensoes() );
		}
		return nE;
	}
	
	public LinkedList< String > getObjetos( String arquivo ) {
		LinkedList< String > lista = new LinkedList< String >();
		lista = getJars();
		for( String li : lista ){
			PluginInterface cla = getClasseJar( li );
			LinkedList< String > l = cla.getExtensoes();
			for( String s : l ){
				if( arquivo.endsWith( s ) ){
					lista = cla.getObjetos( arquivo );
				}
			}
		}
		return lista;
	}
	
	private LinkedList< String > getJars() { // retorna uma lista de jars no diretorio
		LinkedList< String > l = new LinkedList< String >();
		File f = new File( DIRBASE );
		for( File fi : f.listFiles() ){
			String s = fi.toString();
			if( s.endsWith( ".jar" ) ) l.add( s.replace( "\\", "/" ) );
		}
		return l;
	}
	
	/**
	 * Busca e instancia a classe "principal" do jar.
	 * 
	 * @param arquivo jar a ser procurado.
	 * @return uma instância da classe do jar.
	 */
	private PluginInterface getClasseJar( String arquivo ) {
		PluginInterface pl = null;
		URLClassLoader load;
		Class cl = null;
		try{
			URL url = new URL( "jar", "", "file:" + arquivo + "!/" );
			
			JarURLConnection jar = ( JarURLConnection ) url.openConnection(); // abro o url pro jar
			Enumeration arq = jar.getJarFile().entries(); // pego os arquivos que estao no jar
			load = new URLClassLoader( new URL[] { url } ); // pra ler a classe do jar
			
			while( arq.hasMoreElements() ){
				Object object = arq.nextElement();
				String nome = object.toString();
				
				if( nome.endsWith( ".class" ) && !nome.contains( "PluginInterface.class" ) ){
					
					try{
						cl = load.loadClass( nome.replace( ".class", "" ).replace( "/", "." ) );
					} catch( ClassNotFoundException e ){
						e.printStackTrace();
					}
					
					Class cla[] = cl.getInterfaces();
					for( int j = 0; j < cla.length; j++ ){
						if( cla[ j ].getName().contains( "PluginInterface" ) ){
							pl = ( PluginInterface ) cl.newInstance();
						}
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			pl = null;
		}
		return pl;
	}
}
