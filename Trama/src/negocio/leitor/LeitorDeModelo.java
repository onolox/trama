package negocio.leitor;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *Classe utilizada para ler os dados de plugins que sejam válidos conforme o sistema proposto. Esta classe contém o sistema de utilização de plugins do aplicativo.
 * 
 * @author Fabio Marmitt
 */
public class LeitorDeModelo {
	
	/** Endereço base do diretório de plugins */
	private static final String DIRBASE = "plugins/";
	/** Lista utilizada para armazenar objetos da classe */
	private LinkedList< String > lista;
	
	/**
	 *Método que busca os nomes de arquivo e extensões de arquivos que os plugins que estão na pasta plugins podem trabalhar.
	 * 
	 * @return a lista com nomes e as extensões
	 */
	public HashMap< String, LinkedList< String >> getNomesExtensoes() {
		HashMap< String, LinkedList< String >> nE = new HashMap< String, LinkedList< String >>();
		lista = getJars();
		if( lista.isEmpty() ) return null;
		for( String li : lista ){
			try{
				Class cl = getClasseJar( li );
				String nome = ( String ) cl.getMethod( "getNome", new Class[] {} ).invoke( cl.newInstance(), null );
				LinkedList< String > ext = ( LinkedList< String > ) cl.getMethod( "getExtensoes", new Class[] {} ).invoke( cl.newInstance(), null );
				nE.put( nome, ext );
			} catch( Exception e ){
				e.printStackTrace();
				nE = null;
			}
		}
		return nE;
	}
	
	/**
	 * Usado para buscar os nomes dos objetos presentes em um determindado arquivo. Dependendo da extensão do arquivo passado como parâmetro o plugin correto é selecionado.
	 * 
	 * @param arquivo nome do arquivo com extensão à ter os nomes buscados
	 * @return lista de nomes
	 */
	public LinkedList< String > getObjetos( String arquivo ) {
		lista = getJars();
		LinkedList< String > lista2 = null;
		
		try{
			for( String li : lista ){
				Class cl = getClasseJar( li );
				LinkedList< String > l = ( LinkedList< String > ) cl.getMethod( "getExtensoes", new Class[] {} ).invoke( cl.newInstance(), null );
				for( String s : l ){
					if( arquivo.endsWith( s ) ){
						lista2 = ( LinkedList< String > ) cl.getMethod( "getObjetos", new Class[] { String.class } ).invoke( cl.newInstance(), arquivo );
					}
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			lista2 = null;
		}
		return lista2;
	}
	
	/**
	 * Usado para buscar os jars que estão no diretório base.
	 * 
	 * @return lista de jars
	 */
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
	 * Busca e instância a classe que implementa PluginInterface do jar.
	 * 
	 * @param arquivo jar a ser procurado.
	 * @return uma instância da classe do jar.
	 */
	private Class getClasseJar( String arquivo ) {
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
					for( int j = 0; j < cla.length; j++ )
						if( cla[ j ].getName().contains( "PluginInterface" ) ) return cl;
				}
			}
		} catch( Exception e ){
			e.printStackTrace();
			cl = null;
		}
		return cl;
	}
}
