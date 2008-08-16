package negocio;

import java.util.HashMap;
import java.util.LinkedList;

import persistencia.PersistenciaProjeto;
import persistencia.Projeto;
import visao.ModeloTabela;

public class ControleProjeto {
	private Projeto projeto;
	private LinkedList< Matriz > matrizes;
	private PersistenciaProjeto persistenciaProjeto;
	
	public ControleProjeto() {
		projeto = new Projeto();
		persistenciaProjeto = new PersistenciaProjeto();
	}
	
	public LinkedList< ModeloTabela > abrirProjeto( String nome ) {
		return null;
	}
	
	public void abrirProjeto() {
	}
	
	public void criarNovoProjeto() {
	}
	
	public void fecharProjeto() {
	}
	
	public String salvarProjeto() {
		return null;
	}
	
	public String adicionarLinha( String nome, String nomeMatriz ) {
		return null;
	}
	
	public String adicionarColuna( String nome, String nomeMatriz ) {
		return null;
	}
	
	public HashMap< String, LinkedList< String >> destacarElementos( int elemento, String tipo, String nomeMatriz ) {
		return null;
	}
	
	public void imprimir() {
	}
	
	public String ordenarLinha( String nomeMatriz ) {
		return null;
	}
	
	public void exportarImagem() {
	}
	
	public String ordenarColuna( String nomeMatriz ) {
		return null;
	}
	
	/**
	 * @param nomeMatriz
	 */
	public String excluirMatriz( String nomeMatriz ) {
		return null;
	}
	
	/**
	 * @param nomeMatriz
	 */
	public ModeloTabela adicionarMatriz( String nomeMatriz ) {
		return null;
	}
	
	public void sincronizarColuna() {
	}
	
	public void sincronizarLinha() {
	}
	
	/**
	 * @param nomeMatriz
	 * @param linha
	 * @param nomeNovo
	 */
	public String atualizarLinha( String nomeMatriz, int linha, String nomeNovo ) {
		return null;
	}
	
	/**
	 * @param nomeMatriz
	 * @param coluna
	 * @param nomeNovo
	 */
	public String atualizarColuna( String nomeMatriz, int coluna, String nomeNovo ) {
		return null;
	}
	
	/**
	 * @param linha
	 * @param nomeMatriz
	 */
	public String excluirLinha( int linha, String nomeMatriz ) {
		return null;
	}
	
	/**
	 * @param coluna
	 * @param nomeMatriz
	 */
	public String excluirColuna( int coluna, String nomeMatriz ) {
		return null;
	}
	
	public void adicionarColunasModelo() {
	}
	
	public void adicionarLinhasModelo() {
	}
	
	public void sincronizarMatriz() {
	}
	
	/**
	 * @param para
	 * @param de
	 * @param nomeMatriz
	 */
	public String alterarPosicaoColuna( int para, int de, String nomeMatriz ) {
		return null;
	}
	
	/**
	 * @param para
	 * @param de
	 * @param nomeMatriz
	 */
	public String alterarPosicaoLinha( int para, int de, String nomeMatriz ) {
		return null;
	}
	
	/**
	 * @param nomeMatriz
	 */
	public String resetarDestaque( String nomeMatriz ) {
		return "";
	}
	
	/**
	 * @param nome
	 */
	public String getTituloColunaArquivo( String nome ) {
		return "";
	}
	
	/**
	 * @param nome
	 */
	public String getTituloLinhaArquivo( String nome ) {
		return "";
	}
	
	/**
	 * @param nomeMatriz
	 * @param lista
	 */
	public LinkedList< String > triagemObjetos( String nomeMatriz, LinkedList< String > lista ) {
		return null;
	}
	
	/**
	 * @param linha
	 * @param coluna
	 * @param nomeMatriz
	 */
	public String setDado( int linha, int coluna, String nomeMatriz ) {
		return "";
	}
	
	/**
	 * @param nomeArquivo
	 * @param nomeMatriz
	 */
	public String setArquivoLinha( String nomeArquivo, String nomeMatriz ) {
		return null;
	}
	
	/**
	 * @param nomeArquivo
	 * @param nomeMatriz
	 */
	public String setArquivoColuna( String nomeArquivo, String nomeMatriz ) {
		return null;
	}
}
