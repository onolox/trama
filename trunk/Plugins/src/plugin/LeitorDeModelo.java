package plugin;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;

import Interface.PluginInterface;

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
		lista = new LinkedList< String >();
		
		lista = getClasseJar( arquivo ).getObjetos( arquivo );
		
		return lista;
	}
	
	public LinkedList< String > getJars() { // retorna uma lista de jars no diretorio
		LinkedList< String > l = new LinkedList< String >();
		File f = new File( DIRBASE );
		for( File fi : f.listFiles() ){
			String s = fi.toString();
			if( s.endsWith( ".jar" ) ){
				l.add( s.replace( "\\", "/" ) );
				// System.out.println( "Jar: " + s );
			}
		}
		return l;
	}
	
	/**
	 * Busca e instancia a classe "principal" do jar.
	 * 
	 * @param arquivo jar a ser procurado.
	 * @return uma instância da classe do jar.
	 */
	public PluginInterface getClasseJar( String arquivo ) {
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
		} catch( MalformedURLException e ){
			e.printStackTrace();
		} catch( IOException e ){
			e.printStackTrace();
		} catch( InstantiationException e ){
			e.printStackTrace();
		} catch( IllegalAccessException e ){
			e.printStackTrace();
		}
		return pl;
	}
}
