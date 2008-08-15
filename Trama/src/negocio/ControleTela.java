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
         * @param tela    tela
         */
        public ControleTela( Tela tela ) {
        }

        /**
         * 
         * @param nome    nome
         */
        public LinkedList<ModeloTabela> abrirProjeto( String nome ) {
                return null;
        }

        /**
         * 
         * @param nome    nome
         */
        public String adicionarColuna( String nome ) {
                return "";
        }

        /**
         * 
         * @param nome    nome
         */
        public LinkedList<String> adicionarColunasModelo( String nome ) {
                return null;
        }

        /**
         * 
         * @param nome    nome
         */
        public String adicionarLinha( String nome ) {
                return "";
        }

        /**
         * 
         * @param nome    nome
         */
        public LinkedList<String> adicionarLinhasModelo( String nome ) {
                return null;
        }

        /**
         * 
         * @param nome    nome
         */
        public ModeloTabela adicionarMatriz( String nome ) {
                return null;
        }

        /**
         * 
         * @param para    para
         */
        public String alterarPosicaoColuna( String para ) {
                return "";
        }

        /**
         * 
         * @param para    para
         */
        public String alterarPosicaoLinha( String para ) {
                return "";
        }

        /**
         * 
         * @param nome    nome
         */
        public String atualizarColuna( String nome ) {
                return "";
        }

        /**
         * 
         * @param nome    nome
         */
        public String atualizarLinha( String nome ) {
                return "";
        }

        public String criarNovoProjeto() {
                return "";
        }

        public HashMap<String, LinkedList<String>> destacarElementos() {
                return null;
        }

        public String excluirColuna() {
                return "";
        }

        public String excluirLinha() {
                return "";
        }

        public String excluirMatriz() {
                return "";
        }

        public void fecharProjeto() {
        }

        public HashMap<String, LinkedList<String>> getNomeExtensoes() {
                return null;
        }

        public String ordenarColuna() {
                return "";
        }

        public String ordenarLinha() {
                return "";
        }

        public String resetarDestaque() {
                return "";
        }

        /**
         * 
         * @param nome    nome
         */
        public String salvarProjeto( String nome ) {
                return "";
        }

        public String setDado() {
                return "";
        }

        /**
         * 
         * @param nome
         * @param linha
         * @param coluna    coluna
         */
        public String setMatrizAtual( String nome, int linha, int coluna ) {
                return "";
        }

        public String sincronizarColuna() {
                return "";
        }

        public String sincronizarLinha() {
                return "";
        }

        public String sincronizarMatriz() {
                return "";
        }
}