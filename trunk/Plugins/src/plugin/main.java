package plugin;

public class main {
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		LeitorDeModelo l = new LeitorDeModelo();
		// System.out.println( l.getJars() );
		System.out.println( l.getObjetos( "requerimentos.txt" ) );
	// l.getClasseJar( "PluginTexto.jar" );
	}
	
}
