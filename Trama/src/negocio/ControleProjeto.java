/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio;

/**
 *
 * @author Fabio
 */
public class ControleProjeto {
	public Projeto m_Projeto;
	private ProjetoDAO m_ProjetoDAO;
	private Matriz m_Matriz;
	public DadosMatriz m_DadosMatriz;
	private DadosMatriz dadosMatriz;
	private Matriz matriz;
	private Projeto projeto;
	private ProjetoDAO projetoDAO;
	private LinkedList<Matriz> matrizes;
	public ModeloTabela m_ModeloTabela;
	private PersistenciaProjeto m_PersistenciaProjeto;



	/**
	 * 
	 * @param nome
	 */
public LinkedList<ModeloTabela> abrirProjeto(String nome){

	}

	public void abrirProjeto(){

	}

	public void criarNovoProjeto(){

	}

	public void fecharProjeto(){

	}

	public String salvarProjeto(){

	}

	/**
	 * 
	 * @param nome
	 * @param nomeMatriz
	 */
	public String adicionarLinha(String nome, String nomeMatriz){

	}

	/**
	 * 
	 * @param nome
	 * @param nomeMatriz
	 */
	public String adicionarColuna(String nome, String nomeMatriz){

	}

	/**
	 * 
	 * @param elemento
	 * @param tipo
	 * @param nomeMatriz
	 */
	public HashMap<String, LinkedList<String>> destacarElementos(int elemento, String tipo, String nomeMatriz){

	}

	public void imprimir(){

	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String ordenarLinha(String nomeMatriz){

	}

	public void exportarImagem(){

	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String ordenarColuna(String nomeMatriz){

	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String excluirMatriz(String nomeMatriz){

	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public ModeloTabela adicionarMatriz(String nomeMatriz){

	}

	public void sincronizarColuna(){

	}

	public void sincronizarLinha(){

	}

	/**
	 * 
	 * @param nomeMatriz
	 * @param linha
	 * @param nomeNovo
	 */
	public String atualizarLinha(String nomeMatriz, int linha, String nomeNovo){

	}

	/**
	 * 
	 * @param nomeMatriz
	 * @param coluna
	 * @param nomeNovo
	 */
	public String atualizarColuna(String nomeMatriz, int coluna, String nomeNovo){

	}

	/**
	 * 
	 * @param linha
	 * @param nomeMatriz
	 */
	public String excluirLinha(int linha, String nomeMatriz){

	}

	/**
	 * 
	 * @param coluna
	 * @param nomeMatriz
	 */
	public String excluirColuna(int coluna, String nomeMatriz){

	}

	public void adicionarColunasModelo(){

	}

	public void adicionarLinhasModelo(){

	}

	public void sincronizarMatriz(){

	}

	/**
	 * 
	 * @param para
	 * @param de
	 * @param nomeMatriz
	 */
	public String alterarPosicaoColuna(int para, int de, String nomeMatriz){

	}

	/**
	 * 
	 * @param para
	 * @param de
	 * @param nomeMatriz
	 */
	public String alterarPosicaoLinha(int para, int de, String nomeMatriz){

	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String resetarDestaque(String nomeMatriz){
		return "";
	}

	/**
	 * 
	 * @param nome
	 */
	public String getTituloColunaArquivo(String nome){
		return "";
	}

	/**
	 * 
	 * @param nome
	 */
	public String getTituloLinhaArquivo(String nome){
		return "";
	}

	/**
	 * 
	 * @param nomeMatriz
	 * @param lista
	 */
	public LinkedList<String> triagemObjetos(String nomeMatriz, LinkedList<String> lista){
		return null;
	}

	/**
	 * 
	 * @param linha
	 * @param coluna
	 * @param nomeMatriz
	 */
	public String setDado(int linha, int coluna, String nomeMatriz){
		return "";
	}

	/**
	 * 
	 * @param nomeArquivo
	 * @param nomeMatriz
	 */
	public String setArquivoLinha(String nomeArquivo, String nomeMatriz){

	}

	/**
	 * 
	 * @param nomeArquivo
	 * @param nomeMatriz
	 */
	public String setArquivoColuna(String nomeArquivo, String nomeMatriz){

	}
}
