package negocio.leitor.Interface;

import java.util.LinkedList;

/**
 * Interface necessária para qualquer plugin que deseje funcionar com o aplicativo Trama.<br>
 * Para construir um plugin para o Trama esta interface deve ser extendida e seus métodos implementados.
 * 
 * @author Fabio Marmitt
 */
public interface PluginInterface {
	
	/**
	 * Método utilizado para buscar o nome do tipo de arquivo.< br />
	 * Este nome vai aparecer na lista de nomes de arquivos nos diálogos de importação de nomes do arquivo.>br> Como por exemplo: Arquivos do Netbeans, Arquivo Texto, etc..
	 * 
	 * @return nome do tipo de arquivo.
	 */
	public String getNome();
	
	/**
	 * Método usado para se conhecer o nome das extensões dos arquivos que o plugin pode trabalhar.<br />
	 * Como por exemplo .txt, .etd, .xml, etc..
	 * 
	 * @return a lista com os nomes das extensões.
	 */
	public LinkedList< String > getExtensoes();
	
	/**
	 * Método usado para extrair os objetos dos arquivos.
	 * 
	 * @param arquivo arquivo caminho e nome do arquivo à ser abertos
	 * @return lista de nomes extraídos.
	 */
	public LinkedList< String > getObjetos( String arquivo );
}
