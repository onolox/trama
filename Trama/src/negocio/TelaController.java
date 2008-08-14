package negocio;

/**
 * @author Fabio
 * @version 1.0
 * @created 22-mai-2008 19:13:03
 */
public class TelaController {

	public Matriz m_Matriz;
	public Imprimir m_Imprimir;
	public ExportarImagem m_ExportarImagem;
	public ExportarPDF m_ExportarPDF;
	public AtualizarModelo m_AtualizarModelo;
	public ControleProjeto m_ControleProjeto;
	public LerDoModelo m_LerDoModelo;
	private LerDoModelo lerDoModelo;
	private AtualizarModelo atualizarModelo;
	private ControleProjeto controleProjeto;
	private ExportarImagem exportarImagem;
	private ExportarPDF exportarPDF;
	private Imprimir imprimir;
	public ModeloTabela m_ModeloTabela;
	private Tela tela;

	/**
	 * 
	 * @param tela
	 */
	public TelaController(Tela tela){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param nome
	 */
	public LinkedList<ModeloTabela> abrirProjeto(String nome){

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
	 */
	public String adicionarLinha(String nome){

	}

	/**
	 * 
	 * @param nome
	 */
	public String adicionarColuna(String nome){

	}

	/**
	 * 
	 * @param elemento
	 * @param tipo
	 * @param nomeMatriz
	 */
	public HashMap<String, LinkedList<String>> destacarElementos(int elemento, String tipo, String nomeMatriz){

	}

	public String imprimir(){

	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String ordenarLinha(String nomeMatriz){

	}

	public String exportarImagem(){

	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String ordenarColuna(String nomeMatriz){

	}

	/**
	 * 
	 * @param nome
	 */
	public String excluirMatriz(String nome){

	}

	/**
	 * 
	 * @param nome
	 */
	public ModeloTabela adicionarMatriz(String nome){

	}

	/**
	 * 
	 * @param nome
	 */
	public void sincronizarColuna(String nome){

	}

	/**
	 * 
	 * @param nome
	 */
	public void sincronizarLinha(String nome){

	}

	/**
	 * 
	 * @param nome
	 * @param linha
	 */
	public String atualizarLinha(String nome, int linha){

	}

	/**
	 * 
	 * @param nome
	 * @param coluna
	 */
	public String atualizarColuna(String nome, int coluna){

	}

	/**
	 * 
	 * @param linha
	 * @param matriz
	 */
	public String excluirLinha(int linha, String matriz){

	}

	/**
	 * 
	 * @param coluna
	 * @param matriz
	 */
	public String excluirColuna(int coluna, String matriz){

	}

	/**
	 * 
	 * @param nome
	 */
	public LinkedList<Strings> adicionarColunasModelo(String nome){

	}

	/**
	 * 
	 * @param nome
	 */
	public LinkedList<Strings> adicionarLinhasModelo(String nome){

	}

	/**
	 * 
	 * @param nome
	 */
	public String sincronizarMatriz(String nome){

	}

	public void alterarPosicaoColuna(){

	}

	/**
	 * 
	 * @param de
	 * @param para
	 */
	public String alterarPosicaoLinha(int de, int para){

	}

	/**
	 * 
	 * @param de
	 * @param para
	 */
	public String alterarPosicaoColuna(int de, int para){

	}

	public String exportarPDF(){

	}

	public HashMap<String, LinkedList<String>> getNomeExtensoes(){
		return null;
	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String resetarDestaque(String nomeMatriz){
		return "";
	}

}

/**
 * @author Fabio
 * @version 1.0
 * @updated 03-jun-2008 12:43:56
 */
public class ControleTela {

	private AtualizarModelo atualizarModelo;
	private ControleProjeto controleProjeto;
	private ExportarImagem exportarImagem;
	private ExportarPDF exportarPDF;
	private Imprimir imprimir;
	private LeitorDeModelo lerDoModelo;
	private Tela tela;
	public LeitorDeModelo m_LeitorDeModelo;
	public Imprimir m_Imprimir;
	public ExportarImagem m_ExportarImagem;
	public ExportarPDF m_ExportarPDF;
	public AtualizarModelo m_AtualizarModelo;
	public ControleProjeto m_ControleProjeto;

	public ControleTela(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param tela
	 */
	public ControleTela(Tela tela){

	}

	/**
	 * 
	 * @param nome
	 */
	public LinkedList<ModeloTabela> abrirProjeto(String nome){
		return null;
	}

	/**
	 * 
	 * @param coluna
	 */
	public String adicionarColuna(String coluna){
		return "";
	}

	/**
	 * 
	 * @param nome
	 */
	public LinkedList<String> adicionarColunasModelo(String nome){
		return null;
	}

	/**
	 * 
	 * @param linha
	 */
	public String adicionarLinha(String linha){
		return "";
	}

	/**
	 * 
	 * @param nome
	 */
	public LinkedList<Strings> adicionarLinhasModelo(String nome){
		return null;
	}

	/**
	 * 
	 * @param nome
	 */
	public ModeloTabela adicionarMatriz(String nome){
		return null;
	}

	/**
	 * 
	 * @param de
	 * @param para
	 */
	public String alterarPosicaoColuna(int de, int para){
		return "";
	}

	/**
	 * 
	 * @param de
	 * @param para
	 */
	public String alterarPosicaoLinha(int de, int para){
		return "";
	}

	/**
	 * 
	 * @param nome
	 */
	public String atualizarColuna(String nome){
		return "";
	}

	/**
	 * 
	 * @param nome
	 */
	public String atualizarLinha(String nome){
		return "";
	}

	public void criarNovoProjeto(){

	}

	/**
	 * 
	 * @param elemento
	 * @param tipo
	 */
	public HashMap<String, LinkedList<String>> destacarElementos(){
		return null;
	}

	/**
	 * 
	 * @param coluna
	 * @param matriz
	 */
	public String excluirColuna(){
		return "";
	}

	/**
	 * 
	 * @param linha
	 * @param matriz
	 */
	public String excluirLinha(){
		return "";
	}

	/**
	 * 
	 * @param nome
	 */
	public String excluirMatriz(){
		return "";
	}

	public String exportarImagem(){
		return "";
	}

	public String exportarPDF(){
		return "";
	}

	public void fecharProjeto(){

	}

	public HashMap<String, LinkedList<String>> getNomeExtensoes(){
		return null;
	}

	public String imprimir(){
		return "";
	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String ordenarColuna(){
		return "";
	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String ordenarLinha(){
		return "";
	}

	/**
	 * 
	 * @param nomeMatriz
	 */
	public String resetarDestaque(){
		return "";
	}

	public String salvarProjeto(){
		return "";
	}

	/**
	 * 
	 * @param nome
	 */
	public void sincronizarColuna(){

	}

	/**
	 * 
	 * @param nome
	 */
	public void sincronizarLinha(){

	}

	/**
	 * 
	 * @param nome
	 */
	public String sincronizarMatriz(){
		return "";
	}

	public String setDado(){

	}

}