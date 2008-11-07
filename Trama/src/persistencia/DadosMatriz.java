package persistencia;

import java.util.LinkedList;

/**
 * Classe de persistência que possui os dados de uma matriz do projeto.<br/>
 * A matriz é representada por uma {@link LinkedList} dentro de outra {@link LinkedList}. Assim é possível ter uma matriz que se expande conforme a necessidade.
 * 
 * @author Fabio Marmitt
 */
public class DadosMatriz {
	
	/** Representa os dados da matriz */
	private LinkedList< LinkedList > linha;
	private String nomeMatriz;
	/** Quantidade de colunas */
	private int qColunas = 1;
	/** Quantidade de linhas */
	private int qLinhas = 0;
	/** Lista de nomes das colunas */
	private LinkedList< String > tituloColuna;
	/** Nome do arquivo de importação das colunas */
	private String tituloColunaArquivo;
	/** Lista de nomes das colunas */
	private LinkedList< String > tituloLinha;
	/** Nome do arquivo de importação das linhas */
	private String tituloLinhaArquivo;
	
	/**
	 * Construtor padrão.
	 * 
	 * @param nome nome da matriz
	 */
	public DadosMatriz( String nome ) {
		tituloLinha = new LinkedList< String >();
		tituloColuna = new LinkedList< String >();
		linha = new LinkedList< LinkedList >();
		this.nomeMatriz = nome;
		tituloColuna.add( "" );
	}
	
	/**
	 * Retorna uma lista de colunas.
	 * 
	 * @param linha índice da linha
	 * @return lista de colunas
	 */
	public LinkedList< String > getLinha( int linha ) {
		return this.linha.get( linha );
	}
	
	/**
	 * Retorna todas as linhas da matriz.
	 * 
	 * @return todas as linhas da matriz.
	 */
	public LinkedList< LinkedList > getLinhas() {
		return linha;
	}
	
	/**
	 * Retorna o nome da matriz.
	 * 
	 * @return nome da matriz.
	 */
	public String getNomeMatriz() {
		return nomeMatriz;
	}
	
	/**
	 * Método usado para buscar a quantidade de colunas que a matriz possui.
	 * 
	 * @return a quantidade de colunas que a matriz possui.
	 */
	public int getQColunas() {
		return qColunas;
	}
	
	/**
	 * Método usado para buscar a quantidade de linhas que a matriz possui.
	 * 
	 * @return a quantidade de linhas que a matriz possui.
	 */
	public int getQLinhas() {
		return qLinhas;
	}
	
	/**
	 * Retorna a lista de títulos das colunas.
	 * 
	 * @return lista de títulos das colunas.
	 */
	public LinkedList< String > getTituloColuna() {
		return tituloColuna;
	}
	
	/**
	 * Retorna o título da coluna.
	 * 
	 * @param index índice
	 * @return título da coluna.
	 */
	public String getTituloColuna( int index ) {
		return tituloColuna.get( index );
	}
	
	/**
	 * Retorna o nome do arquivo de importação de dados da coluna.
	 * 
	 * @return arquivo de importação de dados da coluna.
	 */
	public String getTituloColunaArquivo() {
		return tituloColunaArquivo;
	}
	
	/**
	 * Retorna a lista de títulos das linhas.
	 * 
	 * @return lista de títulos das linhas.
	 */
	public LinkedList< String > getTituloLinha() {
		return tituloLinha;
	}
	
	/**
	 * Retorna o nome do arquivo de importação de dados da linha.
	 * 
	 * @return arquivo de importação de dados da linha.
	 */
	public String getTituloLinhaArquivo() {
		return tituloLinhaArquivo;
	}
	
	/**
	 * Usado para colocar uma lista de linhas.
	 * 
	 * @param linha lista de linhas
	 */
	public void setLinha( LinkedList< LinkedList > linha ) {
		this.linha = linha;
	}
	
	/**
	 * Usado para colocar o nome da matriz.
	 * 
	 * @param nome nome da matriz.
	 */
	public void setNomeMatriz( String nome ) {
		this.nomeMatriz = nome;
	}
	
	/**
	 * Usado para aumentar ou diminuir a quantidade de colunas.
	 * 
	 * @param colunas Quantidade para aumentar ou diminuir. Numero positivo aumenta, negativo diminui.
	 */
	public void setQColunas( int colunas ) {
		this.qColunas += colunas;
	}
	
	/**
	 * Usado para aumentar ou diminuir a quantidade de linhas.
	 * 
	 * @param linhas Quantidade para aumentar ou diminuir. Numero positivo aumenta, negativo diminui.
	 */
	public void setQLinhas( int linhas ) {
		this.qLinhas += linhas;
	}
	
	/**
	 * Coloca o nome do título de uma determinada coluna.
	 * 
	 * @param index índice
	 * @param titulo nome
	 */
	public void setTituloColuna( int index, String titulo ) {
		tituloColuna.set( index, titulo );
	}
	
	/**
	 * Coloca o nome do arquivo usado para importar os títulos da coluna.
	 * 
	 * @param arquivo arquivo usado para importar os títulos da coluna
	 */
	public void setTituloColunaArquivo( String arquivo ) {
		tituloColunaArquivo = arquivo;
	}
	
	/**
	 * Coloca a lista de nomes dos títulos da linha.
	 * 
	 * @param tituloLinha lista de nomes dos títulos da linha.
	 */
	public void setTituloLinha( LinkedList< String > tituloLinha ) {
		this.tituloLinha = tituloLinha;
	}
	
	/**
	 * Coloca o nome do arquivo usado para importar os títulos da linha.
	 * 
	 * @param arquivo nome do arquivo
	 */
	public void setTituloLinhaArquivo( String arquivo ) {
		tituloLinhaArquivo = arquivo;
	}
}
