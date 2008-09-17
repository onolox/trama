package Interface;

import java.util.LinkedList;

/**
 * Interface necessária para qualquer plugin que deseje funcionar com o aplicativo Trama.
 * 
 * @author Fabio Marmitt
 */
public interface PluginInterface {
	
	/**
	 * Método utilizado para buscar os nomes das extensões que o plugin pode trabalhar.< br /> Como por exemplo: Arquivos do Netbeans, Arquivo Texto, etc.
	 * 
	 * @return a lista com os nomes das extensões.
	 */
	public String getNome();
	
	/**
	 * Método usado para se conhecer o nome das extensões dos arquivos que o plugin pode trabalhar.<br /> Como por exemplo .txt, .etd, .xml, etc.
	 * 
	 * @return o nome.
	 */
	public LinkedList< String > getExtensoes();
	
	/**
	 * Método usado para extrair os objetos dos arquivos.
	 * 
	 * @param arquivo arquivo caminho e nome do arquivo à ser aberto
	 * @return lista de objetos extraídos.
	 */
	public LinkedList< String > getObjetos( String arquivo );
}
