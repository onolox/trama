package negocio.leitor.Interface;

import java.util.LinkedList;

/**
 * Interface necessária para qualquer plugin que deseje funcionar com o aplicativo Trama.
 * 
 * @author Fabio Marmitt
 */
public interface PluginInterface {
	
	/**
	 * Método utilizado para buscar os nomes das extensões que o plugin pode trabalhar.
	 * 
	 * @return a lista com os nomes das extensões.
	 */
	public String getNome();
	
	/**
	 * Método usado para se conhecer o nome dos arquivos que o plugin pode trabalhar.
	 * 
	 * @return o nome.
	 */
	public LinkedList< String > getExtensoes();
	
	/**
	 * Metodo usado para extrair os objetos dos arquivos.
	 * 
	 * @return lista de objetos extraídos.
	 */
	public LinkedList< String > getObjetos( String arquivo );
}
