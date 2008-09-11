/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package persistencia;

import java.util.LinkedList;

/**
 * @author Fabio
 */
public class DadosMatriz {
	private LinkedList< LinkedList > linha;
	private String nomeMatriz;
	private int qColunas = 1;
	private int qLinhas = 0;
	private LinkedList< String > tituloColuna;
	private String tituloColunaArquivo;
	private LinkedList< String > tituloLinha;
	private String tituloLinhaArquivo;
	
	public DadosMatriz( String nome ) {
		tituloLinha = new LinkedList< String >();
		tituloColuna = new LinkedList< String >();
		linha = new LinkedList< LinkedList >();
		this.nomeMatriz = nome;
		tituloColuna.add( "" );
	}
	
	public LinkedList< String > getLinha( int linha ) {
		return this.linha.get( linha );
	}
	
	public LinkedList< LinkedList > getLinhas() {
		return linha;
	}
	
	public String getNomeMatriz() {
		return nomeMatriz;
	}
	
	public int getQColunas() {
		return qColunas;
	}
	
	public int getQLinhas() {
		return qLinhas;
	}
	
	public LinkedList< String > getTituloColuna() {
		return tituloColuna;
	}
	
	public String getTituloColuna( int index ) {
		return tituloColuna.get( index );
	}
	
	public String getTituloColunaArquivo() {
		return tituloColunaArquivo;
	}
	
	public LinkedList< String > getTituloLinha() {
		return tituloLinha;
	}
	
	public String getTituloLinhaArquivo() {
		return tituloLinhaArquivo;
	}
	
	public void setLinha( LinkedList< LinkedList > linha ) {
		this.linha = linha;
	}
	
	public void setNomeMatriz( String nome ) {
		this.nomeMatriz = nome;
	}
	
	/**
	 * Numero positivo aumenta, negativo diminui.
	 * 
	 * @param colunas Quantidade para aumentar ou diminuir.
	 */
	public void setQColunas( int colunas ) {
		this.qColunas += colunas;
	}
	
	/**
	 * Numero positivo aumenta, negativo diminui.
	 * 
	 * @param linhas Quantidade para aumentar ou diminuir.
	 */
	public void setQLinhas( int linhas ) {
		this.qLinhas += linhas;
	}
	
	public void setTituloColuna( int index, String titulo ) {
		tituloColuna.set( index, titulo );
	}
	
	/**
	 * @param arquivo
	 */
	public void setTituloColunaArquivo( String arquivo ) {
		tituloColunaArquivo = arquivo;
	}
	
	public void setTituloLinha( LinkedList< String > tituloLinha ) {
		this.tituloLinha = tituloLinha;
	}
	
	/**
	 * @param arquivo
	 */
	public void setTituloLinhaArquivo( String arquivo ) {
		tituloLinhaArquivo = arquivo;
	}
}
