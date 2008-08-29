package plugin;

public class main {
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		LeitorDeModelo l = new LeitorDeModelo();
		// System.out.println( l.getJars() );
		System.out.println( l.getObjetos( "Class Diagram 1_1219955468531.etlp" ) );
	// l.getClasseJar( "PluginTexto.jar" );
	}
	
}
