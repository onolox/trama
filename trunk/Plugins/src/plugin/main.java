package plugin;

public class main {
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		LeitorDeModelo l = new LeitorDeModelo();
		 System.out.println( l.getObjetos( "E:/Meus documentos/Java2/Plugins/arquivos/Classes de Análise_1220827347255.etlp" ) );
		// System.out.println( l.getObjetos( "E:/Meus documentos/Java2/Plugins/arquivos/requerimentos.txt" ) );
	}
	
}
