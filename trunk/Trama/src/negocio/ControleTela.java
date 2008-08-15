package negocio;

import java.util.HashMap;
import java.util.LinkedList;
import negocio.leitor.LeitorDeModelo;
import visao.*;

/**
 * @author Fabio
 * @version 1.0
 * @updated 03-jun-2008 12:43:56
 */
public class ControleTela {
    private ControleProjeto controleProjeto;
    private Tela tela;
    private LeitorDeModelo leitorDeModelo;
    private int colunaAtual,  linhaAtual;
    private String matrizAtual;

    /**
     *
     * @param tela
     */
    public ControleTela( Tela tela ) {
    }

    /**
     *
     * @param nome
     */
    public LinkedList<ModeloTabela> abrirProjeto( String nome ) {
        return null;
    }

    /**
     *
     * @param coluna
     */
    public String adicionarColuna( String coluna ) {
        return "";
    }

    /**
     *
     * @param nome
     */
    public LinkedList<String> adicionarColunasModelo( String nome ) {
        return null;
    }

    /**
     *
     * @param linha
     */
    public String adicionarLinha( String linha ) {
        return "";
    }

    /**
     *
     * @param nome
     */
    public LinkedList<String> adicionarLinhasModelo( String nome ) {
        return null;
    }

    /**
     *
     * @param nome
     */
    public ModeloTabela adicionarMatriz( String nome ) {
        return null;
    }

    /**
     *
     * @param de
     * @param para
     */
    public String alterarPosicaoColuna( int de, int para ) {
        return "";
    }

    /**
     *
     * @param de
     * @param para
     */
    public String alterarPosicaoLinha( int de, int para ) {
        return "";
    }

    /**
     *
     * @param nome
     */
    public String atualizarColuna( String nome ) {
        return "";
    }

    /**
     *
     * @param nome
     */
    public String atualizarLinha( String nome ) {
        return "";
    }

    public void criarNovoProjeto() {
    }

    /**
     *
     * @param elemento
     * @param tipo
     */
    public HashMap<String, LinkedList<String>> destacarElementos() {
        return null;
    }

    /**
     *
     * @param coluna
     * @param matriz
     */
    public String excluirColuna() {
        return "";
    }

    /**
     *
     * @param linha
     * @param matriz
     */
    public String excluirLinha() {
        return "";
    }

    /**
     *
     * @param nome
     */
    public String excluirMatriz() {
        return "";
    }

    public String exportarImagem() {
        return "";
    }

    public String exportarPDF() {
        return "";
    }

    public void fecharProjeto() {
    }

    public HashMap<String, LinkedList<String>> getNomeExtensoes() {
        return null;
    }

    public String imprimir() {
        return "";
    }

    /**
     *
     * @param nomeMatriz
     */
    public String ordenarColuna() {
        return "";
    }

    /**
     *
     * @param nomeMatriz
     */
    public String ordenarLinha() {
        return "";
    }

    /**
     *
     * @param nomeMatriz
     */
    public String resetarDestaque() {
        return "";
    }

    public String salvarProjeto() {
        return "";
    }

    /**
     *
     * @param nome
     */
    public void sincronizarColuna() {
    }

    /**
     *
     * @param nome
     */
    public void sincronizarLinha() {
    }

    /**
     *
     * @param nome
     */
    public String sincronizarMatriz() {
        return "";
    }

    public String setDado() {
        return null;
    }
}